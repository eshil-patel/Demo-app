package com.example.Demo.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

@Repository
public class BaseDao {

    public ExecutorService executorService;

    public JdbcTemplate jdbcTemplate;

    public Semaphore postgresSemaphore;

    public BaseDao(@Qualifier("postgresExecutorService")ExecutorService executorService,
                   JdbcTemplate jdbcTemplate){
        this.executorService = executorService;
        this.jdbcTemplate = jdbcTemplate;
        // make a fair semaphore, limiting postgres connections to 20
        this.postgresSemaphore = new Semaphore(20,true);
    }

    public <T> CompletableFuture<List<T>> query(String sql, Object[] params, int[] types, RowMapper<T> rowMapper){
        return queryDbWithSemaphore(() -> jdbcTemplate.query(sql,params,types,rowMapper));
    }

    private <T> CompletableFuture<T> queryDbWithSemaphore(Supplier<T> supplier){
        try{
            postgresSemaphore.acquire();
            return CompletableFuture.supplyAsync(supplier);
        } catch (InterruptedException e){
            return CompletableFuture.completedFuture(null);
        }finally{
            postgresSemaphore.release();
        }
    }
}
