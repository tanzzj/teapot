package com.teamer.teapot.project.database.service.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.teamer.teapot.common.model.*;
import com.teamer.teapot.common.model.vo.AbstractDatabaseExecuteVO;
import com.teamer.teapot.common.model.vo.DatabaseDMLVO;
import com.teamer.teapot.common.model.vo.DatabaseQueryVO;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.datasource.manager.dao.DatabaseManagementDAO;
import com.teamer.teapot.project.database.dao.ProjectDBDAO;
import com.teamer.teapot.project.database.service.ProjectDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Slf4j
@Service
public class ProjectDBServiceImpl implements ProjectDBService {

    @Autowired
    ProjectDBDAO projectDBDAO;
    @Autowired
    DatabaseManagementDAO databaseManagementDAO;

    /**
     * add a project related database instance
     *
     * @param projectDatabase (
     *                        projectId
     *                        databaseId
     *                        )
     * @return Result
     */
    @Override
    public Result addProjectDatabaseInstance(ProjectDatabase projectDatabase) {
        //todo check id exist
        projectDatabase.setDatabaseId(UUIDFactory.getShortUUID());
        projectDBDAO.addProjectDatabaseInstance(projectDatabase);
        return Result.success(new ProjectDatabase().setDatabaseId(projectDatabase.getDatabaseId()));
    }

    /**
     * query project related database instances list
     *
     * @param project (projectId)
     * @return List<ProjectDatabase>
     */
    @Override
    public Result queryProjectDataBaseList(Project project) {
        List<ProjectDatabase> projectDatabaseList = projectDBDAO.queryProjectDataBaseList(project);
        return Result.success(projectDatabaseList);
    }

    /**
     * 执行目标db脚本
     * 目前支持查询语句
     *
     * @param sqlParams (
     *                  sql,
     *                  databaseId
     *                  )
     * @return Result
     */
    @Override
    public Result executeSQL(SQLParams sqlParams) throws ClassNotFoundException, SQLException {
        Database database = databaseManagementDAO.queryDatabaseDetail(new Database().setDatabaseId(sqlParams.getDatabaseId()));
        List<AbstractDatabaseExecuteVO> responseList = new ArrayList<>();
        if (database != null) {
            //现在仅支持mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取mysql连接
            try (Connection connection = DriverManager.getConnection(
                    database.getDatabaseConnection(),
                    database.getUsername(),
                    database.getPassword()
            )) {
                Statement statement = connection.createStatement();
                //将sql string 取出 分析成多sqlList
                List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(sqlParams.getSql(), "mysql");
                for (SQLStatement sqlStatement : sqlStatementList) {
                    try {
                        if (sqlStatement instanceof SQLSelectStatement) {
                            ResultSet resultSet = statement.executeQuery(sqlStatement.toLowerCaseString());
                            int columnCount = resultSet.getMetaData().getColumnCount();
                            //具体数据信息列表
                            List<List> resultList = new ArrayList<>();
                            List<MetaData> metaDataList = new ArrayList<>();
                            while (resultSet.next()) {
                                //行记录
                                List<Object> resultListScope = new ArrayList<>();
                                for (int i = 0; i < columnCount; i++) {
                                    resultListScope.add(resultSet.getObject(i + 1));
                                    //取得sql执行表信息
                                    ResultSetMetaData meta = resultSet.getMetaData();
                                    MetaData metaData = new MetaData()
                                            .setName(resultSet.getMetaData().getColumnName(i + 1))
                                            .setMysqlType(meta.getColumnType(i + 1));
                                    metaDataList.add(metaData);
                                }
                                resultList.add(resultListScope);
                            }
                            responseList.add(
                                    new DatabaseQueryVO()
                                            .setMetaData(metaDataList)
                                            .setDataList(resultList)
                            );
                        } else {
                            statement.execute(sqlStatement.toLowerCaseString());
                            responseList.add(new DatabaseDMLVO().setResult("ok"));
                        }
                    } catch (SQLException e) {
                        log.error("获取连接失败", e);
                        responseList.add(new DatabaseDMLVO().setResult("error" + e.getMessage()));
                    }
                }
            }
            return Result.success("success", responseList);
        } else {
            return Result.fail("database not exist");
        }
    }
}