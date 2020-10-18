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
     * sql 执行成功
     */
    public static final String SQL_OPERATION_SUCCESS = "ok";

    /**
     * sql 执行失败
     */
    public static final String SQL_OPERATION_FAIL = "error";


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
        //返回列表
        List<AbstractDatabaseExecuteVO> responseList = new ArrayList<>();
        //判断数据库是否存在
        Database database = getDatabase(sqlParams.getDatabaseId());
        if (database == null) {
            return Result.fail("database not exist");
        }
        //加载数据库驱动
        loadDatabaseDriver(database.getDatabaseType());
        try (
                Connection connection = DriverManager.getConnection(
                        database.getDatabaseConnection(),
                        database.getUsername(),
                        database.getPassword()
                )
        ) {
            //取消自动提交，保证所有sql执行为一个原子；
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            //将sql string 取出 ，分析成多sqlList
            List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(sqlParams.getSql(), database.getDatabaseType());
            //分别执行
            for (SQLStatement sqlStatement : sqlStatementList) {
                responseList.add(this.execute(sqlStatement, connection));
            }
            //存在任何失败的情况
            if (responseList.stream().anyMatch(sqlExecuteResult -> SQL_OPERATION_FAIL.equals(sqlExecuteResult.getResult()))) {
                return Result.fail("execution fail", responseList);
            }
            //一并提交
            connection.commit();
            return Result.success(responseList);
        }
    }

    /**
     * 执行sql方法
     *
     * @param sqlStatement sql语句
     * @param connection   传入的connection
     * @return AbstractDatabaseExecuteVO
     * @throws SQLException sql异常
     */
    protected AbstractDatabaseExecuteVO execute(SQLStatement sqlStatement, Connection connection) throws SQLException {
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            if (sqlStatement instanceof SQLSelectStatement ||
                    sqlStatement instanceof SQLShowTablesStatement ||
                    sqlStatement instanceof MySqlShowStatement
            ) {
                resultSet = statement.executeQuery(sqlStatement.toLowerCaseString());
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
                return new DatabaseQueryVO()
                        .setMetaData(metaDataList)
                        .setDataList(resultList)
                        .setSqlType("select")
                        .setResult(SQL_OPERATION_SUCCESS);
            } else {
                statement.execute(sqlStatement.toLowerCaseString());
                return new DatabaseDMLVO().setResult(SQL_OPERATION_SUCCESS).setSqlType("dml");
            }
        } catch (SQLException e) {
            log.error("sql exception:{}", e.getMessage());
            connection.rollback();
            return new DatabaseDMLVO()
                    .setResult(SQL_OPERATION_FAIL)
                    .setMessage("error sql :" + sqlStatement.toLowerCaseString() + "error message:" + e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }


}
