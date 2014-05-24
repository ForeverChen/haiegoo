package com.haiegoo.ucenter.service.user;

import com.haiegoo.ucenter.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 后台用户服务类
 * @author Linpn
 */
public interface UserService extends UserDetailsService, Serializable {
	/**
	 * 获取后台用户
	 * @param userId 后台用户ID
	 * @return 返回后台用户对象
	 */
	public User getUser(int userId);

	/**
	 * 获取后台用户
	 * @param username/code 后台用户名或用户工号
	 * @return 返回后台用户对象
	 */
	public User getUser(String username);

	/**
	 * 获取后台用户
	 * @param username/code 后台用户名或用户工号
	 * @param password 密码
	 * @return 返回后台用户对象
	 */
	public User getUser(String username, String password);
	
	/**
	 * 获取后台用户列表
	 * @param params 查询参数
	 * @param sorters 记录的排序，如sorters.put("id","desc")，该参数如果为空表示按默认排序
	 * @param start 查询的起始记录,可为null
	 * @param limit 查询的总记录数, 如果值为-1表示查询到最后,可为null
	 * @return 返回后台用户列表
	 */
	public List<User> getUserList(Map<String,Object> params, Map<String,String> sorters, Long start, Long limit);
	
	/**
	 * 获取用户总数
	 * @param params 查询参数
	 * @return
	 */
	public long getUserCount(Map<String, Object> params);
	
	/**
	 * 删除后台用户
	 * @param userId 后台用户ID
	 */
	public void delUser(int userId);
	
	/**
	 * 删除后台用户
	 * @param userIds 后台用户ID列表
	 */
	public void delUser(int[] userIds);

	/**
	 * 添加后台用户
	 * @param user 后台用户对象
	 * @return 返回插入的主键ID
	 */
	public long addUser(final User user);

	/**
	 * 修改后台用户
	 * @param user 后台用户对象
	 * @return 返回受影响的行数
	 */
	public long editUser(final User user);
}
