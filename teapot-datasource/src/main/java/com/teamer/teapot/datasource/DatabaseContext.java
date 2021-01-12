package com.teamer.teapot.datasource;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teamer.teapot.common.exception.BusinessException;
import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.SQLParams;
import com.teamer.teapot.datasource.enums.DatabaseTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.sql.*;
import java.util.*;

/**
 * 数据库策略模式执行上下文，用于选择应该使用的sql执行器策略
 *
 * @author tanzj
 * @date 2021/1/13
 */
@Slf4j
public class DatabaseContext {

    public static final String QUERY_DB_SQL_STATEMENT = "select databaseName,databaseId,databaseConnection,username,password,databaseType from t_database where databaseId = ?";

    private static final String MYSQL_TYPE = "mysql";

    private static final String DB_CONNECTION_KEY = "spring.datasource.url";

    private static final String DB_USERNAME_KEY = "spring.datasource.username";

    private static final String DB_PASSWORD_KEY = "spring.datasource.password";

    private static final String YML_PATH = "application.yml";


    private static final Map<String, AbstractDatabaseExecutor> DATABASE_MAP = new HashMap<>(1);

    static {
        DATABASE_MAP.put(DatabaseTypeEnum.MYSQL.getDatabaseName(), new DefaultDatabaseExecutor());
    }

    public static Result executeSql(SQLParams sqlParams) throws SQLException {
        Database database = getDatabase(sqlParams.getDatabaseId());
        if (database != null) {
            return DATABASE_MAP.get(database.getDatabaseType()).setDatabase(database).executeSql(sqlParams);
        }
        throw new BusinessException("database " + sqlParams.getDatabaseId() + " Not exist");
    }

    private static Database getDatabase(String databaseId) {
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

    public static Object loadYaml(String key) {
        Resource resource = new ClassPathResource(YML_PATH);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource);
        Properties properties = yamlPropertiesFactoryBean.getObject();
        return Optional.ofNullable(properties.get(key)).orElseThrow(RuntimeException::new);
    }

    private DatabaseContext() {
    }
}
