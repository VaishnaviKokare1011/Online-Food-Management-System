package com.OnlineFoodManagementSystem.controller;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Choice;
import java.awt.Font;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;

import com.OnlineFoodManagementSystem.Dao.AdminDao;
import com.OnlineFoodManagementSystem.Dao.CategoryDao;
import com.OnlineFoodManagementSystem.Dao.CustomerDAO;
import com.OnlineFoodManagementSystem.Dao.FoodItemDao;
import com.OnlineFoodManagementSystem.Dao.OrderDao;
import com.OnlineFoodManagementSystem.Dao.OrderItemDao;
import com.OnlineFoodManagementSystem.Dao.CartDao;
import com.OnlineFoodManagementSystem.Dao.PaymentDao;

import com.OnlineFoodManagementSystem.bean.Admin;
import com.OnlineFoodManagementSystem.bean.Category;
import com.OnlineFoodManagementSystem.bean.Customer;
import com.OnlineFoodManagementSystem.bean.FoodItem;
import com.OnlineFoodManagementSystem.bean.Order;
import com.OnlineFoodManagementSystem.bean.OrderItem;
import com.OnlineFoodManagementSystem.bean.Payment;


public class Main extends Frame implements ActionListener {

    Label title, userLabel, passLabel, loginTypeLabel, message;
    TextField username, password, confirmPassword;
    CheckboxGroup group;
    Checkbox admin, customer;
    Button login, register;


   
    // MAIN CONSTRUCTOR
    

