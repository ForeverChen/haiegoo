package com.haiegoo.shopmng.web.product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.framework.utils.TreeRecursiveHandle;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.shopping.model.product.Brand;
import com.haiegoo.shopping.model.product.Category;
import com.haiegoo.shopping.service.product.BrandService;
import com.haiegoo.shopping.service.product.CategoryService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

public class BrandCateController extends PageAdmController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {

	}


	/**
	 * Ajax请求：获取品牌列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getBrandList(HttpServletExtendRequest request,
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback = null;
		
		try {
			// 获取参数
			callback = request.getParameter("callback");

			// 查询数据
			List<Brand> list = brandService.getBrandList();

			// 组装JSON
			JSONObject json = new JSONObject();
			json.put("records", JSONArray.fromObject(list));

			//输出数据
			out.println(callback + "("+ json +")");
			
		} catch (Exception e) {
			out.println(callback + "({success: false, msg: '"+ e.getMessage() +"'})");
			logger.error(e.getMessage(), e);
		}
		out.close();
	}

	/**
	 * Ajax请求：添加/编辑品牌
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void saveBrand(HttpServletExtendRequest request,
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			// 创建对象
			Brand brand = request.getBindObject(Brand.class, "brand");
			
			//保存图片
			MultipartFile multipartFile = request.getFile("brand.logo");
			if(multipartFile!=null && !multipartFile.isEmpty()){
				String filename = DigestUtils.md5Hex(multipartFile.getBytes()) + multipartFile.getOriginalFilename().replaceAll(".*(\\.\\w+)$", "$1");
				brand.setLogo("WEB-RES/imgs/" + filename);
				FileUtils.writeByteArrayToFile(new File(request.getServletContext().getRealPath("/")+ brand.getLogo()), multipartFile.getBytes());
			}
			
			if (brand.getId()==null) {
                // 添加对象
				brandService.addBrand(brand);
			}else{
                // 编辑对象
				brandService.editBrand(brand);
			}

			// 输出数据
			out.println("{success: true}");
			
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		out.close();
	}

	/**
	 * Ajax请求：删除品牌
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delBrand(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			int id = request.getIntParameter("id", 0);	
			brandService.delBrand(id);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	/**
	 * Ajax请求：上移
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void upBrand(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String id = request.getParameter("id");
			String parentId = request.getParameter("parentId");
			
			Brand brand = brandService.getBrand(Integer.valueOf(id));
			Brand prevBrand = brandService.getBrand(Integer.valueOf(parentId));
			
			//排序对调
			int temp = brand.getSort();
			brand.setSort(prevBrand.getSort());
			prevBrand.setSort(temp);
			
			brandService.editBrand(brand);
			brandService.editBrand(prevBrand);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	/**
	 * Ajax请求：下移
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void downBrand(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String id = request.getParameter("id");
			String nextId = request.getParameter("nextId");
			
			Brand brand = brandService.getBrand(Integer.valueOf(id));
			Brand nextBrand = brandService.getBrand(Integer.valueOf(nextId));
			
			//排序对调
			//排序对调
			int temp = brand.getSort();
			brand.setSort(nextBrand.getSort());
			nextBrand.setSort(temp);
			
			brandService.editBrand(brand);
			brandService.editBrand(nextBrand);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		out.close();
	}
	
	
	/**
	 * Ajax请求：获取商品分类列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getCategoryList(HttpServletExtendRequest request,
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		try {
			// 组装JSON
			JSONObject json = this.getTreeCategoryOfJSON();
			
			// 输出数据
			out.println(json);
			
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		out.close();
	}

	/**
	 * Ajax请求：保存商品分类
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void saveCategory(HttpServletExtendRequest request,
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try {
			// 创建对象
			Category category = request.getBindObject(Category.class, "category");
			if (category.getId()==null) {
				category = categoryService.addCategory(category);
			}else{
				category = categoryService.editCategory(category);
			}

			// 输出数据
			out.println("{success: true, category:"+ JSONObject.fromObject(category).toString() +"}");
			
		} catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		out.close();
	}
	
	/**
	 * Ajax请求：删除商品分类数据节点
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delCategory(HttpServletExtendRequest request, HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();

        try {
            // 获取参数
			int id = request.getIntParameter("id", 0);
            categoryService.delCategory(id);
            
            // 输出数据
			out.println("{success: true}");
            
        } catch (Exception e) {
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
        }
        
        out.close();
	}
	
	/**
	 * Ajax请求：上移商品分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void upCategory(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String id = request.getParameter("id");
			String prevId = request.getParameter("prevId");
			
			Category category = categoryService.getCategory(Integer.valueOf(id));
			Category prevCategory = categoryService.getCategory(Integer.valueOf(prevId));
			
			//排序对调
			int temp = category.getSort();
			category.setSort(prevCategory.getSort());
			prevCategory.setSort(temp);
			
			categoryService.editCategory(category);
			categoryService.editCategory(prevCategory);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	/**
	 * Ajax请求：下移商品分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void downCategory(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String id = request.getParameter("id");
			String nextid = request.getParameter("nextId");
			
			Category category = categoryService.getCategory(Integer.valueOf(id));
			Category nextCategory = categoryService.getCategory(Integer.valueOf(nextid));
			
			//排序对调
			int temp = category.getSort();
			category.setSort(nextCategory.getSort());
			nextCategory.setSort(temp);
			
			categoryService.editCategory(category);
			categoryService.editCategory(nextCategory);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * 获取品牌的Json对象
     * @return 返回Combo格式的JSON对象 
	 */
	public JSONArray getComboBrandOfJSON() {
		JSONArray json = new JSONArray();
		
		List<Brand> list = brandService.getBrandList(null);
		
		for(Brand brand : list) {
			JSONArray node = new JSONArray();
			
			String name = brand.getName();			
			Integer id = brand.getId();

    		node.add(id);
    		node.add(name);
			json.add(node);
		}
		return json;
	}

	/**
     *获取分类JSON格式的树
     * @return 返回Tree格式的JSON对象 
     */
	public JSONObject getTreeCategoryOfJSON() {
		final JSONObject json = new JSONObject();

		try {
			json.put("id", 0);
			json.put("name", "商品分类");
			json.put("children", new JSONArray());

			final List<Category> list = categoryService.getCategoryList();

			// 递归加载
			TreeRecursiveHandle<JSONObject> treeRecursiveHandle = new TreeRecursiveHandle<JSONObject>(){
				public void recursive(JSONObject treeNode) throws Exception{
					for(Category category : list){	
						if (category.getPid().equals(treeNode.getInt("id"))) {				
							//获取JSON对象
							JSONObject node = JSONObject.fromObject(category);
							
							node.put("id", category.getId());
							node.put("text", category.getName());
							node.put("leaf", true);
							
							int level = category.getPath().split("/").length;
                            if (level == 1) {
                                node.put("iconCls", "icon-module-group");		// 一级分类
                            }else if (level == 2) {
                            	node.put("iconCls", "icon-module-app");			// 二级分类
                            }else if(level==3) {
                            	node.put("iconCls", "icon-module-function");	// 三级分类
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
			throw new RuntimeException("查询分类树出错", e);
		}
		
		return json;
	}
}
