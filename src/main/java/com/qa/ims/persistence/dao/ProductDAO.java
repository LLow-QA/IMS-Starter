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

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;


public class ProductDAO implements Dao<Product> {

	private OrderLineDAO orderLineDAO = new OrderLineDAO();
	
	
	public static final Logger LOGGER = LogManager.getLogger();

	
	@Override
	public List<Product> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				 Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery("select * from products");) {
				
				List<Product> products = new ArrayList<>();
				
				while (resultSet.next()) {
					
					products.add(modelFromResultSet(resultSet));
					
				}
				return products;
				
			} catch (SQLException e) {
				
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
				
			}
			return new ArrayList<>();
	}

	@Override
	public Product create(Product product) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("INSERT INTO products(product_name, product_desc, price, stock)"
					+ " values('" + product.getName()+ "','" 
					+ product.getDescription() + "'," + product.getPrice()+ ","
					+ product.getStock()+ ");");
			
			return readLatest();
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		
		return null;
	}
	
	public Product readProduct(Long id) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM products where product_id = " + id);) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public Product update(Product product) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("update products set product_name = '" + product.getName() + "', product_desc = '"
					+ product.getDescription() + "', price = " + product.getPrice() + ", stock = "
					+ product.getStock() + " WHERE product_id = " + product.getId() + ";");
			
			return readProduct(product.getId());
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}

	@Override
	public int delete(long id) {
		
		orderLineDAO.deleteProduct(id);
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			return statement.executeUpdate("delete from products where product_id = " + id + ";");
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}

		return 0;
	}

	@Override
	public Product modelFromResultSet(ResultSet resultSet) throws SQLException {
		
		Long id = resultSet.getLong("product_id");
		String name = resultSet.getString("product_name");
		String desc = resultSet.getString("product_desc");
		double price = resultSet.getDouble("price");
		int stock = resultSet.getInt("stock");
		
		return new Product(id,name,desc,price,stock);
		
	}
	
	public Product readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery("SELECT * FROM products ORDER BY product_id DESC LIMIT 1");) {
			
			resultSet.next();
			return modelFromResultSet(resultSet);
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
	
	public Long returnProductID(String name) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				 Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery("SELECT product_id FROM products WHERE product_name = '" + name +"'");) {
				
				if(resultSet.next()) {
				return resultSet.getLong("product_id");
				}else {
					return null;
				}
			} catch (Exception e) {
				
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
				
			}
		return null;
	}
	
	public List<Product> listToString() {
		
		List<Product> products = readAll();
		for (Product product : products) {
			
			LOGGER.info(product.toString());
		}
		return products;
	}
	
	public Integer stockCheck(Long prodID, int quantityPurchased) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
		int totalStock = readProduct(prodID).getStock();
		
		int newTotal = totalStock - quantityPurchased;
		
		if(newTotal >= 0) {
				
				statement.executeUpdate("update products set stock = "
						+ newTotal + " WHERE product_id = '"+ prodID + "';");
			
				return newTotal;
				
		}else {
			
			return newTotal;
			
		}
		
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
			
	return null;
		
	}
	
	public Integer removeStockFromOrder(Long orderLineID) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			Long productID = orderLineDAO.readOrderLine(orderLineID).getProductID();
			int oldOrderQuant = orderLineDAO.readOrderLine(orderLineID).getQuantity();
			
			orderLineDAO.setOrderLineQuantToZero(orderLineID);
			
			
			int oldStockQuant = readProduct(productID).getStock();
			int newStockQuant = oldStockQuant + oldOrderQuant;

			statement.executeUpdate("update products set stock = " + newStockQuant + " WHERE product_id = '" + productID + "';");
			return newStockQuant;
		
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		return null;
	}
}
