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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demoweb.repository.user",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
public class UserDbConfig {

    @Bean(name = "hikariUserDataSource")
    public DataSource hikariDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/batch-2-user-db?createDatabaseIfNotExist=true");
//        mysqlDataSource.setDatabaseName("batch-2-user-db");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("mafei");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(mysqlDataSource);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("user-service-pool");
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "userEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("hikariUserDataSource") DataSource dataSource) {
        Map<String, String> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.example.demoweb.entity.user")
                .properties(hibernateProperties)
                .build();
    }

    @Bean(name = "userTransactionManager")
    @Primary
    public PlatformTransactionManager userPlatformTransactionManager(
            @Qualifier("userEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean managerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(managerFactoryBean.getObject()));
    }

}
