package com.haiegoo.ucenter.utils.security.adm;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

public class FilterSecurityInterceptor extends org.springframework.security.web.access.intercept.FilterSecurityInterceptor {

	@Override
	public void invoke(FilterInvocation fi) throws IOException,
			ServletException {

        final StopWatch clock = new StopWatch();
        clock.start();
		
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
		
        clock.stop(); 

		//输出访问时间日志		
        this.logger(fi, clock);
	}
	
	
	/**
	 * 方法调用日志
	 */
	private void logger(FilterInvocation fi, StopWatch clock){
		try{
			//输出访问时间日志
			logger.info("Access url path ["+fi.getRequestUrl()+", time: "+ clock.getTime() +"ms]");
			//logger.info("Access url params ["+ToStringBuilder.reflectionToString(fi.getRequest().getParameterMap(), ToStringStyle.SHORT_PREFIX_STYLE)+"]");
		}catch(Exception e){
		}
	}
}