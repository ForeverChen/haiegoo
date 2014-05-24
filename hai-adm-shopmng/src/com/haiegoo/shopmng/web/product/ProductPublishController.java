package com.haiegoo.shopmng.web.product;

import com.haiegoo.commons.enums.State;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.shopping.model.product.Product;
import com.haiegoo.shopping.service.product.ProductService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 我要卖、编辑商品 页面
 * @author Linpn
 */
public class ProductPublishController extends PageAdmController {

	@Resource
	private ProductService productService;
	
	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {

		//查找商品数据
		String action = request.getParameter("action", "");
		Long id = request.getLongParameter("id");
		if(id!=null){
			Product product = productService.getProduct(id);	
			if(product!=null){	
				modeview.addObject("product", product);
				if(action.equals("edit") && product.getState()!=State.DELETED.getCode()){
					modeview.addObject("action", "edit");
					modeview.addObject("module_title", "编辑商品");
				}else{
					modeview.addObject("action", "view");
					modeview.addObject("module_title", "查看商品");
				}
			}else{
				modeview.addObject("action", "publish");
				modeview.addObject("module_title", "发布商品");
			}
		}else{
			modeview.addObject("action", "publish");
			modeview.addObject("module_title", "发布商品");
		}
		

		// 获取可选择的品牌
		BrandCateController brandcate = this.getMvcContext().getBean("/product/brand-cate.jspx", BrandCateController.class);
		JSONArray cbbBrand = brandcate.getComboBrandOfJSON();
		modeview.addObject("cbbBrand", cbbBrand);
		
		// 获取可选择的规格与属性
		SpecPropController specprop = this.getMvcContext().getBean("/product/spec-prop.jspx", SpecPropController.class);
		JSONObject treeSpec = specprop.getTreeSpecificationOfJSON();
		JSONObject treeProp = specprop.getTreePropertyOfJSON();
		modeview.addObject("treeSpec", treeSpec);
		modeview.addObject("treeProp", treeProp);
		
	}
		
	
	/**
	 * Ajax请求：保存商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveProduct(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			Product product = request.getBindObject(Product.class, "product");
			boolean publish = request.getBooleanParameter("publish", false);
			
			if(product.getId()==null){
				//添加对象
				product.setCreateTime(new Date());
				long id = productService.addProduct(product, false, false, false);
				product.setId(id);
			}else{
				//编辑对象
				product.setUpdateTime(new Date());
				long id = productService.editProduct(product, false, false, false);
				product.setId(id);
			}
			
			//设置上架
			if(publish){
				productService.upShelfProduct(new Long[]{product.getId()});
			}
			
			//输出数据
            out.println("{success: true, product:{id:"+ product.getId() +"}}");

		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
//	
//	
//	/**
//	 * Ajax请求：获取商品属性
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	public void getProductProps(HttpServletExtendRequest request, 
//			HttpServletExtendResponse response) throws IOException {
//		PrintWriter out = response.getWriter();
//		
//		try{
//			String cateId = request.getParameter("cateId");
//			String productId = request.getParameter("productId");	
//						
//			List<Property> propsList = propertiesService.getPropertyList(Integer.valueOf(cateId));
//			List<ProductProps> productProps = new ArrayList<ProductProps>();
//			
//			if(productId!=null && !productId.equals("")){
//				Product product = productService.getProduct(Long.valueOf(productId),ProductQueryMode.WITH_PROPS);
//				if(product!=null)
//					productProps = product.getProperty();
//			}			
//		
//			//输出数据
//			JSONObject json = new JSONObject();
//			json.put("success", true);
//			
//			JSONObject data = new JSONObject();			
//			for(Property prop : propsList){
//				String value = "";
//				for(ProductProps productProp : productProps){
//					if(prop.getPropsId().equals(productProp.getPropsId()))
//						value = productProp.getPropsValue();
//				}
//				data.put(prop.getPropsName(), value);
//			}			
//			json.put("data", data);
//						
//			out.println(json.toString());			
//	
//		}catch(Exception e){
//			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
//			logger.error(e.getMessage(), e);
//		}
//		
//		out.close();
//	}
	
	

}
