package com.haiegoo.ucenter.service.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.haiegoo.ucenter.model.admin.Role;

/**
 * 后台角色服务类
 * @author Linpn
 */
public interface RoleService extends Serializable {
	
	/** 管理员 */
	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	/** 普通用户 */
	public final static String ROLE_USER = "ROLE_USER";
	
	
	/**
	 * 获取后台角色
	 * @param roleId 后台角色ID
	 * @return 返回后台角色对象
	 */
	public Role getRole(int roleId);
	
	/**
	 * 获取后台角色
	 * @param name 后台角色名
	 * @return 返回后台角色对象
	 */
	public Role getRole(String name);

	/**
	 * 获取后台角色列表
	 * @return 返回后台角色列表
	 */
	public List<Role> getRoleList();

	/**
	 * 获取后台角色列表
	 * @param params 查询参数
	 * @return 返回后台角色列表
	 */
	public List<Role> getRoleList(Map<String,Object> params);

	/**
	 * 获取后台角色列表
	 * @param userId 后台用户ID
	 * @return 返回后台角色列表
	 */
	public List<Role> getRoleListByUserId(int userId);

	/**
	 * 删除后台角色
	 * @param roleId 后台角色ID
	 */
	public void delRole(int roleId);
	
	/**
	 * 删除后台角色
	 * @param roleIds 后台角色ID列表
	 */
	public void delRole(int[] roleIds);

	/**
	 * 添加后台角色
	 * @param role 后台角色对象
	 * @return 返回添加后的对象
	 */
	public Role addRole(final Role role, boolean syncModule);

	/**
	 * 修改后台角色
	 * @param role 后台角色对象
	 * @return 返回修改后的对象
	 */
	public Role editRole(final Role role, boolean syncModule);
	
}
