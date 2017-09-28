package com.xhj.bms.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhj.bms.security.util.CookieUtils;
import com.xhj.bms.security.util.ServiceUtils;
import com.xhj.bms.security.util.WebUtils;
import org.nutz.dao.entity.Record;
import org.nutz.http.*;
import org.nutz.http.Request.METHOD;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登陆管理
 * @author bo.chen
 *
 */
@Controller
@RequestMapping(value="/login")
public class LoginController {
	
	@Value("${authcenter.url}")
	private String authUrl;

	/**
	 * 登录页
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showLogin")
	 public String showEmployeeReport(ModelMap model) throws Exception {
      return "/admin/include/hrlogin";
	 }

	/**
	 * 登录成功
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/successLogin")
	public String successLoginReport(ModelMap model) throws Exception {
		return "/admin/pages/index1";
	}

	/**
	 * 无权限
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginError")
	public String root(ModelMap model) throws Exception {
		return "/admin/include/loginError";
	}

	/**
	 * 登录 submit
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public JSONObject login(@RequestBody Map<String, Object> param) throws Exception {
		String url = "/user/login";
		JSONObject ujson = ServiceUtils.postService(authUrl,url,param,null);
		NutMap obj = Mapl.maplistToT(ujson.get("obj"), NutMap.class);
		if(obj!=null){
			CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"auth_cityId",obj.getString("auth_cityId"));
	        CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"titleId",obj.getString("titleId"));
	        CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"deptId",obj.getString("deptId"));
	        CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"sso_token",obj.getString("sso_token"));
	        CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"username",obj.getString("username"));
			CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"userId",obj.getString("userId"));
			CookieUtils.addCookie(WebUtils.getRequest(),WebUtils.getResponse(),"marketLevel",obj.getString("marketLevel"));
		}
		ujson.put("obj", obj);
		return ujson;
	}

	/**
	 * 获取用户信息
	 * @param acount
	 * @return
	 */
	@RequestMapping(value = "/findByAccount/{acount}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject  findByAccount(@PathVariable String acount){
		//String acount =  CookieUtils.getCookie(WebUtils.getRequest(), "username");
		String token = CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
        String url =  "/user/findByAccount/"+acount;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		return ujson;
	}

	/**
	 * 获取用户按钮
	 * @param account
	 * @param systemName
	 * @param departmentId
	 * @param titleId
	 * @return
	 */
	@RequestMapping(value = "/getUserButtons/{account}/{systemName}/{departmentId}/{titleId}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserButtons(@PathVariable String account, @PathVariable String systemName, @PathVariable String departmentId, @PathVariable String titleId){
		String token = CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
		String url = "/user/getUserButtons/"+ account + "/BMS/"+departmentId+"/" + titleId;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		return ujson;
	}

	/**
	 * 获取用户菜单
	 * @param account
	 * @param systemName
	 * @param departmentId
	 * @param titleId
	 * @return
	 */
	@RequestMapping(value = "/getUserChannels/{account}/{systemName}/{departmentId}/{titleId}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserChannels(@PathVariable String account,@PathVariable String systemName,@PathVariable String departmentId,@PathVariable String titleId){
		String token = CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
		String url = "/user/getUserChannels/"+ account + "/BMS/"+departmentId+"/" + titleId;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		return ujson;
	}

	/**
	 * 判断用户是否登录
	 *
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/isLogin/{account}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject isLogin(@PathVariable String account) {
		String token = CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
		String acount =  CookieUtils.getCookie(WebUtils.getRequest(), "username");
        String url ="/user/isLogin/"+acount;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		return ujson;
	}

	/**
	 * 注销登录
	 * @param account
	 */
	@RequestMapping(value = "/logout/{account}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject logout(@PathVariable String account){
		String token = CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
		String acount =  CookieUtils.getCookie(WebUtils.getRequest(), "username");
		String url ="/user/logout/"+acount;
		JSONObject ujson = ServiceUtils.getService(authUrl,url,token);
		return ujson;
	}


}
