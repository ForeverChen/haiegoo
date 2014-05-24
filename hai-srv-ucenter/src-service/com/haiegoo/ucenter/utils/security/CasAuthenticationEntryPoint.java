/**
 * 
 */
package com.haiegoo.ucenter.utils.security;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承CasAuthenticationEntryPoint，重写createServiceUrl方法，实现登录后的跳转问题
 * @author pinian.lpn
 */
public class CasAuthenticationEntryPoint extends
		org.springframework.security.cas.web.CasAuthenticationEntryPoint {

	private final String TARGET_URL_PARAMETER = "redirect";

	@Override
	protected String createServiceUrl(final HttpServletRequest request,
			final HttpServletResponse response) {
		
		if (getServiceProperties().getService() != null) {
			String ctx = request.getContextPath();
			String queryString = request.getQueryString();
			String requestURI = request.getRequestURI();
			
			requestURI = requestURI.substring(requestURI.indexOf(ctx) + ctx.length(), requestURI.length());
			if (StringUtils.isNotBlank(queryString)){
				requestURI += "?" + queryString;
			}
			
			String serviceUrl = "";
			if (!requestURI.equals("/") && requestURI.length() > 0) {
				serviceUrl = CommonUtils.constructRedirectUrl(getServiceProperties().getService(), TARGET_URL_PARAMETER, requestURI, false, false);
				getServiceProperties().setService(serviceUrl);
			}
		}
		
		return super.createServiceUrl(request, response);
	}
}
