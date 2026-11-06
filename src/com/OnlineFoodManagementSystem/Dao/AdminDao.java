package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.OnlineFoodManagementSystem.bean.Admin;

public class AdminDao {
	
	    public boolean adminLogin(Admin a) {

	        boolean status = false;

	        try {

	            Class.forName("com.mysql.jdbc.Driver");

	            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management","root", "root");
	    	   PreparedStatement pr = con.prepareStatement(  "select * from admins where User_name=? and Password=?");
	                 
	            pr.setString(1, a.getUser_name());
	            pr.setString(2, a.getPassword());

	            ResultSet rs = pr.executeQuery();

	            if (rs.next()) {
	                status = true;
	            }

	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }
	}


