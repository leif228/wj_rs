package com.wujie.mc.app.framework.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
public class CorsFilter extends OncePerRequestFilter {
    public CorsFilter() {
//        log.info("SimpleCORSFilter init");
    }

//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//
//        chain.doFilter(req, res);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Access-Control-Allow-Origin: 指定授权访问的域
        response.addHeader("Access-Control-Allow-Origin", "*");  //此优先级高于@CrossOrigin配置

        // Access-Control-Allow-Methods: 授权请求的方法（GET, POST, PUT, DELETE，OPTIONS等)
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "*");

//        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        filterChain.doFilter(request, response);
    }

//    @Override
//    public void init(FilterConfig filterConfig) {
//    }

//    @Override
//    public void destroy() {
//    }
}
