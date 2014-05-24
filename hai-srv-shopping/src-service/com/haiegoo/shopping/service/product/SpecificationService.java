package com.haiegoo.shopping.service.product;

import com.haiegoo.shopping.model.product.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SpecificationService extends Serializable {
	/**
	 * 获取规格列表
	 */
	public List<Specification> getSpecificationList();
	/**
	 * 获取规格列表
	 */
	public List<Specification> getSpecificationList(Map<String, Object> params);
	/**
	 * 获取单一规格/规格值
	 */
	public Specification getSpecification(Integer id);
    /**
     * 删除规格/规格值
     */
    public void delSpecification(Integer id);
    /**
     * 删除规格/规格值
     */
    public void delSpecification(String path);
	/**
	 * 添加规格/规格值
	 * @param spec
	 * @return 返回添加后的对象
	 */
	public Specification addSpecification(Specification spec);
	/**
	 * 更新规格/规格值
	 * @param spec
	 * @return 返回修改后的对象
	 */
    public Specification editSpecification(Specification spec);
    
}
