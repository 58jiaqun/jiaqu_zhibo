package com.xhj.bms.controller;

import com.alibaba.druid.util.StringUtils;
import com.xhj.bms.security.util.CookieUtils;
import com.xhj.bms.security.util.WebUtils;
import org.nutz.http.Cookie;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Projack
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BasicController {

    private Log log = Logs.getLog(ApiController.class);
    @RequestMapping(value="/bmsPost/**",method = RequestMethod.POST)
    public String bmsPost(Device device, @RequestBody(required = false) Map<String,Object> params, HttpServletRequest request){
        String url = request.getServletPath().replace("/api/bmsPost","");
        Cookie cookie = new Cookie();
        Request req = Request.create(SERVICE_DOMAIN_URL + url,Request.METHOD.POST);

        if(device.isMobile() || device.isTablet()){
            Map<String,String> cookies = getAppCookies();
            params.put("tusername", cookies.get("username"));
            params.put("tuserId",cookies.get("userId"));
            params.put("tcityId",cookies.get("auth_cityId"));
            params.put("titleId",cookies.get("titleId"));
            params.put("ssoToken",cookies.get("sso_token"));
            params.put("tdeptId",cookies.get("deptId"));
            params.put("marketLevel",cookies.get("marketLevel"));
            String mac = cookies.get("mac");
            cookie.set("mac",mac);
        }else {
            params.put("tusername", CookieUtils.getCookie(request,"username"));
            params.put("tuserId",CookieUtils.getCookie(request,"userId"));
            params.put("tcityId",CookieUtils.getCookie(request,"auth_cityId"));
            params.put("titleId",CookieUtils.getCookie(request,"titleId"));
            params.put("ssoToken",CookieUtils.getCookie(request,"sso_token"));
            params.put("tdeptId",CookieUtils.getCookie(request,"deptId"));
            params.put("marketLevel",CookieUtils.getCookie(request,"marketLevel"));

        }
        cookie.set("tusername", String.valueOf(params.get("tusername")));
        cookie.set("tuserId",String.valueOf(params.get("tuserId")));
        cookie.set("tcityId",String.valueOf(params.get("tcityId")));
        cookie.set("titleId",String.valueOf(params.get("titleId")));
        cookie.set("ssoToken",String.valueOf(params.get("ssoToken")));
        cookie.set("tdeptId",String.valueOf(params.get("tdeptId")));
        cookie.set("marketLevel",String.valueOf(params.get("marketLevel")));
        Sender sender = Sender.create(req);
        sender.setInterceptor(cookie);
        req.getHeader().set("Content-Type", "application/json;charset=UTF-8");
        String json = Json.toJson(params, JsonFormat.compact());
        req.setData(json);
        try {
            Response resp = sender.send();
            return resp.getContent();
        }catch (Exception e){
            log.error(e.getMessage() + ",url:" + url +",params:" + Json.toJson(params));
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/bmsGet/**",method = RequestMethod.GET)
    public String bmsGet(Device device,HttpServletRequest request){
        String url = request.getServletPath().replace("/api/bmsGet","");
        String sso_token = WebUtils.getTokenValue(request);
        if (url.indexOf("?")>0){
            url += "&sso_token="+sso_token;
        }else{
            url += "?sso_token="+sso_token;
        }
        Request req = Request.create(SERVICE_DOMAIN_URL + url,Request.METHOD.GET);
        Cookie cookie = new Cookie();
        if(device.isMobile() || device.isTablet()){
            Map<String,String> cookies = getAppCookies();
            cookie.set("tusername", cookies.get("username"));
            cookie.set("tuserId",cookies.get("userId"));
            cookie.set("tcityId",cookies.get("auth_cityId"));
            cookie.set("titleId",cookies.get("titleId"));
            cookie.set("ssoToken",cookies.get("sso_token"));
            cookie.set("tdeptId",cookies.get("deptId"));
            cookie.set("marketLevel",cookies.get("marketLevel"));
            String mac = cookies.get("mac");
            cookie.set("mac",mac);
        }else {
            cookie.set("tusername", CookieUtils.getCookie(request,"username"));
            cookie.set("tuserId",CookieUtils.getCookie(request,"userId"));
            cookie.set("tcityId",CookieUtils.getCookie(request,"auth_cityId"));
            cookie.set("titleId",CookieUtils.getCookie(request,"titleId"));
            cookie.set("ssoToken",CookieUtils.getCookie(request,"sso_token"));
            cookie.set("tdeptId",CookieUtils.getCookie(request,"deptId"));
            cookie.set("marketLevel",CookieUtils.getCookie(request,"marketLevel"));
        }
        Sender sender = Sender.create(req);
        sender.setInterceptor(cookie);
        req.getHeader().set("Content-Type", "application/json;charset=UTF-8");
        try {
            Response resp = sender.send();
            return resp.getContent();
        }catch (Exception e){
            log.error(e.getMessage() + ",url:" + url);
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getAppCookies(){
        String cookies = WebUtils.getRequest().getHeader("Cookies");

        Map<String, String> cookieMap = new HashMap<>();
        if(!StringUtils.isEmpty(cookies)){
            String[] ss = cookies.split(";");
            for(String s :ss){
                String[] kv = s.split("=");
                cookieMap.put(kv[0], kv[1]);

            }
        }

        return cookieMap;
    }

    //post断路器回调方法的参数可以和入口方法一致
    public String bmsPostError(Map<String,Object> params,HttpServletRequest request){return "网络异常，请重试...";}

    //get断路器回调方法的参数可以和入口方法一致
    public String bmsGetError(HttpServletRequest request){return "网络异常，请重试...";}

}