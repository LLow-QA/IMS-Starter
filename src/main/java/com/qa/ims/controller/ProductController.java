//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

public class ProductController implements CrudController<Product>{

	public static final Logger LOGGER = LogManager.getLogger();

	private ProductDAO productDAO;
	private Utils utils;

	public ProductController(ProductDAO productDAO, Utils utils) {
		
		super();
		this.productDAO = productDAO;
		this.utils = utils;
		
	}
	
	
	@Override
	public List<Product> readAll() {
		
		return null;
	}

	@Override
	public Product create() {
		
		LOGGER.info("Please enter the products name: ");
		String name = utils.getString();
		
		LOGGER.info("Please add a product description: ");
		String desc = utils.getString();
		
		LOGGER.info("Please enter products price: ");
		double price = utils.getDouble();
		
		LOGGER.info("Please amount of stock: ");
		int stock = utils.getInt();
		
		Product product = productDAO.create(new Product(name,desc,price,stock));
		LOGGER.info("Product added.");
		return product;
	}

	@Override
	public Product update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
