package dymo.SQL;

import java.sql.*;

/** 
 * The class that implements DAO to work with a table from the database reader 
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */ 
public class ReaderDAO {
	
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
     * Add reader to the specified data in the database
     */	
	public void addReader(String surname, String name, int year, String email, String password) {
		PoolConnection pool = new PoolConnection(1);
		
		try { 
			con = pool.getConnection();
			sql = "INSERT INTO reader (surname, name, year, email, password, active) " +
				  "VALUES(?, ?, ?, ?, ?, ?)";
      		ps = con.prepareStatement(sql);
    		ps.setString(1, surname);
    		ps.setString(2, name);
    		ps.setInt(3, year);
    		ps.setString(4, email);
    		ps.setString(5, password);
    		ps.setInt(6, 1);
      		ps.execute();
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}		
	}	
	
	/** 
      * Is there a reader with the given id active
      * @return 1 - if the reader is active 
     */	
	public int isActive(int id) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "SELECT active FROM reader WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, id);
    		rs = ps.executeQuery();
    		while (rs.next()) 
    			return rs.getInt("active");
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return 0;
	}
	
	/** 
      * Is there a reader with the given email address and password active
      * @return 1 - if the reader is active 
     */	
	public int isActive(String email, String password) {
		PoolConnection pool = new PoolConnection(1);
		
		try { 
			con = pool.getConnection();
			String sql = "SELECT email, active FROM reader WHERE email=? AND password=?";
      		ps = con.prepareStatement(sql);
    		ps.setString(1, email);
    		ps.setString(2, password);
       		rs = ps.executeQuery();
       		while (rs.next()){
       			String em = rs.getString("email");
       			int ac = rs.getInt("active");
       			if ((em != null) && (ac == 1))
       	            return 1;
       		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return 0;
	}
	
	/** 
      * Is there a reader with the given email in the database
      * @return 1 - if the reader found
     */	
	public int isEmail(String email) {
		PoolConnection pool = new PoolConnection(1);
		
		try { 
			con = pool.getConnection();
			sql = "SELECT email FROM reader WHERE email=?";
      		ps = con.prepareStatement(sql);
    		ps.setString(1, email);
    		ResultSet rs = ps.executeQuery();
       		while (rs.next()) {
       			String em = rs.getString("email");
       			if (em != null)
       				return 0;
       		}	
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return 1;
	}		
	
	/** 
      * Search for the email id reader
      * @return id reader 
     */ 
	public int selId(String email) {
		PoolConnection pool = new PoolConnection(1);
		int id = 0;
		
		try { 
			con = pool.getConnection();
			sql = "SELECT id FROM reader WHERE email=?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, email);
    		ResultSet rs = ps.executeQuery();
			while (rs.next()) {
    			id = rs.getInt("id");
    			break;
    		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return id;
	}	
	
	/** 
      * Show all readers who are registered at the moment
      * @return string HTML table 
     */	
	public String showReader() {
		PoolConnection pool = new PoolConnection(0);
		String sr = "";
		
		try { 
			con = pool.getConnection();
			sql = "SELECT * FROM reader";
      		ps = con.prepareStatement(sql);
      		rs = ps.executeQuery();
    		sr = printResults(rs);		
    	} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return sr;
    }
	
	/** 
     * Change the field value in the active user with the specified id 
     */	
	public void updActive(int id, int active) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE reader SET active=? WHERE id=?";
	    	ps = con.prepareStatement(sql);
    		ps.setInt(1, active);
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
		String surname, name, email;
    	int id, year, active, i = 0, j;
    	StringBuffer buffer = new StringBuffer("");
    	
    	while (rs.next()) {
    		id = rs.getInt("id");
            surname = rs.getString("surname");
            name = rs.getString("name");
            year = rs.getInt("year");
            email = rs.getString("email");
            active = rs.getInt("active");
            
            /*
             * Displays a button with which you can change
             * user status in the system (lock or unlock) 
             */
    		String button = "<TD><INPUT TYPE=\"SUBMIT\" NAME=\"blockReader\" VALUE=" 
    						+ id + " class=\"b3\"></TD>\n";
    		j = i % 2;
    		
    		/*
    		 * Adds a row to the table result.
     		 * Changing table row belongs to a class.
	         * This is done in order to use CSS to separate
	         * adjacent rows separating them in different colors  
    		 */
    		buffer.append("<TR class=\"tr" + j + "\">\n<TD>" + id + "</TD>\n<TD>" + surname
    					  + "</TD>\n<TD>" + name + "</TD>\n<TD>" + year + "</TD>\n<TD>"
    					  + email + "</TD>\n<TD>" + active + "</TD>\n" + button + "</TR>\n");
    		i++;
    	}
    	return buffer.toString();
	}
}