package com.haiegoo.ucenter.utils.security.adm;

import com.haiegoo.ucenter.service.admin.AdminService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * @author user
 */
public class FilterInvocationSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {

	@Resource
	private AdminService adminService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		
		//获取当前请求的URL
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequest().getContextPath()+fi.getRequestUrl();
		
		//获取缓存
		Map<String, Collection<ConfigAttribute>> httpMethodMap = adminService.getSecuritySource();
		if (httpMethodMap != null) {
			//判断当前URL是否在过滤范围
			Iterator<String> ite = httpMethodMap.keySet().iterator();
			while (ite.hasNext()) {
				String resUrl = ite.next();
				if (url.startsWith(resUrl)) {
					Collection<ConfigAttribute> returnCollection = httpMethodMap.get(resUrl);
					// 只要匹配到没有权限，就直接返回
					if (returnCollection.size() <= 0) {
						return returnCollection;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	
}
