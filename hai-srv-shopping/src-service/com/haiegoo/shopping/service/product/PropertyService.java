package com.haiegoo.shopping.service.product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.haiegoo.shopping.model.product.Property;

public interface PropertyService extends Serializable {
	/**
	 * 获取 属性
	 */
	public Property getProperty(Integer id);
	/**
	 * 获取属性列表
	 */
	public List<Property> getPropertyList();
	/**
	 * 获取属性列表
	 */
	public List<Property> getPropertyList(Map<String, Object> params);
    /**
     * 获取属性列表
     */
    public List<Property> getPropertyListByPid(Integer pid);
    /**
     * 删除属性
     */
    public void delProperty(String path);
    /**
     * 删除属性
     */
    public void delProperty(Integer propIds);
	/**
	 * 添加属性
	 */
	public Property addProperty(Property prop);
	/**
     * 更新属性
     */
    public Property editProperty(Property prop);
}
