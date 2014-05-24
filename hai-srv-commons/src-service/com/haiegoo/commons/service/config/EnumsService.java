package com.haiegoo.commons.service.config;

import com.haiegoo.commons.model.config.Enums;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 枚举服务接口
 * @author Linpn
 */
public interface EnumsService extends Serializable {
	
	/**
	 * 获取枚举
	 * @param clazz 枚举类, 全名, 如: com.haiegoo.commons.enums.Status
	 * @return 返回枚举
	 */
	public List<Enums> getEnums(String clazz);
	
	/**
	 * 获取枚举
	 * @param clazz 枚举类, 全名, 如: com.haiegoo.commons.enums.Status
	 * @param key 枚举键,英文
	 * @return 返回枚举元素
	 */
	public Enums getEnumsByKey(String clazz, String key);
	
	/**
	 * 获取枚举
	 * @param clazz 枚举类, 全名, 如: com.haiegoo.commons.enums.Status
	 * @param code 枚举编码,数字
	 * @return 返回枚举元素
	 */
	public Enums getEnumsByCode(String clazz, String code);
	
	/**
	 * 获取枚举
	 * @param clazz 枚举类, 全名, 如: com.haiegoo.commons.enums.Status
	 * @param text 枚举名称,中文,一般用于显示
	 * @return 返回枚举元素
	 */
	public Enums getEnumsByText(String clazz, String text);

	
	/**
	 * 获取枚举
	 * @param id 枚举ID
	 * @return 返回枚举对象
	 */
	public Enums getEnums(int id);
	
	/**
	 * 获取枚举列表
	 * @return 返回枚举列表
	 */
	public List<Enums> getEnumsList();
	
	/**
	 * 获取枚举列表
	 * @param params 查询参数
	 * @return 返回枚举列表
	 */
	public List<Enums> getEnumsList(Map<String,Object> params);
	
	/**
	 * 删除枚举
	 * @param id 枚举ID
	 */
	public void delEnums(int id);
	
	/**
	 * 删除枚举
	 * @param ids 参数ID列表
	 */
	public void delEnums(int[] ids);
	
	/**
	 * 添加枚举
	 * @param enums 枚举对象
	 */
	public void addEnums(Enums enums);	
	
	/**
	 * 修改枚举
	 * @param enums 枚举对象
	 */
	public void editEnums(Enums enums);	

}
