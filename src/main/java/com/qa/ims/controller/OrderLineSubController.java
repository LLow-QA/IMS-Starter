package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineDAO;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;
import com.qa.ims.utils.Utils;

public class OrderLineSubController {

	public static final Logger LOGGER = LogManager.getLogger();
	private Utils utils = new Utils();
	
	private OrderLineDAO orderLineDAO = new OrderLineDAO();
	private ProductDAO productDAO = new ProductDAO();
	private OrderDAO orderDAO = new OrderDAO();
	

	public List<OrderLineAndProduct> readAllOrderLines(Long orderID) {
		
		List<OrderLineAndProduct> ordProds = orderLineDAO.allOrderLinesByOrder(orderID);
		for (OrderLineAndProduct ordProd : ordProds) {
			
			LOGGER.info(ordProd.toString());
		}
		LOGGER.info('\n');
		return ordProds;
		
	}

	public OrderLine createOrderLine(Long ID) {

		Long orderID = ID;
		
		
		productDAO.listToString();
		
		LOGGER.info('\n' + "Which product would you like to order?");
		String userChoice = utils.getString();
		Long productID = productDAO.returnProductID(userChoice);
		
		if (productID == null) {
			
			LOGGER.info("Error - Please enter a valid product name.");
			createOrderLine(ID);
			
		}else {
		
			LOGGER.info("How many would you like the purchase?");
			int quant = utils.getInt();
			OrderLine orderLine = orderLineDAO.create(new OrderLine(orderID, productID, quant));
			return orderLine;
		}
		return null;
	}


	public Long updateOrderLine(Long custID) {
	
		boolean more = true;
		boolean correct = false;
		Long orderLineID = null;
		Long productID = null;
		OrderLine orderLine = null;
		
		//choosing which order the customer wants to update
		Long orderChoice = utils.getLong();
		
		if (orderDAO.readOrder(orderChoice).getCustomerID() != custID) {
			
			LOGGER.info("Please enter a valid ID.");
			updateOrderLine(custID);
			
		}else {int x = 0;}
		
		Long orderID = orderChoice;
		
		//choosing which orderline the customer wants to update
		while(more) {
			
			LOGGER.info(readAllOrderLines(orderID));
			
				do {
				
					// Checking that the ID entered is valid.
					LOGGER.info("Please select the ID you would like to change: ");
					Long orderLineChoice = utils.getLong();
					orderLineID = orderLineDAO.readOrderLine(orderLineChoice).getOrderLineID();
					
					if (orderLineDAO.readOrderLine(orderLineChoice).getOrderID() == orderID) {
						correct = true;
					}
					
				}while(!correct);
				
				correct = false;
				
				do {
					
					// Checking that the product name that is entered exists.
					LOGGER.info("Which product to you want to update/change: ");
					String productName = utils.getString();
					productID = productDAO.returnProductID(productName);
					
					if (productID != null) {
						correct = true;
					}
				
				}while(!correct);
				
			
			
			LOGGER.info("Please update the order quantity: ");
			int quant = utils.getInt();
			
			orderLine = orderLineDAO.update(new OrderLine(orderLineID,orderID,productID,quant));
			
			LOGGER.info(orderLine);
			
			
			LOGGER.info("Do you want to make any more updates? y/n");
			more = utils.getBool();
		
		} 
		
		
		return orderID;
		////END
	}
	


	public int deleteOrderLine(Long orderID) {
		
		LOGGER.info(readAllOrderLines(orderID));
		LOGGER.info("Please enter the id of the orderline you would like to delete: ");
		Long id = utils.getLong();
		
		if (orderLineDAO.readOrderLine(id).getOrderID() == orderID) {
		return orderLineDAO.delete(id);
		}else {
			LOGGER.info("That orderline is not within the chosen order, please selcted an ID that is within the order.");
			deleteOrderLine(orderID);
		}
		return 0;
	}
	

	
	
}
