package com.haiegoo.ucenter.service.admin.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.model.admin.RoleModule;
import com.haiegoo.ucenter.service.admin.RoleService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 后台角色服务类
 * 
 * @author Linpn
 */
public class RoleServiceImpl extends BaseService implements RoleService {

	private static final long serialVersionUID = -6044363077079748792L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientAdmin;
	
	@Override
	public Role getRole(int roleId) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			Role role = (Role) this.sqlMapClientAdmin.queryForObject("role.getRole", params);
			return role;
		} catch (Exception e) {
			throw new RpcException("查询角色出错", e);
		}
	}

	@Override
	public Role getRole(String name) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			Role role = (Role) this.sqlMapClientAdmin.queryForObject("role.getRole", params);
			return role;
		} catch (Exception e) {
			throw new RpcException("查询角色出错", e);
		}
	}

	@Override
	public List<Role> getRoleList() {
		return this.getRoleList(null);
	}

	@Override
	public List<Role> getRoleList(Map<String, Object> params) {
		try {
			@SuppressWarnings("unchecked")
			List<Role> list = this.sqlMapClientAdmin.queryForList("role.getRole", params);
			return list;
		} catch (Exception e) {
			throw new RpcException("查询角色出错", e);
		}
	}

	@Override
	public List<Role> getRoleListByUserId(int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);

		List<Role> list = this.getRoleList(params);
		return list;
	}

	@Override
	@Transactional
	public void delRole(int roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", roleId);

		List<Role> list = this.getRoleList(params);
		if (list.size() > 0) {
			throw new RpcException("请先删除子节点");
		}

		this.delRole(new int[] { roleId });
	}

	@Override
	@Transactional
	public void delRole(int[] roleIds) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleIds", roleIds);

			this.sqlMapClientAdmin.delete("role.delRole", params);
			this.sqlMapClientAdmin.delete("admin_role.delAdminRole",params);
			this.sqlMapClientAdmin.delete("role_module.delRoleModule", params);

		} catch (Exception e) {
			throw new RpcException("删除角色出错", e);
		}

	}

	@Override
	@Transactional
	public Role addRole(final Role role, boolean syncModule) {
		try {
			// 添加角色
			int roleId = (Integer) this.sqlMapClientAdmin.insert("role.addRole", role);

			// 更新排序
			role.setRoleId(roleId);
			role.setSort(Integer.valueOf(role.getPid().toString() + roleId));
			this.editRole(role, syncModule);
			
			return role;
			
		} catch (DuplicateKeyException e) {
			throw new RpcException("角色名称重复", e);
		} catch (Exception e) {
			throw new RpcException("添加角色出错", e);
		}
	}

	@Override
	@Transactional
	public Role editRole(final Role role, boolean syncModule) {
		try {
			// 编辑角色
			this.sqlMapClientAdmin.update("role.editRole", role);

			// 编辑角色模块记录
			if (syncModule) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roleIds", new int[] { role.getRoleId() });

				this.sqlMapClientAdmin.delete("role_module.delRoleModule", params);
				this.sqlMapClientAdmin.execute(
					new SqlMapClientCallback<Integer>() {
						@Override
						public Integer doInSqlMapClient(SqlMapExecutor executor)throws SQLException {
							executor.startBatch();

							for (Module module : role.getModules()) {
								RoleModule roleModule = new RoleModule();
								roleModule.setRoleId(role.getRoleId());
								roleModule.setModuleId(module.getModuleId());
								executor.insert("role_module.addRoleModule", roleModule);
							}

							return executor.executeBatch();
						}
					}
				);
			}
			
			return role;

		} catch (DuplicateKeyException e) {
			throw new RpcException("角色名称重复", e);
		} catch (Exception e) {
			throw new RpcException("编辑角色出错", e);
		}
	}

}
