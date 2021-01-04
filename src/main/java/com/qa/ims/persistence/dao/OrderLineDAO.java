package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.OrderLine;
import com.qa.ims.persistence.domain.OrderLineAndProduct;
import com.qa.ims.utils.DBUtils;


public class OrderLineDAO implements Dao<OrderLine>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	

	@Override
	public List<OrderLine> readAll() {

		try (Connection connection = DBUtils.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from orderline");) {
			
			List<OrderLine> orderLine = new ArrayList<>();
			
			while(resultSet.next()) {
				
				orderLine.add(modelFromResultSet(resultSet));
			}
			return orderLine;
			
		} catch(SQLException e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
				
		return new ArrayList<>();
	}
	
	public OrderLine readLatest() {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("SELECT * FROM orderline ORDER BY orderline_id DESC LIMIT 1");) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public OrderLine create(OrderLine orderLine) {

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("INSERT INTO orderline(order_id, product_id, product_quantity)"
					+ " values(" + orderLine.getOrderID()+ "," + orderLine.getProductID() +
							   "," + orderLine.getQuantity() + ");");
			
			return readLatest();
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
	
	public OrderLine readOrderLine(Long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orderline where orderline_id = " + id);) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public OrderLine update(OrderLine orderLine) {

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("update orderline set order_id = " + orderLine.getOrderID() +
					", product_id = "+ orderLine.getProductID() + ", product_quantity = " + orderLine.getQuantity());
			
			return readOrderLine(orderLine.getOrderLineID());
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public int delete(long id) {

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from orderline where orderline_id = " + id + ";");
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return 0;
	}
	
	public int deleteProduct(long id) {

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from orderline where product_id = " + id);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return 0;
	}
	
	public int deleteOrder(long id) {

		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from orderline where order_id = " + id);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return 0;
	}

	@Override
	public OrderLine modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderLineID = resultSet.getLong("orderline_id");
		Long orderID = resultSet.getLong("order_id");
		Long productID = resultSet.getLong("product_id");
		int quantity = resultSet.getInt("product_quantity");
		
		return new OrderLine(orderLineID, orderID, productID, quantity);
	}
	
	public OrderLineAndProduct newModelFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderLineID = resultSet.getLong("orderline_id");
		String productName = resultSet.getString("product_name");
		int quantity = resultSet.getInt("product_quantity");
		Double price = resultSet.getDouble("price");
		
		return new OrderLineAndProduct(orderLineID, productName, quantity, price);
	}
	
	public List<OrderLineAndProduct> allOrderLinesByOrder(Long id) {
		

		
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("SELECT orderline_id, product_name, product_quantity, price FROM orderline join products on "
						+ "orderline.product_id = products.product_id where order_id = " + id);) {
			
			List<OrderLineAndProduct> list = new ArrayList<>();
			
			while(resultSet.next()) {
			
				list.add(newModelFromResultSet(resultSet));
			
			}
			
			return list;
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return new ArrayList<>();
	}
	
	public Double updateTotalPriceUpdate(Long orderID) {
		
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				 Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery("SELECT SUM(product_quantity*price) AS orderline_total FROM orderline JOIN products on "
				 		+ "orderline.product_id = products.product_id WHERE order_id = "+ orderID);){
			
			resultSet.next();
			return resultSet.getDouble("orderline_total");
			
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return 0D;
		
	}
	
}
