package com.haiegoo.shopmng.web.product;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.commons.enums.State;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.shopping.model.product.Product;
import com.haiegoo.shopping.service.product.BrandService;
import com.haiegoo.shopping.service.product.CategoryService;
import com.haiegoo.shopping.service.product.ProductService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

/**
 * 我要卖、编辑商品 页面
 * @author Linpn
 */
public class ProductPublishController extends PageAdmController {

	@Resource
	private ProductService productService;
	@Resource
	private BrandService brandService;
	@Resource
	protected CategoryService categoryService;
//	@Resource
//	private PropertyService propertiesService;
	
	
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
		
	
//	/**
//	 * Ajax请求：保存商品
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	public void saveProduct(HttpServletExtendRequest request, 
//			HttpServletExtendResponse response) throws IOException {
//		PrintWriter out = response.getWriter();
//		
//		try{
//			//获取参数
//			String productId = request.getParameter("productId");			//商品ID
//			String cateId = request.getParameter("cateId");				//商品所属类别
//			String brandId = request.getParameter("brandId");			//品牌
//			String typeId = request.getParameter("typeId");				//店铺自定义分类
//			String productName = request.getParameter("productName");		//商品的名称
//			String productImage = request.getParameter("productImage");		//商品图片URL
//			String productNumber = request.getParameter("productNumber");	//商品库存数量
//			String weight = request.getParameter("weight");				//商品的重量
//			String marketPrice = request.getParameter("marketPrice");	//市场售价
//			String shopPrice = request.getParameter("shopPrice");		//本店售价
//			String productBrief = request.getParameter("productBrief");		//商品的简短描述
//			String productDesc = request.getParameter("productDesc");		//商品的详细描述
//			String sellerNote = request.getParameter("sellerNote");		//商家备注
//			String isReal = request.getParameter("isReal");				//是否是实物
//			String isOnSale = request.getParameter("isOnSale");			//是否上架销售
//			String hasInvoice = request.getParameter("hasInvoice");		//是否有发票
//			String hasWarranty = request.getParameter("hasWarranty");	//是否有保修
//			
//			//创建对象
//			Product product = new Product();
//			product.setCateId(Integer.valueOf(cateId));
//			product.setBrandId(Integer.valueOf(brandId));
//			product.setTypeId(Integer.valueOf(typeId));
//			product.setProductName(productName);
//			product.setProductImage(productImage);
//			product.setProductNumber(Integer.valueOf(productNumber));
//			product.setWeight(new BigDecimal(weight));
//			product.setMarketPrice(new BigDecimal(marketPrice));
//			product.setShopPrice(new BigDecimal(shopPrice));
//			product.setProductBrief(productBrief);
//			product.setProductDesc(productDesc);
//			product.setSellerNote(sellerNote);
//			product.setIsReal(Byte.valueOf(isReal));
//			product.setIsOnSale(Byte.valueOf(isOnSale));
//			product.setHasInvoice(Byte.valueOf(hasInvoice));
//			product.setHasWarranty(Byte.valueOf(hasWarranty));
//			product.setPublishTime(product.getIsOnSale().byteValue()==1?new Date():null);
//			
//			if(productId==null || productId.equals("")){
//				//添加对象
//				product.setProductSn(UUID.randomUUID().toString().toUpperCase());
//				product.setCreateTime(new Date());
//				productService.addProduct(product);
//			}else{
//				//编辑对象
//				product.setProductId(Long.valueOf(productId));
//				product.setUpdateTime(new Date());
//				productService.editProduct(product);
//			}
//			
//			//输出数据
//			out.println("{success: true}");
//
//		}catch(Exception e){
//			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
//			logger.error(e.getMessage(), e);
//		}
//		
//		out.close();
//	}
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
