//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOTest {

	private final CustomerDAO DAO = new CustomerDAO();

	@BeforeClass
	public static void init() {
		DBUtils.connect("root", "root");
	}

	@Before
	public void setup() {
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
		
		DAO.create(new Customer("James","Pierson",29,"jp@gmail.com","Morg123","23 Word Street","SD23 3GH"));
		DAO.create(new Customer("Hannah","Wardwell",65,"hwe@gmail.com","Matgag3","12 Faor Loop","PE13 5RH"));
		DAO.create(new Customer("Andre","Harlow",40,"igas.43@aol.com","grafac5","1A Lawn Road","DO03 9KF"));
		
	}

	@Test
	public void testCreate() {
		final Customer created = new Customer(5L, "chris", "perrins",72,"reach@ufgs.net", "g", "343 red", "DC3 4rd");
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReturningCustomerID() {
		final String email = "ll@qa.com";
		final Long id = 1L;
		assertEquals(id,DAO.returningCustomerID(email));
	}
	
	@Test
	public void testReadAll() {
		List<Customer> expected = new ArrayList<>();
		expected.add(new Customer(1L, "jordan", "harrison",22,"ll@qa.com", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"));
		expected.add(new Customer(2L,"James","Pierson",29,"jp@gmail.com","Morg123","23 Word Street","SD23 3GH"));
		expected.add(new Customer(3L,"Hannah","Wardwell",65,"hwe@gmail.com","Matgag3","12 Faor Loop","PE13 5RH"));
		expected.add(new Customer(4L,"Andre","Harlow",40,"igas.43@aol.com","grafac5","1A Lawn Road","DO03 9KF"));
		
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Customer(4L,"Andre","Harlow",40,"igas.43@aol.com","grafac5","1A Lawn Road","DO03 9KF"), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Customer(ID, "jordan", "harrison",22,"ll@qa.com", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"), DAO.readCustomer(ID));
	}

	@Test
	public void testUpdate() {
		final Customer updated = new Customer(1L, "chris", "perrins",22,"rech@ufgs.net", "g", "343 red", "DC3 4rd");
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	
	@Test
	public void testReturningCustomers() {
		final String email = "ll@qa.com";
		assertTrue(DAO.returningCustomer(email));
	}	
	
	@Test
	public void testReturningCustomersFalse() {
		final String email = "NOTANEMAIL";
		assertFalse(DAO.returningCustomer(email));
	}
	
}


