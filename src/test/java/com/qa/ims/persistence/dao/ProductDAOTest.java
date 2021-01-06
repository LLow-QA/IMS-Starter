package com.qa.ims.persistence.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class ProductDAOTest {
	
	private final ProductDAO productDAO = new ProductDAO();

	@BeforeClass
	public static void init() {
		DBUtils.connect("root", "root");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
		productDAO.create(new Product("Banana", "A bunch of yellow bananas.",0.70,50));
		productDAO.create(new Product("Pear", "An oddly shaped pear.",0.99,150));
		productDAO.create(new Product("Dragonfruit", "A very exotic fruit.",15.00,5));
		
		
	}
	
	
	@Test
	public void testCreate() {
		final Product created = new Product(5L,"Cherry","Some particularly red cherries.",4.99,200);
		assertEquals(created, productDAO.create(created));
	}

	
	@Test
	public void testReadAll() {
		List<Product> expected = new ArrayList<>();
		expected.add(new Product(1L, "Apple","A very tasty apple.",1.99,100));
		expected.add(new Product(2L,"Banana", "A bunch of yellow bananas.",0.70,50));
		expected.add(new Product(3L,"Pear", "An oddly shaped pear.",0.99,150));
		expected.add(new Product(4L,"Dragonfruit", "A very exotic fruit.",15.00,5));
		
		assertEquals(expected, productDAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Product(4L,"Dragonfruit", "A very exotic fruit.",15.00,5), productDAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Product(ID, "Apple","A very tasty apple.",1.99,100), productDAO.readProduct(ID));
	}

	@Test
	public void testUpdate() {
		final Product updated = new Product(1L,"Pie","Meaty",1.00,1000);
		assertEquals(updated, productDAO.update(updated));

	}

	@Test
	public void testDelete() {
		
		assertEquals(1, productDAO.delete(1));
	}
	
	@Test
	public void testReturnProductID() {
		final String name = "Apple";
		final Long ID = 1L;
		assertEquals(ID,productDAO.returnProductID(name));
	}
	
	@Test
	public void testReturnProductIDFalse() {
		final String name = "Car";
		
		assertNull(productDAO.returnProductID(name));
	}
	
	@Test
	public void testListToString() {
		
		List<Product> prod = new ArrayList<>();
		prod.add(new Product(1L,"Apple","A very tasty apple.",1.99,100));
		prod.add(new Product(2L,"Banana", "A bunch of yellow bananas.",0.70,50));
		prod.add(new Product(3L,"Pear", "An oddly shaped pear.",0.99,150));
		prod.add(new Product(4L,"Dragonfruit", "A very exotic fruit.",15.00,5));
		
		assertEquals(prod,productDAO.listToString());
		
	}

	@Test
	public void testStockCheck() {
		
		final Integer stockPostPurchase = 93, quantityPurchased = 7;
		final Long productID = 1L;
		
		assertEquals(stockPostPurchase,productDAO.stockCheck(productID, quantityPurchased));
		
		
	}
	
	@Test
	public void testStockCheckNegReturn() {
		
		final Integer stockPostPurchase = -100, quantityPurchased = 200;
		final Long productID = 1L;
		
		assertEquals(stockPostPurchase,productDAO.stockCheck(productID, quantityPurchased));
		
		
	}
	
	@Test
	public void testRemoveStockFromOrder() {
		
		final Long orderLineID = 1L;
		final Integer newStockQuant = 102;
		
		
		assertEquals(newStockQuant,productDAO.removeStockFromOrder(orderLineID));
		

		
		
		
	}

}
