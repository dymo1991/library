package dymo.tags;
	
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/** 
  * Support custom class descriptor
  * EnforceLogin, who is seeking information about the user
  * in the scope of the session, if the information is present,
  * is executed part of the document is placed after the descriptor
  * otherwise is redirected to the authentication page
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class EnforceLoginTag extends TagSupport {
	private String loginPage, errorPage;

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	
	/*
	 * If the information about the user is in
	 * visibility session class support returns EVAL_PAGE
	 * as a result of the document is processed, the remaining
 	 * otherwise is redirected to the authentication page
	 */
	public int doEndTag() throws JspException {
		HttpSession session = pageContext.getSession();
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		String protectedPage = req.getRequestURI();

		if (session.getAttribute("user") == null) {
			session.setAttribute("login-page", loginPage);
			session.setAttribute("error-page", errorPage);
			session.setAttribute("protected-page", protectedPage);
			try {
				pageContext.forward(loginPage);
				return SKIP_PAGE;
			} catch(Exception ex) {
				throw new JspException(ex.getMessage());
			}
		}
		return EVAL_PAGE;
	}
	
	public void release() {
		loginPage = errorPage = null;
	}
}
