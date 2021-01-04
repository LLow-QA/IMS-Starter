//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{

	private OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				 Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery("select * from orders");) {
				
				List<Order> orders = new ArrayList<>();
				
				while (resultSet.next()) {
					
					orders.add(modelFromResultSet(resultSet));
					
				}
				return orders;
				
			} catch (SQLException e) {
				
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
				
			}
			return new ArrayList<>();
	}
	
	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1 ");) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
	

	@Override
	public Order create(Order order) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("INSERT INTO orders(customer_id, date_ordered, total_cost) values("
					+ order.getCustomerID()+ ",'" + order.getDateOrdered()+ "'," + order.getTotalCost() +");");
			
			return readLatest();
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
	
	public Order readOrder(Long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders where order_id = " + id);) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
	

	@Override
	public Order update(Order order) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("update orders set customer_id = " + order.getCustomerID() + ", date_ordered = '"
					+ order.getDateOrdered() + "', total_cost = " + order.getTotalCost());
			
			return readOrder(order.getOrderID());
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public int delete(long id) {
		
		if(orderLineDAO.deleteOrder(id) == 1) {
			
			try (Connection connection = DBUtils.getInstance().getConnection();
					Statement statement = connection.createStatement();) {
				
				return statement.executeUpdate("delete from orders where order_id = " + id);
				
			} catch (Exception e) {
				
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			
			}
			
		}else {
			
			return -1;
		}
		
		return 0;
		
	}
	
	public int deleteCustomer(long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from orders where customer_id = " + id + ";");
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return 0;
		
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		
		Long orderID = resultSet.getLong("order_id");
		Long customerID = resultSet.getLong("customer_id");
		Date dateOrdered = resultSet.getDate("date_ordered");
		Double totalCost = resultSet.getDouble("total_cost");
		
		return new Order(orderID, customerID, dateOrdered, totalCost);
	}
	
	public Order updateTotalPriceCreate(Long orderID) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT SUM(product_quantity*price) AS orderline_total FROM orderline JOIN products on "
				 		+ "orderline.product_id = products.product_id WHERE order_id = " + orderID);) {
			
			resultSet.next();
			Double total = resultSet.getDouble("orderline_total");
			
			statement.executeUpdate("update orders set total_cost = " + total + "WHERE order_id = " + orderID);
			
			return readOrder(orderID);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
		
	}
	
	public List<Order> allOrdersByCust(Long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders where customer_id = " + id);) {
			
			List<Order> orders = new ArrayList<>();
			
			while(resultSet.next()) {
				
				orders.add(modelFromResultSet(resultSet));
			}
			
			return orders;
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return new ArrayList<>();
	}
	
	public List<Order> readOrdersByCustomer(Long custID){
		
		List<Order> orders = allOrdersByCust(custID);
		for (Order order : orders) {
			LOGGER.info(order.toString());
		
		}
		return orders;
	}
}
