package com.example.Demo.controller;

import com.example.Demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/get-name")
    public ResponseEntity<List<String>> getName(@RequestHeader("id") String id) throws InterruptedException {
        try{
            LOGGER.info("Request for id: {}",id);
            List<String> nameList = userService.getNameFromDb(id).join();
            return ResponseEntity.ok(nameList);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(List.of("Error fetching name"));
        }
    }
}