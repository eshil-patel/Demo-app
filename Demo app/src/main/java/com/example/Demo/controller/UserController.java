package com.example.Demo.controller;

import com.example.Demo.config.security.TokenExtractorUtil;
import com.example.Demo.service.UserService;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

  Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/get-name")
  public ResponseEntity<List<String>> getName(@RequestHeader("id") String id) {
    try {
      LOGGER.info("Request for id: {}", id);
      List<String> nameList = userService.getNameFromDb(id).join();
      return ResponseEntity.ok(nameList);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.internalServerError().body(List.of("Error fetching name"));
    }
  }

  @GetMapping("/get-name-platform-thread")
  public ResponseEntity<List<String>> getNamePlatformThread(@RequestHeader("id") String id) {
    try {
      LOGGER.info("Request for id: {}", id);
      List<String> nameList = userService.getNameFromDbPlatformThread(id).join();
      return ResponseEntity.ok(nameList);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.internalServerError().body(List.of("Error fetching name"));
    }
  }

  @GetMapping("/mimic-external-service")
  public ResponseEntity<String> mimicExternalService() {
    LOGGER.info("Inside external service");
    return ResponseEntity.ok("Successfully called mock external service");
  }

  @GetMapping("/context-propagation")
  public ResponseEntity<List<String>> testContextPropagation(){
    LOGGER.info("testing context propagation");
    List<String> testList = userService.testThreadContextPropagation().join();
    return ResponseEntity.ok(testList);
  }

  @GetMapping("/context-propagation-async")
  public CompletableFuture<ResponseEntity<List<String>>> testContextPropagationAsync(HttpServletRequest request){
    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String,String> headerMap = new HashMap<>();
    headerNames.asIterator().forEachRemaining(header -> headerMap.put(header,request.getHeader(header)));
    LOGGER.info("Headers {}",headerMap);
    return userService.testThreadContextPropagation().thenApply(list -> {
      return ResponseEntity.ok(list);
    });

  }
}
