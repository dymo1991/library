package dymo.contr;

import dymo.ord.*;
import dymo.other.*;
import dymo.reader.*;
import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

/** 
 * Control servlet requests reader
 * @version 1.01
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String ERRORLOGINING_JSP = "/test/errorLogining.html";
	private static String MAIN_HTML = "/test/main.html";
	private static String REGISTRATION_HTML = "/test/registration.html";
	private static String SEARCH_JSP = "/test/search.jsp";
	private static String SEARCH_HTML = "/test/search.html";
	private static String SENDPASSWORD_HTML = "/test/sendPassword.html";
	private static String AUTHENTICATE = "/test/authenticate";
	private static String BADEMAIL_HTML = "/test/badEmail.html";

  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
    
		// Get object class Map request parameters
		@SuppressWarnings("unchecked")
		Map parameters = request.getParameterMap();
		
		// Managing user requests
		if (parameters.containsKey("errorLogining")) {
			forward = ERRORLOGINING_JSP;
		} else if (parameters.containsKey("registration")) {
			String p = request.getParameter("password");
			String p1 = request.getParameter("password1");
			String ch = request.getParameter("takeRule");
			String empty = "";
			
			if ((p.equals(p1))&& (ch != null) && (!p.equals(empty))) {
				String surname = request.getParameter("surname");
				String name = request.getParameter("name");
				String yearSt = request.getParameter("year");
				int year = Integer.parseInt(yearSt);
				String email = request.getParameter("email");
				Reader reader = new Reader(surname, name, year, email, p);
				ReaderControl readerControl = new ReaderControl();
			
				if (readerControl.addReader(reader) == 1) {
					forward = MAIN_HTML;
				} else {
					forward = BADEMAIL_HTML;
				}
			} else {
				forward = REGISTRATION_HTML;
			}
		} else if (parameters.containsKey("b_registration")) {
			forward = REGISTRATION_HTML;
		} else if (parameters.containsKey("search")) {
			forward = SEARCH_JSP;
		} else if (parameters.containsKey("b_search")) {
			forward = SEARCH_HTML;
		} else if (parameters.containsKey("addBook")) {
			String rd = (String) request.getSession().getAttribute("user");
			String idSt = request.getParameter("addBook");
			int id = Integer.parseInt(idSt);
			OrderControl orderControl = new OrderControl();
			orderControl.orderBook(id, rd);   
			forward = SEARCH_HTML;
		} else if (parameters.containsKey("b_sendPassword")) {
			forward = SENDPASSWORD_HTML;
		} else if (parameters.containsKey("sendPassword")) {
			String email = request.getParameter("email");
			SendEmail sendEmail = new SendEmail();
			sendEmail.sendPassword(email); 
			forward = MAIN_HTML;
		} else if (parameters.containsKey("enter")) {
			forward = AUTHENTICATE;
		} else {
			forward = MAIN_HTML;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
} 