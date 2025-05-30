package com.example.Demo.config.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdFilter implements Filter {
  private static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";
  private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";
  private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String correlationId = ((HttpServletRequest) request).getHeader(CORRELATION_ID_HEADER_NAME);
    if (correlationId == null || correlationId.trim().isEmpty()) {
      correlationId = UUID.randomUUID().toString();
    }
    MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
    }
  }
}
