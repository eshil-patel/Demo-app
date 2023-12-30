package com.example.Demo.service;

import com.example.Demo.config.security.TokenExtractorUtil;
import com.example.Demo.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public CompletableFuture<List<String>> getNameFromDb(String id) throws InterruptedException {
        return CompletableFuture.allOf(validationOne(),
                validationTwo(),
                validationThree()
                ).thenCompose(
                        ignoreResults -> userDao.getNameFromDb(id)
        );
    }

    public CompletableFuture<Void> validationOne(){
        LOGGER.info("Validation 1 token {}", TokenExtractorUtil.extractTokenFromSecurityContext());
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<Void> validationTwo(){
        LOGGER.info("Validation 2 token: {}", TokenExtractorUtil.extractTokenFromSecurityContext());
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<Void> validationThree() throws InterruptedException {
        Thread.sleep(20);
        LOGGER.info("Validation 3 token: {}", TokenExtractorUtil.extractTokenFromSecurityContext());
        return CompletableFuture.completedFuture(null);
    }
}