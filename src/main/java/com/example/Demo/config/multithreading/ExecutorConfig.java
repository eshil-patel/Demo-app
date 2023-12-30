package com.example.Demo.config.multithreading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.Executor;
@Configuration
public class ExecutorConfig {

    /**
     * Probably no need for this, given that TaskExecutor extends this class but leaving in anyway for now
     * */

    @Bean
    public Executor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(25);
        // need to do this !
        executor.initialize();
        //JDK 21 compliant (I think)
        //SimpleAsyncTaskExecutor executor1 = new SimpleAsyncTaskExecutor("Rest Executor Virtual");
        //executor1.setVirtualThreads(true);
        // this passes along the SecurityContext
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }
}
