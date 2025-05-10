package com.example.Demo.config.multithreading;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.Demo.util.Constants.POSTGRES_EXECUTOR_SERVICE;

@Configuration
public class ExecutorServiceConfigJdk21 {
    @Primary
    @Bean
    @Qualifier(POSTGRES_EXECUTOR_SERVICE)
    public ExecutorService postgres21ExecutorService() {
        // JDK 21 compliant
        return new DelegatingSecurityContextExecutorService(
                Executors.newVirtualThreadPerTaskExecutor());
    }
}
