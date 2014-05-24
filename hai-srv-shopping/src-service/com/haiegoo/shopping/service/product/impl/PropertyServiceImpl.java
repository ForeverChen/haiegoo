package com.haiegoo.shopping.service.product.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.shopping.model.product.Property;
import com.haiegoo.shopping.service.product.PropertyService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyServiceImpl extends BaseService implements PropertyService {

	private static final long serialVersionUID = 903012848202090356L;
	
	@Resource
	protected SqlMapClientTemplate sqlMapClientShopping;

	@Override
	public Property getProperty(Integer id) {
		List<Property> list = this.getPropertyList();
		for(Property prop : list){
			if(prop.getId().equals(id))
				return prop;
		}
		return null;
	}
	
	@Override
	public List<Property> getPropertyList() {
		return this.getPropertyList(null);
	}

	@Override
    public List<Property> getPropertyList(Map<String, Object> params) {
        try {
            if (params == null) 
                params = new HashMap<String, Object>();
            
            @SuppressWarnings("unchecked")
			List<Property> list = this.sqlMapClientShopping.queryForList("property.getProperty", params);
            return list;
            
        } catch (Exception e) {
			throw new RpcException("查询属性出错", e);
        }
    }

	@Override
	public List<Property> getPropertyListByPid(Integer pid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		List<Property> list = this.getPropertyList(params);
		return list;
	}
	
	@Override
	public void delProperty(Integer id) {
		Property property = this.getProperty(id);
		if (property != null){
			this.delProperty(property.getPath());
		}
	}

	@Override
	public void delProperty(String path) {
		try {
        	this.sqlMapClientShopping.delete("property.delProperty", path);
        } catch (Exception e) {
			throw new RpcException("删除属性出错", e);
        }
	}

	@Override
	public Property addProperty(Property prop) {
		try {
            Integer id = (Integer)this.sqlMapClientShopping.insert("property.addProperty", prop);

			//更新排序
            prop.setId(id);
            prop.setSort(id);
            if(prop.getPid()==0) {
            	prop.setPath(id+"");
            }else {
            	prop.setPath(this.getProperty(prop.getPid()).getPath()+"/"+id);
            }            
            this.editProperty(prop);
            
            return prop;
            
        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的属性名称重复", e);
        } catch (Exception e) {
			throw new RpcException("添加属性出错", e);
        }
	}
	
	@Override
	public Property editProperty(Property prop) {
		try {
            this.sqlMapClientShopping.update("property.editProperty", prop);
            return prop;
            
        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的属性名称重复", e);
        } catch (Exception e) {
			throw new RpcException("编辑属性出错", e);
        }
	}
}
