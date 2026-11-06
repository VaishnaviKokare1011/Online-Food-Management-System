package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.OnlineFoodManagementSystem.bean.Payment;

public class PaymentDao {

    public boolean addPayment(Payment p) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/online_food_management",
                "root",
                "root"
            );

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Payment " +
                "(Order_Id, Customer_Id, Payment_Method, Payment_Status, Amount) " +
                "VALUES (?, ?, ?, ?, ?)"
            );

            ps.setInt(1, p.getOrder_Id());
            ps.setInt(2, p.getCustomer_Id());
            ps.setString(3, p.getPayment_Method());
            ps.setString(4, p.getPayment_Status());
            ps.setDouble(5, p.getAmount());

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            return result > 0;

        } catch (Exception e) {
            System.out.println("Payment Error : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}