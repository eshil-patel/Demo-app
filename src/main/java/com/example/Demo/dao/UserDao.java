package com.example.Demo.dao;

import com.example.Demo.config.security.TokenExtractorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class UserDao {

    Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private final BaseDao baseDao;


    public UserDao(final BaseDao baseDao){
        this.baseDao = baseDao;
    }

    public CompletableFuture<List<String>> getNameFromDb(String id){
        LOGGER.info("DAO Token: {}",TokenExtractorUtil.extractTokenFromSecurityContext());
        String sql = """
                SELECT NAME from USERS where id = ?
                """;
        return baseDao.query(
                sql,
                new Object[]{id},
                new int[]{Types.VARCHAR},
                (rs,i) -> rs.getString("NAME")
        );
    }

    public CompletableFuture<List<String>> getNameFromDbPlatformThread(String id){
        LOGGER.info("DAO Token: {}",TokenExtractorUtil.extractTokenFromSecurityContext());
        String sql = """
                SELECT NAME from USERS where id = ?
                """;
        return baseDao.queryPlatformThread(
                sql,
                new Object[]{id},
                new int[]{Types.VARCHAR},
                (rs,i) -> rs.getString("NAME")
        );
    }


}
