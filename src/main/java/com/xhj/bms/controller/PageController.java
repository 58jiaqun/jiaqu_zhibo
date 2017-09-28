package com.xhj.bms.controller;

import com.xhj.bms.security.util.Cache;
import com.xhj.bms.security.util.CacheManager;
import com.xhj.bms.security.util.CookieUtils;
import org.nutz.json.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
@Controller
@RequestMapping(value="/page")
public class PageController {

    @RequestMapping(value="/menu/**")
    public String menu(HttpServletRequest request){
        String url = request.getServletPath().replace("/page/menu","");
        this.setBkeys(request);
        return "/admin/pages"+url;
    }

    @RequestMapping(value="/**")
    public String bmsPage(HttpServletRequest request){
        String url = request.getServletPath().replace("/page","");
        this.setBkeys(request);
        return "/admin/pages"+url;
    }

    public static void setBkeys(HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username");
        String titleId = CookieUtils.getCookie(request,"titleId");
        Cache cacheInfo= CacheManager.getCacheInfo(username+"_"+titleId+"_bms_bntKey");
        List<String> buttons = new ArrayList<>();
        String s= Json.toJson(buttons);
        if(cacheInfo!=null&&!CacheManager.cacheExpired(cacheInfo)){
        //if(cacheInfo!=null){
            s = cacheInfo.getValue().toString();
        }
       // System.out.println("\n\n\n\n\n"+s);
        request.setAttribute("btnkeys",s);
    }


}
