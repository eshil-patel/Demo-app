package com.example.Demo.config.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {

  @Bean
  public JdbcTemplate jdbcTemplate(@Qualifier("jdbcDatasource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  @Qualifier("jdbcDatasource")
  @ConditionalOnExpression("${postgres.enabled:true}")
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
    config.setUsername("postgres");
    config.setPassword("postgres");
    config.setMaximumPoolSize(5);
    HikariDataSource dataSource = new HikariDataSource(config);
    return dataSource;
  }

  @Bean
  @Qualifier("jdbcDatasource")
  @ConditionalOnExpression("!${postgres.enabled:true}")
  public DataSource h2dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.h2.Driver");
    config.setJdbcUrl("jdbc:h2:mem:example-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    config.setUsername("sa");
    config.setPassword("sa");
    HikariDataSource dataSource = new HikariDataSource(config);
    return dataSource;
  }
}
