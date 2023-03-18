package com.example.demoweb.util;

import com.example.demoweb.entity.UserContactEntity;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig {
    @Bean(name = "dataSource")
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
    @Primary
    public DataSource hikariDataSource(@Qualifier("dataSource") DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(dataSource);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("user-service-pool");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.example.demoweb.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        factoryBean.setHibernateProperties(hibernateProperties);
        return factoryBean;
    }

}
