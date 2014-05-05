package com.haiegoo.ass.web.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haiegoo.commons.model.config.Enums;
import com.haiegoo.commons.service.config.EnumsService;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class SysEnums extends PageAdmController {
	
	@Resource
	private EnumsService enumsService;

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	

	/**
	 * Ajax请求：获取系统枚举列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getEnums(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback=request.getParameter("callback");
		
		try{
			//获取参数
			String searchKey = request.getParameter("searchKey", "", "UTF-8");
			
			//查询枚举
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("searchKey", searchKey);
			
			//查询数据
			List<Enums> list = enumsService.getEnumsList(params);

			//组装JSON
			JSONObject json = new JSONObject();
			
			json.put("key", "枚举与参数");
			json.put("iconCls", "icon-docs");
			json.put("children", new JSONArray());
			
			reset:
			for(Enums enums : list){
				//枚举类
				JSONArray clazzs = json.getJSONArray("children");
				for(int j=0; j<clazzs.size(); j++){
					JSONObject group = clazzs.getJSONObject(j);
					if(enums.getClazz().equals(group.getString("key"))){
						//枚举或参数
						JSONArray items = group.getJSONArray("children"); 
						items.add(this.getNodeOfEnums(enums));
						continue reset;
					}
				}

				//当没有枚举类时，添加枚举类
				clazzs.add(this.getClazzOfEnums(enums));
				continue reset;
			}
			
			//输出数据
			out.println(json);
			
		}catch(Exception e){
			out.println(callback + "({success: false, msg: '"+ e.getMessage() +"'})");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：保存枚举
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveEnums(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{						
			//创建对象
			Enums enums = request.getBindObject(Enums.class, "enums");
			
			if(enums.getId()==null){
				//添加对象
				enumsService.addEnums(enums);
			}else{
				//编辑对象
				enumsService.editEnums(enums);
			}
			
			//输出数据
			out.println("{success: true}");

		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：删除枚举
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delEnums(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			int id = request.getIntParameter("id");
			enumsService.delEnums(id);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	
	
	private JSONObject getClazzOfEnums(Enums enums){
		//枚举类型为空，表示为K-V参数
		if(StringUtils.isBlank(enums.getClazz()))
			return this.getNodeOfEnums(enums);
		
		//枚举类型
		JSONObject group = new JSONObject();
		group.put("key", enums.getClazz());
		group.put("iconCls", "icon-module-group");
		group.put("children", new JSONArray());
		group.put("expanded", true);
		group.getJSONArray("children").add(this.getNodeOfEnums(enums));
		return group;
	}
	
	private JSONObject getNodeOfEnums(Enums enums){
		//枚举或参数
		JSONObject node = new JSONObject();
		node.put("id", enums.getId());
		node.put("clazz", enums.getClazz());
		node.put("key", enums.getKey());
		node.put("code", enums.getCode());
		node.put("text", enums.getText());
		node.put("leaf", true);
		node.put("iconCls", "icon-enums");
		
		return node;
	}

}
