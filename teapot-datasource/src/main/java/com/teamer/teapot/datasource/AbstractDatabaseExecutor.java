package com.teamer.teapot.datasource;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLShowTablesStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowStatement;
import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.MetaData;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.SQLParams;
import com.teamer.teapot.common.model.vo.AbstractDatabaseExecuteVO;
import com.teamer.teapot.common.model.vo.DatabaseDMLVO;
import com.teamer.teapot.common.model.vo.DatabaseQueryVO;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Slf4j
public abstract class AbstractDatabaseExecutor {

    /**
     * 加载数据源驱动
     *
     * @param dbType 数据源类型
     * @return Class
     */
    public abstract Class loadDatabaseDriver(String dbType);

    /**
     * 取得当前sql驱动的数据库
     *
     * @param databaseId 取得连接的数据库id
     * @return Database
     */
    public abstract Database getDatabase(String databaseId);

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
    public Result executeSql(SQLParams sqlParams) throws SQLException {
        Database database = getDatabase(sqlParams.getDatabaseId());
        List<AbstractDatabaseExecuteVO> responseList = new ArrayList<>();
        if (database != null) {
            loadDatabaseDriver(database.getDatabaseType());
            try (Connection connection = DriverManager.getConnection(
                    database.getDatabaseConnection(),
                    database.getUsername(),
                    database.getPassword()
            )) {
                Statement statement = connection.createStatement();
                //将sql string 取出 分析成多sqlList
                List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(sqlParams.getSql(), "mysql");
                //分别执行
                for (SQLStatement sqlStatement : sqlStatementList) {
                    try {
                        if (sqlStatement instanceof SQLSelectStatement ||
                                sqlStatement instanceof SQLShowTablesStatement ||
                                sqlStatement instanceof MySqlShowStatement
                        ) {
                            ResultSet resultSet = statement.executeQuery(sqlStatement.toLowerCaseString());
                            //取得记录的行数
                            int columnCount = resultSet.getMetaData().getColumnCount();
                            //具体数据信息列表
                            List<Map> resultList = new ArrayList<>();
                            //表元信息
                            Set<MetaData> metaDataList = new HashSet<>();
                            while (resultSet.next()) {
                                //行记录
                                Map<String, Object> dataMap = new LinkedHashMap<>();
                                for (int i = 0; i < columnCount; i++) {
                                    //key,value的形式存储查询结果
                                    dataMap.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
                                    //取得sql执行表信息
                                    ResultSetMetaData meta = resultSet.getMetaData();
                                    MetaData metaData = new MetaData()
                                            .setName(resultSet.getMetaData().getColumnName(i + 1))
                                            .setMysqlType(meta.getColumnType(i + 1));
                                    metaDataList.add(metaData);
                                }
                                resultList.add(dataMap);
                            }
                            responseList.add(
                                    new DatabaseQueryVO()
                                            .setMetaData(metaDataList)
                                            .setDataList(resultList)
                                            .setSqlType("select")
                                            .setResult("ok")
                            );
                        } else {
                            statement.execute(sqlStatement.toLowerCaseString());
                            responseList.add(new DatabaseDMLVO().setResult("ok").setSqlType("dml"));
                        }
                    } catch (SQLException e) {
                        log.error("sql exception", e);
                        responseList.add(
                                new DatabaseDMLVO()
                                        .setResult("error")
                                        .setMessage("error:" + e.getMessage())
                        );
                    }
                }
            }
            return Result.success(responseList);
        } else {
            return Result.fail("database not exist");
        }
    }


}
