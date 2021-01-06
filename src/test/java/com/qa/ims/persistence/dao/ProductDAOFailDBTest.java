package com.qa.ims.persistence.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class ProductDAOFailDBTest {
	
	private final ProductDAO productDAO = new ProductDAO();

	@BeforeClass
	public static void init() {
		DBUtils.connect("rout", "clout");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
	}
	
	
	@Test
	public void testCreate() throws Exception {
		
		final Product created = new Product(5L,"Cherry","Some particularly red cherries.",4.99,200);
		
		
		assertNull(productDAO.create(created));
		
	}

	
	@Test
	public void testReadAll() throws Exception {
		
		List<Product> expected = new ArrayList<>();
		
		
		assertEquals(expected, productDAO.readAll());
		
	}

	@Test
	public void testReadLatest() throws Exception {
		
		assertNull(productDAO.readLatest());
	
	}

	@Test
	public void testRead() throws Exception {
		
		final long ID = 1L;
		
		
		assertNull(productDAO.readProduct(ID));
		
	}

	@Test
	public void testUpdate() throws Exception {
		
		final Product updated = new Product(1L,"Pie","Meaty",1.00,1000);
		
		
		assertNull(productDAO.update(updated));

	}

	@Test
	public void testDelete() throws Exception {
		
		assertEquals(0, productDAO.delete(1));
		
	}
	
	@Test
	public void testReturnProductID() throws Exception {
		
		final String name = "Apple";

		
		assertNull(productDAO.returnProductID(name));
		
	}
	
	@Test
	public void testStockCheck() throws Exception {
		
		final Integer quantityPurchased = 7;
		final Long productID = 1L;
		
		assertNull(productDAO.stockCheck(productID, quantityPurchased));
		
		
	}
	
	@Test
	public void testRemoveStockFromOrder() throws Exception {
		
		final Long orderLineID = 1L;
		
		assertNull(productDAO.removeStockFromOrder(orderLineID));
		

		
		
		
	}

}
