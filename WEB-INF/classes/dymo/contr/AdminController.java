package dymo.contr;

import dymo.book.*;
import dymo.reader.*;
import dymo.ord.*;
import dymo.other.*;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.swing.*;

/** 
 * Control servlet requests admin
 * @version 1.01
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class AdminController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String ADMIN_HTML = "/test/admin/admin.html";
	private static String ADDBOOK_JSP = "/test/admin/addBook.jsp";
	private static String BADPASSWORD_HTML = "/test/admin/badPassword.html";
	private static String CONTROLADMIN_HTML = "/test/admin/controlAdmin.html";
	private static String CONTROLBOOK_HTML = "/test/admin/controlBook.html";
	private static String CONTROLREADER_JSP = "/test/admin/controlReader.jsp";
	private static String REMOVEBOOK_HTML = "/test/admin/removeBook.html"; 
	private static String RETURNBOOK_HTML = "/test/admin/returnBook.html";
	private static String REVIEWORDER_JSP = "/test/admin/reviewOrder.jsp";
        private static String REVIEWBOOK_JSP = "/test/admin/reviewBook.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		
		// Get object class Map request parameters
		@SuppressWarnings("unchecked")
		Map parameters = request.getParameterMap();
		
		// Managing user requests
		if (parameters.containsKey("enter")) {
			String determinatedPassword = "111";
			String password = request.getParameter("password");
			if(password.equals(determinatedPassword)) {
				HttpSession ses = request.getSession();
				ses.setMaxInactiveInterval(600);
				ses.setAttribute("user", "admin");
				forward = CONTROLADMIN_HTML;
			} else {
				forward = BADPASSWORD_HTML;
			}           
		} else if (parameters.containsKey("addBook")) {
			String author = request.getParameter("author");
			String title = request.getParameter("title");
			String yearSt = request.getParameter("year");
			int year = Integer.parseInt(yearSt);
			String section = request.getParameter("section");
			String shelfSt = request.getParameter("shelf");
			int shelf = Integer.parseInt(shelfSt);
			String rowSt = request.getParameter("row");
			int row = Integer.parseInt(rowSt);
			String ch = request.getParameter("home");
			int home = 1;

			if(ch != null) {
				home = 0;
			} else {
				home = 1;
			}
			BookPlacement bookPlacement = new BookPlacement(author, title, year, section, shelf, row, home);
			BookControl bookControl = new BookControl();
			bookControl.addBook(bookPlacement);       
			forward = ADDBOOK_JSP;
		} else if (parameters.containsKey("downloadCover")) {
			JFileChooser fileopen = new JFileChooser();
                	int ret = fileopen.showDialog(null, "Choose file");                
                	if (ret == JFileChooser.APPROVE_OPTION) {
                    		File file = fileopen.getSelectedFile();
				String filePath = file.getPath();
				HttpSession session = request.getSession();
                		session.setAttribute("filePath", filePath);         	  
                	}
			forward = ADDBOOK_JSP;
		} else if (parameters.containsKey("blockReader")) {
			String idSt = request.getParameter("blockReader");
			int id = Integer.parseInt(idSt);
			ReaderControl readerControl = new ReaderControl();
			readerControl.blockReader(id);
			forward = CONTROLADMIN_HTML;
		} else if (parameters.containsKey("controlAdmin")) {
			forward = CONTROLADMIN_HTML;
		} else if (parameters.containsKey("controlBook")) {
			forward = CONTROLBOOK_HTML;
		} else if (parameters.containsKey("controlReader")) {
			forward = CONTROLREADER_JSP;
		} else if (parameters.containsKey("executeOrder")) {
			OrderControl orderControl = new OrderControl();
			orderControl.executeOrder();
			forward = CONTROLADMIN_HTML;
		} else if (parameters.containsKey("removeBook")) {
			BookControl bookControl = new BookControl();
			String idSt = request.getParameter("id");
			String empt = "";
			
			if (!idSt.equals(empt)) {
				int id = Integer.parseInt(idSt);
				bookControl.removeBook(id);
			} else {
				String author = request.getParameter("author");
				String title = request.getParameter("title");
				String yearSt = request.getParameter("year");
				int year = Integer.parseInt(yearSt);
				Book book = new Book(author, title, year);
				bookControl.removeBook(book);
			}     
			forward = REMOVEBOOK_HTML;
		} else if (parameters.containsKey("returnBook")) {
			String idSt = request.getParameter("id");
			int id = Integer.parseInt(idSt);
			OrderControl orderControl = new OrderControl();
			orderControl.returnBook(id);                     
			forward = RETURNBOOK_HTML;
		} else if (parameters.containsKey("reviewOrder")) {
			forward = REVIEWORDER_JSP;
		} else if (parameters.containsKey("b_reviewBook")) {
			forward = REVIEWBOOK_JSP;
		} else if (parameters.containsKey("b_addBook")) {
			forward = ADDBOOK_JSP;
		} else if (parameters.containsKey("b_removeBook")) {
			forward = REMOVEBOOK_HTML;
		} else if (parameters.containsKey("b_returnBook")) {
			forward = RETURNBOOK_HTML;
		} else {
			forward = ADMIN_HTML;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
} 