package dymo.tags;

import java.io.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;

/** 
  * Support custom class descriptor Counter,
  * that counts the number of applications to handle,
  * and hence to a JSP-document that contains it.
  * Information on the number of addresses stored in a file.
  * Name of the file the same as the corresponding
  * JSP-document, in addition, it must include a suffix. counter
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class CounterTag extends TagSupport {
	
	/**
	 * Number of calls to handle
	 */
	private int count = 0;
	
	/**
	 * The file in which you want to save the number of hits
	 */
	private File file = null;

	/**
	 * Displays the number of applications using variable pageContext.
	 * After the execution of the method return value
	 * SKIP_BODY, which indicates that the body descriptor
	 * even if it has be ignored	
	 */
	public int doStartTag() throws JspException {
		try {
			checkFile();
			readCount();
			pageContext.getOut().print(++count);
		} catch(IOException ex) {
			throw new JspException(ex.getMessage());
		}
		return SKIP_BODY;
	}
	
	/**
	 * Returns EVAL_PAGE, indicating that the servlet container
	 * to handle the remainder of the document that follows the descriptor that is closed
	 */
	public int doEndTag() throws JspException {
		saveCount();
		return EVAL_PAGE;
	}
	
	/**
	 * Check if counter file exists
	 */
	private void checkFile() throws JspException, IOException {
		
		/*
		 * If the counter file does not exist create a new file
		 * and it recorded the number of appeals to the level descriptors 0
		 */
		if(file == null) {
			file  = new File(getCounterFilename());
			count = 0;
		}
		if(!file.exists()) {
			file.createNewFile();
			saveCount();
		}
	}
   
	/**
	 * Creating a counter
	 * @return string with address and name of the file counter
	 */
	private String getCounterFilename() {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      	String servletPath = req.getServletPath();
      	String realPath = pageContext.getServletContext().getRealPath(servletPath);
      	
      	return realPath + ".counter";
	}
   
	/**
	 * Save the value of the number of calls to handle
	 * to file counter
	 */
	private void saveCount() throws JspException {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(count);
			writer.close();
		} catch(Exception ex) {
			throw new JspException(ex.getMessage());
		}
	}
	
	/**
	 * Receipt of applications to handle
	 * from file counter
	 */
	private void readCount() throws JspException {
	   try {
		   FileReader reader = new FileReader(file);
		   count = reader.read();
		   reader.close();
	   } catch(Exception ex) {
		   throw new JspException(ex.getMessage());
	   }
   	}
}
