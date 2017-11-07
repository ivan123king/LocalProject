package com.lw.hibernate.relationship.n21;

/**
 * 一个客户拥有多个订单，所以此处包含一个Customer
 * 但是这不是组合关系，不能在hbm.xml中用component
 * @author lenovo
 *
 */
public class Order {

	private Integer orderId;
	private String orderName;
	
	private Customer customer;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
