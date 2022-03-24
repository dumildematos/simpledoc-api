package com.tcc.simpledocapi.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest requestToUse = (HttpServletRequest)servletRequest;
        HttpServletResponse responseToUse = (HttpServletResponse)servletResponse;

        responseToUse.setHeader("Access-Control-Allow-Origin",requestToUse.getHeader("Origin"));
        filterChain.doFilter(requestToUse,responseToUse);
    }
}
