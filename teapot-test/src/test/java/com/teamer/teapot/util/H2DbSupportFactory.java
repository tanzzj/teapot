package com.teamer.teapot.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * h2单元测试支持
 *
 * @author tanzj
 * @date 2022/9/17
 */
public class H2DbSupportFactory {

    private static SqlSessionFactory sqlSessionFactory;

    private static SqlSession sqlSession;

    private boolean dataInited = false;

    static {
        //测试用的mybatis-test文件
        try (Reader reader = Resources.getResourceAsReader("mybatis-test.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static H2DbSupportFactory getInstance(String[] ddlPath, String[] initDataPath) throws IOException, SQLException {

        //获取数据库建表语句脚本
        StringBuilder ddlSqlStrBuilder = new StringBuilder();
        for (String eachDdlSqlPath : ddlPath) {
            String currentSql = ReadFileUtil.readStringFromResource(eachDdlSqlPath);
            ddlSqlStrBuilder.append(currentSql);
            if (!currentSql.endsWith(";")) {
                ddlSqlStrBuilder.append(";");
            }
        }

        //获取初始化数据
        StringBuilder initDataPathStringBuilder = new StringBuilder();
        if (!Arrays.stream(initDataPath).allMatch(StringUtils::isEmpty)) {
            for (String eachInitDataPath : initDataPath) {
                String currentSql = ReadFileUtil.readStringFromResource(eachInitDataPath);
                initDataPathStringBuilder.append(currentSql);
                if (!currentSql.endsWith(";")) {
                    initDataPathStringBuilder.append(";");
                }
            }
        }

        String ddlSql = ddlSqlStrBuilder.toString();
        String initDataSql = initDataPathStringBuilder.toString();

        H2DbSupportFactory factory = new H2DbSupportFactory();
        factory.setSqlSession(getSqlSessionFactory().openSession());
        if (factory.getSqlSession() == null) {
            sqlSession = getSqlSessionFactory().openSession();
        }

        if (StringUtils.isNotBlank(ddlSql)) {
            //h2每次只能创建一张表
            String[] ddlSqlList = ddlSql.split(";;");
            for (String eachDdlSql : ddlSqlList) {
                sqlSession.getConnection().createStatement().execute(eachDdlSql);
            }
        }

        if (StringUtils.isNotBlank(initDataSql) || !factory.dataInited) {
            sqlSession.getConnection().createStatement().execute(initDataSql);
            factory.dataInited = true;
        }
        sqlSession.commit();
        ;
        return factory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        H2DbSupportFactory.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        H2DbSupportFactory.sqlSession = sqlSession;
    }

    public boolean isDataInited() {
        return dataInited;
    }

    public H2DbSupportFactory setDataInited(boolean dataInited) {
        this.dataInited = dataInited;
        return this;
    }
}
