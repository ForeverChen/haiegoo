package com.haiegoo.ucenter.service.admin.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.ucenter.model.admin.Admin;
import com.haiegoo.ucenter.model.admin.AdminRole;
import com.haiegoo.ucenter.model.admin.Module;
import com.haiegoo.ucenter.model.admin.Role;
import com.haiegoo.ucenter.service.admin.AdminService;
import com.haiegoo.ucenter.service.admin.ModuleService;
import com.haiegoo.ucenter.service.admin.RoleService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeoutException;


/**
 * 后台用户服务类
 * @author Linpn
 */
public class AdminServiceImpl extends BaseService implements AdminService, UserDetailsService {

	private static final long serialVersionUID = -5609524598183928386L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientAdmin;	
	@Resource
	private RoleService roleService;
	@Resource
	private ModuleService moduleService;

	private Md5PasswordEncoder md5 = new Md5PasswordEncoder();	
	private final static String SPRING_SECURITY_METADATA_SOURCE = "SPRING_SECURITY_METADATA_SOURCE";
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
    	// 获取用户
    	Admin user = this.getAdmin(username);
    	user.setPassword("");
//    	if(user!=null){
//	    	user.setLastLoginIp(lastLoginIp);
//	    	user.setLastLoginTime(new Date());
//	    	user.setVisitCount(user.getVisitCount()+1);
//	    	this.editAdmin(user, false);
//    	}
    	
