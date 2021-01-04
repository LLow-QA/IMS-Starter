package com.qa.ims.persistence.dao;

import static org.junit.Assert.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {

	private final OrderDAO orderDAO = new OrderDAO();
	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	private final LocalDate date = LocalDate.now();
	private final Date dateOrdered= Date.valueOf(date);
	
	@BeforeClass
	public static void init() {
		DBUtils.connect("root", "root");
	}

	@Before
	public void setup() {
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
	}

	
	@Test
	public void testCreate() {
		
		final Order created = new Order(2L,1L,dateOrdered,3.98);
		assertEquals(created,orderDAO.create(created));
	}
	
	@Test
	public void testReadAll() {
		
		List<Order> expected = new ArrayList<>();
		Date day = Date.valueOf("2020-12-23");
		
		expected.add(new Order(1L,1L,day,3.98));
		assertEquals(expected,orderDAO.readAll());
	}
	
	@Test
	public void testReadLatest() {
		
		final Date day = Date.valueOf("2020-12-23");
		
		assertEquals(new Order(1L,1L,day,3.98),orderDAO.readLatest());
		
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
		
		assertEquals(-1,orderDAO.delete(3));
		
	}
	
	@Test
	public void testUpdateTotalPriceCreate() {
		
		orderLineDAO.create(new OrderLine(2L,1L,1L,4));
		
		
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
		
		final Long customerID = 1L;
		final Long orderID = 1L;
		final Date date = Date.valueOf("2020-12-23");
		Order order = new Order(orderID,customerID,date,3.98);
		final List<Order> customerOrders = new ArrayList<>();
		customerOrders.add(order);
	
		assertEquals(customerOrders,orderDAO.readOrdersByCustomer(customerID));
	}

}
