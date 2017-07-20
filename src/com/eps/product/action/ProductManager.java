package com.eps.product.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Product;
import com.eps.dao.service.ProductService;
import com.eps.model.action.AnalyseModel;
import com.eps.system.cache.SqlSessionManager;

public class ProductManager {

	private ProductService productService;
	
	private WebApplicationContext webApplicationContext;
	
	public ProductManager(WebApplicationContext webApplicationContext){
		this.webApplicationContext = webApplicationContext;
		productService = (ProductService) webApplicationContext.getBean("productService");
	}
	
	/**
	 * 获取产品列表
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showProductList(Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		List<Product> productMap = productService.queryAllProduct();
		List<Object> resMap = new ArrayList<Object>();
		for(Product p : productMap){
			resMap.add(p);
		}
		
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resMap, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	/**
	 * 根据产品ID获得产品名称
	 * @param session
	 * @param productID
	 * @return
	 * @throws IOException
	 */
	public static String getProductName(String productID) throws IOException{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ProductService productService = (ProductService) webApplicationContext.getBean("productService");
		Product product = productService.queryProductByID(productID);
		if(product != null){
			return product.getProductName();
		}else{
			return "";
		}
	}
	
	/**
	 * 插入产品
	 * @param session
	 * @param productBean
	 * @return
	 */
	public String createProduct(Product product){
		String res = "";
		productService.insertProduct(product);
		res = "true";
		return res;
	}
	
	/**
	 * 更新产品
	 * @param session
	 * @param productBean
	 * @return
	 */
	public String updateProduct(Product product){
		String res = "";
		productService.updateProduct(product);
		res = "true";
		return res;
	}
	
	/**
	 * 查询产品树图
	 * @return
	 */
	public static Map<String,String> getProductTree(){
		List<Product> resMap = new ArrayList<Product>();
		SqlSessionFactory factory = null;
		Map<String,String> productTree = new TreeMap<String, String>();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ProductService productService = (ProductService) webApplicationContext.getBean("productService");
		resMap = productService.queryProductNotLocked();
		for(int i=0;i<resMap.size();i++){
			productTree.put(resMap.get(i).getProductID(), resMap.get(i).getProductName());
		}
		return productTree;
	}
	
}
