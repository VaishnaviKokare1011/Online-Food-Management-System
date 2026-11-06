package com.OnlineFoodManagementSystem.bean;

public class OrderItem {
	private int OrderItem_id;
	private int Order_Id;
	private int Food_id;
	private int Quantity;
	private double Price;
	
	public int getOrderItem_id() {
		return OrderItem_id;
	}
	public void setOrderItem_id(int orderItem_id) {
		OrderItem_id = orderItem_id;
	}
	public int getOrder_Id() {
		return Order_Id;
	}
	public void setOrder_Id(int order_Id) {
		Order_Id = order_Id;
	}
	public int getFood_id() {
		return Food_id;
	}
	public void setFood_id(int food_id) {
		Food_id = food_id;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	
	

}
