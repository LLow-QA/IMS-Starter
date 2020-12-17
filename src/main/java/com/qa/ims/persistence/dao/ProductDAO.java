//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class ProductDAO implements Dao<Product> {

	public static final Logger LOGGER = LogManager.getLogger();

	
	@Override
	public List<Product> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product create(Product product) {
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			
			statement.executeUpdate("INSERT INTO product(product_name, product_desc, price, stock)"
					+ " values('" + product.getName()+ "','" 
					+ product.getDescription() + "','" + "'" + product.getPrice()+ "','"
					+ "'" + product.getStock()+ "');");
			
			return readLatest();
			
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			
		}
		
		return null;
	}

	@Override
	public Product update(Product t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
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
	

}
