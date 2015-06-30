package dymo.SQL;

import java.util.*;
import java.sql.*;

/** 
 * The class that implements DAO to work with ord table from the database 
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */  
public class OrderDAO {
	
	/** 
     * Connect to the database 
     */ 
	private Connection con;
	
	/** 
     * The query string to the database
     */
	private String sql;
	
	/** 
     * Pre-prepared expression
     */ 
	private PreparedStatement ps;
	
	/** 
     * ResultSet object for processing query results
     */ 
	private ResultSet rs;
	
	/** 
     * Add an order with these data in the database
     */	
	public void addOrder(int idbook, int idreader) {
		PoolConnection pool = new PoolConnection(1);
		
		try { 
			con = pool.getConnection();
			sql = "INSERT INTO ord (idbook, idreader, date, isexecute, isreturn) " +
				  "VALUES (?, ?, ?, ?, ?)";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, idbook);
    		ps.setInt(2, idreader);    		
    		ps.setDate(3, getCurrentDate());
    		ps.setInt(4, 0);
    		ps.setInt(5, 0);
    		ps.execute();
    	} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}	
	
	/** 
     * Change all the field value isexecute, which are zero on 1 
     */
	public void executeOrder() {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql= "UPDATE ord SET isexecute=? WHERE isexecute=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, 1);
    		ps.setInt(2, 0);
    		ps.execute();
    	} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}
	
	/** 
        * Get the field value id number idbook order
	* @return id book in table book
     */ 
	public int selIdbook(int id) {
		PoolConnection pool = new PoolConnection(0);
		int idbook = 0;
		
		try { 
			con = pool.getConnection();
			sql = "SELECT idbook FROM ord WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, id);
			rs = ps.executeQuery();
    		while (rs.next()) {
    			idbook = rs.getInt("idbook");
    			break;
			}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return idbook;
	}	
	
	/** 
      * See All orders that are not executed at the moment
      * @return string HTML table
     */
	public String showOrder() {
		PoolConnection pool = new PoolConnection(0);
		String so="";
		
		try { 
			con = pool.getConnection();
			sql = "SELECT b.id, b.author, b.title, b.year, b.section, b.shelf, b.row " +
				  "FROM book b, ord o " + "WHERE o.idbook=b.id AND o.isexecute=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			so = printResults(rs);
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return so;
	}	

	/** 
     * Change the field value in order isreturn with the specified id 
     */	
	public void updIsreturn(int id, int isreturn) {
		PoolConnection pool = new PoolConnection(0);
		int avail = 0;
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE ord SET isreturn=? WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, isreturn);
    		ps.setInt(2, id);
    		ps.execute();
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
			pool.close(); 
		}
	}		
	
	/** 
       * Presentation of query results in the form of HTML tables
       * @return string HTML table 
     */	
	private String printResults(ResultSet rs) throws SQLException {
		String author, title, section;
    	int id, year, shelf, row, i = 1, j;
    	StringBuffer buffer = new StringBuffer("");
    	
    	while (rs.next()) {
    		id = rs.getInt("id");
    		author = rs.getString("author");
    		title = rs.getString("title");
    		year = rs.getInt("year");
    		section = rs.getString("section");
    		shelf = rs.getInt("shelf");
    		row = rs.getInt("row");
    		j = i % 2;
    		
    		/*
    		 * Adds a row to the table result.
     		 * Changing table row belongs to a class.
      	 	 * This is done in order to use CSS to separate
     		 * adjacent rows separating them in different colors
    		 */
    		buffer.append("<TR class=\"tr" + j + "\">\n<TD>" + id + "</TD>\n<TD>"
    					  + author + "</TD>\n<TD>" + title + "</TD>\n<TD>" + year
    					  + "</TD>\n" + "<TD>" + section + "</TD>\n<TD>"+ shelf 
    					  + "</TD>\n<TD>" + row + "</TD>\n</TR>\n");
    		i++;
    	}
    	return buffer.toString();
	}
	
	/** 
     	 * Getting the current date
     	 * @return the current date
     */
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
}