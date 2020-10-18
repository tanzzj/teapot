package com.teamer.teapot.datasource;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teamer.teapot.common.model.Database;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.sql.*;
import java.util.*;

/**
 * 默认mysql执行器
 *
 * @author tanzj
 */
@Slf4j
public class DefaultDatabaseExecutor extends AbstractDatabaseExecutor {

    public static final String QUERY_DB_SQL_STATEMENT = "select databaseName,databaseId,databaseConnection,username,password,databaseType from t_database where databaseId = ?";

    private static final String MYSQL_TYPE = "mysql";

    private static final String DB_CONNECTION_KEY = "spring.datasource.url";

    private static final String DB_USERNAME_KEY = "spring.datasource.username";

    private static final String DB_PASSWORD_KEY = "spring.datasource.password";

    private static final String YML_PATH = "application.yml";

    @Override
    public Class loadDatabaseDriver(String dbType) {
        try {
            return Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("com.mysql.cj.jdbc.Driver not found");
            return null;
        }
    }

    @Override
    public Database getDatabase(String databaseId) {
        String dbConnection = loadYaml(DB_CONNECTION_KEY).toString();
        String username = loadYaml(DB_USERNAME_KEY).toString();
        String password = loadYaml(DB_PASSWORD_KEY).toString();
        try (Connection connection = DriverManager.getConnection(dbConnection, username, password)) {
            List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(QUERY_DB_SQL_STATEMENT, MYSQL_TYPE);
            try (PreparedStatement statement = connection.prepareStatement(sqlStatementList.get(0).toLowerCaseString())) {
                statement.setString(1, databaseId);
                ResultSet resultSet = statement.executeQuery();
                Map<String, Object> dataMap = new LinkedHashMap<>();
                while (resultSet.next()) {
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        dataMap.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
                    }
                    return JSON.toJavaObject(new JSONObject(dataMap), Database.class);
                }
            }
        } catch (SQLException e) {
            log.error("getDatabase error: {}", e.getMessage());
        }
        return null;
    }


    public Object loadYaml(String key) {
        Resource resource = new ClassPathResource(YML_PATH);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource);
        Properties properties = yamlPropertiesFactoryBean.getObject();
        return Optional.ofNullable(properties.get(key)).orElseThrow(RuntimeException::new);
    }
}
