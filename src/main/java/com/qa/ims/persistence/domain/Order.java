//ARE YOU ON A FEATURE BRANCH

package com.qa.ims.persistence.domain;

import java.util.Date;

public class Order {
	
	private Long orderID;
	private Long customerID;
	private Date dateOrdered;
	private double totalCost;
	
	
	public Order(Long orderID, Long customerID, Date dateOrdered, double totalCost) {
		super();
		this.orderID = orderID;
		this.customerID = customerID;
		this.dateOrdered = dateOrdered;
		this.totalCost = totalCost;
	}


	public Order(Long customerID, Date dateOrdered, double totalCost) {
		super();
		this.customerID = customerID;
		this.dateOrdered = dateOrdered;
		this.totalCost = totalCost;
	}


	public Long getOrderID() {
		return orderID;
	}


	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}


	public Long getCustomerID() {
		return customerID;
	}


	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}


	public Date getDateOrdered() {
		return dateOrdered;
	}


	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}


	public double getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}


	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", customerID=" + customerID + ", dateOrdered=" + dateOrdered
				+ ", totalCost=" + totalCost + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customerID == null) {
			if (other.customerID != null)
				return false;
		} else if (!customerID.equals(other.customerID))
			return false;
		if (dateOrdered == null) {
			if (other.dateOrdered != null)
				return false;
		} else if (!dateOrdered.equals(other.dateOrdered))
			return false;
		if (orderID == null) {
			if (other.orderID != null)
				return false;
		} else if (!orderID.equals(other.orderID))
			return false;
		if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost))
			return false;
		return true;
	}
	
	

}