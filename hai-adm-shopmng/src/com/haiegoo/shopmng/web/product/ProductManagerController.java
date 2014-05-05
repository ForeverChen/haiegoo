package com.haiegoo.shopmng.web.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.haiegoo.commons.utils.json.JsonGlobal;
import com.haiegoo.framework.utils.ConvertUtils;
import com.haiegoo.framework.web.HttpServletExtendRequest;
import com.haiegoo.framework.web.HttpServletExtendResponse;
import com.haiegoo.shopping.model.product.Product;
import com.haiegoo.shopping.service.product.ProductService;
import com.haiegoo.ucenter.utils.controller.PageAdmController;

/**
 * 出售中的商品页面
 * @author Linpn
 */
public class ProductManagerController extends PageAdmController {

	@Resource
	private ProductService productService;
	
	
	@Override
	public void execute(HttpServletExtendRequest request,
			HttpServletExtendResponse response, ModelAndView modeview) {
	}
	
	
	/**
	 * Ajax请求：获取出售中的商品列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void getProductList(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String callback=request.getParameter("callback");
			String sort = request.getParameter("sort");
			String dir = request.getParameter("dir");
			long start = request.getParameter("start")==null?0:Integer.valueOf(request.getParameter("start"));
			long limit = request.getParameter("limit")==null?0:Integer.valueOf(request.getParameter("limit"));
			String saleState = request.getParameter("saleState");
			String state = request.getParameter("state");
			String searchKey = request.getParameter("searchKey", "", "UTF-8");
			
			//查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("saleState", saleState);
			params.put("state", state);
			params.put("searchKey", searchKey);
			
			//排序数据
			Map<String,String> sorters = new HashMap<String,String>();
			sorters.put(sort, dir);
			
			//查询数据
			List<Product> list = productService.getProductList(params, sorters, start, limit);
			long count = productService.getProductCount(params);
			
			//组装JSON
			JSONObject json = new JSONObject();
			json.put("count", count);
			json.put("records", new JSONArray());
			
			JSONArray records = json.getJSONArray("records");
			for(Product product : list) {
				JSONObject record = JSONObject.fromObject(product, JsonGlobal.config);
				
				record.put("createTime", record.getString("createTime").replace(" ", "<br/>"));
				record.put("updateTime", record.getString("updateTime").replace(" ", "<br/>"));
				record.put("deleteTime", record.getString("deleteTime").replace(" ", "<br/>"));
				record.put("publishTime", record.getString("publishTime").replace(" ", "<br/>"));
				record.put("upShelfTime", record.getString("upShelfTime").replace(" ", "<br/>"));
				record.put("offShelfTime", record.getString("offShelfTime").replace(" ", "<br/>"));
				
				records.add(record);
			}
			
			//输出数据
			out.println(callback + "("+ json +")");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：删除商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delProduct(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String ids = request.getParameter("ids");	
			Long[] _ids = ConvertUtils.toArrLong(ids.split(","));
			
			productService.delProduct(_ids);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：恢复商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void recoveryProduct(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String ids = request.getParameter("ids");	
			Long[] _ids = ConvertUtils.toArrLong(ids.split(","));	
			
			productService.recoveryProduct(_ids);
			
			//输出数据
			out.println("{success: true}");

		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
		
	/**
	 * Ajax请求：上架商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void upShelfProduct(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String ids = request.getParameter("ids");	
			Long[] _ids = ConvertUtils.toArrLong(ids.split(","));
			
			productService.upShelfProduct(_ids);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	/**
	 * Ajax请求：下架商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void offShelfProduct(HttpServletExtendRequest request, 
			HttpServletExtendResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		try{
			//获取参数
			String ids = request.getParameter("ids");	
			Long[] _ids = ConvertUtils.toArrLong(ids.split(","));
			
			productService.offShelfProduct(_ids);
			
			//输出数据
			out.println("{success: true}");
			
		}catch(Exception e){
			out.println("{success: false, msg: '"+ e.getMessage() +"'}");
			logger.error(e.getMessage(), e);
		}
		
		out.close();
	}
	
	
	
//	/**
//	 * Ajax请求：设置价格
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	public void modifyPrice(HttpServletExtendRequest request, 
//			HttpServletExtendResponse response) throws IOException {
//		PrintWriter out = response.getWriter();
//		
//		try{
//			//获取参数
//			String productId = request.getParameter("productId");
//			String shopPrice = request.getParameter("shopPrice");
//			
//			productService.modifyPrice(Long.valueOf(productId), new BigDecimal(shopPrice));
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
//	
//	/**
//	 * Ajax请求：设置价格
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	public void modifyInventory(HttpServletExtendRequest request, 
//			HttpServletExtendResponse response) throws IOException {
//		PrintWriter out = response.getWriter();
//		
//		try{
//			//获取参数
//			String productId = request.getParameter("productId");
//			String productNumber = request.getParameter("productNumber");
//			
//			productService.modifyInventory(Long.valueOf(productId), Integer.valueOf(productNumber));
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
	


}
