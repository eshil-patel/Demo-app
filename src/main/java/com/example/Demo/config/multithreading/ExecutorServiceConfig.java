package com.example.Demo.config.multithreading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean
    public ExecutorService executorService(){
        //JDK 17 compliant
        //return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(20));
        //JDK 21 compliant
        return new DelegatingSecurityContextExecutorService(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean("postgresExecutorService")
    public ExecutorService postgresExecutorService(){
        // JDK 17 compliant
        //return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(20));
        //JDK 21 compliant
        return new DelegatingSecurityContextExecutorService(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean("postgresExecutorServicePlatformThread")
    public ExecutorService postgresExecutorServicePlatformThreads(){
        return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(50));
    }



}
