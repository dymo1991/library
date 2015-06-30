package dymo.reader;

import dymo.SQL.*;
 
/** 
 * Class by which exercise
 * different actions (control) over readers
 * that registered in the library
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class ReaderControl {
    
	/** 
     * Register a library membership
     * @return 1 - if the user were able to register
     */
    public int addReader(Reader reader) {
    	
		// Create an object of class ReaderDAO to interact with the database
    	ReaderDAO readerDAO = new ReaderDAO();
    	
    	// If the reader of the email is already registered with the specified return 0
        if (readerDAO.isEmail(reader.getEmail()) == 0) {	
        	return 0;
        }
        
        // Register the reader to the specified data 
        readerDAO.addReader(reader.getSurname(), reader.getName(), reader.getYear(),
        							 reader.getEmail(), reader.getPassword());	    		
    	return 1;
    }
 
    /** 
     * Sign a library membership
     * @return 1 - if the reader with the specified data entered them correctly and is active
     */
    public int authorisationReader(Reader reader) {
    	ReaderDAO readerDAO = new ReaderDAO();
    	
    	return readerDAO.isActive(reader.getEmail(), reader.getPassword());    	
    }
    
    /** 
     * Lock / unlock the reader with the specified id
     */
    public void blockReader(int id) {
    	ReaderDAO readerDAO = new ReaderDAO();
        
    	// If the reader is active - block him and vice versa
    	if (readerDAO.isActive(id) == 1) {
        	readerDAO.updActive(id, 0);
        } else {
        	readerDAO.updActive(id, 1);
        }
    }
    
    /** 
	 * Show list of registered readers
	 * @return string HTML table - registered readers
	 */
	public String showResult() {	   
		ReaderDAO readerDAO = new ReaderDAO();
		
	    return readerDAO.showReader();	    	
    }	
}