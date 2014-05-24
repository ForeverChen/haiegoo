package com.haiegoo.shopping.service.product;

import com.haiegoo.shopping.model.product.Brand;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BrandService extends Serializable {
	
	/**
	 * 根据brandId获取品牌
	 * @param brandId
	 * @return
	 */
	Brand getBrand(int brandId);

	/**
	 * 获取品牌列表
	 * @return
	 */
	List<Brand> getBrandList();
	
	/**
	 * 获取品牌列表
	 * @param params 查询参数
	 * @return
	 */
	List<Brand> getBrandList(Map<String,Object> params);
	
	/**
	 * 删除品牌信息
	 * @param id 品牌信息ID
	 */
	void delBrand(int id);
	
	/**
	 * 添加品牌信息
	 * @param brand 品牌信息
	 */
	Brand addBrand(Brand brand);
	
	/**
	 * 修改品牌信息
	 * @param brand 品牌信息
	 */
	Brand editBrand(Brand brand);
}
