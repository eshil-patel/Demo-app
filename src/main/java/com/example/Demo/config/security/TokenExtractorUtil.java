package com.example.Demo.config.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenExtractorUtil {

    public static String extractTokenFromSecurityContext(){
        SecurityContext context = SecurityContextHolder.getContext();
        CustomAuthentication customAuthentication = (CustomAuthentication) context.getAuthentication();
        return customAuthentication.getToken();
    }
}
