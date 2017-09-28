package com.xhj.bms.security.util;

import com.xhj.bms.util.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Projack
 */
public class WebUtils {

    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {}
        return session;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    public static HttpServletResponse getResponse(){
        ServletRequestAttributes attrs = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return attrs.getResponse();
    }

    public static String  getUserName(HttpServletRequest request){
        return WebUtils.getTokenValue(request,0);
    }
    public static String  getCityId(HttpServletRequest request){
        return WebUtils.getTokenValue(request,1);
    }
    public static String  getTitleId(HttpServletRequest request){
        return WebUtils.getTokenValue(request,2);
    }
    public static String  getSsoToken(HttpServletRequest request){
        return WebUtils.getTokenValue(request,3);
    }
    public static String  getDeptId(HttpServletRequest request){
        return WebUtils.getTokenValue(request,4);
    }

    public static String getTokenValue(HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username")==null?"": CookieUtils.getCookie(request,"username");
        String auth_cityId = CookieUtils.getCookie(request,"auth_cityId")==null?"": CookieUtils.getCookie(request,"auth_cityId");
        String titleId = CookieUtils.getCookie(request,"titleId")==null?"": CookieUtils.getCookie(request,"titleId");
        String sso_token = CookieUtils.getCookie(request,"sso_token")==null?"": CookieUtils.getCookie(request,"sso_token");
        String deptId = CookieUtils.getCookie(request,"deptId")==null?"": CookieUtils.getCookie(request,"deptId");
        String userid = CookieUtils.getCookie(request,"userId")==null?"": CookieUtils.getCookie(request,"userId");
        String marketLevel = CookieUtils.getCookie(request,"marketLevel")==null?"": CookieUtils.getCookie(request,"marketLevel");
        String str=username+","+auth_cityId+","+titleId+","+sso_token+","+deptId+","+userid+","+marketLevel;
        return str;
    }

    public static String getTokenValue(HttpServletRequest request,int i) {
        String token = request.getParameter("sso_token");
        String[] str=token.split(",");
        if (str.length>=i+1){
            return str[i];
        }
        return "";
    }
}