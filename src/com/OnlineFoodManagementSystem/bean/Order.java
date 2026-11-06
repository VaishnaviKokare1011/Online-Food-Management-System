package com.OnlineFoodManagementSystem.bean;

import java.security.Timestamp;

public class Order {
	
private int Order_Id;
private int Customer_Id;
private Timestamp Order_Date;
private double Total_Amount;
private String Status;


public int getOrder_Id() {
	return Order_Id;
}
public void setOrder_Id(int order_Id) {
	Order_Id = order_Id;
}
public int getCustomer_Id() {
	return Customer_Id;
}
public void setCustomer_Id(int customer_Id) {
	Customer_Id = customer_Id;
}
public Timestamp getOrder_Date() {
	return Order_Date;
}
public void setOrder_Date(Timestamp order_Date) {
	Order_Date = order_Date;
}
public double getTotal_Amount() {
	return Total_Amount;
}
public void setTotal_Amount(double total_Amount) {
	Total_Amount = total_Amount;
}
public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}



}
