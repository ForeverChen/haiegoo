package com.haiegoo.ucenter.service.admin;

import com.haiegoo.ucenter.model.admin.Admin;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 后台用户服务类
 * @author Linpn
 */
public interface AdminService extends UserDetailsService, Serializable {
	/**
	 * 获取后台用户
	 * @param userId 后台用户ID
	 * @return 返回后台用户对象
	 */
	public Admin getAdmin(int userId);

	/**
	 * 获取后台用户
	 * @param username/code 后台用户名或用户工号
	 * @return 返回后台用户对象
	 */
	public Admin getAdmin(String username);

	/**
	 * 获取后台用户
	 * @param username/code 后台用户名或用户工号
	 * @param password 密码
	 * @return 返回后台用户对象
	 */
	public Admin getAdmin(String username, String password);
	
	/**
	 * 获取后台用户列表
	 * @param params 查询参数
	 * @param sorters 记录的排序，如sorters.put("id","desc")，该参数如果为空表示按默认排序
	 * @param start 查询的起始记录,可为null
	 * @param limit 查询的总记录数, 如果值为-1表示查询到最后,可为null
	 * @return 返回后台用户列表
	 */
	public List<Admin> getAdminList(Map<String,Object> params, Map<String,String> sorters, Long start, Long limit);
	
	/**
	 * 获取用户总数
	 * @param params 查询参数
	 * @return
	 */
	public long getAdminCount(Map<String, Object> params);

	/**
	 * 获取后台角色列表
	 * @param roleId 后台角色ID
	 * @return 返回后台用户列表
	 */
	public List<Admin> getAdminListByRoleId(int roleId);
	
	/**
	 * 删除后台用户
	 * @param userId 后台用户ID
	 */
	public void delAdmin(int userId);
	
	/**
	 * 删除后台用户
	 * @param userIds 后台用户ID列表
	 */
	public void delAdmin(Integer[] userIds);

	/**
	 * 添加后台用户
	 * @param users 后台用户对象
	 * @return 返回插入的主键ID
	 */
	public int addAdmin(final Admin users);

	/**
	 * 添加后台用户
	 * @param users 后台用户对象
	 * @param syncRole 是否同步角色
	 * @param syncModule 是否同步模块
	 * @return 返回插入的主键ID
	 */
	public int addAdmin(final Admin users, boolean syncRole);

	/**
	 * 修改后台用户
	 * @param users 后台用户对象
	 * @return 返回受影响的行数
	 */
	public int editAdmin(final Admin users);

	/**
	 * 修改后台用户
	 * @param users 后台用户对象
	 * @param syncRole 是否同步角色
	 * @param syncModule 是否同步模块
	 * @return 返回受影响的行数
	 */
	public int editAdmin(final Admin users, boolean syncRole);
	
	/**
	 * 获取spring security用户权限资源缓存
	 */
	public Map<String, Collection<ConfigAttribute>> getSecuritySource();
	
	/**
	 * 清空spring security用户权限资源缓存
	 */
	public void clearSecuritySource();
}
