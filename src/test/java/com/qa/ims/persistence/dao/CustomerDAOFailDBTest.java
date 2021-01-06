//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOFailDBTest {

	private final CustomerDAO DAO = new CustomerDAO();

	@BeforeClass
	public static void init() {
		
		DBUtils.connect("Loot", "noot");
	}

	@Before
	public void setup() {
		
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	
	}

	@Test
	public void testCreate() throws Exception {
		
		final Customer created = new Customer(5L, "chris", "perrins",72,"reach@ufgs.net", "g", "343 red", "DC3 4rd");
		
		
		assertNull(DAO.create(created));
		
	}

	@Test
	public void testReturningCustomerID() throws Exception {
		
		final String email = "ll@qa.com";
		
		
		assertNull(DAO.returningCustomerID(email));
		
	}
	
	@Test
	public void testReadAll() throws Exception {
		
		List<Customer> error = new ArrayList<>();
		
		
		assertEquals(error, DAO.readAll());
		
	}

	@Test
	public void testReadLatest() throws Exception {
		
		
		assertNull(DAO.readLatest());
		
	}

	@Test
	public void testRead() throws Exception {
		
		final long ID = 1L;
		
		
		assertNull(DAO.readCustomer(ID));
		
	}

	@Test
	public void testUpdate() throws Exception {
		
		final Customer updated = new Customer(1L, "chris", "perrins",22,"rech@ufgs.net", "g", "343 red", "DC3 4rd");
		
		
		assertNull(DAO.update(updated));
		

	}

	@Test
	public void testDelete() throws Exception {
		
		
		assertEquals(0, DAO.delete(1));
		
	}
	
	
	@Test
	public void testReturningCustomers() throws Exception {
		
		final String email = "ll@qa.com";
		
		
		assertFalse(DAO.returningCustomer(email));
		
	}	
	
	
}


