package com.haiegoo.web;

import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.utils.controller.PageController;

/**
 * 首页
 * @author Linpn
 *
 */
public class Index extends PageController {

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
		
		try{
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
}
