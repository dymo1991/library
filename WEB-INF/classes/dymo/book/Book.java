package dymo.book;

/** 
 * The class that describes the book
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class Book{
	
	/** 
     * Author of the book 
     */
	private String _author;
	
	/** 
     * Name of the book 
     */
	private String _title;
	
	/** 
     * Year of publication Book 
     */
	private int _year;
	
	/** 
          * Constructor books with given
	  * author title and year*/
	public Book(String author, String title, int year) {
		_author = author;
		_title = title;
		_year = year;
	}

	/** 
          * Constructor books with given
          * author and title    */
	public Book(String author, String title) {
		_author = author;
		_title = title;
	}

	/** 
 	  * Get the author of the book
	  * @return author of the book   */
	public String getAuthor() {
		return _author;
	}
	
	/** 
	  * Get name of the book
	  * @return name of the book*/
	public String getTitle() {
		return _title;
	}
	
	/** 
   	  * Get Production year of the book
	  * @return Production year of the book */
	public int getYear() {
		return _year;
	}
}