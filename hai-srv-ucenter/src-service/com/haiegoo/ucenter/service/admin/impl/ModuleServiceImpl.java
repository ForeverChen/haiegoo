package com.haiegoo.ucenter.service.admin.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.service.admin.AdminService;
import com.haiegoo.ucenter.service.admin.ModuleService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 功能模块服务类
 * @author Linpn
 */
public class ModuleServiceImpl extends BaseService implements ModuleService {

	private static final long serialVersionUID = 2218426418456488310L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientAdmin;
	
	@Resource
	private AdminService adminService;
	
	/**
	 * 获取功能模块
	 * @param moduleId 功能模块ID
	 * @return 返回功能模块对象
	 */
	@Override
	public Module getModule(int moduleId) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("moduleId", moduleId);
			Module module = (Module)this.sqlMapClientAdmin.queryForObject("module.getModule", params);
			return module;
		}catch(Exception e){
			throw new RpcException("查询模块出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Module getModule(String text) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("text", text);
			List<Module> list = this.sqlMapClientAdmin.queryForList("module.getModule", params);
			if(list!=null && list.size()>0)
				return list.get(0);
			else
				return null;
		}catch(Exception e){
			throw new RpcException("查询模块出错", e);
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Module getModuleByCode(String code) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("code", code);
			List<Module> list = this.sqlMapClientAdmin.queryForList("module.getModule", params);
			if(list!=null && list.size()>0)
				return list.get(0);
			else
				return null;
		}catch(Exception e){
			throw new RpcException("查询模块出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Module getModuleByUrl(String url) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("url", url);
			List<Module> list = this.sqlMapClientAdmin.queryForList("module.getModule", params);
			for(Module module : list){
				if(module.getUrl().trim().equals(url.trim()))
					return module;
			}
			return null;
		}catch(Exception e){
			throw new RpcException("查询模块出错", e);
		}
	}

	@Override
	public List<Module> getModuleListByType(String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		return this.getModuleList(params);
	}
	
	@Override
	public List<Module> getModuleList() {
		return this.getModuleList(null);
	}
	
	@Override
	public List<Module> getModuleList(Map<String,Object> params) {
		try{
			@SuppressWarnings("unchecked")
			List<Module> list = this.sqlMapClientAdmin.queryForList("module.getModule", params);
			return list;			
		}catch(Exception e){
			throw new RpcException("查询模块出错", e);
		}
	}
	

	
	@Override
	public List<Module> getModuleListByPid(int pid){
		// 子模块列表
		List<Module> listSubModule = new ArrayList<Module>();
		
		//模块列表
		List<Module> list = this.getModuleList();		
		for(Module module : list){		
			//添加最叶子节点到列表中
			if(module.getPid().equals(pid)){
				listSubModule.add(module);
			}
		}
		
		return listSubModule;
	}
	
	@Override
	public List<Module> getModuleListByUser(Admin user, boolean requery) {
		// 模块列表
		List<Module> list = new ArrayList<Module>();
		
		// 重新查询
		if(requery){
			user = adminService.getAdmin(user.getUserId());
		}
		
		// 获取用色中的模块列表
		List<Role> roleList = user.getRoles();
		for(Role role : roleList){
			list.addAll(role.getModules());
		}			

		//去除重复
		list = removeRepeatModule(list);
		
		return list;
	}
	
	@Override
	public List<Module> getModuleListByRoleIds(int[] roleIds) {
		if(roleIds==null || roleIds.length<=0)
			return new ArrayList<Module>();
		
		// 模块列表
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleIds", roleIds);			
		List<Module> list = this.getModuleList(params);
		
		//去除重复
		list = removeRepeatModule(list);
		
		return list;
	}
	
	
	@Override
	@Transactional
	public void delModule(int moduleId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", moduleId);
		
		List<Module>	list = this.getModuleList(params);
		if(list.size()>0){
			throw new RpcException("请先删除子节点");
		}
		
		this.delModule(new int[]{moduleId});
	}
	
	@Override
	@Transactional
	public void delModule(int[] moduleIds) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("moduleIds", moduleIds);
			
			this.sqlMapClientAdmin.delete("module.delModule", params);
			this.sqlMapClientAdmin.delete("role_module.delRoleModule", params);
		}catch(Exception e){
			throw new RpcException("删除模块失败", e);
		}
	}

	
	@Override
	@Transactional
	public Module addModule(Module module) {
		try{
			int moduleId = (Integer)this.sqlMapClientAdmin.insert("module.addModule", module);
			
			//更新排序
			module.setModuleId(moduleId);
			module.setSort(Integer.valueOf(module.getPid().toString() + moduleId));
			this.editModule(module);

			return module;
			
        }catch(DuplicateKeyException e){
			throw new RpcException("模块名称或编号重复", e);	
		}catch(Exception e){
			throw new RpcException("添加模块失败", e);
		}
	}

	@Override
	@Transactional
	public Module editModule(Module module) {
		try{
			this.sqlMapClientAdmin.update("module.editModule", module);
			return module;

        }catch(DuplicateKeyException e){
			throw new RpcException("模块名称或编号重复", e);
		}catch(Exception e){
			throw new RpcException("编辑模块失败", e);
		}
	}

	
	/**
	 * 去除重复模块
	 * @param list 模块列表
	 * @return
	 */
	private List<Module> removeRepeatModule(List<Module> list) {
		// 去除重复
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getModuleId().equals(list.get(i).getModuleId()))
					list.remove(j);
			}
		}
		
		// 排序
		Collections.sort(list, new Comparator<Module>() {
			@Override
			public int compare(Module o1, Module o2) {
				return o1.getSort().compareTo(o2.getSort());
			}
		});		

		return list;
	}

}
