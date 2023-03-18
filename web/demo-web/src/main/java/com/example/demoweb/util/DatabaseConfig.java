package com.example.demoweb.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
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
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        factoryBean.setHibernateProperties(hibernateProperties);
        return factoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager platformTransactionManager(LocalSessionFactoryBean factoryBean) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(factoryBean.getObject());
        return transactionManager;
    }
}
