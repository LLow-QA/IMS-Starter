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


public class OrderDAOFailDBTest {

	private final OrderDAO orderDAO = new OrderDAO();
	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	private final LocalDate date = LocalDate.now();
	private final Date dateOrdered= Date.valueOf(date);
	
	@BeforeClass
	public static void init() {
		DBUtils.connect("soot", "qoot");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");

		
	}

	
	@Test
	public void testCreate() throws Exception {
		
		final Order created = new Order(5L,1L,dateOrdered,3.98);
		assertNull(orderDAO.create(created));
	}
	
	@Test
	public void testReadAll() throws Exception {
		
		List<Order> expected = new ArrayList<>();

		
		assertEquals(expected,orderDAO.readAll());
	}
	
	@Test
	public void testReadLatest() throws Exception {
		
		assertNull(orderDAO.readLatest());
		
	}
	
	@Test
	public void testRead() throws Exception {
		
		final Long ID = 1L;
		
		assertNull(orderDAO.readOrder(ID));
		
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		final Order updated = new Order(1L,1L,dateOrdered,5.50);
		
		assertNull(orderDAO.update(updated));	
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		assertEquals(0,orderDAO.delete(1));
		
	}
	
	@Test
	public void testUpdateTotalPriceCreate() throws Exception {
		
		orderLineDAO.create(new OrderLine(12L,1L,1L,4));
		
		final Long ID = 1L;
		
		assertNull(orderDAO.updateTotalPriceCreate(ID));
		
	}
	
	@Test
	public void testAllOrdersByCust() throws Exception {
		
		final Long ID = 1L;
		List<Order> customerOrders = new ArrayList<>();

		
		assertEquals(customerOrders,orderDAO.allOrdersByCust(ID));
		
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		
		final Long ID = 1L;
		
		
		assertEquals(0,orderDAO.deleteCustomer(ID));
		
	}

}