    public Main() {

        setLayout(null);
        setBackground(Color.WHITE);
        setSize(550, 450);
        setTitle("Online Food Management System");

        init();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


  
    // MAIN LOGIN UI
    
    public void init() {

        title = new Label("ONLINE FOOD MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(80, 30, 400, 40);
        add(title);


        userLabel = new Label("Username / Email :");
        userLabel.setBounds(100, 100, 120, 30);
        add(userLabel);


        username = new TextField();
        username.setBounds(230, 100, 180, 30);
        add(username);


        passLabel = new Label("Password :");
        passLabel.setBounds(100, 150, 100, 30);
        add(passLabel);


        password = new TextField();
        password.setEchoChar('*');
        password.setBounds(230, 150, 180, 30);
        add(password);


        Label confirmPassLabel = new Label("Confirm Password :");
        confirmPassLabel.setBounds(100, 195, 120, 30);
        add(confirmPassLabel);


        confirmPassword = new TextField();
        confirmPassword.setEchoChar('*');
        confirmPassword.setBounds(230, 195, 180, 30);
        add(confirmPassword);


        loginTypeLabel = new Label("Login As :");
        loginTypeLabel.setBounds(100, 250, 100, 30);
        add(loginTypeLabel);


        group = new CheckboxGroup();


        admin = new Checkbox("Admin", group, true);
        admin.setBounds(230, 250, 80, 30);
        add(admin);


        customer = new Checkbox("Customer", group, false);
        customer.setBounds(320, 250, 100, 30);
        add(customer);


        login = new Button("LOGIN");
        login.setBounds(230, 310, 100, 35);
        add(login);
        login.addActionListener(this);


        register = new Button("CUSTOMER REGISTER");
        register.setBounds(200, 355, 150, 40);
        add(register);
        register.addActionListener(this);


        message = new Label("");
        message.setBounds(150, 400, 300, 30);
        add(message);
    }


    // MAIN ACTION
  
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {

            String user = username.getText();
            String pass = password.getText();
            String confirmPass = confirmPassword.getText();


            if (!pass.equals(confirmPass)) {
                message.setText("Password does not match");
                return;
            }


            // ADMIN LOGIN
            if (admin.getState()) {

                Admin a = new Admin();
                a.setUser_name(user);
                a.setPassword(pass);

                AdminDao adao = new AdminDao();
                boolean result = adao.adminLogin(a);


                if (result) {
                    message.setText("Admin Login Successful");
                    new AdminHome();
                } else {
                    message.setText("Invalid Username or Password");
                }
            }


            // CUSTOMER LOGIN
            else if (customer.getState()) {

                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";


                if (!user.matches(emailRegex)) {
                    message.setText("Invalid Email");
                    return;
                }


                if (!pass.matches(passwordRegex)) {
                    message.setText("Invalid Password");
                    return;
                }


                Customer c = new Customer();
                c.setC_Email(user);
                c.setC_Password(pass);


                CustomerDAO cdao = new CustomerDAO();
                boolean result = cdao.customerLogin(c);


                if (result) {

                    message.setText("Customer Login Successful");


                    int customerId = cdao.getCustomerId(c);
                    String customerName = cdao.getCustomerName(customerId);


                    System.out.println("Customer ID : " + customerId);
                    System.out.println("Customer Name : " + customerName);


                    new CustomerHome(customerId, customerName);

                } else {

                    message.setText("Invalid Email or Password");
                }
            }
        }


        // CUSTOMER REGISTER BUTTON
        else if (e.getSource() == register) {

            System.out.println("REGISTER BUTTON CLICKED");

            new CustomerRegistration();
        }
    }


   
    // CUSTOMER REGISTRATION
   

    class CustomerRegistration extends Frame implements ActionListener {

        Label nameLabel, emailLabel, passLabel;
        Label phoneLabel, addressLabel, msg;

        TextField name, email, password;
        TextField phone, address;

        Button registerButton;


        public CustomerRegistration() {

            setLayout(null);
            setSize(500, 450);
            setTitle("Customer Registration");
            setBackground(Color.WHITE);


            nameLabel = new Label("Name :");
            nameLabel.setBounds(70, 50, 100, 30);
            add(nameLabel);


            name = new TextField();
            name.setBounds(180, 50, 200, 30);
            add(name);


            emailLabel = new Label("Email :");
            emailLabel.setBounds(70, 100, 100, 30);
            add(emailLabel);


            email = new TextField();
            email.setBounds(180, 100, 200, 30);
            add(email);


            passLabel = new Label("Password :");
            passLabel.setBounds(70, 150, 100, 30);
            add(passLabel);


            password = new TextField();
            password.setEchoChar('*');
            password.setBounds(180, 150, 200, 30);
            add(password);


            phoneLabel = new Label("Phone :");
            phoneLabel.setBounds(70, 200, 100, 30);
            add(phoneLabel);


            phone = new TextField();
            phone.setBounds(180, 200, 200, 30);
            add(phone);


            addressLabel = new Label("Address :");
            addressLabel.setBounds(70, 250, 100, 30);
            add(addressLabel);


            address = new TextField();
            address.setBounds(180, 250, 200, 30);
            add(address);


            registerButton = new Button("REGISTER");
            registerButton.setBounds(190, 300, 120, 35);
            add(registerButton);
            registerButton.addActionListener(this);


            msg = new Label("");
            msg.setBounds(100, 350, 300, 30);
            add(msg);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == registerButton) {

                String emailText = email.getText().trim();
                String passwordText = password.getText();
                String phoneText = phone.getText().trim();


                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                String phoneRegex = "^[0-9]{10}$";
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";


                if (!emailText.matches(emailRegex)) {
                    msg.setText("Invalid Email");
                    return;
                }


                if (!phoneText.matches(phoneRegex)) {
                    msg.setText("Phone must be 10 digits");
                    return;
                }


                if (!passwordText.matches(passwordRegex)) {
                    msg.setText("Password must be 8+ characters");
                    return;
                }


                Customer c = new Customer();

                c.setC_Name(name.getText());
                c.setC_Email(email.getText());
                c.setC_Password(password.getText());
                c.setC_Phone(phone.getText());
                c.setC_Address(address.getText());


                CustomerDAO cdao = new CustomerDAO();
                boolean result = cdao.registerCustomer(c);


                if (result) {

                    msg.setText("Registration Successful");


                    name.setText("");
                    email.setText("");
                    password.setText("");
                    phone.setText("");
                    address.setText("");


                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }


                    dispose();
                    new Main();

                } else {

                    msg.setText("Registration Failed");
                }
            }
        }
    }


    
    // ADMIN HOME

    class AdminHome extends Frame implements ActionListener {

        Label title;
        Label subTitle;

        Button addFood;
        Button viewFood;
        Button updateFood;
        Button deleteFood;
        Button viewOrders;
        Button addCategory;
        Button exportCustomer;
        Button logout;


        public AdminHome() {

            setLayout(null);
            setSize(600, 600);
            setTitle("Admin Dashboard");
            setBackground(new Color(245, 248, 250));


            title = new Label("ADMIN DASHBOARD");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(190, 35, 250, 40);
            add(title);


            subTitle = new Label("Manage Online Food Management System");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(165, 75, 330, 25);
            add(subTitle);


            addFood = new Button("ADD FOOD");
            addFood.setFont(new Font("Arial", Font.BOLD, 14));
            addFood.setBounds(175, 125, 250, 40);
            add(addFood);
            addFood.addActionListener(this);


            viewFood = new Button("VIEW FOOD");
            viewFood.setFont(new Font("Arial", Font.BOLD, 14));
            viewFood.setBounds(175, 180, 250, 40);
            add(viewFood);
            viewFood.addActionListener(this);


            updateFood = new Button("UPDATE FOOD");
            updateFood.setFont(new Font("Arial", Font.BOLD, 14));
            updateFood.setBounds(175, 235, 250, 40);
            add(updateFood);
            updateFood.addActionListener(this);


            deleteFood = new Button("DELETE FOOD");
            deleteFood.setFont(new Font("Arial", Font.BOLD, 14));
            deleteFood.setBounds(175, 290, 250, 40);
            add(deleteFood);
            deleteFood.addActionListener(this);


            viewOrders = new Button("VIEW ORDERS");
            viewOrders.setFont(new Font("Arial", Font.BOLD, 14));
            viewOrders.setBounds(175, 345, 250, 40);
            add(viewOrders);
            viewOrders.addActionListener(this);


            addCategory = new Button("CATEGORY MANAGEMENT");
            addCategory.setFont(new Font("Arial", Font.BOLD, 14));
            addCategory.setBounds(175, 400, 250, 40);
            add(addCategory);
            addCategory.addActionListener(this);


            exportCustomer = new Button("EXPORT CUSTOMER DATA");
            exportCustomer.setFont(new Font("Arial", Font.BOLD, 14));
            exportCustomer.setBounds(175, 455, 250, 40);
            add(exportCustomer);
            exportCustomer.addActionListener(this);


            logout = new Button("LOGOUT");
            logout.setFont(new Font("Arial", Font.BOLD, 14));
            logout.setBounds(225, 520, 150, 40);
            add(logout);
            logout.addActionListener(this);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == addFood) {
                new AddFood();
            }

            else if (e.getSource() == viewFood) {
                new ViewFoodItems();
            }

            else if (e.getSource() == updateFood) {
                new UpdateFood();
            }

            else if (e.getSource() == deleteFood) {
                new DeleteFood();
            }

            else if (e.getSource() == viewOrders) {
                new AdminViewOrders();
            }

            else if (e.getSource() == addCategory) {
                new CategoryManagement();
            }

            else if (e.getSource() == exportCustomer) {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    java.sql.Connection con = java.sql.DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/online_food_management",
                            "root",
                            "root"
                    );

                    java.sql.PreparedStatement ps = con.prepareStatement(
                            "SELECT C_Id, C_Name, C_Email, C_Phone, C_Address FROM customers"
                    );

                    java.sql.ResultSet rs = ps.executeQuery();

                    File file = new File(
                            "C:\\Users\\hp\\Desktop\\vaishnavi k\\CustomerData.xls"
                    );

                    file.createNewFile();

                    FileWriter fw = new FileWriter(file);

                    fw.write("ID\tName\tEmail\tPhone\tAddress\n");

                    while (rs.next()) {
                        fw.write(
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

                    System.out.println("Customer data successfully exported to Excel file");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            else if (e.getSource() == logout) {
                dispose();
                new Main();
            }
        }
    }


    // ADD FOOD

    class AddFood extends Frame implements ActionListener {

        Label title;
        Label foodNameLabel;
        Label priceLabel;
        Label categoryLabel;
        Label message;

        TextField foodName;
        TextField price;

        Choice categoryChoice;

        Button add;
        Button clear;


        public AddFood() {

            setLayout(null);
            setSize(550, 450);
            setTitle("Add Food");
            setBackground(new Color(245, 248, 250));


            title = new Label("ADD FOOD");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBounds(205, 40, 150, 40);
            add(title);


            foodNameLabel = new Label("Food Name :");
            foodNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            foodNameLabel.setBounds(90, 115, 110, 30);
            add(foodNameLabel);


            foodName = new TextField();
            foodName.setBounds(220, 115, 200, 30);
            add(foodName);


            priceLabel = new Label("Food Price :");
            priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
            priceLabel.setBounds(90, 165, 110, 30);
            add(priceLabel);


            price = new TextField();
            price.setBounds(220, 165, 200, 30);
            add(price);


            categoryLabel = new Label("Category :");
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            categoryLabel.setBounds(90, 215, 110, 30);
            add(categoryLabel);


            categoryChoice = new Choice();
            categoryChoice.setBounds(220, 215, 200, 30);
            add(categoryChoice);


            CategoryDao categoryDao = new CategoryDao();

            String data = categoryDao.getCategoryNames();

            String[] categories = data.split("\n");


            for (String category : categories) {

                if (!category.trim().equals("")) {
                    categoryChoice.add(category);
                }
            }


            add = new Button("ADD FOOD");
            add.setFont(new Font("Arial", Font.BOLD, 13));
            add.setBounds(150, 280, 110, 35);
            add(add);
            add.addActionListener(this);


            clear = new Button("CLEAR");
            clear.setFont(new Font("Arial", Font.BOLD, 13));
            clear.setBounds(280, 280, 100, 35);
            add(clear);
            clear.addActionListener(this);


            message = new Label("");
            message.setBounds(100, 340, 350, 30);
            add(message);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == add) {

                if (foodName.getText().trim().equals("") || price.getText().trim().equals("")) {

                    message.setText("Enter Food Name and Price");
                    return;
                }


                try {

                    String name = foodName.getText().trim();

                    double foodPrice = Double.parseDouble(price.getText().trim());


                    if (categoryChoice.getItemCount() == 0) {

                        message.setText("No Category Available");
                        return;
                    }


                    int catId = Integer.parseInt(categoryChoice.getSelectedItem().split("-")[0]);


                    FoodItem food = new FoodItem();

                    food.setFood_Name(name);
                    food.setFood_Price(foodPrice);
                    food.setCategory_Id(catId);


                    FoodItemDao dao = new FoodItemDao();

                    boolean status = dao.addFood(food);


                    if (status) {

                        message.setText("Food Added Successfully");

                        foodName.setText("");
                        price.setText("");

                        if (categoryChoice.getItemCount() > 0) {
                            categoryChoice.select(0);
                        }

                    } else {

                        message.setText("Food Not Added");
                    }

                } catch (Exception ex) {

                    message.setText("Please enter valid Food Price");
                }
            }


            else if (e.getSource() == clear) {

                foodName.setText("");
                price.setText("");

                if (categoryChoice.getItemCount() > 0) {
                    categoryChoice.select(0);
                }
            }
        }
    }


    // UPDATE FOOD

    class UpdateFood extends Frame implements ActionListener {

        Label title;
        Label idLabel;
        Label nameLabel;
        Label priceLabel;
        Label categoryLabel;
        Label message;

        TextField id;
        TextField name;
        TextField price;
        TextField categoryId;

        Button updateButton;
        Button clearButton;


        public UpdateFood() {

            setLayout(null);
            setSize(550, 500);
            setTitle("Update Food");
            setBackground(new Color(245, 248, 250));


            title = new Label("UPDATE FOOD");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBounds(190, 40, 180, 40);
            add(title);


            idLabel = new Label("Food ID :");
            idLabel.setFont(new Font("Arial", Font.BOLD, 14));
            idLabel.setBounds(90, 105, 110, 30);
            add(idLabel);


            id = new TextField();
            id.setBounds(220, 105, 200, 30);
            add(id);


            nameLabel = new Label("Food Name :");
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setBounds(90, 155, 110, 30);
            add(nameLabel);


            name = new TextField();
            name.setBounds(220, 155, 200, 30);
            add(name);


            priceLabel = new Label("Food Price :");
            priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
            priceLabel.setBounds(90, 205, 110, 30);
            add(priceLabel);


            price = new TextField();
            price.setBounds(220, 205, 200, 30);
            add(price);


            categoryLabel = new Label("Category ID :");
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            categoryLabel.setBounds(90, 255, 110, 30);
            add(categoryLabel);


            categoryId = new TextField();
            categoryId.setBounds(220, 255, 200, 30);
            add(categoryId);


            updateButton = new Button("UPDATE FOOD");
            updateButton.setFont(new Font("Arial", Font.BOLD, 13));
            updateButton.setBounds(140, 325, 120, 35);
            add(updateButton);
            updateButton.addActionListener(this);


            clearButton = new Button("CLEAR");
            clearButton.setFont(new Font("Arial", Font.BOLD, 13));
            clearButton.setBounds(280, 325, 100, 35);
            add(clearButton);
            clearButton.addActionListener(this);


            message = new Label("");
            message.setBounds(100, 385, 350, 30);
            add(message);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == updateButton) {

                try {

                    FoodItem f = new FoodItem();

                    f.setFood_Id(Integer.parseInt(id.getText().trim()));
                    f.setFood_Name(name.getText().trim());
                    f.setFood_Price(Double.parseDouble(price.getText().trim()));
                    f.setCategory_Id(Integer.parseInt(categoryId.getText().trim()));


                    FoodItemDao dao = new FoodItemDao();

                    boolean status = dao.updateFood(f);


                    if (status) {
                        message.setText("Food Updated Successfully");
                    } else {
                        message.setText("Food ID not found");
                    }

                } catch (Exception ex) {

                    message.setText("Please enter valid details");
                }
            }


            else if (e.getSource() == clearButton) {

                id.setText("");
                name.setText("");
                price.setText("");
                categoryId.setText("");
            }
        }
    }


    // DELETE FOOD

    class DeleteFood extends Frame implements ActionListener {

        Label title;
        Label idLabel;
        Label message;

        TextField foodId;

        Button deleteButton;
        Button clearButton;


        public DeleteFood() {

            setLayout(null);
            setSize(500, 350);
            setTitle("Delete Food");
            setBackground(new Color(245, 248, 250));


            title = new Label("DELETE FOOD");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBounds(170, 40, 180, 40);
            add(title);


            idLabel = new Label("Food ID :");
            idLabel.setFont(new Font("Arial", Font.BOLD, 14));
            idLabel.setBounds(80, 120, 100, 30);
            add(idLabel);


            foodId = new TextField();
            foodId.setBounds(200, 120, 200, 30);
            add(foodId);


            deleteButton = new Button("DELETE FOOD");
            deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
            deleteButton.setBounds(125, 190, 120, 35);
            add(deleteButton);
            deleteButton.addActionListener(this);


            clearButton = new Button("CLEAR");
            clearButton.setFont(new Font("Arial", Font.BOLD, 13));
            clearButton.setBounds(265, 190, 100, 35);
            add(clearButton);
            clearButton.addActionListener(this);


            message = new Label("");
            message.setBounds(100, 245, 300, 30);
            add(message);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == deleteButton) {

                try {

                    int id = Integer.parseInt(foodId.getText().trim());

                    FoodItemDao dao = new FoodItemDao();

                    boolean status = dao.deleteFood(id);


                    if (status) {

                        message.setText("Food Deleted Successfully");
                        foodId.setText("");

                    } else {

                        message.setText("Food ID not found");
                    }

                } catch (Exception ex) {

                    message.setText("Enter valid Food ID");
                }
            }


            else if (e.getSource() == clearButton) {

                foodId.setText("");
            }
        }
    }


    // ADMIN VIEW ORDERS

    class AdminViewOrders extends Frame implements ActionListener {

        Label title;
        Label subTitle;

        TextArea orders;

        Button viewButton;


        public AdminViewOrders() {

            setLayout(null);
            setSize(700, 520);
            setTitle("View Orders");
            setBackground(new Color(245, 248, 250));


            title = new Label("ALL ORDERS");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(280, 40, 180, 40);
            add(title);


            subTitle = new Label("View all customer orders");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(260, 80, 220, 25);
            add(subTitle);


            orders = new TextArea();
            orders.setBounds(50, 125, 600, 280);
            orders.setEditable(false);
            orders.setFont(new Font("Monospaced", Font.PLAIN, 14));
            add(orders);


            viewButton = new Button("VIEW ORDERS");
            viewButton.setFont(new Font("Arial", Font.BOLD, 13));
            viewButton.setBounds(280, 430, 140, 35);
            add(viewButton);
            viewButton.addActionListener(this);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == viewButton) {

                OrderDao dao = new OrderDao();

                String data = dao.viewOrders();

                orders.setText(data);
            }
        }
    }


    // CATEGORY MANAGEMENT

    class CategoryManagement extends Frame implements ActionListener {

        Label title;
        Label subTitle;

        Label categoryIdLabel;
        Label categoryNameLabel;
        Label messageLabel;

        TextField categoryIdField;
        TextField categoryNameField;

        Button addButton;
        Button viewButton;
        Button updateButton;
        Button deleteButton;
        Button backButton;

        TextArea categoryArea;


        public CategoryManagement() {

            setLayout(null);
            setSize(700, 600);
            setTitle("Category Management");
            setBackground(new Color(245, 248, 250));


            title = new Label("CATEGORY MANAGEMENT");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBounds(220, 35, 300, 40);
            add(title);


            subTitle = new Label("Add, View, Update and Delete Categories");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(220, 75, 300, 25);
            add(subTitle);


            categoryIdLabel = new Label("Category ID :");
            categoryIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
            categoryIdLabel.setBounds(80, 125, 120, 30);
            add(categoryIdLabel);


            categoryIdField = new TextField();
            categoryIdField.setBounds(220, 125, 200, 30);
            add(categoryIdField);


            categoryNameLabel = new Label("Category Name :");
            categoryNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            categoryNameLabel.setBounds(80, 170, 120, 30);
            add(categoryNameLabel);


            categoryNameField = new TextField();
            categoryNameField.setBounds(220, 170, 200, 30);
            add(categoryNameField);


            addButton = new Button("ADD CATEGORY");
            addButton.setFont(new Font("Arial", Font.BOLD, 13));
            addButton.setBounds(80, 220, 130, 35);
            add(addButton);
            addButton.addActionListener(this);


            viewButton = new Button("VIEW CATEGORIES");
            viewButton.setFont(new Font("Arial", Font.BOLD, 13));
            viewButton.setBounds(225, 220, 140, 35);
            add(viewButton);
            viewButton.addActionListener(this);


            updateButton = new Button("UPDATE");
            updateButton.setFont(new Font("Arial", Font.BOLD, 13));
            updateButton.setBounds(380, 220, 100, 35);
            add(updateButton);
            updateButton.addActionListener(this);


            deleteButton = new Button("DELETE");
            deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
            deleteButton.setBounds(495, 220, 100, 35);
            add(deleteButton);
            deleteButton.addActionListener(this);


            messageLabel = new Label("");
            messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            messageLabel.setBounds(80, 265, 500, 30);
            add(messageLabel);


            categoryArea = new TextArea();
            categoryArea.setBounds(80, 310, 515, 190);
            categoryArea.setEditable(false);
            categoryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            add(categoryArea);


            backButton = new Button("BACK");
            backButton.setFont(new Font("Arial", Font.BOLD, 13));
            backButton.setBounds(275, 520, 120, 35);
            add(backButton);
            backButton.addActionListener(this);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            // ADD CATEGORY
            if (e.getSource() == addButton) {

                String categoryName = categoryNameField.getText().trim();


                if (categoryName.equals("")) {

                    messageLabel.setText("Please enter category name");
                    return;
                }


                Category c = new Category();
                c.setCategory_Name(categoryName);


                CategoryDao cdao = new CategoryDao();

                boolean result = cdao.addCategory(c);


                if (result) {

                    messageLabel.setText("Category Added Successfully");
                    categoryNameField.setText("");

                } else {

                    messageLabel.setText("Category Not Added");
                }
            }


            // VIEW CATEGORY
            else if (e.getSource() == viewButton) {

                CategoryDao cdao = new CategoryDao();

                String data = cdao.viewCategory();

                categoryArea.setText(data);
            }


            // UPDATE CATEGORY
            else if (e.getSource() == updateButton) {

                try {

                    int categoryId = Integer.parseInt(categoryIdField.getText().trim());

                    String categoryName = categoryNameField.getText().trim();


                    if (categoryName.equals("")) {

                        messageLabel.setText("Please enter category name");
                        return;
                    }


                    Category c = new Category();

                    c.setCategory_Id(categoryId);
                    c.setCategory_Name(categoryName);


                    CategoryDao cdao = new CategoryDao();

                    boolean result = cdao.updateCategory(c);


                    if (result) {

                        messageLabel.setText("Category Updated Successfully");

                        categoryIdField.setText("");
                        categoryNameField.setText("");

                    } else {

                        messageLabel.setText("Category ID not found");
                    }

                } catch (Exception ex) {

                    messageLabel.setText("Enter valid Category ID");
                }
            }


            // DELETE CATEGORY
            else if (e.getSource() == deleteButton) {

                try {

                    int categoryId = Integer.parseInt(categoryIdField.getText().trim());


                    CategoryDao cdao = new CategoryDao();

                    boolean result = cdao.deleteCategory(categoryId);


                    if (result) {

                        messageLabel.setText("Category Deleted Successfully");
                        categoryIdField.setText("");

                    } else {

                        messageLabel.setText("Category ID not found");
                    }

                } catch (Exception ex) {

                    messageLabel.setText("Enter valid Category ID");
                }
            }


            // BACK
            else if (e.getSource() == backButton) {

                dispose();

                new AdminHome();
            }
        }
    }


    
    // CUSTOMER HOME

    class CustomerHome extends Frame implements ActionListener {

        int customerId;
        String customerName;

        Label title;
        Label welcome;
        Label subTitle;

        Button viewFood;
        Button placeOrder;
        Button myOrders;
        Button myCart;
        Button logout;


        public CustomerHome(int customerId, String customerName) {

            this.customerId = customerId;
            this.customerName = customerName;


            setLayout(null);
            setSize(600, 520);
            setTitle("Online Food Management System");
            setBackground(new Color(245, 248, 250));


            title = new Label("ONLINE FOOD MANAGEMENT");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(125, 40, 360, 40);
            add(title);


            welcome = new Label("Welcome, " + customerName + "!");
            welcome.setFont(new Font("Arial", Font.BOLD, 19));
            welcome.setBounds(210, 90, 250, 35);
            add(welcome);


            subTitle = new Label("What would you like to do?");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(205, 125, 220, 30);
            add(subTitle);


            viewFood = new Button("VIEW FOOD ITEMS");
            viewFood.setFont(new Font("Arial", Font.BOLD, 14));
            viewFood.setBounds(175, 175, 250, 45);
            add(viewFood);
            viewFood.addActionListener(this);


            placeOrder = new Button("PLACE ORDER");
            placeOrder.setFont(new Font("Arial", Font.BOLD, 14));
            placeOrder.setBounds(175, 235, 250, 45);
            add(placeOrder);
            placeOrder.addActionListener(this);


            myCart = new Button("MY CART");
            myCart.setFont(new Font("Arial", Font.BOLD, 14));
            myCart.setBounds(175, 295, 250, 45);
            add(myCart);
            myCart.addActionListener(this);

            myOrders = new Button("MY ORDERS");
            myOrders.setFont(new Font("Arial", Font.BOLD, 14));
            myOrders.setBounds(175, 355, 250, 45);
            add(myOrders);
            myOrders.addActionListener(this);


            logout = new Button("LOGOUT");
            logout.setFont(new Font("Arial", Font.BOLD, 14));
            logout.setBounds(225, 425, 150, 40);
            add(logout);
            logout.addActionListener(this);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }


        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == viewFood) {

                new ViewFoodItems();

            } else if (e.getSource() == placeOrder) {

                new PlaceOrder(customerId);

            } else if (e.getSource() == myCart) {

                new ViewCart(customerId);

            } else if (e.getSource() == myOrders) {

                new MyOrders(customerId);

            } else if (e.getSource() == logout) {

                dispose();

                new Main();
            }
        }
    }


    // VIEW FOOD ITEMS
 

    class ViewFoodItems extends Frame {

        TextArea foodArea;


        public ViewFoodItems() {

            setLayout(null);
            setSize(650, 500);
            setTitle("Available Food Items");
            setBackground(new Color(245, 248, 250));


            Label title = new Label("AVAILABLE FOOD ITEMS");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBounds(190, 45, 300, 40);
            add(title);


            Label subTitle = new Label("Choose your favourite food");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(230, 85, 250, 25);
            add(subTitle);


            foodArea = new TextArea();
            foodArea.setBounds(60, 125, 530, 270);
            foodArea.setEditable(false);
            foodArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            add(foodArea);


            FoodItemDao fdao = new FoodItemDao();

            String data = fdao.viewFood();

            foodArea.setText(data);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }
    }


    
    // MY CART

    class ViewCart extends Frame implements ActionListener {

        int customerId;
        TextArea cartArea;
        Button checkoutButton;

        public ViewCart(int customerId) {

            this.customerId = customerId;

            setLayout(null);
            setSize(700, 580);
            setTitle("My Cart");
            setBackground(new Color(245, 248, 250));

            Label title = new Label("MY CART");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(280, 40, 180, 40);
            add(title);

            Label subTitle = new Label("Your cart items");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(290, 80, 180, 25);
            add(subTitle);

            cartArea = new TextArea();
            cartArea.setBounds(50, 125, 600, 300);
            cartArea.setEditable(false);
            cartArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            add(cartArea);

            CartDao cdao = new CartDao();
            String data = cdao.viewCart(customerId);

            if (data.equals("") || data.equals("Cart is Empty")) {
                cartArea.setText("Your cart is empty.");
            } else {
                cartArea.setText(data);
            }

            checkoutButton = new Button("CHECKOUT");
            checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
            checkoutButton.setBounds(250, 455, 200, 45);
            add(checkoutButton);
            checkoutButton.addActionListener(this);

            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == checkoutButton) {

                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    java.sql.Connection con = java.sql.DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/online_food_management",
                            "root",
                            "root"
                    );

                    String cartQuery = "SELECT Food_Id, Quantity, Price FROM cart WHERE Customer_Id=?";
                    java.sql.PreparedStatement cartPs = con.prepareStatement(cartQuery);
                    cartPs.setInt(1, customerId);
                    java.sql.ResultSet rs = cartPs.executeQuery();

                    double totalAmount = 0;
                    boolean cartEmpty = true;

                    while (rs.next()) {
                        cartEmpty = false;
                        int quantity = rs.getInt("Quantity");
                        double price = rs.getDouble("Price");
                        totalAmount = totalAmount + (quantity * price);
                    }

                    rs.close();
                    cartPs.close();
                    con.close();

                    if (cartEmpty) {
                        showMessage("Your cart is empty.");
                        return;
                    }

                    Order order = new Order();
                    order.setCustomer_Id(customerId);
                    order.setTotal_Amount(totalAmount);
                    order.setStatus("Placed");

                    OrderDao odao = new OrderDao();
                    int orderId = odao.addOrder(order);

                    if (orderId == 0) {
                        showMessage("Order creation failed.");
                        return;
                    }

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = java.sql.DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/online_food_management",
                            "root",
                            "root"
                    );

                    String itemQuery = "SELECT Food_Id, Quantity, Price FROM cart WHERE Customer_Id=?";
                    java.sql.PreparedStatement itemPs = con.prepareStatement(itemQuery);
                    itemPs.setInt(1, customerId);
                    java.sql.ResultSet itemRs = itemPs.executeQuery();

                    OrderItemDao oidao = new OrderItemDao();
                    boolean itemStatus = true;

                    while (itemRs.next()) {
                        OrderItem oi = new OrderItem();
                        oi.setOrder_Id(orderId);
                        oi.setFood_id(itemRs.getInt("Food_Id"));
                        oi.setQuantity(itemRs.getInt("Quantity"));
                        oi.setPrice(itemRs.getDouble("Price"));

                        if (!oidao.addOrderItem(oi)) {
                            itemStatus = false;
                            break;
                        }
                    }

                    itemRs.close();
                    itemPs.close();
                    con.close();

                    if (!itemStatus) {
                        showMessage("Order items could not be saved.");
                        return;
                    }

                    dispose();
                    new PaymentWindow(customerId, orderId, totalAmount);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    showMessage("Checkout failed.");
                }
            }
        }

        void showMessage(String message) {

            Dialog dialog = new Dialog(this, "Message", true);
            dialog.setLayout(new FlowLayout());

            Label label = new Label(message);
            Button okButton = new Button("OK");

            dialog.add(label);
            dialog.add(okButton);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setSize(350, 150);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }


    // PAYMENT

    class PaymentWindow extends Frame implements ActionListener {

        int customerId;
        int orderId;
        double amount;

        Label title;
        Label orderLabel;
        Label amountLabel;
        Label methodLabel;
        Label message;

        TextField orderIdField;
        TextField amountField;
        Choice methodChoice;
        Button payButton;

        public PaymentWindow(int customerId, int orderId, double amount) {

            this.customerId = customerId;
            this.orderId = orderId;
            this.amount = amount;

            setLayout(null);
            setSize(550, 450);
            setTitle("Payment");
            setBackground(new Color(245, 248, 250));

            title = new Label("PAYMENT");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(210, 40, 150, 40);
            add(title);

            orderLabel = new Label("Order ID :");
            orderLabel.setFont(new Font("Arial", Font.BOLD, 14));
            orderLabel.setBounds(90, 120, 110, 30);
            add(orderLabel);

            orderIdField = new TextField();
            orderIdField.setBounds(220, 120, 200, 30);
            orderIdField.setText(String.valueOf(orderId));
            orderIdField.setEditable(false);
            add(orderIdField);

            amountLabel = new Label("Amount :");
            amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
            amountLabel.setBounds(90, 170, 110, 30);
            add(amountLabel);

            amountField = new TextField();
            amountField.setBounds(220, 170, 200, 30);
            amountField.setText(String.valueOf(amount));
            amountField.setEditable(false);
            add(amountField);

            methodLabel = new Label("Payment Method :");
            methodLabel.setFont(new Font("Arial", Font.BOLD, 14));
            methodLabel.setBounds(90, 220, 120, 30);
            add(methodLabel);

            methodChoice = new Choice();
            methodChoice.add("UPI");
            methodChoice.add("CARD");
            methodChoice.add("CASH ON DELIVERY");
            methodChoice.setBounds(220, 220, 200, 30);
            add(methodChoice);

            payButton = new Button("MAKE PAYMENT");
            payButton.setFont(new Font("Arial", Font.BOLD, 13));
            payButton.setBounds(190, 285, 160, 40);
            add(payButton);
            payButton.addActionListener(this);

            message = new Label("");
            message.setBounds(100, 350, 350, 30);
            add(message);

            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == payButton) {

                try {
                    Payment p = new Payment();
                    p.setOrder_Id(orderId);
                    p.setCustomer_Id(customerId);
                    p.setPayment_Method(methodChoice.getSelectedItem());
                    p.setPayment_Status("Paid");
                    p.setAmount(amount);

                    PaymentDao pdao = new PaymentDao();
                    boolean result = pdao.addPayment(p);

                    if (result) {
                        CartDao cdao = new CartDao();
                        boolean cartResult = cdao.clearCart(customerId);

                        if (cartResult) {
                            message.setText("Payment Successful");
                            payButton.setEnabled(false);
                        } else {
                            message.setText("Payment done, Cart not cleared");
                        }
                    } else {
                        message.setText("Payment Failed");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    message.setText("Something went wrong");
                }
            }
        }
    }


    // MY ORDERS
    

    class MyOrders extends Frame {

        TextArea orderArea;


        public MyOrders(int customerId) {

            setLayout(null);
            setSize(700, 520);
            setTitle("My Orders");
            setBackground(new Color(245, 248, 250));


            Label title = new Label("MY ORDERS");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(280, 40, 180, 40);
            add(title);


            Label subTitle = new Label("Your order history");
            subTitle.setFont(new Font("Arial", Font.PLAIN, 15));
            subTitle.setBounds(290, 80, 180, 25);
            add(subTitle);


            orderArea = new TextArea();
            orderArea.setBounds(50, 125, 600, 300);
            orderArea.setEditable(false);
            orderArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            add(orderArea);


            OrderDao odao = new OrderDao();

            String data = odao.viewCustomerOrders(customerId);

            orderArea.setText(data);


            setVisible(true);


            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }
    }


   
    // PLACE ORDER / ADD TO CART

    class PlaceOrder extends Frame implements ActionListener {

        int customerId;

        Label title;
        Label foodIdLabel;
        Label quantityLabel;

        TextField foodIdField;
        TextField quantityField;

        Button addToCart;

        public PlaceOrder(int customerId) {

            this.customerId = customerId;

            setLayout(null);
            setSize(600, 450);
            setTitle("Add Food to Cart");
            setBackground(new Color(245, 248, 250));

            title = new Label("ADD FOOD TO CART");
            title.setFont(new Font("Arial", Font.BOLD, 25));
            title.setBounds(170, 50, 300, 40);
            add(title);

            foodIdLabel = new Label("Food ID:");
            foodIdLabel.setFont(new Font("Arial", Font.BOLD, 15));
            foodIdLabel.setBounds(120, 140, 100, 30);
            add(foodIdLabel);

            foodIdField = new TextField();
            foodIdField.setBounds(230, 140, 220, 30);
            add(foodIdField);

            quantityLabel = new Label("Quantity:");
            quantityLabel.setFont(new Font("Arial", Font.BOLD, 15));
            quantityLabel.setBounds(120, 200, 100, 30);
            add(quantityLabel);

            quantityField = new TextField();
            quantityField.setBounds(230, 200, 220, 30);
            add(quantityField);

            addToCart = new Button("ADD TO CART");
            addToCart.setFont(new Font("Arial", Font.BOLD, 14));
            addToCart.setBounds(210, 280, 180, 45);
            add(addToCart);

            addToCart.addActionListener(this);

            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == addToCart) {

                try {

                    int foodId = Integer.parseInt(foodIdField.getText().trim());
                    int quantity = Integer.parseInt(quantityField.getText().trim());

                    if (quantity <= 0) {
                        showMessage("Quantity must be greater than 0.");
                        return;
                    }

                    FoodItemDao fdao = new FoodItemDao();
                    double price = fdao.getFoodPrice(foodId);

                    if (price <= 0) {
                        showMessage("Food item not found.");
                        return;
                    }

                    CartDao cdao = new CartDao();
                    boolean result = cdao.addToCart(customerId, foodId, quantity, price);

                    if (result) {
                        showMessage("Food added to cart successfully!");
                        foodIdField.setText("");
                        quantityField.setText("");
                    } else {
                        showMessage("Failed to add food to cart.");
                    }

                } catch (NumberFormatException ex) {
                    showMessage("Please enter valid Food ID and Quantity.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showMessage("Something went wrong.");
                }
            }
        }

        void showMessage(String message) {

            Dialog dialog = new Dialog(this, "Message", true);
            dialog.setLayout(new FlowLayout());

            Label label = new Label(message);
            Button okButton = new Button("OK");

            dialog.add(label);
            dialog.add(okButton);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setSize(350, 150);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }


    // MAIN METHOD
   

    public static void main(String[] args) {

        new Main();
    }
}