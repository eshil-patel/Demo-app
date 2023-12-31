package com.example.Demo.config.multithreading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
@EnableAsync
public class TaskExecutorConfig {

    // SimpleAsyncTaskScheduler will align with JDK 21 Virtual threads in Spring 6.1 (check spring boot)
    // SimpleAsyncTaskExecutor creates a new thread per call, this is inefficient except in JDK 21
    /*@Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(25);
        // need to do this !
        executor.initialize();
        //JDK 21 compliant
        //SimpleAsyncTaskExecutor executor1 = new SimpleAsyncTaskExecutor("Rest Executor Virtual");
        //executor1.setVirtualThreads(true);
        //return new DelegatingSecurityContextAsyncTaskExecutor(executor1);
        // this passes along the SecurityContext
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }*/

    /*@Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor()
    }*/
}
