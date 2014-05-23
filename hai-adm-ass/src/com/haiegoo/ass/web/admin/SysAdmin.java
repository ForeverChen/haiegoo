package com.haiegoo.ass.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.commons.utils.json.JsonGlobal;
import com.haiegoo.commons.utils.ConvertUtils;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class SysAdmin extends PageAdmController {
	
	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	
	
	/**
	 * Ajax请求：获取后台用户列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getAdmin(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback=request.getParameter("callback");
		
		try{
			//获取参数
			String sort = request.getParameter("sort");
			String dir = request.getParameter("dir");
			Long start = request.getLongParameter("start");
			Long limit = request.getLongParameter("limit");
			String searchKey = request.getParameter("searchKey", "", "UTF-8");
			
			//查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("searchKey", searchKey);
			
			//排序数据
			Map<String,String> sorters = new HashMap<String,String>();
			sorters.put(sort, dir);
			
			//查询数据
			List<Admin> list = adminService.getAdminList(params, sorters, start, limit);
			long count = adminService.getAdminCount(params);
			
			//组装JSON
			JSONObject json = new JSONObject();
			json.put("count", count);
			json.put("records", new JSONArray());
			
			JSONArray records = json.getJSONArray("records");
			for(Admin admin : list) {
				JSONObject record = JSONObject.fromObject(admin, JsonGlobal.config);

				//角色
				JSONArray roleIds = new JSONArray();
				JSONArray roleNames = new JSONArray();
				for(Role role : admin.getRoles()){
					roleIds.add(role.getRoleId());
					roleNames.add(role.getName());
				}				
				record.put("roleIds", roleIds);
				record.put("roleNames", roleNames);

				//角色模块
				JSONArray roleModuleIds = new JSONArray();
				for(Role role : admin.getRoles()){
					for(Module module : role.getModules()){
						roleModuleIds.add(module.getModuleId());
					}
				}
				record.put("roleModuleIds", roleModuleIds);	
				
				records.add(record);
			}
			
			//输出数据
			out.println(callback + "("+ json +")");
			
		}catch(Exception e){
			out.println(callback + "({success: false, msg: '"+ e.getMessage() +"'})");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
		
	/**
	 * Ajax请求：保存用户
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveAdmin(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{						
			//创建对象
			Admin user = request.getBindObject(Admin.class, "user");
			Integer[] roleIds = ConvertUtils.toArrInteger(request.getParameter("roleIds").split(","));
			boolean roleModified = request.getBooleanParameter("roleModified");
									
			//密码加密
			user.setPassword(StringUtils.isBlank(user.getPassword()) || user.getPassword().equals("********") ? null : md5.encodePassword(user.getPassword(), user.getUsername()));
			
			//添加角色
			user.setRoles(new ArrayList<Role>());			
			for(int roleId : roleIds){
				Role role = new Role();
				role.setRoleId(roleId);
				user.getRoles().add(role);
			}
			
			if(user.getUserId()==null){
				//添加对象
				adminService.addAdmin(user, roleModified);
			}else{
				//编辑对象
				adminService.editAdmin(user, roleModified);
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
	 * Ajax请求：删除用户
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delAdmin(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String userIds = request.getParameter("userIds");	
			Integer[] _userIds = ConvertUtils.toArrInteger(userIds.split(","));
			
			//判断是否是管理员
			for(int userId : _userIds){
				if(userId==1){
					out.println("{success: false, msg: '不能删除[admin]用户！'}");
					return;
				}	
			}
			
			adminService.delAdmin(_userIds);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}

}
