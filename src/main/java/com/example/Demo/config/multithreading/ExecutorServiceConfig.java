package com.example.Demo.config.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.micrometer.context.ContextExecutorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import static com.example.Demo.util.Constants.DELEGATING_EXECUTOR_SERVICE;
import static com.example.Demo.util.Constants.POSTGRES_EXECUTOR_SERVICE;

@Configuration
public class ExecutorServiceConfig {

  // bean has the same qualifier, but different methodName
  @Bean
  @Qualifier(POSTGRES_EXECUTOR_SERVICE)
  public ExecutorService postgres17ExecutorService() {
    // JDK 17 compliant
    return new DelegatingSecurityContextExecutorService(
            Executors.newFixedThreadPool(50));
  }
  @Bean("postgresExecutorServicePlatformThread")
  public ExecutorService postgresExecutorServicePlatformThreads() {
    return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(50));
  }

  @Bean(DELEGATING_EXECUTOR_SERVICE)
  public ExecutorService delegatingExecutorService(){
    return new DelegatingSecurityContextExecutorService(ContextExecutorService.wrap(Executors.newFixedThreadPool(1)));
    //return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(1));
  }
}
