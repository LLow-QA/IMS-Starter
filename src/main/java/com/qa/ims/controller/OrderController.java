package com.qa.ims.controller;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;



public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();
	
	private CustomerDAO customerDAO = new CustomerDAO();
	private OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	private OrderLineSubController ordSub = new OrderLineSubController();
	private OrderDAO orderDAO;
	private Utils utils;
	
	
	public OrderController(OrderDAO orderDAO, Utils utils) {
		
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	@Override
	public List<Order> readAll() {
		
		LOGGER.info("Do you want to see all order baskets associated with each order as well? (y/n)");
		boolean decision = utils.getBool();
		
		if (decision) {
			
			List<Order> orders = orderDAO.readAll();
			
			for (Order order : orders) {
				
				Long ID = order.getOrderID();
				LOGGER.info(order.toString());
				ordSub.readAllOrderLines(ID);
				
				}
			
			return orders;
			
		} else {
		
			LOGGER.info("Displaying orders only.");
			List<Order> orders = orderDAO.readAll();
			for (Order order : orders) {
				LOGGER.info(order.toString());
				
			}
			
			return orders;
			
		}
	}

	@Override
	public Order create() {
		
		boolean more = true;
	
		Long customerID = emailCheck();
		 
		if (customerID == null) {
			
			return null;
			
		}else {
		
			LocalDate date = LocalDate.now();
			Date orderDate = Date.valueOf(date); 
			
			double total = 0;
			
			orderDAO.create(new Order(customerID,orderDate,total));
			Long orderID = orderDAO.readLatest().getOrderID();
			
			while (more) {
				
				ordSub.createOrderLine(orderID);
				LOGGER.info("Would you like to add more items to you order? (y/n)");
				more = utils.getBool();
			
		}
		
		
		Order orders = orderDAO.updateTotalPriceCreate(orderID);
		LOGGER.info("Order created");
		return orders;
		//END
		
	} 
		
	}

	@Override
	public Order update() {
		
		Double total = 0D;
		
			Long customerID = emailCheck();
			
			if (customerID == null) {
				return null;
				
			}else {
			
			orderDAO.readOrdersByCustomer(customerID);
			
			LOGGER.info("please enter the order ID that you would like to update: ");
			
			Long orderID = ordSub.updateOrderLine(customerID);
			
			LocalDate date = LocalDate.now();
			Date orderDate = Date.valueOf(date); 
			
			total = orderLineDAO.updateTotalPriceUpdate(orderID);
			
			Order order = orderDAO.update(new Order(orderID,customerID,orderDate,total));
			return order;
			
			}
	}

	@Override
	public int delete() {
		
		if(orderDAO.readOrdersByCustomer(emailCheck()).isEmpty()) {
			
			LOGGER.info("Nothing deleted.");
			return -1;
			
		}else {
			LOGGER.info("Please enter the id of the order you would like to delete: ");
			Long orderID = utils.getLong();
			
			LOGGER.info("Do you want to delete all items from the order? (y/n)");
			boolean decision = utils.getBool();
			
			if (decision) {
				
				return orderDAO.delete(orderID);
				
			}else {
				
				return ordSub.deleteOrderLine(orderID);	
			}
		}
	}
	
	//
	public Long emailCheck() {
		
		LOGGER.info("Please enter your email address: ");
		String email = utils.getString();
		
		if(customerDAO.returningCustomer(email)) {
			
			return customerDAO.returningCustomerID(email);
			
		}else {
			
			LOGGER.info("It appears that you dont have an account registered to this email." + '\n' + 
					"Would you like to create an account (y/n)?");
			boolean decision = utils.getBool();
					
			if (decision) {
				
				LOGGER.info("Please type 'RETURN' then select 'CUSTOMER' then 'CREATE' to register an account." + '\n');
				return null;
				
			} else {
				
				LOGGER.info("Please try again.");
				return null;
				
			}
		}
		
	}
	
}