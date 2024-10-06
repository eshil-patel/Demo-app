package com.example.Demo.controller;

import com.example.Demo.model.UserInfo;
import com.example.Demo.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class UserInfoController {

    Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }

    @QueryMapping
    public List<UserInfo> getUserInfo(@Argument List<String> ids, @Argument List<String> names,
                                             @Argument List<String> addresses,
                                             @Argument List<String> dobs, @Argument List<String> gender,
                                             @Argument List<String> states,
                                             @Argument List<String> countries){
        LOGGER.info("Fetching request through GraphQL endpoint");
        return userInfoService.getUserInfo(ids,names,addresses,dobs,gender,states,countries).join();
    }


}
