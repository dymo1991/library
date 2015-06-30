package dymo.book;

/** 
 * Class that describes the location of the books in the library,
 * Class inherits Book
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class BookPlacement extends Book {
	
	/** 
     * The section where is the book 
     */
	private String _section;
	
	/** 
     * Shelve where is the book 
     */
	private int _shelf;
	
	/** 
     * Row where is the book 
     */
	private int _row;
	
	/** 
         * The book is available in the reading room, whether
       	 * ability to take her home
       	 * If the variable is 0 -
     	 * book is only available in the reading room
 	 */
	private int _home;
	
	/** 
      * Constructor location of the book
      * with the given values
      */
	public BookPlacement(String author, String title, int year,
						 String section, int shelf, int row, int home) {
		super(author, title, year);
		_section = section;
		_shelf = shelf;
		_row = row;
		_home = home;
	}
	
	/** 
      * Get name of section where is the book
      * @return name of the the section where is the book
      */
	public String getSection() {
		return _section;
	}
	
	/** 
      * Get number of shelves where is the book
      * @return Number shelf where is the book
      */
	public int getShelf() {
		return _shelf;
	}
	
	/** 
  	 * Get row number where book is
         * @return row number where book is   
	 */
	public int getRow() {
		return _row;
	}

	/** 
         * Test whether the book is available for order
	 * to take it to the subscription
         * @return 0 if the book is only available in the reading room
         */
	public int getHome() {
		return _home;
	}		
}