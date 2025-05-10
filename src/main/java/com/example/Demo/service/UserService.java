package com.example.Demo.service;

import com.example.Demo.config.security.TokenExtractorUtil;
import com.example.Demo.dao.UserDao;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.example.Demo.util.Constants.DELEGATING_EXECUTOR_SERVICE;

@Service
public class UserService {

  Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private UserDao userDao;

  private ExecutorService executorService;

  public UserService(UserDao userDao,
                     @Qualifier(DELEGATING_EXECUTOR_SERVICE) ExecutorService executorService) {
    this.userDao = userDao;
    this.executorService = executorService;
  }

  public CompletableFuture<List<String>> getNameFromDb(String id) throws InterruptedException {
    return CompletableFuture.allOf(validationOne(), validationTwo(), validationThree())
        .thenCompose(ignoreResults -> userDao.getNameFromDb(id));
  }

  public CompletableFuture<List<String>> getNameFromDbPlatformThread(String id)
      throws InterruptedException {
    return CompletableFuture.allOf(validationOne(), validationTwo(), validationThree())
        .thenCompose(ignoreResults -> userDao.getNameFromDbPlatformThread(id));
  }

  public CompletableFuture<List<String>> testThreadContextPropagation(){
    return CompletableFuture.supplyAsync(() -> {
      MDC.put("userId2", UUID.randomUUID().toString());
      LOGGER.info("inside one CF, contextMap {}, security context {}", MDC.getCopyOfContextMap(),TokenExtractorUtil.extractTokenFromSecurityContext());
      return List.of("1");
    },executorService);
  }

  public CompletableFuture<Void> validationOne() {
    LOGGER.info("Validation 1 token {}", TokenExtractorUtil.extractTokenFromSecurityContext());
    return CompletableFuture.completedFuture(null);
  }

  public CompletableFuture<Void> validationTwo() {
    LOGGER.info("Validation 2 token: {}", TokenExtractorUtil.extractTokenFromSecurityContext());
    return CompletableFuture.completedFuture(null);
  }

  public CompletableFuture<Void> validationThree() throws InterruptedException {
    Thread.sleep(150);
    LOGGER.info("Validation 3 token: {}", TokenExtractorUtil.extractTokenFromSecurityContext());
    return CompletableFuture.completedFuture(null);
  }




}
