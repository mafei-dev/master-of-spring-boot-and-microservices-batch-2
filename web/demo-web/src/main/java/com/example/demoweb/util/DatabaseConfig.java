package com.example.demoweb.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/batch-2-user-db?createDatabaseIfNotExist=true");
//        mysqlDataSource.setDatabaseName("batch-2-user-db");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("mafei");
        System.out.println("mysqlDataSource = " + mysqlDataSource);
        return mysqlDataSource;
    }
}
