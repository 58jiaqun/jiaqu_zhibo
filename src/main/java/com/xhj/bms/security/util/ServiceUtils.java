package com.xhj.bms.security.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.nutz.http.*;
import org.nutz.lang.Lang;

import java.util.Map;

/**
 * Created by wujian on 2017-04-20.
 */
public class ServiceUtils {

    public static JSONObject getService(String authUrl,String url,String token){
        String surl=authUrl + url;
        Request requestFind = Request.create(surl, Request.METHOD.GET);
        Header header = Header.create();
        String ip = Lang.getIP(WebUtils.getRequest());
        header.set("x-forwarded-for", ip);
        header.set("Proxy-Client-IP", ip);
        header.set("WL-Proxy-Client-IP", ip);
        requestFind.setHeader(header);
        Cookie cookie = new Cookie();
        cookie.set("sso_token", token);
        requestFind.setCookie(cookie);
        requestFind.getHeader().set("Content-Type", "application/json;charset=UTF-8");
        Response resp = Sender.create(requestFind).send();
        String u = resp.getContent();
        JSONObject ujson = JSON.parseObject(u);
        return ujson;
    }

    public static JSONObject postService(String authUrl, String url, Map<String, Object> param,String token){
        String surl=authUrl + url;
        Request requestFind =	 Request.create(surl, Request.METHOD.POST);
        Header header = Header.create();
        String ip = Lang.getIP(WebUtils.getRequest());
        header.set("x-forwarded-for", ip);
        header.set("Proxy-Client-IP", ip);
        header.set("WL-Proxy-Client-IP", ip);
        requestFind.setHeader( header);
        requestFind.setData(JSON.toJSONString(param));
        requestFind.getHeader().set("Content-Type", "application/json;charset=UTF-8");
        Response resp = Sender.create(requestFind).send();
        String u = resp.getContent();
        JSONObject ujson = JSON.parseObject(u);
        return ujson;
    }

    public static JSONObject postServiceCall(String authUrl, String url,String host, Map<String, Object> param,String authorization,String token){
        String surl=authUrl + url;
        Request requestFind =	 Request.create(surl, Request.METHOD.POST);
        Header header = Header.create();
        String ip = Lang.getIP(WebUtils.getRequest());
        header.set("Host", host);
        header.set("Accept","application/json");
        header.set("Content-Type", "application/json;charset=utf-8");
        header.set("Authorization", authorization);
        header.set("x-forwarded-for", ip);
        requestFind.setHeader( header);
        requestFind.setData(JSON.toJSONString(param));
        requestFind.getHeader().set("Content-Type", "application/json;charset=UTF-8");
        Response resp = Sender.create(requestFind).send();
        String u = resp.getContent();
        JSONObject ujson = JSON.parseObject(u);
        return ujson;
    }
}
