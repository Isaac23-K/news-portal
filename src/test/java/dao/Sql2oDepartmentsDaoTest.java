package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

class Sql2oDepartmentsDaoTest {
    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oNewsDao sql2oNewsDao;
    private static Sql2oUserDao sql2oUserDao;
    private static Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/company_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "isaac", "kaptoge");
        sql2oDepartmentsDao = new Sql2oDepartmentsDao(sql2o);
        sql2oNewsDao = new Sql2oNewsDao(sql2o);
        sql2oUserDao = new Sql2oUserDao(sql2o);
        System.out.println("connected to database");
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() throws  Exception{
        sql2oDepartmentsDao.clearAll();
        sql2oNewsDao.clearAll();
        sql2oUserDao.clearAll();
    }
    @AfterAll
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection is closed");
    }

    @Test
    void adding_() {
    }
}