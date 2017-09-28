package com.xhj.bms.security.controller;

import com.xhj.bms.security.util.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xhj.bms.security.util.WebUtils;

@Controller
@RequestMapping(value="/")
public class BaseController {
	@RequestMapping
	 public String root(ModelMap model) throws Exception {
		String token =  CookieUtils.getCookie(WebUtils.getRequest(), "sso_token");
		if(!StringUtils.isEmpty(token)){
			 return "/index";
		}
		return "/admin/include/hrlogin";
	 }
}
