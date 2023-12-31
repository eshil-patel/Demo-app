package com.example.Demo.config.multithreading;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;

@Configuration
public class SemaphoreConfig {

    @Value("${postgres.connection.max.size}")
    int postgresConnectionMaxSize;

    @Bean
    public Semaphore postgresSemaphore(){
        // make a fair semaphore, limiting the number of connections to postgres based on config value 
        return new Semaphore(postgresConnectionMaxSize,true);
    }
}
