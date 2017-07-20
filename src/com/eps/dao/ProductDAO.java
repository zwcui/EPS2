package com.eps.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.Product;

@MapperScan
public interface ProductDAO {

	public List<Product> queryAllProduct();
	
	public List<Product> queryProductNotLocked();
	
	public Product queryProductByID(String productID);
	
	public void insertProduct(Product product);

	public void updateProduct(Product product);

}
