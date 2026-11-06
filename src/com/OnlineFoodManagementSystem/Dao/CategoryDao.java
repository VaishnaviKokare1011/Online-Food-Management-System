package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.OnlineFoodManagementSystem.bean.Category;

public class CategoryDao {

   
    public boolean addCategory(Category c) {

        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root", "root");

            PreparedStatement pr = con.prepareStatement("insert into categories(category_name) values(?)");

            pr.setString(1, c.getCategory_Name());

            int i = pr.executeUpdate();

            if (i > 0) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }


    public String viewCategory() {

        String data = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management",  "root", "root");

            PreparedStatement pr = con.prepareStatement( "select * from categories");

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {

                data = data
                        + "Category ID : " + rs.getInt("category_id")
                        + "\nCategory Name : " + rs.getString("category_name")
                        + "\n-----------------------------\n";
            }

            if (data.equals("")) {
                data = "No Categories Found";
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }
    public String getCategoryNames() {

        String data = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management", "root", "root");

            PreparedStatement pr =  con.prepareStatement("select category_id, category_name from categories");

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {

                data = data
                        + rs.getInt("category_id")
                        + "-"
                        + rs.getString("category_name")
                        + "\n";
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }

    
    public boolean updateCategory(Category c) {

        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management",   "root", "root");

            PreparedStatement pr = con.prepareStatement("update categories set category_name=? where category_id=?");

            pr.setString(1, c.getCategory_Name());
            pr.setInt(2, c.getCategory_Id());

            int i = pr.executeUpdate();

            if (i > 0) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }


  
    public boolean deleteCategory(int categoryId) {

        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/online_food_management", "root","root");

            PreparedStatement pr = con.prepareStatement("delete from categories where category_id=?");

            pr.setInt(1, categoryId);

            int i = pr.executeUpdate();

            if (i > 0) {
                status = true;
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }
}