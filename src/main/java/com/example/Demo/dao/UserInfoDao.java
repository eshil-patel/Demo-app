package com.example.Demo.dao;

import com.example.Demo.model.Gender;
import com.example.Demo.model.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class UserInfoDao {

    private final BaseDao baseDao;

    public UserInfoDao(BaseDao baseDao){
        this.baseDao = baseDao;
    }

    public CompletableFuture<List<UserInfo>> getUserInfo(Map<String,List<String>> argumentMap){
        String whereClause = argumentMap.entrySet().stream()
                .map(e -> String.format("%s in (%s)",e.getKey(), Collections.nCopies(e.getValue().size(),"?")
                        .stream().collect(Collectors.joining(","))))
                .collect(Collectors.joining(" AND "));
        String sql = String.format("""
                SELECT ID,NAME,ADDRESS,DOB,GENDER,STATE,COUNTRY
                FROM USER_INFO
                WHERE %s
                """, whereClause);
        Object[] params = argumentMap.entrySet().stream().flatMap(e ->
                e.getValue().stream()).toArray();
        int[] types = ArrayUtils.toPrimitive(Collections.nCopies(params.length,Types.VARCHAR).toArray(new Integer[0]));
        return baseDao.query(sql,
                params,
                types,
                (rs,i) -> new UserInfo(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("DOB"),
                        Gender.valueOf(rs.getString("GENDER")),
                        rs.getString("STATE"),
                        rs.getString("COUNTRY")
                ));
    }
}
