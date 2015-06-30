package dymo.other;

import java.sql.*;

/** 
 * Class by which create a database library
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class CreatingDatabase {
    
	// The query string to MySQL to create a database library
	private final static String createDatabaseQuery =
			"CREATE DATABASE library CHARACTER SET utf8 COLLATE utf8_general_ci";
 
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        
        try {
        	
            // Load the driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Line up a connection to the database
            String url = "jdbc:mysql://localhost/mysql";
            
            // Create a connection to the database
            connection = DriverManager.getConnection(url, "root", "supercvs");
            statement = connection.createStatement();
            statement.executeUpdate(createDatabaseQuery);
            // Close the connection to the database
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}