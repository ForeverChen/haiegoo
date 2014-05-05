package com.haiegoo.shopping.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.shopping.model.product.Brand;
import com.haiegoo.shopping.service.product.BrandService;

public class BrandServiceImpl extends BaseService implements BrandService {
	
	private static final long serialVersionUID = -3766551616822925785L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientShopping;

	@Override
	public Brand getBrand(int id) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			
			List<Brand> list = this.getBrandList(params);
			if(list==null || list.isEmpty()){
				return null;
			}
			
			return list.get(0);
			
		}catch(Exception e){
			throw new RpcException("查询品牌出错", e);
		}
	}
	
	@Override
	public List<Brand> getBrandList() {
		return this.getBrandList(null);
	}
	
	@Override
	public List<Brand> getBrandList(Map<String, Object> params) {
		try{
			@SuppressWarnings("unchecked")
			List<Brand> list = this.sqlMapClientShopping.queryForList("brand.getBrand", params);
			return list;
		}catch(Exception e){
			throw new RpcException("查询品牌出错", e);
		}
	}

	@Override
	public void delBrand(int id) {
		try{
			this.sqlMapClientShopping.delete("brand.delBrand", id);
		}catch(Exception e){
			throw new RpcException("删除品牌出错", e);
		}
	}

	@Override
	public Brand addBrand(Brand brand) {
		try{
			Integer id = (Integer) this.sqlMapClientShopping.insert("brand.addBrand", brand);
			
			//更新排序
			brand.setId(id);
			brand.setSort(id);
			this.editBrand(brand);
			
			return brand;
			
        }catch(DuplicateKeyException e){
			throw new RpcException("品牌名称重复", e);
		}catch(Exception e){
			throw new RpcException("添加品牌出错", e);
		}
	}

	@Override
	public Brand editBrand(Brand brand) {
		try{
			this.sqlMapClientShopping.update("brand.editBrand", brand);
			return brand;
			
        }catch(DuplicateKeyException e){
			throw new RpcException("品牌名称重复", e);
		}catch(Exception e){
			throw new RpcException("编辑品牌出错", e);
		}
	}
}
