package com.example.Demo.config.rest;

import com.example.Demo.config.security.TokenExtractorUtil;
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    String token = TokenExtractorUtil.extractTokenFromSecurityContext();
    System.out.println("Token: " + token);
    request.getHeaders().add("token", token);
    return execution.execute(request, body);
  }
}
