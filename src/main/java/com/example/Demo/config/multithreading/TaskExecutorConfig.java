package com.example.Demo.config.multithreading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
@EnableAsync
public class TaskExecutorConfig {

  // SimpleAsyncTaskScheduler will align with JDK 21 Virtual threads in Spring 6.1 (check spring
  // boot)
  // SimpleAsyncTaskExecutor creates a new thread per call, this is inefficient except in JDK 21
  @Bean
  public TaskExecutor taskExecutor() {
    // IF you want more configs, use the following
    // SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor("Virtual thread
    // simple sync task executor");
    // simpleAsyncTaskExecutor.setVirtualThreads(true);
    VirtualThreadTaskExecutor executor = new VirtualThreadTaskExecutor("Virtual thread executor-");
    return new DelegatingSecurityContextAsyncTaskExecutor(executor);
  }
}