    	return  user;
	}

	@Override
	public Admin getAdmin(int userId) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			Admin admin = (Admin)this.sqlMapClientAdmin.queryForObject("admin.getAdmin", params);
			return admin;
		}catch(Exception e){
			throw new RpcException("查询用户出错", e);
		}
	}

	@Override
	public Admin getAdmin(String username) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("username", username);
			Admin admin = (Admin)this.sqlMapClientAdmin.queryForObject("admin.getAdmin", params);
			return admin;
		}catch(Exception e){
			throw new RpcException("查询用户出错", e);
		}
	}

	@Override
	public Admin getAdmin(String username, String password) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("username", username);
			params.put("password", md5.encodePassword(password, username));
			Admin admin = (Admin)this.sqlMapClientAdmin.queryForObject("admin.getAdmin", params);
			return admin;
		}catch(Exception e){
			throw new RpcException("查询用户出错", e);
		}
	}

	@Override
	public List<Admin> getAdminList(Map<String, Object> params, Map<String, String> sorters, Long start, Long limit) {
		try{
			if(params==null)
				params = new HashMap<String,Object>();

			params.put("sort", this.getDbSort(sorters));
			params.put("start", start);
			params.put("limit", limit);
			
			@SuppressWarnings("unchecked")
			List<Admin> list = this.sqlMapClientAdmin.queryForList("admin.getAdmin", params);
			return list;
			
		}catch(Exception e){
			throw new RpcException("查询用户出错", e);
		}
	}
	
	@Override
	public long getAdminCount(Map<String, Object> params) {
		try{
			long count = (Long)this.sqlMapClientAdmin.queryForObject("admin.getAdminCount", params);			
			return count;		
		}catch(Exception e){
			throw new RpcException("查询用户出错", e);
		}
	}
	
	
	@Override
	public List<Admin> getAdminListByRoleId(int roleId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleId", roleId);
		
		List<Admin> list = this.getAdminList(params, null, null, null);
		return list;
	}

	@Override
	@Transactional
	public void delAdmin(int userId) {
		this.delAdmin(new Integer[]{userId});
	}	

	@Override
	@Transactional
	public void delAdmin(Integer[] userIds) {
		try{
			if(userIds==null || userIds.length<=0)
				throw new RpcException("userIds不能为空");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userIds", userIds);
			
			this.sqlMapClientAdmin.delete("admin.delAdmin", params);
			
		}catch(Exception e){
			throw new RpcException("删除用户出错", e);
		}
	}
	
	@Override
	@Transactional
	public int addAdmin(final Admin users) {
		return this.addAdmin(users, false);		
	}
	
	@Override
	@Transactional
	public int addAdmin(final Admin users, boolean syncRole) {
		try{
			// 添加用户记录
			int userId = (Integer)this.sqlMapClientAdmin.insert("admin.addAdmin", users);		
			users.setUserId(userId);	

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userIds", new int[]{users.getUserId()});
			
			// 添加角色记录
			if(syncRole){
				this.sqlMapClientAdmin.delete("admin_role.delAdminRole", params);
				this.sqlMapClientAdmin.execute(new SqlMapClientCallback<Integer>(){
					@Override
					public Integer doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						
						for(Role role : users.getRoles()){
							AdminRole adminRole = new AdminRole();
							adminRole.setUserId(users.getUserId());
							adminRole.setRoleId(role.getRoleId());
							executor.insert("admin_role.addAdminRole", adminRole);
						}
						
						return executor.executeBatch();
					}
				});
			}
			
			return userId;

        }catch(DuplicateKeyException e){
			throw new RpcException("用户名或工号重复", e);	
        } catch (Exception e) {
			throw new RpcException("添加用户出错", e);
		}
	}

	@Override
	@Transactional
	public int editAdmin(final Admin users) {
		return this.editAdmin(users, false);
	}

	@Override
	@Transactional
	public int editAdmin(final Admin users, boolean syncRole) {
		try{
			// 编辑用户记录
			int count = this.sqlMapClientAdmin.update("admin.editAdmin", users);

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userIds", new int[]{users.getUserId()});			

			// 编辑角色记录
			if(syncRole){
				this.sqlMapClientAdmin.delete("admin_role.delAdminRole", params);
				this.sqlMapClientAdmin.execute(new SqlMapClientCallback<Integer>(){
					@Override
					public Integer doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						
						for(Role role : users.getRoles()){
							AdminRole adminRole = new AdminRole();
							adminRole.setUserId(users.getUserId());
							adminRole.setRoleId(role.getRoleId());
							executor.insert("admin_role.addAdminRole", adminRole);
						}
						
						return executor.executeBatch();
					}
				});
			}
			
			return count;

        }catch(DuplicateKeyException e){
			throw new RpcException("用户名或工号重复", e);
        } catch (Exception e) {
			throw new RpcException("编辑用户出错", e);
		}
	}
	

	/**
	 * 获取spring security用户权限资源
	 */
	public Map<String, Collection<ConfigAttribute>> getSecuritySource() {
		Map<String, Collection<ConfigAttribute>> httpMethodMap = null;
		
		try{
			//从Memcached获取资源
			httpMethodMap = this.getMemcachedClient().get(SPRING_SECURITY_METADATA_SOURCE);
			
			//如果资源为空，则重新载入
			if(httpMethodMap==null || httpMethodMap.size()==0){
				httpMethodMap = this.loadSecuritySource();
			}
			
		}catch(Exception e){
			logger.error("获取spring security用户权限资源 出错", e);
		}
		
		return httpMethodMap;
	}
	

	/**
	 * 清空spring security用户权限资源
	 */
	public void clearSecuritySource() {
		try {
			this.getMemcachedClient().delete(SPRING_SECURITY_METADATA_SOURCE);
		} catch (Exception e) {
			logger.error("清空spring security用户权限资源 出错", e);
		}
	}

	
	
	/**
	 * 同步载入spring security用户权限资源
	 */
	private synchronized Map<String, Collection<ConfigAttribute>> loadSecuritySource() throws TimeoutException, InterruptedException, MemcachedException{
		//同步完再做一次判断
		Map<String, Collection<ConfigAttribute>> httpMethodMap = this.getMemcachedClient().get(SPRING_SECURITY_METADATA_SOURCE);
		
		//为空，读取数据库
		if(httpMethodMap==null || httpMethodMap.size()==0){
			httpMethodMap = new HashMap<String, Collection<ConfigAttribute>>();
			
			//获取全部资源
			List<Module> moduleList = this.moduleService.getModuleList(null);
			for (Module module : moduleList) {
				if(ModuleService.MODULE.equals(module.getType()) || ModuleService.FUNCTION.equals(module.getType())){		
					if(module.getUrl().trim().equals(""))
						continue;
					
					//获取资源
					Collection<ConfigAttribute> atts = httpMethodMap.get(module.getUrl());
					if(atts==null){
						httpMethodMap.put(module.getUrl(), new ArrayList<ConfigAttribute>());
					}
				}
			}
			
			//获取角色中的资源
			List<Role> roleList = this.roleService.getRoleList(null);
			for (Role role : roleList) {
				moduleList = role.getModules();
				for (Module module : moduleList) {
					if(ModuleService.MODULE.equals(module.getType()) || ModuleService.FUNCTION.equals(module.getType())){	
						if(module.getUrl().trim().equals(""))
							continue;
						
						//获取资源
						Collection<ConfigAttribute> atts = httpMethodMap.get(module.getUrl());
						if(atts==null){
							httpMethodMap.put(module.getUrl(), new ArrayList<ConfigAttribute>());
							atts = httpMethodMap.get(module.getUrl());
						}
						
						//添加资源角色，并判断资源中是否已经存在角色
						boolean isHad = false;
						for(ConfigAttribute ca : atts){
							if(ca.getAttribute().equals(role.getName())){
								isHad = true;
								break;
							}
						}
						if(!isHad){
							atts.add(new SecurityConfig(role.getName()));
						}						
					}
				}
			}
			
			//将资源保存到Memcached
			this.getMemcachedClient().set(SPRING_SECURITY_METADATA_SOURCE, 3600, httpMethodMap);
		}
		
		return httpMethodMap;
	}
}
