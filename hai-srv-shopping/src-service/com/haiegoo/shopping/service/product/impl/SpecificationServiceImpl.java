package com.haiegoo.shopping.service.product.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.shopping.model.product.Specification;
import com.haiegoo.shopping.service.product.SpecificationService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class SpecificationServiceImpl extends BaseService implements SpecificationService {

	private static final long serialVersionUID = -6264342136824470264L;
	
	@Resource
	protected SqlMapClientTemplate sqlMapClientShopping;

	
	@Override
	public Specification getSpecification(Integer id) {
		List<Specification> list = this.getSpecificationList();
		for(Specification spec : list){
			if(spec.getId().equals(id))
				return spec;
		}
		return null;
	}
	
	@Override
	public List<Specification> getSpecificationList() {
		return this.getSpecificationList(null);
	}

	@Override
    public List<Specification> getSpecificationList(Map<String, Object> params) {
        try {            
            @SuppressWarnings("unchecked")
			List<Specification> list = this.sqlMapClientShopping.queryForList("specification.getSpecification", params);
            return list;
            
        } catch (Exception e) {
			throw new RpcException("查询规格出错", e);
        }
    }

	@Override
	public void delSpecification(Integer id) {
		Specification specification = this.getSpecification(id);
		if (specification != null){
			this.delSpecification(specification.getPath());
		}
	}

	@Override
	public void delSpecification(String path) {
		try {
        	this.sqlMapClientShopping.delete("specification.delSpecification", path);
        } catch (Exception e) {
			throw new RpcException("删除规格出错", e);
        }
	}

	@Override
	public Specification addSpecification(Specification spec) {
		try {
            Integer id = (Integer)this.sqlMapClientShopping.insert("specification.addSpecification", spec);

			//更新排序
            spec.setId(id);
            spec.setSort(id);
            if(spec.getPid()==0) {
            	spec.setPath(id+"");
            }else {
            	spec.setPath(this.getSpecification(spec.getPid()).getPath()+"/"+id);
            }
            this.editSpecification(spec);
            
            return spec;
            
        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的规格名称重复", e);
        } catch (Exception e) {
			throw new RpcException("添加规格出错", e);
        }
	}

	@Override
	public Specification editSpecification(Specification spec) {
		try {
            this.sqlMapClientShopping.update("specification.editSpecification", spec);
            return spec;
            
        }catch(DuplicateKeyException e){
			throw new RpcException("同一级别下的规格名称重复", e);
        } catch (Exception e) {
			throw new RpcException("编辑规格出错", e);
        }
	}
}
