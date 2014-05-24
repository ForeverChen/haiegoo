package com.haiegoo.shopping.service.product.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.enums.State;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.commons.utils.DateUtils;
import com.haiegoo.shopping.enums.SaleState;
import com.haiegoo.shopping.model.product.Product;
import com.haiegoo.shopping.service.product.ProductService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

public class ProductServiceImpl extends BaseService implements ProductService {

	private static final long serialVersionUID = 2550947800977832647L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientShopping;
	
	@Override
	public Product getProduct(Long id) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			@SuppressWarnings("unchecked")
			List<Product> list = this.sqlMapClientShopping.queryForList(
					"product.getProduct", params);
			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			throw new RpcException("查询商品出错", e);
		}
	}

	@Override
	public List<Product> getProductList(Map<String, Object> params,
			Map<String, String> sorters, Long start, Long limit) {
		try {
			params.put("start", start);
			params.put("limit", limit);
			params.put("sort", this.getDbSort(sorters));
			@SuppressWarnings("unchecked")
			List<Product> list = this.sqlMapClientShopping.queryForList(
					"product.getProduct", params);
			return list;
		} catch (Exception e) {
			throw new RpcException("查询商品出错", e);
		}
	}

	@Override
	public Long getProductCount(Map<String, Object> params) {
		try {
			Long count = (Long) this.sqlMapClientShopping.queryForObject(
					"product.getProductCount", params);
			return count;
		} catch (Exception e) {
			throw new RpcException("查询商品出错", e);
		}
	}

	@Override
	public long addProduct(final Product product, boolean syncSpec,
			boolean syncProps, boolean syncImgs) {
		try {
			// 添加商品基本属性
			long productId = (Long) this.sqlMapClientShopping.insert("product.addProduct", product);
			product.setId(productId);

//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("productId", product.getId());
//
//			// 添加规格
//			if (syncSpec && product.getSpecList() != null) {
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
//								for (Specification spec : product.getSpecList()) {
//									ProductSpec productSpec = new ProductSpec();
//									productSpec.setProductId(product.getId());
//									productSpec.setSpecId(spec.getPid());
//									productSpec.setSpecValue(spec.getId());
//									executor.insert("ProductSpec.addSpec",
//											productSpec);
//								}
//
//								return executor.executeBatch();
//							}
//						});
//			}
//
//			// 添加属性
//			if (syncProps && product.getPropsList() != null) {
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
////								for (Property props : product.getPropsList()) {
////									ProductProps productProps = new ProductProps();
////									productProps.setProductId(product.getId());
////									productProps.setPropsId(props.getPid());
////									if (props.getId() != null)
////										productProps.setPropsValue(String
////												.valueOf(props.getId()));
////									else
////										productProps.setPropsValue(props
////												.getInput());
////									executor.insert("ProductProps.addProps",
////											productProps);
////								}
//
//								return executor.executeBatch();
//							}
//						});
//			}
//
//			// 添加图片
//			if (syncImgs && product.getImgList() != null) {
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
//								for (Pictures pictures : product.getImgList()) {
//									ProductImgs productImgs = new ProductImgs();
//									productImgs.setProductId(product.getId());
//									productImgs.setImageId(pictures.getId());
//									productImgs.setSort(pictures.getSort());
//									productImgs.setType(pictures.getType());
//									productImgs.setUrl(pictures.getUrl());
//									executor.insert("ProductImgs.addImgs",
//											productImgs);
//								}
//
//								return executor.executeBatch();
//							}
//						});
//			}

			return product.getId();
			
		} catch (Exception e) {
			throw new RpcException("添加商品出错", e);
		}
	}

	@Override
	public long editProduct(final Product product, boolean syncSpec,
			boolean syncProps, boolean syncImgs) {
		try {
			// 编辑商品基本属性
			this.sqlMapClientShopping.update("product.editProduct", product);
			
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("productId", product.getId());
//			// 编辑规格
//			if (syncSpec && product.getSpecList() != null) {
//				this.sqlMapClientShopping.delete("ProductSpec.delSpec",
//						params);
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
//								for (Specification spec : product.getSpecList()) {
//									ProductSpec productSpec = new ProductSpec();
//									productSpec.setProductId(product.getId());
//									productSpec.setSpecId(spec.getPid());
//									productSpec.setSpecValue(spec.getId());
//									executor.insert("ProductSpec.addSpec",
//											productSpec);
//								}
//
//								return executor.executeBatch();
//							}
//						});
//			}
//
//			// 编辑属性
//			if (syncProps && product.getPropsList() != null) {
//				this.sqlMapClientShopping.delete("ProductProps.delProps",
//						params);
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
////								for (Property props : product.getPropsList()) {
////									ProductProps productProps = new ProductProps();
////									productProps.setProductId(product.getId());
////									productProps.setPropsId(props.getPid());
////									if (props.getId() != null)
////										productProps.setPropsValue(String
////												.valueOf(props.getId()));
////									else
////										productProps.setPropsValue(props
////												.getInput());
////									executor.insert("ProductProps.addProps",
////											productProps);
////								}
//
//								return executor.executeBatch();
//							}
//						});
//			}
//
//			// 编辑图片
//			if (syncImgs && product.getImgList() != null) {
//				this.sqlMapClientShopping.delete("ProductImgs.delImgs",
//						params);
//				this.sqlMapClientShopping.execute(
//						new SqlMapClientCallback<Integer>() {
//							@Override
//							public Integer doInSqlMapClient(
//									SqlMapExecutor executor)
//									throws SQLException {
//								executor.startBatch();
//
//								for (Pictures pictures : product.getImgList()) {
//									ProductImgs productImgs = new ProductImgs();
//									productImgs.setProductId(product.getId());
//									productImgs.setImageId(pictures.getId());
//									productImgs.setSort(pictures.getSort());
//									productImgs.setType(pictures.getType());
//									productImgs.setUrl(pictures.getUrl());
//									executor.insert("ProductImgs.addImgs",
//											productImgs);
//								}
//
//								return executor.executeBatch();
//							}
//						});
//			}

			return product.getId();
			
		} catch (Exception e) {
			throw new RpcException("编辑商品出错", e);
		}
	}

	@Override
	public Long[] upShelfProduct(final Long[] ids) {
		try {
			final List<Long> result = new ArrayList<Long>();
			
			this.sqlMapClientShopping.execute(new SqlMapClientCallback<Integer>(){
				@Override
				public Integer doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					
					executor.startBatch();

					for(long id : ids){
						// 验证是否可以上架操
						Product product = getProduct(id);
						
						// 设置参数
						if(product.getSaleState()!=SaleState.SELLING.getCode()){
							// 设置上架
							product.setSaleState(SaleState.SELLING.getCode());
							product.setUpShelfTime(new Date());
							
							//判断是不是首次发布
							if(product.getSaleState()==0 || DateUtils.format(product.getPublishTime()).equals("1000-01-01 00:00:00")){
								product.setPublishTime(new Date());
							}
							
							// 执行SQL
							sqlMapClientShopping.update("product.editProduct", product);
						}
						result.add(id);
					}
					
					return executor.executeBatch();
				}
			});
			
			return result.toArray(new Long[]{});
			
		} catch (Exception e) {
			throw new RpcException("上架商品出错", e);
		}
	}

	@Override
	public Long[] offShelfProduct(final Long[] ids) {
		try {
			final List<Long> result = new ArrayList<Long>();
			
			this.sqlMapClientShopping.execute(new SqlMapClientCallback<Integer>(){
				@Override
				public Integer doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					
					executor.startBatch();

					for(long id : ids){
						// 验证是否可以下架
						Product product = getProduct(id);
						
						// 设置参数
						if(product.getSaleState()!=SaleState.HAND_OFF_SHELF.getCode()){
							// 设置手动下架
							product.setSaleState(SaleState.HAND_OFF_SHELF.getCode());
							product.setOffShelfTime(new Date());
							
							// 执行SQL
							sqlMapClientShopping.update("product.editProduct", product);
						}
						result.add(id);
					}
					
					return executor.executeBatch();
				}
			});
			
			return result.toArray(new Long[]{});
			
		} catch (Exception e) {
			throw new RpcException("下架商品出错", e);
		}
	}

	@Override
	public Long[] delProduct(final Long[] ids) {
		try {
			final List<Long> result = new ArrayList<Long>();
			
			this.sqlMapClientShopping.execute(new SqlMapClientCallback<Integer>(){
				@Override
				public Integer doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					
					executor.startBatch();

					for(long id : ids){
						// 验证是否可以删除
						Product product = getProduct(id);
						
						// 设置参数
						if(product.getState()!=State.DELETED.getCode()){
							// 设置删除
							product.setState(State.DELETED.getCode());
							product.setDeleteTime(new Date());	
							
							// 执行SQL
							sqlMapClientShopping.update("product.editProduct", product);
						}
						result.add(id);
					}
					
					return executor.executeBatch();
				}
			});
			
			return result.toArray(new Long[]{});
			
		} catch (Exception e) {
			throw new RpcException("删除商品出错", e);
		}
	}

	@Override
	public Long[] recoveryProduct(final Long[] ids) {
		try {
			final List<Long> result = new ArrayList<Long>();
			
			this.sqlMapClientShopping.execute(new SqlMapClientCallback<Integer>(){
				@Override
				public Integer doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					
					executor.startBatch();

					for(long id : ids){
						// 验证是否可以恢复
						Product product = getProduct(id);
						
						// 设置参数
						if(product.getState()!=State.ENABLE.getCode()){
							// 设置恢复
							product.setState(State.ENABLE.getCode());
							product.setDeleteTime(DateUtils.parse("1000-01-01 00:00:00"));
							
							// 执行SQL
							sqlMapClientShopping.update("product.editProduct", product);
						}
						result.add(id);
					}
					
					return executor.executeBatch();
				}
			});
			
			return result.toArray(new Long[]{});
			
		} catch (Exception e) {
			throw new RpcException("恢复商品出错", e);
		}
	}
}
