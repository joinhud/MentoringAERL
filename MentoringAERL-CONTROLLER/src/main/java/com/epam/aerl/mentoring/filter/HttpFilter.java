package com.epam.aerl.mentoring.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HttpFilter implements Filter {
  private static final Logger LOG = LogManager.getLogger(HttpFilter.class);
  private static final String USER_AGENT_HEADER = "User-Agent";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      LOG.info("Requested URL: " + ((HttpServletRequest)request).getRequestURL().toString());
      LOG.info("User Agent: " + ((HttpServletRequest) request).getHeader(USER_AGENT_HEADER));
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }
}
