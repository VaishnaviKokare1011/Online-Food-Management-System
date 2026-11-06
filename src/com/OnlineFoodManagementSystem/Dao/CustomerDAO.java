package com.OnlineFoodManagementSystem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.OnlineFoodManagementSystem.bean.Customer;

public class CustomerDAO {

    
    public boolean registerCustomer(Customer c) {

        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management","root","root");
            PreparedStatement check = con.prepareStatement( "select * from customers where C_Email=?" );

            check.setString(1, c.getC_Email());

            ResultSet rs = check.executeQuery();

            if (rs.next()) {

                System.out.println("Email already exists");

            } else {
            	
            
            PreparedStatement pr = con.prepareStatement( "insert into customers(C_Name,C_Email,C_Password,C_Phone,C_Address) values(?,?,?,?,?)");

            pr.setString(1, c.getC_Name());
            pr.setString(2, c.getC_Email());
            pr.setString(3, c.getC_Password());
            pr.setString(4, c.getC_Phone());
            pr.setString(5, c.getC_Address());

            int i = pr.executeUpdate();

            if (i > 0) {
                status = true;
            	}
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }


   
    public boolean customerLogin(Customer c) {

        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root", "root" );

            PreparedStatement pr = con.prepareStatement("select * from customers where C_Email=? and C_Password=?");

           pr.setString(1, c.getC_Email());
            pr.setString(2,c.getC_Password());

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                status = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

public int getCustomerId(Customer c) {

    int customerId = 0;

    try {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root","root" );

        PreparedStatement pr = con.prepareStatement("select C_Id from customers where C_Email=? and C_Password=?");

        pr.setString(1, c.getC_Email());
        pr.setString(2, c.getC_Password());

        ResultSet rs = pr.executeQuery();

        if (rs.next()) {
            customerId = rs.getInt("C_Id");
        }

        con.close();

    } catch (Exception e) {
        System.out.println(e);
    }

    return customerId;
}
public String getCustomerName(int customerId) {

    String customerName = "";

    try {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management",  "root", "root");

        PreparedStatement pr = con.prepareStatement( "select C_Name from customers where C_Id=?" );

        pr.setInt(1, customerId);

        ResultSet rs = pr.executeQuery();

        if (rs.next()) {
            customerName = rs.getString("C_Name");
        }

        con.close();

    } catch (Exception e) {
        System.out.println(e);
    }

    return customerName;
}
public boolean exportCustomerData() {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_food_management", "root", "root"  );

        PreparedStatement ps = con.prepareStatement("SELECT C_Id, C_Name, C_Email, C_Phone, C_Address, Registration_Date, " +"FLOOR(DATEDIFF(DATE(Registration_Date), " +"(SELECT MIN(DATE(Registration_Date)) FROM customers)) / 7) + 1 AS Week_No " +"FROM customers ORDER BY Registration_Date" );

        ResultSet rs = ps.executeQuery();

        java.io.File file = new java.io.File(
                System.getProperty("user.home") + "\\Desktop\\CustomerData.xls"
        );

        java.io.FileWriter fw = new java.io.FileWriter(file);

        fw.write("Registration Date\tWeek\tID\tName\tEmail\tPhone\tAddress\n");

        while (rs.next()) {

            String registrationDate = rs.getString("Registration_Date");

            if (registrationDate == null) {
                registrationDate = "Not Available";
            }

            int weekNumber = rs.getInt("Week_No");

            fw.write(
                    registrationDate + "\t" +
                    "Week " + weekNumber + "\t" +
                    rs.getInt("C_Id") + "\t" +
                    rs.getString("C_Name") + "\t" +
                    rs.getString("C_Email") + "\t" +
                    rs.getString("C_Phone") + "\t" +
                    rs.getString("C_Address") + "\n"
            );
        }

        fw.close();
        rs.close();
        ps.close();
        con.close();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}