package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.OrderLineSubController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineDAO;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;


@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	
	private final LocalDate date = LocalDate.now();
	private final Date orderDate = Date.valueOf(date); 

	@BeforeClass
	public static void init() {
		
		DBUtils.connect("root", "root");
		
	}
	
	@Mock
	private Utils utils;
	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	private ProductDAO productDAO;
	
	@Mock
	private CustomerDAO customerDAO;
	
	@Mock
	private OrderLineDAO orderLineDAO;
	
	@Mock
	private OrderLineSubController ordSub;
	
	@InjectMocks
	private	OrderController orderController;
	
	@Before
	public void build() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		MockitoAnnotations.initMocks(this);
		
	}

	@Test
	public void testCreate() {
		
		final String EMAIL = "ll@qa.com";
		final Long customerID = 1L, orderID = 1L;
		final double halfwayTotal = 0;
		final Order halfOrder = new Order(customerID,orderDate,halfwayTotal);
		final Order halfOrder1 = new Order(1L,customerID,orderDate,halfwayTotal);
		final OrderLine ord = new OrderLine(1L,1L,1L,2);
		final double totalCost = 3.98;
		final Order created = new Order(1L,orderDate,totalCost);
		
		Mockito.when(utils.getString()).thenReturn(EMAIL);
		Mockito.when(customerDAO.returningCustomer(EMAIL)).thenReturn(true);
		Mockito.when(customerDAO.returningCustomerID(EMAIL)).thenReturn(customerID);
		Mockito.when(orderDAO.create(halfOrder)).thenReturn(halfOrder);
		Mockito.when(orderDAO.readLatest()).thenReturn(halfOrder1);
		Mockito.when(ordSub.createOrderLine(orderID)).thenReturn(ord);
		Mockito.when(utils.getBool()).thenReturn(false);
		Mockito.when(orderDAO.updateTotalPriceCreate(orderID)).thenReturn(created);
		
		assertEquals(created,orderController.create());
		
		
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(customerDAO, Mockito.times(1)).returningCustomer(EMAIL);
		Mockito.verify(customerDAO, Mockito.times(1)).returningCustomerID(EMAIL);
		Mockito.verify(orderDAO, Mockito.times(1)).create(halfOrder);
		Mockito.verify(orderDAO, Mockito.times(1)).readLatest();
		Mockito.verify(ordSub,Mockito.times(1)).createOrderLine(orderID);
		Mockito.verify(utils, Mockito.times(1)).getBool();
		Mockito.verify(orderDAO, Mockito.times(1)).updateTotalPriceCreate(orderID);
	}

	@Test
	public void testCreateWrongEmailDecisionNO() {
		
		Mockito.when(utils.getBool()).thenReturn(false);
		
		assertNull(orderController.create());
		
		
		Mockito.verify(utils,Mockito.times(1)).getBool();
		
	}
	
	@Test
	public void testCreateWrongEmailDecisionYES() {
		
		Mockito.when(utils.getBool()).thenReturn(true);
		
		assertNull(orderController.create());
		
		
		Mockito.verify(utils,Mockito.times(1)).getBool();
		
	}
	
	@Test
	public void testReadAllOrdersOnly() {
		
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L,1L,orderDate,3.98));
		
		Mockito.when(utils.getBool()).thenReturn(false);
		Mockito.when(orderDAO.readAll()).thenReturn(orders);
		
		assertEquals(orders,orderController.readAll());
		
		
		Mockito.verify(utils, Mockito.times(1)).getBool();
		Mockito.verify(orderDAO,Mockito.times(1)).readAll();
		
	}
	
	@Test
	public void testReadAllOrdersAndOrderLines() {
		
		final List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L,1L,orderDate,3.98));
		
		final List<OrderLineAndProduct> ordProd = new ArrayList<>();
		ordProd.add(new OrderLineAndProduct(1L,"Apple",2,1.99));
		
		final Long orderID = 1L;
		
		Mockito.when(utils.getBool()).thenReturn(true);
		Mockito.when(ordSub.readAllOrderLines(orderID)).thenReturn(ordProd);
		Mockito.when(orderDAO.readAll()).thenReturn(orders);
		
		assertEquals(orders,orderController.readAll());
		
		
		Mockito.verify(utils, Mockito.times(1)).getBool();
		Mockito.verify(ordSub,Mockito.times(1)).readAllOrderLines(orderID);
		Mockito.verify(orderDAO,Mockito.times(1)).readAll();
		
	}
	
	@Test
	public void testDeleteWholeOrder() {
		
		final String EMAIL = "ll@qa.com";
		final long ID = 1L, customerID = 1L;

		Mockito.when(utils.getString()).thenReturn(EMAIL);
		Mockito.when(customerDAO.returningCustomer(EMAIL)).thenReturn(true);
		Mockito.when(customerDAO.returningCustomerID(EMAIL)).thenReturn(customerID);
		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(utils.getBool()).thenReturn(true);
		Mockito.when(orderDAO.delete(ID)).thenReturn(1);

		assertEquals(1L, this.orderController.delete());

		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils,Mockito.times(1)).getBool();
		Mockito.verify(orderDAO, Mockito.times(1)).delete(ID);
		
	}
	
	@Test
	public void testDeleteOrderLine() {
		
		final String EMAIL = "ll@qa.com";
		final long ID = 1L, customerID = 1L;

		Mockito.when(utils.getString()).thenReturn(EMAIL);
		Mockito.when(customerDAO.returningCustomer(EMAIL)).thenReturn(true);
		Mockito.when(customerDAO.returningCustomerID(EMAIL)).thenReturn(customerID);
		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(utils.getBool()).thenReturn(false);
		Mockito.when(ordSub.deleteOrderLine(ID)).thenReturn(1);

		assertEquals(1L, this.orderController.delete());

		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils,Mockito.times(1)).getBool();
		Mockito.verify(ordSub, Mockito.times(1)).deleteOrderLine(ID);
		
	}

	@Test
	public void testUpdate() {
		
		final String EMAIL = "ll@qa.com";
		final Long customerID = 1L , orderID = 1L;
		final Date dateOfOrder = Date.valueOf("2020-12-23");
		
		final Order order1 = new Order(1L,1L,dateOfOrder,3.98);
		final List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(order1);
		
		final OrderLineAndProduct ordProd = new OrderLineAndProduct(1L,"Apple",2,1.99);
		final List<OrderLineAndProduct> ordProdList = new ArrayList<>();
		ordProdList.add(ordProd);
		
		Mockito.when(utils.getString()).thenReturn(EMAIL);
		Mockito.when(customerDAO.returningCustomer(EMAIL)).thenReturn(true);
		Mockito.when(customerDAO.returningCustomerID(EMAIL)).thenReturn(customerID);
		Mockito.when(orderDAO.readOrdersByCustomer(customerID)).thenReturn(customerOrders);
		Mockito.when(ordSub.updateOrderLine(customerID)).thenReturn(orderID);
		
		Order orderUpdated = new Order(orderID,customerID,orderDate,7.96);
		
		Mockito.when(orderLineDAO.updateTotalPriceUpdate(orderID)).thenReturn(7.96);
		Mockito.when(orderDAO.update(orderUpdated)).thenReturn(orderUpdated);
		
		assertEquals(orderUpdated,orderController.update());
		
		
		Mockito.verify(customerDAO,Mockito.times(1)).returningCustomer(EMAIL);
		Mockito.verify(customerDAO,Mockito.times(1)).returningCustomerID(EMAIL);
		Mockito.verify(orderDAO,Mockito.times(1)).readOrdersByCustomer(customerID);
		Mockito.verify(utils,Mockito.times(1)).getString();
		Mockito.verify(ordSub,Mockito.times(1)).updateOrderLine(customerID);
		Mockito.verify(orderLineDAO,Mockito.times(1)).updateTotalPriceUpdate(orderID);
		Mockito.verify(orderDAO,Mockito.times(1)).update(orderUpdated);
		
	}
	
	@Test
	public void testUpdateReturnNull() {
		
		Mockito.when(utils.getBool()).thenReturn(false);
		
		assertNull(orderController.update());
		
		
		Mockito.verify(utils,Mockito.times(1)).getBool();
		
	}
	
}

	
