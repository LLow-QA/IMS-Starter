package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderLineSubController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineDAO;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;


@RunWith(MockitoJUnitRunner.class)
public class OrderLineSubControllerTest {

	
	@Mock
	private Utils utils;
	
	@Mock
	private OrderLineDAO orderLineDAO;
	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	private ProductDAO productDAO;
	
	@InjectMocks
	private OrderLineSubController ordSub;
	
	
	@Test
	public void testCreateOrderLine(){
		
		final Long orderID = 1L,productID = 1L;
		final String productName = "Apple";
		final int quantity = 2;
		
		List<Product> prods = new ArrayList<>();
		prods.add(new Product(1L,"Apple","A very tasty apple.",1.99,100));
		
		final OrderLine orderLine1 = new OrderLine(orderID,productID,quantity);
		
		Mockito.when(productDAO.listToString()).thenReturn(prods);
		Mockito.when(utils.getString()).thenReturn(productName);
		Mockito.when(productDAO.returnProductID(productName)).thenReturn(productID);
		Mockito.when(utils.getInt()).thenReturn(quantity);
		Mockito.when(productDAO.stockCheck(productID, quantity)).thenReturn(98);
		Mockito.when(orderLineDAO.create(orderLine1)).thenReturn(orderLine1);
		
		assertEquals(orderLine1,ordSub.createOrderLine(orderID));
		
		Mockito.verify(productDAO,Mockito.times(1)).listToString();
		Mockito.verify(utils,Mockito.times(1)).getString();
		Mockito.verify(productDAO,Mockito.times(1)).returnProductID(productName);
		Mockito.verify(utils,Mockito.times(1)).getInt();
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, quantity);
		Mockito.verify(orderLineDAO,Mockito.times(1)).create(orderLine1);
	}
	
	@Test
	public void testCreateOrderLineNotEnoughStock(){
		
		final Long orderID = 1L,productID = 1L;
		final String productName = "Apple";
		final int quantity = 200, stock = 100;;
		Product product1 = new Product(1L,"Apple","A very tasty apple.",1.99,stock);
		List<Product> prods = new ArrayList<>();
		prods.add(product1);
		
		
		final OrderLine orderLine2 = new OrderLine(orderID,productID,stock);
		
		Mockito.when(productDAO.listToString()).thenReturn(prods);
		Mockito.when(utils.getString()).thenReturn(productName);
		Mockito.when(productDAO.returnProductID(productName)).thenReturn(productID);
		Mockito.when(utils.getInt()).thenReturn(quantity);
		Mockito.when(productDAO.stockCheck(productID, quantity)).thenReturn(-100);
		Mockito.when(productDAO.readProduct(productID)).thenReturn(product1);
		Mockito.when(utils.getBool()).thenReturn(true);
		Mockito.when(productDAO.stockCheck(productID, stock)).thenReturn(0);
		Mockito.when(orderLineDAO.create(orderLine2)).thenReturn(orderLine2);
		
		assertEquals(orderLine2,ordSub.createOrderLine(orderID));
		
		Mockito.verify(productDAO,Mockito.times(1)).listToString();
		Mockito.verify(utils,Mockito.times(1)).getString();
		Mockito.verify(productDAO,Mockito.times(1)).returnProductID(productName);
		Mockito.verify(utils,Mockito.times(1)).getInt();
		Mockito.verify(utils,Mockito.times(1)).getBool();
		Mockito.verify(productDAO,Mockito.times(1)).readProduct(productID);
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, quantity);
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, stock);
		Mockito.verify(orderLineDAO,Mockito.times(1)).create(orderLine2);
	}
	
	@Test
	public void testReadAllOrderLines() {
		
		final Long orderID =1L;

		OrderLineAndProduct ordProd = new OrderLineAndProduct(1L,"Apple",2,1.99);
		List<OrderLineAndProduct> ordProds = new ArrayList<>();
		ordProds.add(ordProd);
		
		Mockito.when(orderLineDAO.allOrderLinesByOrder(orderID)).thenReturn(ordProds);
		
		assertEquals(ordProds,ordSub.readAllOrderLines(orderID));
		
		Mockito.verify(orderLineDAO,Mockito.times(1)).allOrderLinesByOrder(orderID);
		
	}
	
	@Test
	public void testUpdateOrderLine() {
		
		final Long customerID = 1L, orderID = 1L, orderLineID = 1L,productID = 1L;;
		final String productName = "Apple";
		final Date dateOfOrder = Date.valueOf("2020-12-23");
		final int quantity = 2 , newQuant = 4;
		
		final Order order1 = new Order(1L,1L,dateOfOrder,3.98);
		final List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(order1);
		
		final List<OrderLineAndProduct> ordProd = new ArrayList<>();
		ordProd.add(new OrderLineAndProduct(1L,"Apple",2,1.99));
		
		final OrderLine orderLine = new OrderLine(orderLineID,orderID,productID,quantity);
		
		Mockito.when(utils.getLong()).thenReturn(orderID,orderLineID);
		Mockito.when(orderDAO.readOrder(orderID)).thenReturn(order1);
		Mockito.when(orderLineDAO.allOrderLinesByOrder(orderID)).thenReturn(ordProd);
		Mockito.when(orderLineDAO.readOrderLine(orderLineID)).thenReturn(orderLine);
		Mockito.when(productDAO.removeStockFromOrder(orderLineID)).thenReturn(102);
		Mockito.when(utils.getString()).thenReturn(productName);
		Mockito.when(productDAO.returnProductID(productName)).thenReturn(productID);
		Mockito.when(productDAO.stockCheck(productID, newQuant)).thenReturn(98);
		
		OrderLine newOrderLine = new OrderLine(orderLineID,orderID,productID,4);
		
		Mockito.when(utils.getInt()).thenReturn(4);
		Mockito.when(orderLineDAO.update(newOrderLine)).thenReturn(newOrderLine);
		Mockito.when(utils.getBool()).thenReturn(false);
		
		assertEquals(customerID,ordSub.updateOrderLine(customerID));
		
		
		Mockito.verify(utils,Mockito.times(2)).getLong();
		Mockito.verify(orderDAO,Mockito.times(1)).readOrder(orderID);
		Mockito.verify(orderLineDAO,Mockito.times(1)).allOrderLinesByOrder(orderID);
		Mockito.verify(orderLineDAO,Mockito.times(2)).readOrderLine(orderLineID);
		Mockito.verify(productDAO,Mockito.times(1)).removeStockFromOrder(orderLineID);
		Mockito.verify(utils,Mockito.times(1)).getString();
		Mockito.verify(productDAO,Mockito.times(1)).returnProductID(productName);
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, newQuant);
		Mockito.verify(utils,Mockito.times(1)).getInt();
		Mockito.verify(orderLineDAO,Mockito.times(1)).update(newOrderLine);
		Mockito.verify(utils,Mockito.times(1)).getBool();
		
	}
	
	@Test
	public void testUpdateOrderLineNotEnoughStock() {
		
		final Long customerID = 1L, orderID = 1L, orderLineID = 1L,productID = 1L;;
		final String productName = "Apple";
		final Date dateOfOrder = Date.valueOf("2020-12-23");
		final int quantity = 2 , newQuant = 200, stock = 102;
		
		final Order order1 = new Order(1L,1L,dateOfOrder,3.98);
		final List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(order1);
		
		Product product1 = new Product(1L,"Apple","A very tasty apple.",1.99,stock);
		
		final List<OrderLineAndProduct> ordProd = new ArrayList<>();
		ordProd.add(new OrderLineAndProduct(1L,"Apple",2,1.99));
		
		final OrderLine orderLine = new OrderLine(orderLineID,orderID,productID,quantity);
		
		Mockito.when(utils.getLong()).thenReturn(orderID,orderLineID);
		Mockito.when(orderDAO.readOrder(orderID)).thenReturn(order1);
		Mockito.when(orderLineDAO.allOrderLinesByOrder(orderID)).thenReturn(ordProd);
		Mockito.when(orderLineDAO.readOrderLine(orderLineID)).thenReturn(orderLine);
		Mockito.when(productDAO.removeStockFromOrder(orderLineID)).thenReturn(102);
		Mockito.when(utils.getString()).thenReturn(productName);
		Mockito.when(productDAO.returnProductID(productName)).thenReturn(productID);
		Mockito.when(productDAO.stockCheck(productID, newQuant)).thenReturn(-98);
		Mockito.when(productDAO.readProduct(productID)).thenReturn(product1);
		Mockito.when(productDAO.stockCheck(productID, stock)).thenReturn(0);

		
		OrderLine newOrderLine = new OrderLine(orderLineID,orderID,productID,stock);
		
		Mockito.when(utils.getInt()).thenReturn(newQuant);
		Mockito.when(orderLineDAO.update(newOrderLine)).thenReturn(newOrderLine);
		Mockito.when(utils.getBool()).thenReturn(true,false);
		
		assertEquals(customerID,ordSub.updateOrderLine(customerID));
		
		
		Mockito.verify(utils,Mockito.times(2)).getLong();
		Mockito.verify(orderDAO,Mockito.times(1)).readOrder(orderID);
		Mockito.verify(orderLineDAO,Mockito.times(1)).allOrderLinesByOrder(orderID);
		Mockito.verify(orderLineDAO,Mockito.times(2)).readOrderLine(orderLineID);
		Mockito.verify(productDAO,Mockito.times(1)).removeStockFromOrder(orderLineID);
		Mockito.verify(utils,Mockito.times(1)).getString();
		Mockito.verify(productDAO,Mockito.times(1)).returnProductID(productName);
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, newQuant);
		Mockito.verify(productDAO,Mockito.times(1)).stockCheck(productID, stock);
		Mockito.verify(productDAO,Mockito.times(1)).readProduct(productID);
		Mockito.verify(utils,Mockito.times(1)).getInt();
		Mockito.verify(orderLineDAO,Mockito.times(1)).update(newOrderLine);
		Mockito.verify(utils,Mockito.times(2)).getBool();
		
	}
	
	@Test
	public void testDeleteOrderLine(){
		
		final Long orderID = 1L, orderLineID = 1L;
		final OrderLine orderLineDel = new OrderLine(orderLineID,orderID,1L,2);
		final OrderLineAndProduct ordProd = new OrderLineAndProduct(1L,"Apple",2,1.99);
		final List<OrderLineAndProduct> list = new ArrayList<>();
		list.add(ordProd);
		
		Mockito.when(orderLineDAO.allOrderLinesByOrder(orderID)).thenReturn(list);
		Mockito.when(utils.getLong()).thenReturn(orderLineID);
		Mockito.when(orderLineDAO.readOrderLine(orderID)).thenReturn(orderLineDel);
		Mockito.when(orderLineDAO.delete(orderLineID)).thenReturn(1);
		
		assertEquals(1,ordSub.deleteOrderLine(orderID));
		
		Mockito.verify(utils,Mockito.times(1)).getLong();
		Mockito.verify(orderLineDAO,Mockito.times(1)).readOrderLine(orderID);
		Mockito.verify(orderLineDAO,Mockito.times(1)).delete(orderLineID);
	}
}
