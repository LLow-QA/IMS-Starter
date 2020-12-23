package com.qa.ims.controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.ProductController;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private ProductDAO productDao;

	@InjectMocks
	private ProductController productController;
	
	
	@Test
	public void testCreate() {
		final String P_NAME = "Pear", P_DESC = "A nice pear.";
		final int  STOCK = 100;
		final double PRICE = 1.00;
		final Product created = new Product(P_NAME,P_DESC,PRICE,STOCK);
		
		Mockito.when(utils.getString()).thenReturn(P_NAME,P_DESC);
		Mockito.when(utils.getInt()).thenReturn(STOCK);
		Mockito.when(utils.getDouble()).thenReturn(PRICE);
		Mockito.when(productDao.create(created)).thenReturn(created);
		
		assertEquals(created, productController.create());
		
		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(productDao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(3L,"Banana","A bunch of curvy bananas.", 0.89,104));

		Mockito.when(productDao.readAll()).thenReturn(products);

		assertEquals(products, productController.readAll());

		Mockito.verify(productDao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Product updated = new Product(3L, "Orange", "large satsumas.",3.99,500);

		Mockito.when(this.utils.getLong()).thenReturn(3L);
		Mockito.when(this.utils.getString()).thenReturn(updated.getName(), updated.getDescription());
		
		Mockito.when(this.utils.getInt()).thenReturn(updated.getStock());
		Mockito.when(this.utils.getDouble()).thenReturn(updated.getPrice());
		
		Mockito.when(this.productDao.update(updated)).thenReturn(updated);

		assertEquals(updated, this.productController.update());

		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.productDao, Mockito.times(1)).update(updated);
	}

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(productDao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.productController.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(productDao, Mockito.times(1)).delete(ID);
	}

}

