package dymo.other;

import java.sql.*;
 
/* 
 * Class by which create a table in the database library
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class CreatingTable{
	
	// The query string to create a table book
    private final static String createTableQueryBook = "CREATE TABLE `book` (" +
            "`id` int(11) NOT NULL auto_increment," +
            "`author` varchar(50) default NULL," +
            "`title` varchar(50) default NULL," +
            "`year` year default NULL," +
            "`section` varchar(50) default NULL," +
            "`shelf` int(5) default NULL," +
            "`row` int(5) default NULL," +
            "`available` int(5) default NULL," +
            "`quantity` int(5) default NULL," +
            "PRIMARY KEY  (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    // The query string to create a table reader
    private final static String createTableQueryReader = "CREATE TABLE `reader` (" +
            "`id` int(11) NOT NULL auto_increment," +
            "`surname` varchar(50) default NULL," +
            "`name` varchar(50) default NULL," +
            "`year` year default NULL," +
            "`email` varchar(50) default NULL," +
            "`password` varchar(50) default NULL," +
            "`active` Boolean default NULL," +
            "PRIMARY KEY  (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    // The query string to create table ord
    private final static String createTableQueryOrder = "CREATE TABLE `ord` (" +
            "`id` int(11) NOT NULL auto_increment," +
            "`idbook` int(11) default NULL," +
            "`idreader` int(11) default NULL," +
            "`date` date default NULL," +
            "`isexecute` Boolean default NULL," +
            "`isreturn` Boolean default NULL," +
            "PRIMARY KEY  (`id`)," +
            "FOREIGN KEY (`idbook`) REFERENCES book (`id`)," +
            "FOREIGN KEY (`idreader`) REFERENCES reader (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
 
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        String url;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            // Connecting to the database library
            url = "jdbc:mysql://localhost/library" +
                  "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "supercvs");
            statement = connection.createStatement();
            
            // Create tables
            statement.executeUpdate(createTableQueryBook);
            statement.executeUpdate(createTableQueryOrder);
            statement.executeUpdate(createTableQueryReader);
            // Close the connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
}