package dymo.SQL;

import java.sql.*;

/** 
 * The class that implements DAO to work with table book from the database
 * @version 1.01
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */ 
public class BookDAO {
	
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
     * Add book to the specified data in the database
     */
	public void addBook(String author, String title, int year,
						String section, int shelf, int row, int home) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "INSERT INTO book (author, title, year, " +
				  "section, shelf, row, available, quantity, home) " +
				  "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, author);
			ps.setString(2, title);
			ps.setInt(3, year);
			ps.setString(4, section);
			ps.setInt(5, shelf);
			ps.setInt(6, row);
			ps.setInt(7, 1);
			ps.setInt(8, 1);
			ps.setInt(9, home);
			ps.execute();
    	} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}	
	
	/** 
     * To delete a book with the specified id
     */
	public void delBook(int id) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "DELETE FROM book WHERE id=?";
	    	ps = con.prepareStatement(sql);
    		ps.setInt(1, id);
	    	ps.execute();
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}
	
	/** 
     * To delete a book chosen by the author, title and year
     */
	public void delBook(String author, String title, int year) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "DELETE FROM book WHERE author=? AND title=? AND year=?";
    		ps = con.prepareStatement(sql);
			ps.setString(1, author);
			ps.setString(2, title);
			ps.setInt(3, year);
			ps.execute();
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}
	
	/** 
    * Get number of available copies of the book from specified id
      * @return the number of available copies of the book
     */ 
	public int selAvailable(int id) {
		PoolConnection pool = new PoolConnection(0);
		int avail = 0;
		
		try { 
			con = pool.getConnection();
			sql = "SELECT available FROM book WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
    			avail = rs.getInt("available");
    			break;
    		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return avail;
	}
	
	/** 
     * Get number of available copies of the book
      * specified by the author, title and year
      * @return the number of available copies of the book
     */ 
	public int selAvailable(String author, String title, int year) {
		PoolConnection pool = new PoolConnection(0);
		int avail = 0;
		try { 
			con = pool.getConnection();
			sql = "SELECT available FROM book WHERE author=? AND title=? AND year=?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, author);
    		ps.setString(2, title);
    		ps.setInt(3, year);
    		rs = ps.executeQuery();
    		while (rs.next()) {
    			avail = rs.getInt("available");
    			break;
    		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return avail;
	}
	
	/** 
    * Get the number of copies of the book by the given id
      * @return the number of copies of the book
     */ 
	public int selQuantity(int id) {
		PoolConnection pool = new PoolConnection(0);
		int quant = 0;
		
		try { 
			con = pool.getConnection();
			sql = "SELECT quantity FROM book WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
    			quant=rs.getInt("quantity");
    			break;
    		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return quant;
	}
	
	/** 
   * Get the number of copies of the book chosen by the author, title and year
      * @return the number of copies of the book 
     */ 
	public int selQuantity(String author, String title, int year) {
		PoolConnection pool = new PoolConnection(0);
		int quant = 0;
		
		try { 
			con = pool.getConnection();
			sql = "SELECT quantity FROM book WHERE author=? AND title=? AND year=?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, author);
    		ps.setString(2, title);
    		ps.setInt(3, year);
    		rs=ps.executeQuery();
    		while (rs.next()) {
    			quant = rs.getInt("quantity");
    			break;
    		}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return quant;
	}
	
	/** 
    * See All the book that match your search criteria
      * search can by author and title, the author only, and only by title
      * @return string HTML table 
     */
	public String showBook(int i, String author, String title) {
		PoolConnection pool = new PoolConnection(1);
		String sb = "";
		
		try { 
			con = pool.getConnection();
			
			// Â çàëåæíîñò³ â³ä ïîøóêîâîãî çàïèòó øóêàºìî êíèæêó â ÁÄ
			if(i == 1) {
    				sql = "SELECT id, author, title, year, available, home " +
    					  "FROM book WHERE title=?";
    				ps = con.prepareStatement(sql);
    	      		ps.setString(1, title);
      		} else if(i == 2) {
      				sql = "SELECT id, author, title, year, available, home " +
      					  "FROM book WHERE author=?";
      				ps = con.prepareStatement(sql);
      	      		ps.setString(1, author);
      		} else {
      				sql = "SELECT id, author, title, year, available, home " +
      					  "FROM book WHERE author=? AND title=?";
      				ps = con.prepareStatement(sql);
      	      		ps.setString(1, author);
      	    		ps.setString(2, title);
  			}
      		rs = ps.executeQuery();
    		sb = printResults(rs);
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return sb;
	}
	
	/** 
     * Change the value of the available the book with the specified id
     */	
	public void updAvailable(int id, int avail) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE book SET available=? WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, avail);
    		ps.setInt(2, id);
	    	ps.execute();
		} catch(SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}	
	
	/** 
      * Change the value and quantity fields availalble
      * books chosen by the author, title and year
     */	
	public void updAvailQuant(String author, String title, int year, int available, int quantity) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE book SET available=?, quantity=? WHERE author=? AND title=? AND year=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, available);
    		ps.setInt(2, quantity);
			ps.setString(3, author);
			ps.setString(4, title);
			ps.setInt(5, year);
			ps.execute();
		} catch(SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
	}
	
	/** 
     * Change the field value quantity books with specified id
     */	
	public void updQuantity(int id, int quantity) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE book SET quantity=? WHERE id=?";
    		ps = con.prepareStatement(sql);
    		ps.setInt(1, quantity);
    		ps.setInt(2, id);
	    	ps.execute();
		}	
		catch(SQLException e) { 
			e.printStackTrace(); 
		} 
		finally { 
		    pool.close(); 
		}
	}	
	
	/** 
      * Change the field value quantity
      * books chosen by the author, title and year
     */
	public void updQuantity(String author, String title, int year, int quantity) {
		PoolConnection pool = new PoolConnection(0);
		
		try { 
			con = pool.getConnection();
			sql = "UPDATE book SET quantity=? WHERE author=? AND title=? AND year=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantity);
			ps.setString(2, author);
			ps.setString(3, title);
			ps.setInt(4, year);
			ps.execute();
		} catch(SQLException e) { 
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
		String author, title;
    	int id, year, available, home, i = 1, j;
    	StringBuffer buffer = new StringBuffer("");
    	
    	while (rs.next()) {
    		id = rs.getInt("id");
    		author = rs.getString("author");
    		title = rs.getString("title");
    		year = rs.getInt("year");
    		available = rs.getInt("available");
    		home = rs.getInt("home");
    		String button;
    		
    		/*
    		* Depending on the number of books available in the library,
     		* and their availability form an entry
     		* or the order in the table of the search result
    		 */
    		if (available == 0) {
    			button = "<TD><H3>Òèì÷àñîâî íåäîñòóïíà</H3></TD>\n";
    		} else if(home == 0) {
    			button = "<TD>Äîñòóïíà â ÷èòàëüíîìó çàë³</TD>\n";
    		} else {		
    			button = "<TD><INPUT TYPE=\"SUBMIT\" NAME=\"addBook\" VALUE="
    					 + id + " class=\"b3\"></TD>\n";
    		}
    		j = i % 2;
    		
    		/*
    		* Adds a row to the table result.
     		* Changing table row belongs to a class.
	        * This is done in order to use CSS to separate
	        * adjacent rows separating them in different colors
    		 */
    		buffer.append("<TR class=\"tr" + j + "\">\n<TD>" + i + "</TD>\n<TD>"
    					  + author + "</TD>\n<TD>" + title + "</TD>\n<TD>"
    					  + year + "</TD>\n<TD>" + available + "</TD>\n"
    					  + button + "</TR>\n");
    		i++;
    	}
    	return buffer.toString();
	}
	
	/** 
     * Show all books which are in library at the moment
      * @return string HTML table 
    */	
	public String showAllBooksInLibrary() {
		PoolConnection pool = new PoolConnection(0);
		String sr = "";
		
		try { 
			con = pool.getConnection();
			sql = "SELECT author, title, year, SUM(CASE WHEN home=1 then quantity else 0 END) as quantityWithoutReadingRoom," + 
				  " SUM(quantity) as quantity FROM book GROUP BY author, title, year";
			ps = con.prepareStatement(sql);
     		rs = ps.executeQuery();
     		sr = printAllBooks(rs);		
   	} catch (SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		return sr;
   }

	/** 
       * Presentation of query results in the form of HTML tables
       * @return string HTML table 
     */	
	private String printAllBooks(ResultSet rs) throws SQLException {
	String author, title;
    	int year, quantityWithoutReadingRoom, quantity, i = 1, j;
    	StringBuffer buffer = new StringBuffer("");
	
    	while (rs.next()) {
    		author = rs.getString("author");
    		title = rs.getString("title");
    		year = rs.getInt("year");
    		quantityWithoutReadingRoom = rs.getInt("quantityWithoutReadingRoom");
    		quantity = rs.getInt("quantity");
    		j = i % 2;
    		
    		/*
    		 * Adds a row to the table result.
     		 * Changing table row belongs to a class.
      	 	 * This is done in order to use CSS to separate
     		 * adjacent rows separating them in different colors
    		 */
    		buffer.append("<TR class=\"tr" + j + "\">\n<TD>" + author + "</TD>\n<TD>"
    					  + title + "</TD>\n<TD>" + year
    					  + "</TD>\n" + "<TD>" + quantityWithoutReadingRoom 
    					  + "</TD>\n<TD>" + quantity + "</TD>\n</TR>\n");
    		i++;
    	}
    	return buffer.toString();
	}

}