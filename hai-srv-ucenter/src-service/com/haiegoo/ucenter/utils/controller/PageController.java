package com.haiegoo.ucenter.utils.controller;

import com.haiegoo.commons.service.config.EnumsService;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.framework.web.controller.BaseController;
import com.haiegoo.ucenter.model.user.User;
import com.haiegoo.ucenter.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 页面MVC控制层基类，主要编写业务公共方法
 * 
 * @author Linpn
 */
public abstract class PageController extends BaseController {

	/** 系统基础配置，读取app.conf里的信息 */
	@Resource
	protected Properties appConf;
	/** 前台用户服务类 */
	@Resource
	protected UserService userService;	
	/** 枚举服务类 */
	@Resource
	protected EnumsService enumsService;
	
	/** 加密对象 */
	protected final Md5PasswordEncoder md5 = new Md5PasswordEncoder();	

	/**
	 * 初始化ModelAndView
	 * @param request
	 * @param response
	 * @param modelview
	 */
	@Override
	protected void initModelAndView(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modelview) throws Exception {
		
		//登录用户
		modelview.addObject("user",this.getCurrUser());
		
		//请求地址
		modelview.addObject("page_context", request.getContextPath());
		modelview.addObject("page_action", request.getContextPath() + request.getServletPath());
		modelview.addObject("page_location", this.getFullURL(request));

		//加载app.conf参数
		for (Entry<Object, Object> entry : appConf.entrySet()) {
			String key = entry.getKey().toString().replaceAll("\\.", "_");
			String value = entry.getValue().toString();
			modelview.addObject(key, value);
		}
	}
	
	
	/**
	 * 获取Redis应用级(application server cache)缓存
	 * @return 返回RedisTemplate对象
	 */
	@SuppressWarnings("unchecked")
	public RedisTemplate<String, Object> getRedisTemplate() {
		return this.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
	}

	
	/**
	 * 获取当前用户
	 * @return
	 */
	public User getCurrUser(){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication==null)
				return null;
			if(authentication.getPrincipal().equals("anonymousUser"))
				return null;
	
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return (User)userDetails;
		}catch(Exception e){
			logger.error("保存当前用户出错", e);
		}
		
		return null;
	}
	

	/**
	 *	获取当前网页的完整URL
	 * @param request HttpServletRequest对象
	 * @return
	 */
	public String getFullURL(HttpServletRequest request){
		String url = request.getAttribute("javax.servlet.forward.request_uri")==null?null:request.getAttribute("javax.servlet.forward.request_uri").toString();
		String appUrl = appConf.getProperty("app.url");
		if(StringUtils.isNotBlank(url)){
			String context = url.replaceAll("(/\\w+)(.*)", "$1");
			String appContext = appUrl.replaceAll("(.+)(/\\w+)(.*)", "$2");
			if(appContext.equals(context)){
				url = appUrl + url.replaceAll("(/\\w+)(.*)", "$2");
			}
		}else{
			url = appUrl + request.getServletPath() + (StringUtils.isBlank(request.getQueryString())?"":"?"+request.getQueryString());
		}
		return url;
	}
}
