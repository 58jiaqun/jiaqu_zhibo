package com.xhj.bms.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.xhj.bms.entity.CallBack;
import com.xhj.bms.security.util.ServiceUtils;
import com.xhj.bms.util.DateUtil;
import com.xhj.bms.util.SigUtil;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 电话管理
 * @author bo.chen
 *
 */
@Controller
@RequestMapping(value="/phone")
public class PhoneController {
	
	//主机名
	private String callurl="http://apitest.emic.com.cn";
	private String host = "apitest.emic.com.cn";
	//主账号
	private String accountSid="f154689ef472b4b7236db6ab74122d19";
	//主账号令牌
	private String authToken="bb173062091aaf1b780240e4b5d3d69d";
	//子账号
	private String subAccountSid = "0dc627f2e4937a5a0c875150d24805a4";
	//子账号令牌
	private String subAccountToken="ac63ae29379213f29e82efb34af4e94b";
	//版本
	private String softVersion="20161206";
	//应用id
	private String appId = "ebea85aff2d8c2ec4cd50dec39096de7";
	//主叫号码
	private String phoneFrom="18975800220";
	//被叫号码
	private String phoneTo="18573100166";
	//账套
	private String accounts="SubAccounts";

	private String dateformat="yyyyMMddHHmmss";

	/**
	 * 双向拨号 submit
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/call", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject call(@RequestBody Map<String, Object> param) throws Exception {
		phoneFrom=(String) param.get("from");
		phoneTo=(String) param.get("to");
		//{"from":"18975800220","to":"18573100166"}
		String datetime =  DateUtil.formatDate(new Date(),dateformat);
        //REST API验证参数
		String sig = SigUtil.createSigStr(subAccountSid,subAccountToken,datetime);
		//报头验证信息
		String authorization =SigUtil.createAuthorization(datetime,subAccountSid);

		System.out.println("sig="+sig+"\nauthorization="+authorization);
		String baseUrl = "/"+softVersion+"/"+accounts+"/"+subAccountSid;
		String function="/Calls/callBack?sig="+sig;
		String url =baseUrl+function;

		//参数设置
		CallBack call = new CallBack();
		call.setFrom(phoneFrom);
		call.setTo(phoneTo);
		call.setAppId(appId);
		param.put("callBack",call);
		JSONObject ujson = ServiceUtils.postServiceCall(callurl,url,host,param,authorization,null);
		NutMap obj = Mapl.maplistToT(ujson.get("obj"), NutMap.class);
		return ujson;
	}


	/** 获取主账号信息
	 *  submit
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject accountInfo(@RequestBody Map<String, Object> param) throws Exception {
		//账套
		accounts="Accounts";
		String dateformat="yyyyMMddHHmmss";
		String datetime =  DateUtil.formatDate(new Date(),dateformat);
		//REST API验证参数
		String sig = SigUtil.createSigStr(accountSid,authToken,datetime);
		//报头验证信息
		String authorization =SigUtil.createAuthorization(datetime,accountSid);
		System.out.println("sig="+sig+"\nauthorization="+authorization);
		String baseUrl = "/"+softVersion+"/"+accounts+"/"+accountSid;
		String function="/AccountInfo/?sig="+sig;
		String url =baseUrl+function;
		JSONObject ujson = ServiceUtils.postServiceCall(callurl,url,host,param,authorization,null);
		NutMap obj = Mapl.maplistToT(ujson.get("obj"), NutMap.class);
		return ujson;
	}


}
