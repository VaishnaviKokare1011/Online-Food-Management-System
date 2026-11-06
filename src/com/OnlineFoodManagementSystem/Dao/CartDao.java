package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDao {

    String url = "jdbc:mysql://localhost:3306/online_food_management";
    String username = "root";
    String password = "root";

    public boolean addToCart(int customerId, int foodId, int quantity, double price) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String check = "SELECT * FROM cart WHERE Customer_Id=? AND Food_Id=?";
            PreparedStatement ps1 = con.prepareStatement(check);

            ps1.setInt(1, customerId);
            ps1.setInt(2, foodId);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {

                int oldQuantity = rs.getInt("Quantity");
                int newQuantity = oldQuantity + quantity;

                String update = "UPDATE cart SET Quantity=? WHERE Customer_Id=? AND Food_Id=?";

                PreparedStatement ps2 = con.prepareStatement(update);

                ps2.setInt(1, newQuantity);
                ps2.setInt(2, customerId);
                ps2.setInt(3, foodId);

                ps2.executeUpdate();

                ps2.close();

            } else {

                String insert = "INSERT INTO cart(Customer_Id, Food_Id, Quantity, Price) VALUES(?,?,?,?)";

                PreparedStatement ps3 = con.prepareStatement(insert);

                ps3.setInt(1, customerId);
                ps3.setInt(2, foodId);
                ps3.setInt(3, quantity);
                ps3.setDouble(4, price);

                ps3.executeUpdate();

                ps3.close();
            }

            rs.close();
            ps1.close();
            con.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String viewCart(int customerId) {

        String data = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String query =
                    "SELECT c.Cart_Id, f.Food_Name, c.Quantity, c.Price, " +
                    "(c.Quantity * c.Price) AS Total " +
                    "FROM cart c " +
                    "JOIN food_items f ON c.Food_Id = f.Food_Id " +
                    "WHERE c.Customer_Id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                data += "Cart ID      : " + rs.getInt("Cart_Id") + "\n";
                data += "Food Name    : " + rs.getString("Food_Name") + "\n";
                data += "Quantity     : " + rs.getInt("Quantity") + "\n";
                data += "Price        : " + rs.getDouble("Price") + "\n";
                data += "Total        : " + rs.getDouble("Total") + "\n";
                data += "----------------------------------\n";
            }

            if (data.equals("")) {
                data = "Cart is Empty";
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            data = "Error while viewing cart";
        }

        return data;
    }

    public boolean clearCart(int customerId) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, username, password);

            String query = "DELETE FROM cart WHERE Customer_Id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, customerId);

            ps.executeUpdate();

            ps.close();
            con.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}