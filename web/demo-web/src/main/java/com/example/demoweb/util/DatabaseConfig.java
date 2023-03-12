package com.example.demoweb.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private DatabaseConfig() {
    }

    public static DataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/batch-2-user-db?createDatabaseIfNotExist=true");
//        mysqlDataSource.setDatabaseName("batch-2-user-db");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("mafei");
        return mysqlDataSource;
    }
}
