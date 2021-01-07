//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.CustomerController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;


@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private CustomerDAO dao;

	@InjectMocks
	private CustomerController controller;

	@Test
	public void testCreate() {
		final String F_NAME = "barry", L_NAME = "scott", EMAIL = "lagd@dga.com", ADDRESS = "23 dfaf adgfadg", POSTCODE = "ds34 3sd", PASSWORD = "gsdfg42q";
		final int AGE = 20;
		final Customer created = new Customer(F_NAME, L_NAME, AGE, EMAIL, PASSWORD, ADDRESS, POSTCODE);

		Mockito.when(utils.getString()).thenReturn(F_NAME, L_NAME,EMAIL,PASSWORD,ADDRESS,POSTCODE);
		Mockito.when(utils.getInt()).thenReturn(AGE);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(6)).getString();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "jordan", "harrison",22,"ll@qa.com", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"));

		Mockito.when(dao.readAll()).thenReturn(customers);

		assertEquals(customers, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Customer updated = new Customer(1L, "chris", "perrins",22,"reach@ufgs.net", "g", "343 red", "DC3 4rd");

		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn(updated.getFirstName(), updated.getSurname(), updated.getEmail(), 
														updated.getPassword(), updated.getAddress(), updated.getPostcode());
		
		Mockito.when(this.utils.getInt()).thenReturn(updated.getAge());
		Mockito.when(this.dao.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(6)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
	}

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}
