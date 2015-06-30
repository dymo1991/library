package dymo.book;

import dymo.SQL.*;

/** 
 * Class by which exercise
 * different actions (control) over the books,
 * that are available in the library
 * @version 1.01
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class BookControl {

	/** 
	  * Add item class BookPlacement,
	  * that is a book with the specified location
	  * to the library    */
	public void addBook(BookPlacement bookPlacement) {

		// Create an object of class BookDAO to interact with the database
		BookDAO bookDAO = new BookDAO();
		
		/* 
		 * Determine the number of books available in the library
		 * with the specified data */
		int quant = bookDAO.selQuantity(bookPlacement.getAuthor(),
										bookPlacement.getTitle(), bookPlacement.getYear());
		
		/*
		 * If such a book in the library missing-add it,
		 * another increase the number of available and accessible books
		 * with the specified data on one */
    	if (quant == 0) {
    		bookDAO.addBook(bookPlacement.getAuthor(), bookPlacement.getTitle(),
    						bookPlacement.getYear(), bookPlacement.getSection(),
    						bookPlacement.getShelf(), bookPlacement.getRow(),
    						bookPlacement.getHome());	
    	} else {
    		int avail = bookDAO.selAvailable(bookPlacement.getAuthor(), 
    										 bookPlacement.getTitle(), bookPlacement.getYear());    		
    		bookDAO.updAvailQuant(bookPlacement.getAuthor(), bookPlacement.getTitle(), 
    							  bookPlacement.getYear(), avail+1, quant+1);
    	}    	
    }
    
	/** 
     * Remove a book from the library According to specified
     */
    public void removeBook(Book book) {    	
    	BookDAO bookDAO = new BookDAO();
    	int quant = bookDAO.selQuantity(book.getAuthor(), book.getTitle(), book.getYear());
    	
		/*
		 * If this book at the library one remove it
		 * another reduce the number of available and accessible book on 1
		 */
        if (quant == 1) {
       		bookDAO.delBook(book.getAuthor(), book.getTitle(), book.getYear());   		
        } else {
    		bookDAO.updQuantity(book.getAuthor(), book.getTitle(), book.getYear(), quant-1);
        }    		
    }
    
    /** 
     * Remove a book from the library with the specified id
     */
    public void removeBook(int id) {    	
    	BookDAO bookDAO = new BookDAO();    	
    	int quant = bookDAO.selQuantity(id);
    	
        if (quant == 1) {
       		bookDAO.delBook(id);   		
        } else {
    		bookDAO.updQuantity(id, quant-1);
        }
    }
    
    /** 
      * Find all books that match a user's search
      * @return string HTML table - search results */
    public String showResult(Book book) {		
		String empt = "";
		BookDAO bookDAO = new BookDAO();
		
	     /*
	      * Depending on which of the fields in the search query is empty
	      * Search is performed only by author or by title only,
              * If both fields completed and the search is made by the author and by title
	      */
		if (empt.equals(book.getAuthor())) {
			return bookDAO.showBook(1, book.getAuthor(), book.getTitle());
		} else if (empt.equals(book.getTitle())) {
			return bookDAO.showBook(2, book.getAuthor(), book.getTitle());
		} else {
 			return bookDAO.showBook(3, book.getAuthor(), book.getTitle());
		}
	}

	/** 
	 * Show list of books
	 * @return string HTML table - registered books
	 */
	public String showAllBooks() {	   
		BookDAO bookDAO = new BookDAO();
		
	    return bookDAO.showAllBooksInLibrary();	    	
    	}	
	
}