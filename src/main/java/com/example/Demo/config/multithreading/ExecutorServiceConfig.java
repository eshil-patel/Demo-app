package com.example.Demo.config.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

@Configuration
public class ExecutorServiceConfig {

  @Bean("postgresExecutorService")
  public ExecutorService postgresExecutorService() {
    // JDK 21 compliant
    return new DelegatingSecurityContextExecutorService(
        Executors.newVirtualThreadPerTaskExecutor());
  }
  @Bean("postgresExecutorServicePlatformThread")
  public ExecutorService postgresExecutorServicePlatformThreads() {
    return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(50));
  }
}
