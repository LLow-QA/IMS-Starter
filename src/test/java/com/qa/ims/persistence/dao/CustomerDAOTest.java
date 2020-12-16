//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

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
		DBUtils.connect("root", "pass");
	}

	@Before
	public void setup() {
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer created = new Customer(2L, "chris", "perrins",22,"reach@ufgs.net", "g", "343 red", "DC3 4rd");
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Customer> expected = new ArrayList<>();
		expected.add(new Customer(1L, "jordan", "harrison",22,"lafga@ufgs.net", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Customer(1L, "jordan", "harrison",22,"lafga@ufgs.net", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Customer(ID, "jordan", "harrison",22,"lafga@ufgs.net", "dgsfhaf8g", "343 fasd sf", "DC3 4rd"), DAO.readCustomer(ID));
	}

	@Test
	public void testUpdate() {
		final Customer updated = new Customer(1L, "chris", "perrins",22,"reach@ufgs.net", "g", "343 red", "DC3 4rd");
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
}
