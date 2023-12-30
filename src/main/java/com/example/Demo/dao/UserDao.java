package com.example.Demo.dao;

import com.example.Demo.config.security.TokenExtractorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Repository
public class UserDao {

    Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    public ExecutorService executorService;

    public JdbcTemplate jdbcTemplate;

    public UserDao(@Qualifier("postgresExecutorService")ExecutorService executorService,
                   JdbcTemplate jdbcTemplate){
        this.executorService = executorService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public CompletableFuture<List<String>> getNameFromDb(String id){
        LOGGER.info("DAO Token: {}",TokenExtractorUtil.extractTokenFromSecurityContext());
        String sql = """
                SELECT NAME from USERS where id = ?
                """;
        return CompletableFuture.supplyAsync(() ->
                jdbcTemplate.query(
                sql,
                new Object[]{id},
                new int[]{Types.VARCHAR},
                (rs,i) -> rs.getString("NAME"))
                ,executorService);
    }
}
