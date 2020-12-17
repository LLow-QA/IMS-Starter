//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.controller;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	CustomerDAO customerDAO = new CustomerDAO();
	
	private OrderDAO orderDAO;
	private Utils utils;
	
	CustomerController customerController = new CustomerController(customerDAO, utils);
	
	
	public OrderController(OrderDAO orderDAO, Utils utils) {
		
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order.toString());
		}
		return orders;
	}

	@Override
	public Order create() {
		
		LOGGER.info("Please enter your email: ");
		String email = utils.getString();
		if(customerDAO.returningCustomer(email)) {
			
			Long customerID = customerDAO.returningCustomerID(email);
			
			long millis=System.currentTimeMillis();  
			java.sql.Date date=new java.sql.Date(millis);  
			Date orderDate = date;  
			
			LOGGER.info("Total cost: ");
			double total = utils.getDouble(); ///// THIS NEEDS TO UPDATE BASED ON WHAT IS BOUGHT
			
			Order order = orderDAO.create(new Order(customerID,orderDate,total));
			LOGGER.info("Order created");
			return order;
			
		} else {
			LOGGER.info("It appears that you dont have an account registered to this email," + '\n' + 
					"Would you like to create an account (y/n)?");
			boolean decision = utils.getBool();
					
			if (decision) {
				customerController.create();
				create();
			} else {
				LOGGER.info("Please try again.");
				create();
			}
		}
		return null;
		
	}

	@Override
	public Order update() {
								//// NEED TO FILL OUT ORDERLINE IN ORDER TO ENABLE UPDATES
		return null;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete: ");
		Long id = utils.getLong();
		return customerDAO.delete(id);
	}

}
