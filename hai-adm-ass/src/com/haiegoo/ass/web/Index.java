package com.haiegoo.ass.web;

import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.utils.controller.PageAdmController;
import com.haiegoo.ucenter.enums.SysAdmEnum;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

public class Index extends PageAdmController {

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
		
		Admin user =this.getCurrUser();		
		if(user!=null){
			JSONObject systemModule = this.getSystemModuleOfJSON(user, 
														request.getParameter("sysname"), 
														request.getParameter("sysurl"));
			modeview.addObject("search", systemModule.getJSONArray("search"));		
			modeview.addObject(SysAdmEnum.casass.getCode(), systemModule.getJSONArray(SysAdmEnum.casass.getCode()));
			modeview.addObject(SysAdmEnum.shopmng.getCode(), systemModule.getJSONArray(SysAdmEnum.shopmng.getCode()));
			modeview.addObject(SysAdmEnum.erpcrm.getCode(), systemModule.getJSONArray(SysAdmEnum.erpcrm.getCode()));
		}
	}

}
