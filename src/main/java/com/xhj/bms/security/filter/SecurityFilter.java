package com.xhj.bms.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xhj.bms.security.util.*;
import org.nutz.http.*;
import org.nutz.http.Request.METHOD;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class SecurityFilter implements Filter {

	private String authUrl= (String) CookieUtils.props.get("authcenter.url");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res=(HttpServletResponse)response;
		HttpServletRequest req=(HttpServletRequest)request;
		res.setHeader("Cache-Control", "no-cache");//or no-cache
		res.setHeader("Pragrma", "no-cache");
		res.setDateHeader("Expires", 0);
		String acount =  CookieUtils.getCookie(req, "username");
		String token =  CookieUtils.getCookie(req, "sso_token");
		String titleId =  CookieUtils.getCookie(req, "titleId");
		String deptId =  CookieUtils.getCookie(req, "deptId");
		//判断用户是否登录
		String url = "/user/isAuth/"+acount;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		String  status = ujson.get("status").toString();
		//登录成功或有效
		if("200".equals(status)){
			if(acount==null || token==null){
				res.sendRedirect("/login/showLogin");
				return;
			}else{

				//按钮key
				Cache cacheInfo=CacheManager.getCacheInfo(acount+"_"+titleId+"_bms_bntKey");
				if(cacheInfo==null||CacheManager.cacheExpired(cacheInfo)){
					//String bntkey=cacheInfo.getValue().toString();
					url = "/user/getUserButtonKeys/" + acount + "/BMS/" + deptId + "/" + titleId;
					ujson = ServiceUtils.getService(authUrl, url, token);
					String keyStatus = ujson.get("status").toString();
					if ("200".equals(keyStatus)) {
						long klong = (1000 * 60 * 10);
						CacheManager.putCacheInfo(acount+"_"+titleId+"_bms_bntKey", ujson.get("obj"), klong);
					}
				}
				chain.doFilter(request, response);
				//用户不为空，且用户信息缓存已过期
				/*if(CacheManager.cacheExpired(CacheManager.getCacheInfo(acount+"_info"))){
					// 获取用户信息
					url = "/user/findByAccount/"+acount;
					JSONObject ujson2 = ServiceUtils.getService(authUrl,url,token);
					String  status2 = ujson2.get("status").toString();
					if("500".equals(status2)){
						res.sendRedirect("/login/loginError");
						return;
					}else{
						//用户信息已存在，用户缓存信息无效，进行用户缓存保存
						long i = (1000 * 60 * 30);
						CacheManager.putCacheInfo(acount + "_info", ujson2.get("obj"), i);
						//用户菜单权限
						url = "/user/getUserPemissions/" + acount + "/BMS/"+deptId+"/"+ titleId;
						JSONObject rjson = ServiceUtils.getService(authUrl,url,token);;
						if ("200".equals(rjson.get("status").toString())) {
							//菜单权限保存进缓存
							CacheManager.putCacheInfo(acount + "_psm", rjson.get("obj"), i);
						}
						 cacheInfo=CacheManager.getCacheInfo(acount+"_psm");
						boolean flag = false;
						if(cacheInfo!=null) {
							List<Map<String, Object>> listMap = JSON.parseObject(cacheInfo.getValue().toString(), new TypeReference<List<Map<String, Object>>>() {
							});
							String channelIdParam = "" + req.getParameter("id");//菜单ID
							flag = this.getFlag(listMap, channelIdParam, req.getRequestURL().toString());
						}
						if(!flag){
							System.out.println("权限不足！");
							res.sendRedirect("/login/loginError");
							return;
						}

						chain.doFilter(request, response);
					}
				}else{
					//用户缓存信息有效
					 cacheInfo=CacheManager.getCacheInfo(acount+"_psm");
					//用户菜单权限
					if(cacheInfo==null){
						url = "/user/getUserPemissions/" + acount + "/BMS/" + deptId + "/" + titleId;
						JSONObject rjson = ServiceUtils.getService(authUrl, url, token);
						;
						if ("200".equals(rjson.get("status").toString())) {
							//菜单权限保存进缓存
							CacheManager.putCacheInfo(acount + "_psm", rjson.get("obj"), i);
						}
						cacheInfo = CacheManager.getCacheInfo(acount + "_psm");
					}
					boolean flag = false;
					if(cacheInfo!=null) {
						List<Map<String, Object>> listMap = JSON.parseObject(cacheInfo.getValue().toString(), new TypeReference<List<Map<String, Object>>>() {
						});
						String channelIdParam = "" + req.getParameter("id");//菜单ID
						flag = this.getFlag(listMap, channelIdParam, req.getRequestURL().toString());
					}
					if(!flag){
						System.out.println("权限不足！");
						res.sendRedirect("/login/loginError");
						return;
					}

					chain.doFilter(request, response);
				}*/
			}
		}else{
			CookieUtils.removeCookie(req,res,"sso_token");
			res.sendRedirect("/login/loginError");
			return;
		}
	}

	@Override
	public void destroy() {

	}

	private boolean getFlag(List<Map<String, Object>> listMap,String channelIdParam,String url){
		boolean flag = false;
		if(url.indexOf("/menu/")>0){
			if(channelIdParam==null||channelIdParam.equals("")||channelIdParam.equals("null")) return flag;
		for (Map map: listMap){
			String channelId=(String)""+map.get("channelId");
			if(channelId.equals(channelIdParam)){
				flag = true;
				break;
			}
		}
		}else {
			flag=true;
		}
		return flag;
	}

}
