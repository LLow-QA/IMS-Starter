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
		
		List<Product> products = productDAO.readAll();
		for (Product product : products) {
			
			LOGGER.info(product.toString());
			
		}
		return products;
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
		
		LOGGER.info("Please enter the id of the product you would like to update: ");
		Long id = utils.getLong();
		
		LOGGER.info("Please update the product name: ");
		String name = utils.getString();
		
		LOGGER.info("Please update the product description: ");
		String desc = utils.getString();
		
		LOGGER.info("Please update the products price: ");
		double price = utils.getDouble();
		
		LOGGER.info("Please update the products stock: ");
		int stock = utils.getInt();
		
		
		Product product = productDAO.update(new Product(id, name, desc, price, stock));
		LOGGER.info("Customer Updated");
		return product;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the product you would like to delete: ");
		Long id = utils.getLong();
		
		return productDAO.delete(id);
	}
	
	

}
