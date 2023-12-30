package com.example.Demo.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomAuthenticationFilterChain extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        doFilterInternal(request,response);
        filterChain.doFilter(request,response);
    }

    public void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String tokenHeader = httpServletRequest.getHeader("token");
        // Set the token in the authentication context
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = new CustomAuthentication(tokenHeader);
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }
}
