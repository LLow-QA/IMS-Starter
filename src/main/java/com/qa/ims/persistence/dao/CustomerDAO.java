//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAO implements Dao<Customer> {
	
	private OrderDAO orderDAO = new OrderDAO();
	private OrderLineDAO orderLineDAO = new OrderLineDAO();

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Customer modelFromResultSet(ResultSet resultSet) throws SQLException {
		
		Long id = resultSet.getLong("customer_id");
		String firstName = resultSet.getString("fName");
		String surname = resultSet.getString("lName");
		int age = resultSet.getInt("age");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		String address = resultSet.getString("address");
		String postcode = resultSet.getString("postcode");
		
		return new Customer(id, firstName, surname, age, email, password, address, postcode);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Customer> readAll() {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("select * from customers");) {
			
			List<Customer> customers = new ArrayList<>();
			
			while (resultSet.next()) {
				
				customers.add(modelFromResultSet(resultSet));
				
			}
			return customers;
			
		} catch (SQLException e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return new ArrayList<>();
	}

	public Customer readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("SELECT * FROM customers ORDER BY customer_id DESC LIMIT 1");) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Customer create(Customer customer) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("INSERT INTO customers(fName, lName, age, email, password,"
					+ "address, postcode) values('" + customer.getFirstName()+ "','" 
					+ customer.getSurname() + "'," + customer.getAge()+ ",'"
					+ customer.getEmail()+ "','"  + customer.getPassword()+ "','" +
					 customer.getAddress()+ "','" + customer.getPostcode()+   "');");
			
			
			return readLatest();
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	public Customer readCustomer(Long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers where customer_id = " + id);) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 */
	@Override
	public Customer update(Customer customer) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("update customers set fName = '" + customer.getFirstName() + "', lName = '"
					+ customer.getSurname() + "', age = " + customer.getAge() + ", email = '"
					+ customer.getEmail() + "', password = '" + customer.getPassword() + "', address = '" 
					+ customer.getAddress() + "', postcode = '" + customer.getPostcode() +
					"' where customer_id =" + customer.getId());
			
			return readCustomer(customer.getId());
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public int delete(long id) {
		
		orderLineDAO.deleteOrder(id);
		orderDAO.deleteCustomer(id);
				
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from customers where customer_id = " + id);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		
		return 0;
		
	}
	
	public boolean returningCustomer(String email) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT email FROM customers");) {
			
			while(resultSet.next()) {
				String mail = resultSet.getString("email");
				if(email.equals(mail))
				{
					
					return true;
			
				}
			}
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return false;
	}
	
	public Long returningCustomerID(String email) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT customer_id FROM customers WHERE email = '" + email + "'");) {
			
			resultSet.next();
			Long id = resultSet.getLong("customer_id");
			return id;
				
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

}
