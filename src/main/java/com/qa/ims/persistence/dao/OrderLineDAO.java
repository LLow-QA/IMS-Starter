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
			
			statement.executeUpdate("INSERT INTO orderline(orderline_id, order_id, product_id, product_quantity)"
					+ " values('"+ orderLine.getOrderLineID()+ "','" + orderLine.getOrderID()
					+ "','" + orderLine.getProductID() + "','" + orderLine.getQuantity() + "');");
			
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
			
			statement.executeUpdate("update orderline set order_id ='" + orderLine.getOrderID() +
					"', product_id ='"+ orderLine.getProductID() + "', product_quantity ='" + orderLine.getQuantity());
			
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
			
			return statement.executeUpdate("delete from orderline where orderline_id = " + id);
			
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
	

}
