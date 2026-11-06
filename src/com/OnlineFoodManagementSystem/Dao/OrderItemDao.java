package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.OnlineFoodManagementSystem.bean.OrderItem;

public class OrderItemDao {
	

	   
	    public boolean addOrderItem(OrderItem oi) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root","root");
	            PreparedStatement pr = con.prepareStatement( "insert into order_items(Order_Id,Food_Id,Quantity,Price) values(?,?,?,?)");
	            pr.setInt(1, oi.getOrder_Id());
	            pr.setInt(2, oi.getFood_id());
	            pr.setInt(3, oi.getQuantity());
	            pr.setDouble(4, oi.getPrice());

	            int i = pr.executeUpdate();

	            if (i > 0) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }


	  
	    public void viewOrderItems(int orderId) {

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root","root");
	            PreparedStatement pr = con.prepareStatement(   "select * from Order_Items where Order_Id=?");
	    	            
	                 

	            pr.setInt(1, orderId);

	            ResultSet rs = pr.executeQuery();

	            while (rs.next()) {

	                System.out.println( "Order Item Id : " + rs.getInt("order_item_id") + "\nOrder Id : " + rs.getInt("order_id") + "\nFood Id : " + rs.getInt("food_id") + "\nQuantity : " + rs.getInt("quantity") +  "\nPrice : " + rs.getDouble("price"));
	                System.out.println("--------------------------");
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }


	   
	    public boolean updateOrderItem(OrderItem oi) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/online_food_management","root","root");

	            PreparedStatement pr = con.prepareStatement( "update Order_Items set Food_Id=?, Quantity=?, Price=? where Order_Item_Id=?");

	            pr.setInt(1, oi.getFood_id());
	            pr.setInt(2, oi.getQuantity());
	            pr.setDouble(3, oi.getPrice());
	            pr.setInt(4, oi.getOrderItem_id());

	            int i = pr.executeUpdate();

	            if (i > 0) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }


	  
	    public boolean deleteOrderItem(int orderItemId) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root","root");
	            PreparedStatement pr = con.prepareStatement( "delete from Order_Items where Order_Item_Id=?");
	            pr.setInt(1, orderItemId);

	            int i = pr.executeUpdate();

	            if (i > 0) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }
	    
	    public double getFoodPrice(int foodId) {

	        double price = 0;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root", "root");

	            PreparedStatement pr = con.prepareStatement("select Food_Price from Food_Items where Food_Id=?");

	            pr.setInt(1, foodId);

	            ResultSet rs = pr.executeQuery();

	            if (rs.next()) {
	                price = rs.getDouble("price");
	            }

	            con.close();

	        } catch (Exception e) {

	            System.out.println(e);

	        }

	        return price;
	    }
	}


