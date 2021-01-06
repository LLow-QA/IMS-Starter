package com.qa.ims.persistence.dao;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;

import com.qa.ims.utils.DBUtils;

public class OrderLineDAOFailDBTest {

	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	@BeforeClass
	public static void init() {
		DBUtils.connect("roof", "rooo");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	
	}
	
	
	@Test
	public void testCreate() throws Exception {
		
		final OrderLine created = new OrderLine(9L,1L,1L,4);
		
		
		assertNull(orderLineDAO.create(created));
		
	}
	
	@Test
	public void testReadAll() throws Exception {
		
		List<OrderLine> expected = new ArrayList<>();

		
		assertEquals(expected,orderLineDAO.readAll());
		
	}
	
	@Test
	public void testReadLatest() throws Exception {
		
		assertNull(orderLineDAO.readLatest());
		
	}
	
	@Test
	public void testReadOrderLine() throws Exception {
		
		final Long ID = 1L;
		
		
		assertNull(orderLineDAO.readOrderLine(ID));
		
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		final OrderLine updated = new OrderLine(1L,1L,1L,6);
		
		
		assertNull(orderLineDAO.update(updated));
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		final Long ID = 1L;
		
		
		assertEquals(0,orderLineDAO.delete(ID));
		
	}
	
	
	@Test
	public void testDeleteProduct() throws Exception {
		
		final Long ID = 1L;
		
		
		assertEquals(0,orderLineDAO.deleteProduct(ID));
		
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		
		final Long ID = 1L;
		
		
		assertEquals(0,orderLineDAO.deleteOrder(ID));
		
	}
	
	@Test
	public void testAllOrderLinesByOrder() {

		final Long ID = 2L;
		final List<OrderLineAndProduct> list = new ArrayList<>();
		
		
		assertEquals(list,orderLineDAO.allOrderLinesByOrder(ID));
	}
	
	@Test
	public void testupdateTotalPriceUpdate() {
		
		final Long ID = 1L;
		final Double error = 0D;
		
		
		assertEquals(error,orderLineDAO.updateTotalPriceUpdate(ID));
		
	}
	
	@Test
	public void testSetOrderLineQuantToZero() {
		
		final Long orderLineID = 1L;
		
		
		assertNull(orderLineDAO.setOrderLineQuantToZero(orderLineID));
		
	}
	
	@Test
	public void testReadOrderLineAndProductTest() {
		
		final Long id = 1L;
		
		
		assertNull(orderLineDAO.readOrderLineAndProduct(id));
		
	}
	
}
