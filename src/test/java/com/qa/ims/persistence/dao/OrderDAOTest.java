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
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;


public class OrderDAOTest {

	private final OrderDAO orderDAO = new OrderDAO();
	private final ProductDAO productDAO = new ProductDAO();
	private final CustomerDAO customerDAO = new CustomerDAO();
	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	private final LocalDate date = LocalDate.now();
	private final Date dateOrdered= Date.valueOf(date);
	
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
		
		final Order created = new Order(5L,1L,dateOrdered,3.98);
		assertEquals(created,orderDAO.create(created));
	}
	
	@Test
	public void testReadAll() {
		
		List<Order> expected = new ArrayList<>();
		Date day = Date.valueOf("2020-12-23");
		
		expected.add(new Order(1L,1L,day,3.98));
		expected.add(new Order(2L,2L,dateOrdered,82.63));
		expected.add(new Order(3L,4L,dateOrdered,17.80));
		expected.add(new Order(4L,2L,dateOrdered,35.45));
		
		assertEquals(expected,orderDAO.readAll());
	}
	
	@Test
	public void testReadLatest() {
		
		assertEquals(new Order(4L,2L,dateOrdered,35.45),orderDAO.readLatest());
		
	}
	
	@Test
	public void testRead() {
		
		final Long ID = 1L;
		final Date day = Date.valueOf("2020-12-23");
		
		assertEquals(new Order(ID,1L,day,3.98),orderDAO.readOrder(ID));
		
	}
	
	@Test
	public void testUpdate() {
		
		final Order updated = new Order(1L,1L,dateOrdered,5.50);
		assertEquals(updated,orderDAO.update(updated));	
		
	}
	
	@Test
	public void testDelete() {
		
		assertEquals(1,orderDAO.delete(1));
		
	}
	
	@Test
	public void testDeleteFalse() {
		
		assertEquals(0,orderDAO.delete(997887));
		
	}
	
	@Test
	public void testUpdateTotalPriceCreate() {
		
		orderLineDAO.create(new OrderLine(5L,1L,1L,4));
		
		
		final Long ID = 1L;
		final double totalCost = 11.94;
		final Date day = Date.valueOf("2020-12-23");
		final Order updated = new Order(1L,1L,day,totalCost);
		
		assertEquals(updated,orderDAO.updateTotalPriceCreate(ID));
		
	}
	
	@Test
	public void testAllOrdersByCust() {
		
		final Long ID = 1L;
		final Date day = Date.valueOf("2020-12-23");
		List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(new Order(1L,ID,day,3.98));
		
		assertEquals(customerOrders,orderDAO.allOrdersByCust(ID));
		
	}
	
	@Test
	public void testReadOrdersByCustomer() {
		
		final Long customerID = 2L;

		final List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(new Order(2L,2L,dateOrdered,82.63));
		customerOrders.add(new Order(4L,2L,dateOrdered,35.45));
	
		assertEquals(customerOrders,orderDAO.readOrdersByCustomer(customerID));
	}
	
	@After
	public void teardown() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
	}

}
