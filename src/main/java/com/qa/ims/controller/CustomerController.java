//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

/**
 * Takes in customer details for CRUD functionality
 *
 */
public class CustomerController implements CrudController<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	private CustomerDAO customerDAO;
	private Utils utils;

	public CustomerController(CustomerDAO customerDAO, Utils utils) {
		
		super();
		this.customerDAO = customerDAO;
		this.utils = utils;
		
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Customer> readAll() {
		
		List<Customer> customers = customerDAO.readAll();
		for (Customer customer : customers) {
			
			LOGGER.info(customer.toString());
			
		}
		return customers;
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Customer create() {
		
		LOGGER.info("Please enter your first name: ");
		String firstName = utils.getString();
		
		LOGGER.info("Please enter your surname: ");
		String surname = utils.getString();
		
		LOGGER.info("Please enter your age: ");
		int age = utils.getInt();
		
		LOGGER.info("Please enter your email: ");
		String email = utils.getString();
		
		LOGGER.info("Please enter a password: ");
		String password = utils.getString();
		
		LOGGER.info("Please enter your address: ");
		String address = utils.getString();
		
		LOGGER.info("Please enter your postcode: ");
		String postcode = utils.getString();
		
		Customer customer = customerDAO.create(new Customer(firstName, surname, age, email, password, address, postcode));
		LOGGER.info("Customer created.");
		return customer;
		
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Customer update() {
		
		LOGGER.info("Please enter the id of the customer you would like to update: ");
		Long id = utils.getLong();
		
		LOGGER.info("Please update your first name: ");
		String firstName = utils.getString();
		
		LOGGER.info("Please update your surname: ");
		String surname = utils.getString();
		
		LOGGER.info("Please update your age: ");
		int age = utils.getInt();
		
		LOGGER.info("Please update your email: ");
		String email = utils.getString();
		
		LOGGER.info("Please update a password: ");
		String password = utils.getString();
		
		LOGGER.info("Please update your address: ");
		String address = utils.getString();
		
		LOGGER.info("Please update your postcode: ");
		String postcode = utils.getString();
		
		Customer customer = customerDAO.update(new Customer(id, firstName, surname, age, email, password, address, postcode));
		LOGGER.info("Customer Updated");
		return customer;
		
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		
		LOGGER.info("Please enter the id of the customer you would like to delete: ");
		Long id = utils.getLong();
		return customerDAO.delete(id); //use cascade
		
	}

}
