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

public class OrderLineDAOTest {

	private final OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	
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
		
		final OrderLine created = new OrderLine(2L,1L,1L,4);
		assertEquals(created,orderLineDAO.create(created));
		
	}
	
	@Test
	public void testReadAll() {
		
		List<OrderLine> expected = new ArrayList<>();
		
		expected.add(new OrderLine(1L,1L,1L,2));
		assertEquals(expected,orderLineDAO.readAll());
		
	}
	
	@Test
	public void testReadLatest() {
		
		assertEquals(new OrderLine(1L,1L,1L,2),orderLineDAO.readLatest());
		
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

		final Long ID = 1L;
		final List<OrderLineAndProduct> list = new ArrayList<>();
		list.add(new OrderLineAndProduct(1L,"Apple",2,1.99));
		
		assertEquals(list,orderLineDAO.allOrderLinesByOrder(ID));
	}
	
	@Test
	public void testupdateTotalPriceUpdate() {
		
		final Long ID = 1L;
		final Double total = 3.98;
		
		assertEquals(total,orderLineDAO.updateTotalPriceUpdate(ID));
		
	}
	
}
