package com.haiegoo.ucenter.utils.controller;

import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.framework.utils.TreeRecursiveHandle;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.framework.web.controller.BaseController;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.service.admin.AdminService;
import com.haiegoo.ucenter.service.admin.ModuleService;
import com.haiegoo.ucenter.service.admin.RoleService;

/**
 * 页面MVC控制层基类，主要编写业务公共方法
 * @author Linpn
 */
public abstract class PageAdmController extends BaseController {
	
//	/**
//	 *  Spring MVC的DispatcherServlet加载的ApplicationContext上下文 。
//	 *  DispatcherServlet加载的Bean是MVC私有的上下文，不能通过注入的方式获取。
//	 *  如想要获取系统用户的Action，可用 appMvcContext.getBean("/admin/SysAdmin.jspx")。
//	 */
//	private ApplicationContext appMvcContext;
	
	/** 系统基础配置，读取app.conf里的信息 */
	@Resource 	
	protected Properties appConf;
//	/** 动态枚举工具类，获取EnumsService的枚举数据，并动态创建本地的动态枚举类 */
//	@Resource 	
//	protected EnumsContext enumsContext;
	/** 系统用户服务类 */
	@Resource
	protected AdminService adminService;	
	/** 用户角色服务类 */
	@Resource
	protected RoleService roleService;	
	/** 应用模块服务类 */
	@Resource
	protected ModuleService moduleService;

	/** 加密对象 */
	protected final Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	
	
