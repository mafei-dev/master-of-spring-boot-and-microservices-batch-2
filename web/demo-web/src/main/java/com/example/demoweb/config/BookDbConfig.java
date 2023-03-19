package com.example.demoweb.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demoweb.repository.book",
        entityManagerFactoryRef = "bookEntityManagerFactory",
        transactionManagerRef = "bookTransactionManager"
)
public class BookDbConfig {


    @Bean(name = "hikariBookDataSource")
    public DataSource hikariBookDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/batch-2-boot-db?createDatabaseIfNotExist=true");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("mafei");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(mysqlDataSource);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("user-service-pool");
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "bookEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("hikariBookDataSource") DataSource dataSource) {
        Map<String, String> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.example.demoweb.entity.book")
                .properties(hibernateProperties)
                .build();
    }

    @Bean(name = "bookTransactionManager")
    public PlatformTransactionManager bookTransactionManager(
            @Qualifier("bookEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean managerFactoryBean) {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(Objects.requireNonNull(managerFactoryBean.getObject()));
//        jpaTransactionManager.setJpaProperties(hibernateProperties);
        return jpaTransactionManager;
    }

}
