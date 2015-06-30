package dymo.auth;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/** 
 * Servlet end of the session reader
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class LogoutServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		
		// Get the current session
		HttpSession session = request.getSession();
		
		// Finish the session
		session.invalidate();
		
		// Displays a message about the successful completion of the session
		PrintWriter out=response.getWriter();
		out.println("<H1>You exit successfully</H1>");
	}
}