	/**
	 * 初始化ModelAndView
	 * @param request
	 * @param response
	 * @param modelview
	 */
	@Override
	protected void initModelAndView(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modelview) throws Exception {
		
		//登录用户
		modelview.addObject("admin",this.getCurrUser());
		
		//请求路径
		modelview.addObject("page_context", request.getContextPath());
		modelview.addObject("page_action", request.getContextPath() + request.getServletPath());
		modelview.addObject("page_location", request.getRequestURL()+"?"+request.getQueryString());

		//加载app.conf参数
		for (Entry<Object, Object> entry : appConf.entrySet()) {
			String key = entry.getKey().toString().replaceAll("\\.", "_");
			String value = entry.getValue().toString();
			modelview.addObject(key, value);
		}
		
		//模块信息  
		Admin user = this.getCurrUser();
		if(user!=null){
			Module module = this.getCurrModule(request);
			if(module!=null){
				//如果是操作功能，则获取该功能所在的模块
				if(ModuleService.FUNCTION.equals(module.getType())){
					module = moduleService.getModule(module.getPid());
				}
				if(ModuleService.MODULE.equals(module.getType())){
					modelview.addObject("module_title", module.getText());		//标题
					modelview.addObject("module_function", this.getFunctionByModuleIdOfJSON(this.getCurrUser(), module.getModuleId()));	//操作功能
				}
			}
		}
	}
	
	
	/**
	 *  Spring MVC的DispatcherServlet加载的ApplicationContext上下文 。
	 *  DispatcherServlet加载的Bean是MVC私有的上下文，不能通过注入的方式获取。
	 *  如想要获取系统用户的Action，可用 this.getMvcContext().getBean("/admin/SysAdmin.jspx")。
	 */
	public ApplicationContext getMvcContext(){
		return this.getApplicationContext();
	}
	
	
	/**
	 * 获取Redis应用级(application server cache)缓存
	 * @return 返回RedisTemplate对象
	 */
	@SuppressWarnings("unchecked")
	public RedisTemplate<String, Object> getRedisTemplate() {
		return this.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
	}
	
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public Admin getCurrUser(){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication==null)
				return null;
			if(authentication.getPrincipal().equals("anonymousUser"))
				return null;
	
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return (Admin)userDetails;
			
		}catch(Exception e){
			logger.error("获取当前用户出错", e);
		}
		
		return null;
	}
	
	
	/**
	 * 获取当前系统
	 * @param request request对象
	 */
	public Module getCurrSystem(HttpServletExtendRequest request) {
		String func = request.getParameter("func");
		String path = request.getContextPath() + request.getServletPath() + (func!=null ? "?func="+func : "");  
		Module module = moduleService.getModuleByUrl(path);
		return module;
	}
	
	
	/**
	 * 获取当前模块
	 * @param request request对象
	 */
	public Module getCurrModule(HttpServletExtendRequest request) {
		String func = request.getParameter("func");
		String path = request.getContextPath() + request.getServletPath() + (func!=null ? "?func="+func : "");  
		Module module = moduleService.getModuleByUrl(path);
		return module;
	}
	
	

	//------------------------------------- 公共方法 ----------------------------------------------//
	
	/**
	 * 
	 * 获取首页上有权限的模块列表，并转换成指定的JSON格式
	 * @param user 后台用户对象,如果user为null，则查出所有的模块
	 * @param sysname 当前的系统
	 * @param sysurl 当前系统的URL
	 * @return 返回JSON对象
	 */
	public JSONObject getSystemModuleOfJSON(Admin user, String sysname, String sysurl) {
 		final JSONObject json = new JSONObject();
		
		try {			
			json.put("id", 0);
			json.put("items", new JSONArray());
			json.put("search", new JSONArray());
			json.put(SysAdmEnum.casass.getCode(), new JSONArray());
			json.put(SysAdmEnum.shopmng.getCode(), new JSONArray());
			json.put(SysAdmEnum.erpcrm.getCode(), new JSONArray());
			
			final List<Module> list;
			if(user==null)
				list = moduleService.getModuleList();
			else
				list = moduleService.getModuleListByUser(user, false);
						
			//递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>(){
				public void recursive(JSONObject treeNode) throws Exception{
					for(Module module : list){
						if(module.getPid().equals(treeNode.getInt("id"))){
							//只查系统、模块组、模块
							if(!(ModuleService.SYSTEM.equals(module.getType()) || 
									ModuleService.GROUP.equals(module.getType()) ||
									ModuleService.MODULE.equals(module.getType())) ){
								break;
							}
							
							//获取JSON对象
							JSONObject node = new JSONObject();
							node.put("id", module.getModuleId());
							node.put("title", module.getText());

							if(ModuleService.GROUP.equals(module.getType())){
								node.put("items", new JSONArray());
							}else
							if(ModuleService.MODULE.equals(module.getType())){
								node.put("name", module.getUrl().replaceFirst("^/[^/]+/", ""));  //去除context
								node.put("url", module.getUrl());
								node.put("icon", module.getImage());
								node.put("description", module.getDescription());
								
								//如果是模块，就添加到搜索池里
								JSONObject searchNode = new JSONObject();
								searchNode.put("fullName", node.getString("name"));
								searchNode.put("name", node.getString("title"));
								searchNode.put("url", "#!" + node.getString("url").replaceFirst("/ass/", "/casass/"));  //EXT搜索的URL格式
								searchNode.put("icon", "icon-singleton");
								searchNode.put("sort", 3);
								searchNode.put("meta", new JSONObject());
								json.getJSONArray("search").add(searchNode);
							}
														
							//递归
							this.recursive(node);
							
							//添加到树中
							if(!treeNode.has("items")){
								treeNode.put("items", new JSONArray());
							}
							JSONArray items = treeNode.getJSONArray("items");
							items.add(node);
						}
					}
				}
			};
			treeRecursiveHandle.recursive(json);
			
			//手动整理各个子系统的模块
			JSONArray items = json.getJSONArray("items");
			for(int i=0;i<items.size();i++){
				JSONObject system = items.getJSONObject(i);
				JSONArray module = system.getJSONArray("items");
				String name = system.getString("title");
				
				if(SysAdmEnum.casass.getName().equals(name)){
					json.put(SysAdmEnum.casass.getCode(), module);
				}else
				if(SysAdmEnum.shopmng.getName().equals(name)){
					json.put(SysAdmEnum.shopmng.getCode(), getLocUrlModule(module, SysAdmEnum.shopmng.getCode(), sysname, sysurl));
				}else
				if(SysAdmEnum.erpcrm.getName().equals(name)){
					json.put(SysAdmEnum.erpcrm.getCode(), getLocUrlModule(module, SysAdmEnum.erpcrm.getCode(), sysname, sysurl));
				}
			}
			
			//删除临时数据
			json.remove("items");

		} catch (Exception e) {
			throw new RpcException("查询模块出错", e);
		}
		
		return json;
	}
	
	
	/**
	 * 获取操作功能列表
	 * @param user 用户
	 * @param moduleId 模块ID
	 * @return 返回操作功能列表
	 */
	public JSONObject getFunctionByModuleIdOfJSON(Admin user, int moduleId) {
		// 操作功能列表
		JSONObject json = new JSONObject();
		
		//模块列表
		List<Module> list = moduleService.getModuleListByUser(user, false);		
		for(Module module : list){		
			//获取有权限的操作功能和操作常量
			if(module.getPid().equals(moduleId)){
				if(ModuleService.FUNCTION.equals(module.getType())){
					json.put(module.getText(), module.getUrl());
				}
			}
		}

		return json;
	}
	
	
	
	/**
	 * 如果是本地系统，将模块更改为本地的URL，如果不是，则不做更改
	 * @param module 系统下的模块列表
	 * @param thanSysname 要比对的系统
	 * @param sysname 当前的系统
	 * @param sysurl 当前系统的URL
	 * @return 返回本地系统修改过的URL
	 * @throws Exception
	 */
	private JSONArray getLocUrlModule(JSONArray module, String thanSysname, final String sysname, final String sysurl) throws Exception{
		if(StringUtils.isNotBlank(thanSysname) && StringUtils.isNotBlank(sysname) && StringUtils.isNotBlank(sysurl)){
			if(sysname.equals(thanSysname)){
				TreeRecursiveHandle<JSONArray> trh = new TreeRecursiveHandle<JSONArray>(){
					public void recursive(JSONArray treeNode) throws Exception{
						for(int i=0;i<treeNode.size();i++){
							JSONObject node = treeNode.getJSONObject(i);
							if(node.has("url") && StringUtils.isNotBlank(node.getString("url"))){
								node.put("url", sysurl +"/"+ node.getString("url").replaceFirst("^/[^/]+/", ""));
							}
							if(node.has("items")){
								this.recursive(node.getJSONArray("items"));
							}
						}
					}
				};
				trh.recursive(module);
			}
		}
		return module;
	}
	
}
