package dymo.reader;

/** 
 * The class that describes a reader 
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class Reader {
	
	/** 
     * Surname reader
     */
	private String _surname;

	/** 
     * Name a reader
     */
	private String _name;
	
	/** 
     * Year of Birth reader
     */
	private int _year;
	
	/** 
     * Email a reader
     */
	private String _email;
	
	/** 
     * Password under which registered a reader
     */
	private String _password;
	
	/** 
     * Constructor reader
     */
	public Reader(String surname, String name, int year, String email, String password) {
		_surname = surname;
		_name = name;
		_year = year;
		_email = email;
		_password = password;
	}
	
	/** 
      * Constructor with a given reader
      * email and password
     */
	public Reader(String email, String password) {
		_email = email;
		_password = password;
	}
	
	/** 
      * Get Surname of the reader
      * @return Surname of the reader
     */
	public String getSurname() {
		return _surname;
	}
	
	/** 
	 * Get name of the reader
         * @return the name of the reader
	 */
	public String getName() {
		return _name;
	}
	
	/** 
      * Get year of birth of the reader
      * @return year of birth of the reader
      */
	public int getYear() {
		return _year;
	}
	
	/** 
      * Get email reader
      * @return email reader
     */
	public String getEmail() {
		return _email;
	}
	
	/** 
     * Get password reader
      *@return password reader
     */
	public String getPassword() {
		return _password;
	}
}