package com.OnlineFoodManagementSystem.bean;

import java.sql.Timestamp;

public class Payment {

    private int Payment_Id;
    private int Order_Id;
    private int Customer_Id;
    private String Payment_Method;
    private String Payment_Status;
    private Timestamp Payment_Date;
    private double Amount;

    public int getPayment_Id() {
        return Payment_Id;
    }

    public void setPayment_Id(int payment_Id) {
        Payment_Id = payment_Id;
    }

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

    public String getPayment_Method() {
        return Payment_Method;
    }

    public void setPayment_Method(String payment_Method) {
        Payment_Method = payment_Method;
    }

    public String getPayment_Status() {
        return Payment_Status;
    }

    public void setPayment_Status(String payment_Status) {
        Payment_Status = payment_Status;
    }

    public Timestamp getPayment_Date() {
        return Payment_Date;
    }

    public void setPayment_Date(Timestamp payment_Date) {
        Payment_Date = payment_Date;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}