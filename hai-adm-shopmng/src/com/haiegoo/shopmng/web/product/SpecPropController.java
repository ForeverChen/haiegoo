package com.haiegoo.shopmng.web.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.framework.utils.TreeRecursiveHandle;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.shopping.model.product.Category;
import com.haiegoo.shopping.model.product.Property;
import com.haiegoo.shopping.model.product.Specification;
import com.haiegoo.shopping.service.product.CategoryService;
import com.haiegoo.shopping.service.product.PropertyService;
import com.haiegoo.shopping.service.product.SpecificationService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class SpecPropController extends PageAdmController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SpecificationService specificationService;
	@Autowired
	private PropertyService propertyService;

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	
	/**
     * 获取规格树
     */
	public void getSpecification(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            // 组装JSON
            JSONObject json = this.getTreeSpecificationOfJSON();

            // 输出数据
            out.println(json);

        } catch (Exception e) {
            out.println("{success: false, msg: '" + e.getMessage() + "'}");
            e.printStackTrace();
        }
        out.close();
    }
	
	/**
     * 添加/编辑规格
     */
	public void saveSpecification(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            // 创建对象
            Specification spec = request.getBindObject(Specification.class, "specification");
            
            //specId转为path存储
            if(StringUtils.isNotBlank(spec.getCatePath())){
	            Category cate = categoryService.getCategory(Integer.valueOf(spec.getCatePath()));
	            if(cate!=null){
	            	spec.setCatePath(cate.getPath());
	            }else{
	            	spec.setCatePath("");
	            }
            }
			
            if (spec.getId() == null) {
                // 添加对象
            	spec = specificationService.addSpecification(spec);
            } else {
                // 编辑对象
            	spec = specificationService.editSpecification(spec);
            }
            
            // 输出数据
            out.println("{success: true, specification:"+ JSONObject.fromObject(spec).toString() +"}");
            
        } catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
            e.printStackTrace();
        }
        out.close();
    }
	
	/**
	 * 删除规格
	 */
	public void delSpecification(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			// 获取参数
			int id = request.getIntParameter("id", 0);
			specificationService.delSpecification(id);
			
			// 输出数据
            out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
            e.printStackTrace();
		}
		out.close();
	}
	
	/**
	 * 上移规格值
	 */
	public void upSpecification(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			//获取参数
			Integer id = request.getIntParameter("id");
			Integer prevId = request.getIntParameter("prevId");
			
			Specification spe = specificationService.getSpecification(id);
			Specification prevSpe = specificationService.getSpecification(prevId);
			
			int temp = spe.getSort();
			spe.setSort(prevSpe.getSort());
			prevSpe.setSort(temp);
			
			specificationService.editSpecification(spe);
			specificationService.editSpecification(prevSpe);
			
			//输出数据
			out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			e.printStackTrace();
		}
		out.close();
	}
	
	/**
	 * 下移规格值
	 */
	public void downSpecification(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			//获取参数
			Integer id = request.getIntParameter("id");
			Integer nextId = request.getIntParameter("nextId");
			
			Specification spe = specificationService.getSpecification(id);
			Specification nextSpe = specificationService.getSpecification(nextId);
			
			int temp = spe.getSort();
			spe.setSort(nextSpe.getSort());
			nextSpe.setSort(temp);
			
			specificationService.editSpecification(spe);
			specificationService.editSpecification(nextSpe);
			
			//输出数据
			out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			e.printStackTrace();
		}
		out.close();
	}
	
	
	/**
     * 获取属性列表
     */
	public void getProperty(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            // 组装JSON
            JSONObject json = this.getTreePropertyOfJSON();

            // 输出数据
            out.println(json);

        } catch (Exception e) {
            out.println("{success: false, msg: '" + e.getMessage() + "'}");
            e.printStackTrace();
        }
        out.close();
    }
	
	/**
     * 添加/编辑属性
     */
	public void saveProperty(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
            // 创建对象
            Property prop = request.getBindObject(Property.class, "property");
            
            //specId转为path存储
            if(StringUtils.isNotBlank(prop.getCatePath())){
	            Category cate = categoryService.getCategory(Integer.valueOf(prop.getCatePath()));
	            if(cate!=null){
	            	prop.setCatePath(cate.getPath());
	            }else{
	            	prop.setCatePath("");
	            }
            }
            
            if (prop.getId() == null) {
                // 添加对象
            	prop = propertyService.addProperty(prop);
            } else {
                // 编辑对象
            	prop = propertyService.editProperty(prop);
            }
            
            // 输出数据
            out.println("{success: true, property:"+ JSONObject.fromObject(prop).toString() +"}");
            
        } catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
            e.printStackTrace();
        }
        out.close();
	}
	
	/**
	 * 删除属性
	 */
	public void delProperty(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			// 获取参数
			int id = request.getIntParameter("id", 0);
			propertyService.delProperty(id);
			
			// 输出数据
            out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
            e.printStackTrace();
		}
		out.close();
	}
	
	/**
	 * 上移属性值
	 */
	public void upProperty(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			//获取参数
			Integer id = request.getIntParameter("id");
			Integer prevId = request.getIntParameter("prevId");
			
			Property prop = propertyService.getProperty(id);
			Property prevProp = propertyService.getProperty(prevId);
			
			int temp = prop.getSort();
			prop.setSort(prevProp.getSort());
			prevProp.setSort(temp);
			
			propertyService.editProperty(prop);
			propertyService.editProperty(prevProp);
			
			//输出数据
			out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			e.printStackTrace();
		}
		out.close();
	}
	
	/**
	 * 下移属性值
	 */
	public void downProperty(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			//获取参数
			Integer id = request.getIntParameter("id");
			Integer nextId = request.getIntParameter("nextId");
			
			Property prop = propertyService.getProperty(id);
			Property nextProp = propertyService.getProperty(nextId);
			
			int temp = prop.getSort();
			prop.setSort(nextProp.getSort());
			nextProp.setSort(temp);
			
			propertyService.editProperty(prop);
			propertyService.editProperty(nextProp);
			
			//输出数据
			out.println("{success: true}");
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			e.printStackTrace();
		}
		out.close();
	}
	
    

	/**
     *获取规格JSON格式的树
     * @return 返回Tree格式的JSON对象 
     */
	public JSONObject getTreeSpecificationOfJSON() {
		final JSONObject json = new JSONObject();
		
        try {
        	json.put("id", 0);
			json.put("name", "商品规格");
            json.put("children", new JSONArray());
            
            final List<Specification> list = specificationService.getSpecificationList();

			// 递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>(){
				public void recursive(JSONObject treeNode) throws Exception{
					for(Specification spec : list){	
						if (spec.getPid().equals(treeNode.getInt("id"))) {				
							//获取JSON对象
							JSONObject node = JSONObject.fromObject(spec);
							
							node.put("id", spec.getId());
							node.put("text", spec.getName());
							node.put("leaf", true);
                            
                            //把catePath转成中文名称
							if(StringUtils.isNotBlank(spec.getCatePath())){
	                            String catePathName = categoryService.getPathNameByPath(spec.getCatePath());
	                            node.put("catePathName", catePathName);
							}
							
                            // 图标
                            if (spec.getPid() == 0) {
                                node.put("iconCls", "icon-module-app");
                            }else{
                            	node.put("iconCls", "icon-module-function");
                            }
														
							//递归
							this.recursive(node);
							
							//添加到树中
							JSONArray children;
							try {
								children = treeNode.getJSONArray("children");
							} catch (JSONException e) {
								treeNode.put("children", new JSONArray());
								children = treeNode.getJSONArray("children");
							}							
							children.add(node);
							treeNode.put("leaf", false);
						}
					}
				}
			};

			treeRecursiveHandle.recursive(json);

        } catch (Exception e) {
			throw new RuntimeException("查询规格树出错", e);
        }
        
        return json;
	}
    

	/**
     *获取属性JSON格式的树
     * @return 返回Tree格式的JSON对象 
     */
	public JSONObject getTreePropertyOfJSON() {
		final JSONObject json = new JSONObject();
		
        try {
        	json.put("id", 0);
			json.put("name", "商品属性");
            json.put("children", new JSONArray());
            
            final List<Property> list = propertyService.getPropertyList();

			// 递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>(){
				public void recursive(JSONObject treeNode) throws Exception{
					for(Property prop : list){	
						if (prop.getPid().equals(treeNode.getInt("id"))) {				
							//获取JSON对象
							JSONObject node = JSONObject.fromObject(prop);
							
							node.put("id", prop.getId());
							node.put("text", prop.getName());
							node.put("leaf", true);
                            
                            //把catePath转成中文名称
							if(StringUtils.isNotBlank(prop.getCatePath())){
	                            String catePathName = categoryService.getPathNameByPath(prop.getCatePath());
	                            node.put("catePathName", catePathName);
							}

                            // 图标
                            if (prop.getPid() == 0) {
                                node.put("iconCls", "icon-module-app");
                            }else{
                            	node.put("iconCls", "icon-module-function");
                            }
														
							//递归
							this.recursive(node);
							
							//添加到树中
							JSONArray children;
							try {
								children = treeNode.getJSONArray("children");
							} catch (JSONException e) {
								treeNode.put("children", new JSONArray());
								children = treeNode.getJSONArray("children");
							}							
							children.add(node);
							treeNode.put("leaf", false);
						}
					}
				}
			};

			treeRecursiveHandle.recursive(json);

        } catch (Exception e) {
			throw new RuntimeException("查询属性出错", e);
        }
        
        return json;
	}
}