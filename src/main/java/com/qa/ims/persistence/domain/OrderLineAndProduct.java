package com.qa.ims.persistence.domain;

public class OrderLineAndProduct {

	private Long orderLineID;
	private String productName;
	private int productQuantity;
	private double price;
	
	
	public OrderLineAndProduct(Long orderLineID, String productName, int productQuantity, double price) {
		super();
		this.orderLineID = orderLineID;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.price = price;
	}


	public Long getOrderLineID() {
		return orderLineID;
	}


	public void setOrderLineID(Long orderLineID) {
		this.orderLineID = orderLineID;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getProductQuantity() {
		return productQuantity;
	}


	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Product in orders basket: OrderLine ID = " + orderLineID + ", Product name = " + productName + ", Product Quantity = "
				+ productQuantity + ", product Price = " + price;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineAndProduct other = (OrderLineAndProduct) obj;
		if (orderLineID == null) {
			if (other.orderLineID != null)
				return false;
		} else if (!orderLineID.equals(other.orderLineID))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productQuantity != other.productQuantity)
			return false;
		return true;
	}
	
	
	
	
}
