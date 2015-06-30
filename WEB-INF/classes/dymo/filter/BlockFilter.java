package dymo.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** 
 * Filter that blocks the request if the user
 * is not authenticated as an administrator
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class BlockFilter implements Filter {  
    private FilterConfig config;
    
    // Create a new BlockFilter 
    public BlockFilter() {}
    
    /*
     * Initialized object FilterConfig
     * The method is called only once -
     * when the web-container creates an instance of the filter
     */
    public void  init(FilterConfig filterConfig)  throws ServletException {
        this.config = filterConfig;
    }
    
    /*
     * Checks role and forwards the request,
     * calling method chain.doFilter, if the user
     * belongs to the administrator roles, for any
     * different role redirect to a page entered password
     */
    public void  doFilter(ServletRequest request, ServletResponse response,
		  				  FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = null;
    	boolean authenticated = false;
    	PrintWriter out = null;
     
    	if (request instanceof HttpServletRequest) {
    		req = (HttpServletRequest) request;
    		HttpSession session=req.getSession();

    		// Get username
    		String user = (String) session.getAttribute("user");
        
    		// Checks whether the current user is an administrator 
    		String admin = "admin";
    		if (user != null) {
    			authenticated = user.equals(admin);
    		}
    	}

    	/*
    	 * If the administrator authenticated pass request further,
     	 * another redirects to page enter the password
    	 */
    	if (authenticated) {
    		chain.doFilter(request,response);
    	} else {
    		((HttpServletResponse) response).sendRedirect("admin.html");
    	}
  	}
    
  	/*
  	 * Called before deleting an instance of the filter
         * of maintenance web-container
  	 */
    public void destroy() {}  
}