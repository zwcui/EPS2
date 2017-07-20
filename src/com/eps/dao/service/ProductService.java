package com.eps.dao.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.Product;
import com.eps.dao.ProductDAO;

@Service
@Transactional
public class ProductService extends BaseService {

	private ProductDAO productDAO;

	@Resource(name="productDAO")
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public List<Product> queryAllProduct(){
		logger.info("queryAllProduct()");
		return productDAO.queryAllProduct();
	}
	
	public List<Product> queryProductNotLocked(){
		logger.info("queryProductNotLocked()");
		return productDAO.queryProductNotLocked();
	}
	
	public Product queryProductByID(String productID){
		logger.info("queryProductByID()");
		return productDAO.queryProductByID(productID);
	}
	
	public void insertProduct(Product product){
		logger.info("insertProduct()");
		productDAO.insertProduct(product);
	}

	public void updateProduct(Product product){
		logger.info("updateProduct()");
		productDAO.updateProduct(product);
	}
	
}
