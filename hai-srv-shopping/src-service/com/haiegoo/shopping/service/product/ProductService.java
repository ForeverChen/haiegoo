package com.haiegoo.shopping.service.product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.haiegoo.shopping.model.product.Product;


/**
 * 商品信息服务类
 * @author Administrator
 *
 */
public interface ProductService extends Serializable {
	
	/**
	 * 获取商品
	 * @param id 商品ID
	 * @return 返回商品对象
	 */
	public Product getProduct(Long id);
	
	/**
	 * 获取商品列表
	 * @param params 查询参数
	 * @param sorters 记录的排序，如sorters.put("id","desc")，该参数如果为空表示按默认排序
	 * @param start 查询的起始记录,可为null
	 * @param limit 查询的总记录数, 如果值为-1表示查询到最后,可为null
	 * @return 返回商品列表
	 */
	public List<Product> getProductList(Map<String,Object> params, Map<String,String> sorters, Long start, Long limit);
	
	/**
	 * 获取商品总数
	 * @param params 查询参数
	 * @return
	 */
	public Long getProductCount(Map<String, Object> params);

	/**
	 * 添加商品
	 * @param product 商品对象
	 * @param syncSpec 是否同步规格
	 * @param syncProps 是否同步属性
	 * @param syncImgs 是否同步图片
	 */
	public long addProduct(final Product product,boolean syncSpec,boolean syncProps,boolean syncImgs);

	/**
	 * 修改商品
	 * @param product 商品对象
	 * @param syncSpec 是否同步规格
	 * @param syncProps 是否同步属性
	 * @param syncImgs 是否同步图片
	 */
	public long editProduct(final Product product,boolean syncSpec,boolean syncProps,boolean syncImgs);
	
	/**
	 * 商品上架
	 * @param ids 商品ID列表
	 * @return 返回上架成功的id
	 */
	public Long[] upShelfProduct(Long[] ids);
	
	/**
	 * 商品下架
	 * @param ids 商品ID列表
	 * @return 返回下架成功的id
	 */
	public Long[] offShelfProduct(Long[] ids);

	/**
	 * 删除商品
	 * @param ids 商品ID列表
	 * @return 返回删除成功的id
	 */
	public Long[] delProduct(Long[] ids);
	
	/**
	 * 恢复商品
	 * @param ids 商品ID列表
	 * @return 返回恢复成功的id
	 */
	public Long[] recoveryProduct(Long[] ids);
	
}
