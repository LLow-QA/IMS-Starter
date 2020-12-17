package com.qa.ims.persistence.domain;

public class OrderLine {
	
	private Long orderLineID;
	private Long orderID;
	private Long productID;
	private int quantity;
	
	
	public OrderLine(Long orderLineID, Long orderID, Long productID, int quantity) {
		super();
		this.orderLineID = orderLineID;
		this.orderID = orderID;
		this.productID = productID;
		this.quantity = quantity;
	}


	public OrderLine(Long orderID, Long productID, int quantity) {
		super();
		this.orderID = orderID;
		this.productID = productID;
		this.quantity = quantity;
	}


	public Long getOrderLineID() {
		return orderLineID;
	}


	public void setOrderLineID(Long orderLineID) {
		this.orderLineID = orderLineID;
	}


	public Long getOrderID() {
		return orderID;
	}


	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}


	public Long getProductID() {
		return productID;
	}


	public void setProductID(Long productID) {
		this.productID = productID;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "OrderLine [orderLineID=" + orderLineID + ", orderID=" + orderID + ", productID=" + productID
				+ ", quantity=" + quantity + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (orderID == null) {
			if (other.orderID != null)
				return false;
		} else if (!orderID.equals(other.orderID))
			return false;
		if (orderLineID == null) {
			if (other.orderLineID != null)
				return false;
		} else if (!orderLineID.equals(other.orderLineID))
			return false;
		if (productID == null) {
			if (other.productID != null)
				return false;
		} else if (!productID.equals(other.productID))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
	
	
	

}
