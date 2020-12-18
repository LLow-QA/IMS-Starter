package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineDAO;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.utils.Utils;

public class OrderLineController implements CrudController<OrderLine> {

	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrderLineDAO orderLineDAO;
	private Utils utils;
	
	ProductDAO productDAO = new ProductDAO();
	OrderDAO orderDAO = new OrderDAO();
	
	public OrderLineController(OrderLineDAO orderLineDAO, Utils utils) {
		
		super();
		this.orderLineDAO = orderLineDAO;
		this.utils = utils;
	}
	
	
	@Override
	public List<OrderLine> readAll() {

		List<OrderLine> line = orderLineDAO.readAll();
		for (OrderLine orderLine : line) {
			LOGGER.info(orderLine.toString());
		}
		return line;
	}

	@Override
	public OrderLine create() {
		
		Long orderID = orderDAO.readLatest().getOrderID();
		
		LOGGER.info(productDAO.readAll());
		LOGGER.info('\n' + "Which product would you like to order?");
		String userChoice = utils.getString();
		Long productID = productDAO.returnProductID(userChoice);
		
		if (productID == null) {
			
			LOGGER.info("Error - Please enter a valid product name.");
			create();
			
		} else {
			
			LOGGER.info("How many would you like the purchase?");
			int quant = utils.getInt();
			
			OrderLine orderLine = orderLineDAO.create(new OrderLine(orderID, productID, quant));
			
			return orderLine;
		}
		return null;
	}

	@Override
	public OrderLine update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		
		LOGGER.info("Please enter the id of the order you would like to delete: ");
		Long id = utils.getLong();
		return orderLineDAO.delete(id);
	}
	
	public double productPrice(Long id) {
		return productDAO.readProduct(id).getPrice();
	}

}
