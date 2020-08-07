package com.teamer.teapot;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.sql.*;

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

}
