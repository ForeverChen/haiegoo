package com.haiegoo.shopping.service.product;

import com.haiegoo.shopping.model.product.Category;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CategoryService extends Serializable {
	
	/**
	 * 根据id获取商品分类
	 * @param id
	 * @return
	 */
	Category getCategory(int id);
	
	/**
	 * 获取商品分类列表
	 * @return
	 */
	List<Category> getCategoryList();
	
	/**
	 * 获取商品分类列表
	 * @param params 查询参数
	 * @return
	 */
	List<Category> getCategoryList(Map<String,Object> params);
	

	/**
	 * 将path转成中文格式，中间用'>'分隔
	 * @param path
	 * @return
	 */
	String getPathNameByPath(String path);
	
	
	/**
	 * 删除商品分类信息
	 * @param id 商品分类信息ID
	 */
	void delCategory(Integer id);
	
	/**
	 * 删除商品分类信息
	 * @param path 商品分类路径
	 */
	void delCategory(String path);
	
	/**
	 * 添加商品分类信息
	 * @param Category 商品分类信息
	 * @return 返回添加后的对象
	 */
	Category addCategory(Category Category);	
	
	/**
	 * 修改商品分类信息
	 * @param Category 商品分类信息
	 * @return 返回修改后的对象
	 */
	Category editCategory(Category Category);
}
