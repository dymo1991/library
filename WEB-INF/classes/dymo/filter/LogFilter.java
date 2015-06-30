package dymo.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/** 
 * Filter logging system events using log4j
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class LogFilter implements Filter {
    private FilterConfig config;
    private Logger log;
    
    // Create a new LogFilter
    public LogFilter() {}
    
    public void  init(FilterConfig filterConfig) throws ServletException {
    	this.config = filterConfig;
    	
    	log = Logger.getLogger(LogFilter.class);
    	log.info("Logger instantiated in " + getClass().getName());
    }
    
    public void  doFilter(ServletRequest request, ServletResponse response,
    					  FilterChain chain) throws java.io.IOException, ServletException {
 
    	HttpServletRequest req = null;
    	if ((log != null) && (request instanceof HttpServletRequest)) {        
    		req = (HttpServletRequest) request;
    		
    		/*
    		 * In the log is recorded the IP address from which the request was made
		 * and which pages
    		 */
    		log.info("Request received from: " + req.getRemoteHost() +
    				 " for: " + req.getRequestURL()); 
        }
     
    	// Passes the request down the chain of filters
    	chain.doFilter(request,response);
    }
    
    /*
  	 * Called before deleting an instance of the filter
   	 * of maintenance web-container
  	 */  
    public void destroy() {
      log = null;
    }
}