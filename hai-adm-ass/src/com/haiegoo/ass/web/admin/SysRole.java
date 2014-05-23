package com.haiegoo.ass.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.utils.ConvertUtils;
import com.haiegoo.framework.utils.TreeRecursiveHandle;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class SysRole extends PageAdmController {
	

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	
	
	/**
	 * Ajax请求：获取用户角色列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//组装JSON
			JSONObject json = this.getTreeRoleOfJSON();

			//输出数据
			out.println(json);
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}	
	
		
	/**
	 * Ajax请求：保存角色
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{			
			//创建对象
			Role role = request.getBindObject(Role.class, "role");
			Integer[] moduleIds = ConvertUtils.toArrInteger(request.getParameter("moduleIds").split(","));
			boolean moduleModified = request.getBooleanParameter("moduleModified");
			
			//添加模块
			role.setModules(new ArrayList<Module>());			
			for(Integer moduleId : moduleIds){
				Module module = new Module();
				module.setModuleId(moduleId);
				role.getModules().add(module);
			}			
			
			if(role.getRoleId()==null){
				//添加对象
				role = roleService.addRole(role, moduleModified);
			}else{
				//编辑对象
				role = roleService.editRole(role, moduleModified);
			}
			
			//输出数据
			adminService.clearSecuritySource();
			
			//输出数据
            out.println("{success: true, role:"+ JSONObject.fromObject(role).toString() +"}");

		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：删除角色
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			Integer roleId = request.getIntParameter("roleId");	
			
			//判断是否是管理员
			if(roleId==1){
				out.println("{success: false, msg: '不能删除[管理员]角色！'}");
				return;
			}	

			roleService.delRole(roleId);
			
			//输出数据
			out.println("{success: true}");
			adminService.clearSecuritySource();
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：上移应用模块
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void upRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String roleId = request.getParameter("roleId");
			String prevRoleId = request.getParameter("prevRoleId");
			
			Role role = roleService.getRole(Integer.valueOf(roleId));
			Role prevRole = roleService.getRole(Integer.valueOf(prevRoleId));
			
			//排序对调
			int temp = role.getSort();
			role.setSort(prevRole.getSort());
			prevRole.setSort(temp);
			
			roleService.editRole(role, false);
			roleService.editRole(prevRole, false);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：下移应用模块
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void downRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String roleId = request.getParameter("roleId");
			String nextRoleId = request.getParameter("nextRoleId");
			
			Role role = roleService.getRole(Integer.valueOf(roleId));
			Role nextRole = roleService.getRole(Integer.valueOf(nextRoleId));
			
			//排序对调
			int temp = role.getSort();
			role.setSort(nextRole.getSort());
			nextRole.setSort(temp);
			
			roleService.editRole(role, false);
			roleService.editRole(nextRole, false);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}	
	
	
	/**
	 * Ajax请求：通过模块ID获取用户ID，包含角色关联的用户
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getUsersByRole(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback=request.getParameter("callback");
		
		try{
			Integer roleId = request.getIntParameter("roleId");
			
			List<Admin> list;			
			if(roleId!=null){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("roleId", roleId);		
				list = adminService.getAdminList(params, null, null, null);
			}else{
				list = new ArrayList<Admin>();
			}
			
			//组装JSON
			JSONObject json = new JSONObject();
			json.put("records", new JSONArray());
			
			JSONArray records = json.getJSONArray("records");
			for(Admin admin : list) {
				JSONObject record = new JSONObject();
				record.put("userId", admin.getUserId());
				record.put("username", admin.getUsername());
				record.put("code", admin.getCode());
				record.put("name", admin.getName());
				
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
	 * 获取JSON格式的树型角色,EXTJS中使用
	 * @return 返回JSON对象，json.get("leafMap")可以获取叶子节点集合
	 */
	public JSONObject getTreeRoleOfJSON() {
		final JSONObject json = new JSONObject();

		try {
			json.put("id", 0);
			json.put("text", "用户角色");
			json.put("roleId", 0);
			json.put("name", "用户角色");
			json.put("iconCls", "icon-role-root");
			json.put("children", new JSONArray());
			json.put("leafMap", new JSONObject());
			json.put("role", new JSONArray());
			
			final List<Role> list = roleService.getRoleList();

			// 递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>() {
				public void recursive(JSONObject treeNode)
						throws Exception {
					for (Role role : list) {
						if (role.getPid().equals(treeNode.getInt("id"))) {

							JSONObject node = JSONObject.fromObject(role);

							node.put("id", role.getRoleId());
							node.put("text", role.getName());
							node.put("expanded", false);
							node.put("leaf", true);
							node.put("nLeaf", role.getLeaf());

							// 图标
							if (role.getLeaf() == 0)
								node.put("iconCls", "icon-role-group");
							else
								node.put("iconCls", "icon-role-leaf");

							// 角色中的模块ID
							JSONArray modules = new JSONArray();
							for (Module module : role.getModules()) {
								modules.add(module.getModuleId());
							}
							node.put("modules", modules);

							// 递归
							this.recursive(node);

							// 添加到树中
							JSONArray children;
							try {
								children = treeNode.getJSONArray("children");
							} catch (JSONException e) {
								treeNode.put("children", new JSONArray());
								children = treeNode.getJSONArray("children");
							}
							children.add(node);
							treeNode.put("leaf", false);

							// 添加最叶子节点到列表中
							if (role.getLeaf() == 1) {
								json.getJSONObject("leafMap").put(
										role.getRoleId(), role.getName());
							}
							json.getJSONArray("role").add(
									JSONArray.fromObject(new Object[] {
											role.getRoleId(),
											role.getName() }));

						}
					}
				}
			};

			treeRecursiveHandle.recursive(json);

		} catch (Exception e) {
			throw new RpcException("查询角色出错", e);
		}

		return json;
	}
}
