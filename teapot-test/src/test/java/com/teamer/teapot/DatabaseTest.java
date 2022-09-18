package com.teamer.teapot;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.fastjson.JSON;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.teamer.teapot.common.model.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class DatabaseTest {
    @Test
    public void databaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/teapot";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from t_portal_user");
        System.out.println(JSON.toJSONString(resultSet.getMetaData().unwrap(Object.class)));

    }

    @Test
    public void formatSQL() throws ClassNotFoundException, SQLException {
        String sql = "select * from t_portal_user";
        String updateSQL = "update t_project set projectName = '2323' where projectId = 'DL7ulWzq'";

        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取mysql连接
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/teapot",
                "root",
                "root"
        );
        Statement statement = connection.createStatement();

//        int result = statement.executeUpdate(updateSQL);
        boolean result = statement.execute(updateSQL);
//        System.out.println(result);

        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, "mysql");

    }

    @Test
    public void test() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("database", new Database().setEnv("123"));
        String express = "com.teamer.teapot.DatabaseTest.invokeMethod(database)";
        Object r = runner.execute(express, context, null, true, false);
    }


    public static void invokeMethod(Database database) {
        System.out.println(Optional.ofNullable(database.getDatabaseName()).orElse("hello!!!"));
    }


    @ClassRule
    public static MySQLContainer mysqld = (MySQLContainer) new MySQLContainer<>()
        .withUsername("admin")
        .withPassword("cnbp")
        .withDatabaseName("cnbp");


    @Before
    public void init() {
        mysqld.start();
    }

    @After
    public void after() {
        mysqld.stop();
    }
}