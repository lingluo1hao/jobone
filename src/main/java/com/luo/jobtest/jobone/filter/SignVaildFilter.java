package com.usys.store.zuul.filter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.usys.store.core.util.SignUtil;
import com.usys.store.interfaces.model.StoreApiInfo;
import com.usys.store.zuul.service.IStoreApiInfoService;

public class SignVaildFilter extends ZuulFilter{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IStoreApiInfoService storeApiInfoService;

	@Override
	public Object run() {
		Object object = null;
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURI = request.getRequestURI();
		log.info((String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString())));
		log.info(request.getContentType());
		
		if (requestURI.contains("/v2/api-docs")) {
			ctx.setSendZuulResponse(true); 		// 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true); 		// 设值，让下一个Filter看到上一个Filter的状态
			return null;
		}
		
		//object = validSign(ctx, request, requestURI);				//校验签名
		return object;

	}

	public Object validSign(RequestContext ctx, HttpServletRequest request, String requestURI) {
		String appid = request.getParameter("appid");
		String platformID = request.getParameter("platformID");
		String timestamp = request.getParameter("timestamp");
		String reqSign = request.getParameter("sign");
		boolean temp = StringUtils.isBlank(timestamp) || StringUtils.isBlank(reqSign) || StringUtils.isBlank(platformID)
				|| StringUtils.isBlank(appid);
		String reqParameter = "";
		
		Map<String, String[]> map = request.getParameterMap();
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		for (String key : map.keySet()) {
			parameters.put(key, request.getParameter(key.toString()));
			log.info(key + ":" + request.getParameter(key.toString()));
			reqParameter = key + "=" + request.getParameter(key.toString()) + "&";
		}
		
		if (!"".equals(reqParameter)) {
			reqParameter = reqParameter.substring(0, reqParameter.length()-1);
		}

		if (temp) {
			log.error("当前请求：" + request.getRequestURL().toString() + reqParameter);
			log.error("缺少基础参数");
			ctx.setSendZuulResponse(false); // 过滤该请求，不对其进行路由
			ctx.setResponseStatusCode(200); // 返回错误码
			ctx.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
			ctx.setResponseBody("{\"code\":\"40001\",\"msg\":\"签名错误\",\"data\":\"缺少基础参数\"}"); // 返回错误内容
			ctx.set("isSuccess", false);
			return null;
		}

		if (requestURI.contains("/member/")) {
			log.info("requestURI: " + requestURI);
			log.info("会员服务，不进行校验。 ");
			ctx.setSendZuulResponse(true); // 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true); // 设值，让下一个Filter看到上一个Filter的状态
			return null;
		}

		// 测试使用
		if (timestamp.equals("123456") && reqSign.equals("gzusys@2014*UDKELkskj")) {
			ctx.setSendZuulResponse(true); // 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true); // 设值，让下一个Filter看到上一个Filter的状态
			return null;
		}

		// 校验请求是否超时
		try {
			if (!SignUtil.validateTimeStamp(timestamp)) { // 超时
				ctx.setSendZuulResponse(false); // 过滤该请求，不对其进行路由
				ctx.setResponseStatusCode(200);
				ctx.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
				ctx.setResponseBody("{\"code\":\"40003\",\"msg\":\"签名过期或者手机时间有误\",\"data\":\"\"}");// 返回错误内容
				ctx.set("isSuccess", false);
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			ctx.setSendZuulResponse(false); // 过滤该请求，不对其进行路由
			ctx.setResponseStatusCode(200);
			ctx.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
			ctx.setResponseBody("{\"code\":\"40004\",\"msg\":\"签名错误\",\"data\":\"\"}");// 返回错误内容
			ctx.set("isSuccess", false);
			return null;
		}

		StoreApiInfo storeApiInfo = storeApiInfoService.getStoreApiInfoByAppid(appid);
		if (storeApiInfo == null) {
			log.error("当前请求：" + request.getRequestURL().toString() + reqParameter);
			log.error("APPID有误");
			ctx.setSendZuulResponse(false); // 过滤该请求，不对其进行路由
			ctx.setResponseStatusCode(200); // 返回错误码
			ctx.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
			ctx.setResponseBody("{\"code\":\"40004\",\"msg\":\"非法应用\",\"data\":\"\"}");// 返回错误内容
			ctx.set("isSuccess", false);
			return null;
		}

		String appSecret = storeApiInfo.getAppSecret();

		String sign = SignUtil.createSign("UTF-8", parameters, appSecret);
		if (sign.equals(reqSign)) {
			ctx.setSendZuulResponse(true); // 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true); // 设值，让下一个Filter看到上一个Filter的状态
			return null;
		} else {
			log.error("当前请求：" + request.getRequestURL().toString() + reqParameter);
			log.error("签名校验失败");
			ctx.setSendZuulResponse(false); // 过滤该请求，不对其进行路由
			ctx.setResponseStatusCode(200); // 返回错误码
			ctx.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
			ctx.setResponseBody("{\"code\":\"40004\",\"msg\":\"签名错误\",\"data\":\"\"}");// 返回错误内容
			ctx.set("isSuccess", false);
			return null;
		}
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
