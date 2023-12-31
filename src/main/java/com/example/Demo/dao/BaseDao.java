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

    public ExecutorService executorServicePlatformThread;

    public JdbcTemplate jdbcTemplate;

    public Semaphore postgresSemaphore;

    public BaseDao(@Qualifier("postgresExecutorService")ExecutorService executorService,
                   @Qualifier("postgresExecutorServicePlatformThread")ExecutorService executorServicePlatformThread,
                   JdbcTemplate jdbcTemplate,
                   Semaphore semaphore){
        this.executorService = executorService;
        this.executorServicePlatformThread = executorServicePlatformThread;
        this.jdbcTemplate = jdbcTemplate;
        this.postgresSemaphore = semaphore;
    }

    public <T> CompletableFuture<List<T>> query(String sql, Object[] params, int[] types, RowMapper<T> rowMapper){
        return queryDbWithSemaphore(() -> jdbcTemplate.query(sql,params,types,rowMapper));
    }

    public <T> CompletableFuture<List<T>> queryPlatformThread(String sql, Object[] params, int[] types, RowMapper<T> rowMapper){
        return CompletableFuture.supplyAsync(() -> jdbcTemplate.query(sql,params,types,rowMapper),executorServicePlatformThread);
    }

    private <T> CompletableFuture<T> queryDbWithSemaphore(Supplier<T> supplier){
        try{
            postgresSemaphore.acquire();
            return CompletableFuture.supplyAsync(supplier,executorService);
        } catch (InterruptedException e){
            return CompletableFuture.completedFuture(null);
        }finally{
            postgresSemaphore.release();
        }
    }
}
