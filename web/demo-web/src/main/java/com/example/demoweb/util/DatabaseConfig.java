package com.example.demoweb.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/batch-2-user-db?createDatabaseIfNotExist=true");
//        mysqlDataSource.setDatabaseName("batch-2-user-db");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("mafei");
        System.out.println("mysqlDataSource = " + mysqlDataSource);
        return mysqlDataSource;
    }

    @Bean(name = "hikariDataSource")
    public DataSource hikariDataSource(@Qualifier("dataSource") DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(dataSource);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("user-service-pool");
        return new HikariDataSource(hikariConfig);
    }
}
