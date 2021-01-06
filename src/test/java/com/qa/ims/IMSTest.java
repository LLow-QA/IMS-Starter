package com.qa.ims;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.ProductController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class IMSTest {
	
	@Mock
	private Utils utils;
	
	@Mock
	private CustomerDAO customerDAO;
	
	@Mock
	private ProductDAO productDAO;
	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	CustomerController customers;
	
	@Mock
	ProductController products;
	
	@Mock
	OrderController orders;
	
	@Mock
	private DBUtils DB;
	
	@InjectMocks
	private IMS ims;

	
	@Test
	public void imsStartUp() {
		
		final String user = "root", pass = "root";
		
		Mockito.when(utils.getString()).thenReturn(user,pass,"Customer","Create","Read","Update","Delete","Return",
				"Product","Create","Read","Update","Delete","Return","orDer","Create","Read","Update","Delete","Return",
				"Stop");
	
		ims.imsSystem();
		
		Mockito.verify(utils,Mockito.times(21)).getString();
	}
	
	
	@Test
	public void imsWrongUserPass() throws Exception{
		
		final String wrongUser = "poot", wrongPass = "loot";
		
		Mockito.when(utils.getString()).thenReturn(wrongUser,wrongPass,"Stop");
	
		ims.imsSystem();
		
		Mockito.verify(utils,Mockito.times(3)).getString();
	}
	

}
