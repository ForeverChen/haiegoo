package com.haiegoo.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.GenericFilterBean;

import com.haiegoo.framework.web.wrapper.MemcachedHttpServletRequestWrapper;

/**
 * Memcached集群拦截器
 * @author Linpn
 */
public class MemcachedSessionClusterFilter extends GenericFilterBean {
	
	private static MemcachedClient cache;
	private String memcachedClient;		// MemcachedClient 的 bean id
	private String filterSuffix;		// 要过滤的扩展名
	

	@Override
	protected void initFilterBean() throws ServletException {
		try {
	    	//创建memcached客户端
	    	if(cache==null){
	    		cache = ContextLoader.getCurrentWebApplicationContext().getBean(memcachedClient, MemcachedClient.class);
	    	}
		} catch (Exception e) {
			throw new ServletException("创建MemcachedClient出错", e);
		} 
	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest _request = (HttpServletRequest)request;
		String url = _request.getRequestURI();
		
		if(this.filterSuffix!=null && !url.matches(filterSuffix)){
			chain.doFilter(new MemcachedHttpServletRequestWrapper(_request, cache), response);
		}else{
			chain.doFilter(request, response);
		}
	}


	/**
	 * MemcachedClient 的 bean id
	 */
	public void setMemcachedClient(String memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	

	/**
	 * 要过滤的扩展名
	 */
	public void setFilterSuffix(String filterSuffix) {
		this.filterSuffix = filterSuffix.replaceAll("\\s+", "")
				.replaceAll("\\*\\.", ".+\\\\.")
				.replaceAll(",", "\\$|") + "$";
	}

}
