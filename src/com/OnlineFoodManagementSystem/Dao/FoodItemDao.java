package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.OnlineFoodManagementSystem.bean.FoodItem;

public class FoodItemDao {
	

	    
	    public boolean addFood(FoodItem f) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root","root");
	            PreparedStatement pr = con.prepareStatement("insert into Food_items(Food_name,Food_price,Category_id) values(?,?,?)");
	                    
	            pr.setString(1, f.getFood_Name());
	            pr.setDouble(2, f.getFood_Price());
	            pr.setInt(3, f.getCategory_Id());

	            int i = pr.executeUpdate();

	            if (i > 0) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }


	    public String viewFood() {

	        String data = "";

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root", "root" );

	            PreparedStatement pr = con.prepareStatement( "select * from Food_items"  );

	            ResultSet rs = pr.executeQuery();

	            while (rs.next()) {

	                data = data
	                        + "Food ID : " + rs.getInt("Food_id")
	                        + "    Food Name : " + rs.getString("Food_name")
	                        + "   Food Price : " + rs.getDouble("Food_price")
	                        + "    Category ID : " + rs.getInt("Category_id")
	                        + "\n";

	            }

	            con.close();

	        } catch (Exception e) {

	            System.out.println(e);

	        }

	        return data;
	    }


	    
	    public boolean updateFood(FoodItem f) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root","root");
	                   
	                   
	            PreparedStatement pr = con.prepareStatement("update Food_Items set Food_Name=?, Food_Price=?, Category_Id=? where Food_Id=?");
	                    
	            pr.setString(1, f.getFood_Name());
	            pr.setDouble(2, f.getFood_Price());
	            pr.setInt(3, f.getCategory_Id());
	            pr.setInt(4, f.getFood_Id());

	            int i = pr.executeUpdate();

	            if (i > 0) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }


	  
	    public boolean deleteFood(int foodId) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root","root");     
	            PreparedStatement pr = con.prepareStatement(  "delete from Food_Items where Food_Id=?");
	                  
	            

	            pr.setInt(1, foodId);

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

	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root","root");

	            PreparedStatement pr = con.prepareStatement( "select Food_Price from food_items where Food_Id=?");

	            pr.setInt(1, foodId);

	            ResultSet rs = pr.executeQuery();

	            if (rs.next()) {
	                price = rs.getDouble("Food_Price");
	            }

	            con.close();

	        } catch (Exception e) {

	            System.out.println(e);
	        }

	        return price;
	    }
		
		
	}

