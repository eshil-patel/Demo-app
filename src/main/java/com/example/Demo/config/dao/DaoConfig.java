package com.example.Demo.config.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("jdbcDatasource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("jdbcDatasource")
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMaximumPoolSize(5);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

}
