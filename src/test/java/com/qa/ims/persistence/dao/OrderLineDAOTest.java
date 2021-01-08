package com.qa.ims.persistence.dao;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class OrderLineDAOTest {

	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	private final ProductDAO productDAO = new ProductDAO();
	private final CustomerDAO customerDAO = new CustomerDAO();
	private final OrderDAO orderDAO = new OrderDAO();
	
	private final LocalDate date = LocalDate.now();
	private final Date dateOrdered = Date.valueOf(date); 
	
	@BeforeClass
	public static void init() {
		DBUtils.connect("root", "password123!");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
		productDAO.create(new Product("Banana", "A bunch of yellow bananas.",0.70,50));
		productDAO.create(new Product("Pear", "An oddly shaped pear.",0.99,150));
		productDAO.create(new Product("Dragonfruit", "A very exotic fruit.",15.00,6));
		
		customerDAO.create(new Customer("James","Pierson",29,"jp@gmail.com","Morg123","23 Word Street","SD23 3GH"));
		customerDAO.create(new Customer("Hannah","Wardwell",65,"hwe@gmail.com","Matgag3","12 Faor Loop","PE13 5RH"));
		customerDAO.create(new Customer("Andre","Harlow",40,"igas.43@aol.com","grafac5","1A Lawn Road","DO03 9KF"));
		
		orderDAO.create(new Order(2L,dateOrdered,82.63));
		orderDAO.create(new Order(4L,dateOrdered,17.80));
		orderDAO.create(new Order(2L,dateOrdered,35.45));
		
		orderLineDAO.create(new OrderLine(2L,3L,7));
		orderLineDAO.create(new OrderLine(2L,4L,5));
		orderLineDAO.create(new OrderLine(2L,2L,1));
		orderLineDAO.create(new OrderLine(4L,1L,15));
		orderLineDAO.create(new OrderLine(4L,2L,8));
		orderLineDAO.create(new OrderLine(3L,2L,4));
		orderLineDAO.create(new OrderLine(3L,4L,1));
		
	}
	
	
	@Test
	public void testCreate() {
		
		final OrderLine created = new OrderLine(9L,1L,1L,4);
		assertEquals(created,orderLineDAO.create(created));
		
	}
	
	@Test
	public void testReadAll() {
		
		List<OrderLine> expected = new ArrayList<>();
		
		expected.add(new OrderLine(1L,1L,1L,2));
		expected.add(new OrderLine(2L,2L,3L,7));
		expected.add(new OrderLine(3L,2L,4L,5));
		expected.add(new OrderLine(4L,2L,2L,1));
		expected.add(new OrderLine(5L,4L,1L,15));
		expected.add(new OrderLine(6L,4L,2L,8));
		expected.add(new OrderLine(7L,3L,2L,4));
		expected.add(new OrderLine(8L,3L,4L,1));
		
		assertEquals(expected,orderLineDAO.readAll());
		
	}
	
	@Test
	public void testReadLatest() {
		
		assertEquals(new OrderLine(8L,3L,4L,1),orderLineDAO.readLatest());
		
	}
	
	@Test
	public void testReadOrderLine() {
		
		final Long ID = 1L;
		
		assertEquals(new OrderLine(ID,1L,1L,2),orderLineDAO.readOrderLine(ID));
		
	}
	
	@Test
	public void testUpdate() {
		
		final OrderLine updated = new OrderLine(1L,1L,1L,6);
		assertEquals(updated,orderLineDAO.update(updated));
		
	}
	
	@Test
	public void testDelete() {
		
		final Long ID = 1L;
		
		assertEquals(1,orderLineDAO.delete(ID));
		
	}
	
	
	@Test
	public void testDeleteProduct() {
		
		final Long ID = 1L;
		
		assertEquals(1,orderLineDAO.deleteProduct(ID));
		
	}
	
	@Test
	public void testDeleteOrder() {
		
		final Long ID = 1L;
		
		assertEquals(1,orderLineDAO.deleteOrder(ID));
		
	}
	
	@Test
	public void testAllOrderLinesByOrder() {

		final Long ID = 2L;
		final List<OrderLineAndProduct> list = new ArrayList<>();
		list.add(new OrderLineAndProduct(2L,"Pear",7,0.99));
		list.add(new OrderLineAndProduct(3L,"Dragonfruit",5,15.00));
		list.add(new OrderLineAndProduct(4L,"Banana",1,0.70));
		
		
		
		assertEquals(list,orderLineDAO.allOrderLinesByOrder(ID));
	}
	
	@Test
	public void testupdateTotalPriceUpdate() {
		
		final Long ID = 1L;
		final Double total = 3.98;
		
		assertEquals(total,orderLineDAO.updateTotalPriceUpdate(ID));
		
	}
	
	@Test
	public void testSetOrderLineQuantToZero() {
		
		final Long orderLineID = 1L;
		Integer x = 0;
		
		assertEquals(x,orderLineDAO.setOrderLineQuantToZero(orderLineID));
	}
	
	@Test
	public void testReadOrderLineAndProductTest() {
		
		final Long id = 1L;
		OrderLineAndProduct ordProd = new OrderLineAndProduct(1L,"Apple",2,1.99);

		
		assertEquals(ordProd,orderLineDAO.readOrderLineAndProduct(id));
		
	}
	
	@After
	public void teardown() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
	}
}
