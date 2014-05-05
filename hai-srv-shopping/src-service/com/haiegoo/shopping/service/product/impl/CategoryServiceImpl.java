package com.haiegoo.shopping.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.shopping.model.product.Category;
import com.haiegoo.shopping.service.product.CategoryService;

public class CategoryServiceImpl extends BaseService implements CategoryService {

	private static final long serialVersionUID = -8031284549098387291L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientShopping;

	@Override
	public Category getCategory(int id) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			List<Category> list = this.getCategoryList(params);
			if (list == null || list.isEmpty()) {
				return null;
			}
			
			return list.get(0);
			
		} catch (Exception e) {
			throw new RpcException("查询分类出错", e);
		}
	}

	@Override
	public List<Category> getCategoryList() {
		return this.getCategoryList(null);
	}

	@Override
	public List<Category> getCategoryList(Map<String, Object> params) {
		try {
			@SuppressWarnings("unchecked")
			List<Category> list = this.sqlMapClientShopping.queryForList(
					"category.getCategory", params);
			return list;
		} catch (Exception e) {
			throw new RpcException("查询分类出错", e);
		}
	}

	@Override
	public String getPathNameByPath(String path) {
		try{
			if(StringUtils.isNotBlank(path)){
		        String pathName = null;
		        String[] cateIds = path.split("/");
		        for(String cateId : cateIds){
		        	if(pathName==null){
		        		pathName = this.getCategory(Integer.valueOf(cateId)).getName();
		        	}else{
		        		pathName += " > " + this.getCategory(Integer.valueOf(cateId)).getName();
		        	}
		        }
		        return pathName;
			}else{
				return "";
			}
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public void delCategory(Integer id) {
		Category category = this.getCategory(id);
		if (category != null){
			this.delCategory(category.getPath());
		}
	}

	@Override
	public void delCategory(String path) {
		try {
			this.sqlMapClientShopping.delete("category.delCategory", path);
		} catch (Exception e) {
			throw new RpcException("删除分类出错", e);
		}
	}

	@Override
	public Category addCategory(Category category) {
		try {
			Integer id = (Integer) this.sqlMapClientShopping.insert("category.addCategory", category);
			
			//更新排序
			category.setId(id);
			category.setSort(id);
			if (category.getPid()==0) {
				category.setPath(id+"");
			}else {
				category.setPath(this.getCategory(category.getPid()).getPath()+"/"+id);
			}
			this.editCategory(category);
			
			return category;

        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的分类名称重复", e);
		} catch (Exception e) {
			throw new RpcException("添加分类出错", e);
		}
	}

	@Override
	public Category editCategory(Category category) {
		try {
			this.sqlMapClientShopping.update("category.editCategory", category);
			return category;
			
        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的分类名称重复", e);
		} catch (Exception e) {
			throw new RpcException("编辑分类出错", e);
		}
	}
}
