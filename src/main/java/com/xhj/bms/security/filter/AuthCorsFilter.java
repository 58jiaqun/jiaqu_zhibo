package com.xhj.bms.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


@Component
public class AuthCorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-credentials","true");
        if(request.getHeader("Origin")==null){
        	response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
        }else{
        	response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Cache-Control", "no-cache");//or no-cache
        response.setHeader("Pragrma", "no-cache"); 
        response.setDateHeader("Expires", 0);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}