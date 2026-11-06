package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.OnlineFoodManagementSystem.bean.Order;

public class OrderDao {
	
	public int addOrder(Order o) {

	    int orderId = 0;

	    try {

	        Class.forName("com.mysql.cj.jdbc.Driver");

	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root", "root" );

	        PreparedStatement pr = con.prepareStatement("insert into orders(Customer_Id, Order_Date, Total_Amount, Status) " + "values(?, NOW(), ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS );

	        pr.setInt(1, o.getCustomer_Id());
	        pr.setDouble(2, o.getTotal_Amount());
	        pr.setString(3, o.getStatus());

	        int i = pr.executeUpdate();

	        if (i > 0) {

	            ResultSet rs = pr.getGeneratedKeys();

	            if (rs.next()) {

	                orderId = rs.getInt(1);
	            }
	        }

	        con.close();

	    } catch (Exception e) {

	        System.out.println(e);
	    }

	    return orderId;
	}
	  

	  
	public String viewOrders() {

	    String data = "";

	    try {

	        Class.forName("com.mysql.cj.jdbc.Driver");

	        Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root", "root");

	        PreparedStatement pr = con.prepareStatement("select * from orders");

	        ResultSet rs = pr.executeQuery();

	        while (rs.next()) {

	            data = data
	                    + "Order ID : " + rs.getInt("Order_Id")
	                    + "\nCustomer ID : " + rs.getInt("Customer_Id")
	                    + "\nOrder Date : " + rs.getDate("Order_Date")
	                    + "\nTotal Amount : " + rs.getDouble("Total_Amount")
	                    + "\nStatus : " + rs.getString("Status")
	                    + "\n-----------------------------\n";
	        }

	        if (data.equals("")) {
	            data = "No Orders Found";
	        }

	        con.close();

	    } catch (Exception e) {

	        System.out.println(e);
	        data = "Error while viewing orders";
	    }

	    return data;
	}
	
	
	
	

	    public String viewCustomerOrders(int customerId) {

	        String data = "";

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root","root"  );

	            String sql ="select o.Order_Id, o.Order_Date, o.Total_Amount, o.Status, " + "oi.Food_Id, oi.Quantity, oi.Price, f.Food_Name " + "from orders o "+ "join order_items oi on o.Order_Id = oi.Order_Id " + "join food_items f on oi.Food_Id = f.Food_Id "  + "where o.Customer_Id=?";

	            PreparedStatement pr = con.prepareStatement(sql);

	            pr.setInt(1, customerId);

	            ResultSet rs = pr.executeQuery();

	            while (rs.next()) {

	                data = data
	                        + "Order ID : " + rs.getInt("Order_Id")
	                        + "\nOrder Date : " + rs.getDate("Order_Date")
	                        + "\nFood Name : " + rs.getString("Food_Name")
	                        + "\nQuantity : " + rs.getInt("Quantity")
	                        + "\nPrice : " + rs.getDouble("Price")
	                        + "\nTotal Amount : " + rs.getDouble("Total_Amount")
	                        + "\nStatus : " + rs.getString("Status")
	                        + "\n-----------------------------\n";
	            }

	            if (data.equals("")) {

	                data = "No Orders Found";
	            }

	            con.close();

	        } catch (Exception e) {

	            System.out.println(e);
	        }

	        return data;
	    }
	}


