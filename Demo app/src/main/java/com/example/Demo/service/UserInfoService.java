package com.example.Demo.service;

import com.example.Demo.dao.UserInfoDao;
import com.example.Demo.model.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.CompletableFuture;

@Service
public class UserInfoService {


    private final UserInfoDao userInfoDao;

    public UserInfoService(UserInfoDao userInfoDao){
        this.userInfoDao = userInfoDao;
    }

    public CompletableFuture<List<UserInfo>> getUserInfo(List<String> ids, List<String> names, List<String> addresses,
                                                         List<String> dobs, List<String> gender, List<String> states,
                                                         List<String> countries){
        Map<String,List<String>> paramMap = new HashMap<>();
        addToMapIfNotNull(paramMap,"id",ids);
        addToMapIfNotNull(paramMap,"name",names);
        addToMapIfNotNull(paramMap,"address",addresses);
        addToMapIfNotNull(paramMap,"dob",dobs);
        addToMapIfNotNull(paramMap,"gender",gender);
        addToMapIfNotNull(paramMap,"state",states);
        addToMapIfNotNull(paramMap,"country",countries);
        return userInfoDao.getUserInfo(paramMap);
    }

    private void addToMapIfNotNull(Map<String,List<String>> paramMap ,String key, List<String> value){
        if(!CollectionUtils.isEmpty(value)){
            paramMap.put(key,value);
        }
    }

}
