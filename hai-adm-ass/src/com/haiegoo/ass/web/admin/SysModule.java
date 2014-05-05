package com.haiegoo.ass.web.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.framework.utils.TreeRecursiveHandle;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.service.admin.ModuleService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class SysModule extends PageAdmController {

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	
	
	/**
	 * Ajax请求：获取用户应用模块列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//组装JSON
			JSONObject json = this.getTreeModuleOfJSON();

			//输出数据
			out.println(json);
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
		
	/**
	 * Ajax请求：保存应用模块
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{			
			//创建对象
			Module module = request.getBindObject(Module.class, "module");	
			Module parent = moduleService.getModule(module.getPid());
			
			//判断编号是否正确
			if(parent!=null && StringUtils.isNotBlank(parent.getCode())){
				String code = module.getCode();
				if(!parent.getCode().equals(code.substring(0, code.length()-2))){
					out.println("{success: false, msg: '编码输入不正确，编码格式为：父编码+两位字符！'}");
					return;
				}
			}
			
			//保存图片
			MultipartFile multipartFile = request.getFile("module.image");
			if(multipartFile!=null && !multipartFile.isEmpty()){
				String filename = DigestUtils.md5Hex(multipartFile.getBytes()) + multipartFile.getOriginalFilename().replaceAll(".*(\\.\\w+)$", "$1");
				module.setImage("WEB-RES/imgs/" + filename);
				FileUtils.writeByteArrayToFile(new File(request.getServletContext().getRealPath("/")+ module.getImage()), multipartFile.getBytes());
			}
			
			if(module.getModuleId()==null){
				//添加对象
				module = moduleService.addModule(module);
			}else{
				//编辑对象
				module = moduleService.editModule(module);
			}
            
			//清空安全资源数据
			adminService.clearSecuritySource();
			
			//输出数据
            out.println("{success: true, module:"+ JSONObject.fromObject(module).toString() +"}");

		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}	
	
	
	/**
	 * Ajax请求：删除应用模块
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			Integer moduleId = request.getIntParameter("moduleId");	
			
			moduleService.delModule(moduleId);
			adminService.clearSecuritySource();
			
			//输出数据
			out.println("{success: true}");
			
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
	public void upModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String moduleId = request.getParameter("moduleId");
			String prevModuleId = request.getParameter("prevModuleId");
			
			Module module = moduleService.getModule(Integer.valueOf(moduleId));
			Module prevModule = moduleService.getModule(Integer.valueOf(prevModuleId));
			
			//排序对调
			int temp = module.getSort();
			module.setSort(prevModule.getSort());
			prevModule.setSort(temp);
			
			moduleService.editModule(module);
			moduleService.editModule(prevModule);
			
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
	public void downModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String moduleId = request.getParameter("moduleId");
			String nextModuleId = request.getParameter("nextModuleId");
			
			Module module = moduleService.getModule(Integer.valueOf(moduleId));
			Module nextModule = moduleService.getModule(Integer.valueOf(nextModuleId));
			
			//排序对调
			int temp = module.getSort();
			module.setSort(nextModule.getSort());
			nextModule.setSort(temp);
			
			moduleService.editModule(module);
			moduleService.editModule(nextModule);
			
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
	public void getUsersByModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback=request.getParameter("callback");
		
		try{
			Integer moduleId = request.getIntParameter("moduleId");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("moduleId", moduleId);		
			List<Admin> list = adminService.getAdminList(params, null, null, null);
			
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
	 * Ajax请求：通过模块ID获取角色ID
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getRoleByModule(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			Integer moduleId = request.getIntParameter("moduleId");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("moduleId", moduleId);			
			List<Role> list = roleService.getRoleList(params);
			
			//组装JSON
			JSONArray json = new JSONArray();
			
			for(Role role : list){
				json.add(role.getRoleId());
			}
			
			//输出数据
			out.println(json);
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}

	
	/**
	 * 获取JSON格式的树型模块
	 * @return 返回JSON对象
	 */
	public JSONObject getTreeModuleOfJSON() {
		final JSONObject json = new JSONObject();
		
		try {
			json.put("moduleId", 0);
			json.put("id", 0);
			json.put("text", "应用模块");
			json.put("iconCls", "icon-docs");
			json.put("children", new JSONArray());
			
			final List<Module> list = moduleService.getModuleList();
						
			//递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>(){
				public void recursive(JSONObject treeNode) throws Exception{
					for(Module module : list){
						if(module.getPid().equals(treeNode.getInt("id"))){						
							//获取JSON对象
							JSONObject node = JSONObject.fromObject(module);
							
							node.put("id", module.getModuleId());
							node.put("text", module.getText());
							json.put("iconCls", module.getIconCls());
							node.put("leaf", true);

							node.put("nExpanded", module.getExpanded());
							if(module.getExpanded()==1)
								node.put("expanded", true);
							else
								node.put("expanded", false);
							
							if(module.getType().equals(ModuleService.SYSTEM))
								node.put("expanded", false);
														
							//递归
							this.recursive(node);
							
							//添加到树中
							JSONArray children;
							try {
								children = treeNode.getJSONArray("children");
							} catch (JSONException e) {
								treeNode.put("children", new JSONArray());
								children = treeNode.getJSONArray("children");
							}							
							children.add(node);
							treeNode.put("leaf", false);
						}
					}
				}
			};
			
			treeRecursiveHandle.recursive(json);
			
		} catch (Exception e) {
			throw new RuntimeException("查询模块出错", e);
		}
		
		return json;		
	}

}
