package dymo.ord;

import dymo.SQL.*;

/** 
 * Class by which is separate
 * action (control) over Bookstore
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class OrderControl {

	/** 
	 * Return the book that was ordered
	 * under data sequence number (id order) 
	 */
    public void returnBook(int id) {
    	
    	// Create an object of class OrderDAO and BookDAO to interact with the database
		OrderDAO orderDAO = new OrderDAO();
		BookDAO bookDAO = new BookDAO();
		
		// Define id book by id order 
		int idbook = orderDAO.selIdbook(id);    	
		
		/* 
		 * Determine the amount available at the moment
		 * in the library with the specified idbook (id book)
		 */
		int avail = bookDAO.selAvailable(idbook);
		
		// Increase the number of available books with the specified id on 1
		bookDAO.updAvailable(idbook, avail+1);
    	
		/*
		 * Indicates that the reader back book that was
		 * the indicated id order
		 */  
		orderDAO.updIsreturn(id, 1);
	}
    
    /** 
	 * Place an order book with the specified id of the user,
	 * that entered the system during the specified email
	 */
    public void orderBook(int id, String rd) {
		ReaderDAO readerDAO = new ReaderDAO();
		BookDAO bookDAO = new BookDAO();
		OrderDAO orderDAO = new OrderDAO();
		
		// Determine user id to the specified email
		int idreader = readerDAO.selId(rd);
		
		// Determine the number of available books
    	int avail = bookDAO.selAvailable(id);
    	
    	// Reduces the number of available books on 1
		bookDAO.updAvailable(id, avail-1);
    	
		// Registers Order (add to database) if the book is available
		if (avail>0) {
			orderDAO.addOrder(id, idreader);
		}
	}
    
    /** 
	* Show orders that are not executed at the moment
	* @return string HTML table - pending order
	 */
	public String showResult() {
		OrderDAO orderDAO = new OrderDAO();
		
    	return orderDAO.showOrder();    	
    }
	
	/** 
	 * Execute order, that send in a library
	 */
	public void executeOrder() {		
		OrderDAO orderDAO = new OrderDAO();
		
    	orderDAO.executeOrder();   
	}	
}