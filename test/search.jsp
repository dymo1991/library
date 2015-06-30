<%@page contentType="text/html; charset=windows-1251" %>
<%@ taglib uri='/WEB-INF/tlds/dymo.tld' prefix='dymo' %>
<html>
    <head>
        <TITLE>Welcome to the library</TITLE>
        <link href="client.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <dymo:enforceLogin loginPage='/library/test/main.html'
                           errorPage='/library/test/errorLogining.html'/>
        <H1>Welcome to the library</H1>
	   				<H2>The result of your search query</H2>
        <FORM ACTION="/library/test/logout">
            <CENTER>
	   				        <INPUT TYPE="SUBMIT" NAME="exit" VALUE="Exit" class="b1">
	   								</CENTER>
        </FORM>
	   				<FORM ACTION="/library/test/controller">
            <TABLE>
	   							     <TR class="trt">
                    <TD>Number</TD>
                    <TD>Author</TD>
                    <TD>Name</TD>
                    <TD>Year</TD>
	   							         <TD>Availability</TD>
                    <TD>Click to Order</TD>
                </TR>
                <%@ page import="dymo.book.*" %>
                <%
                    String author = request.getParameter("author");
                    String title = request.getParameter("title");
                    Book book = new Book(author, title);
                    BookControl bookControl = new BookControl();
                    out.println(bookControl.showResult(book));
                %>
            </TABLE>
    			 	   <CENTER>
    			 	       <INPUT TYPE="SUBMIT" NAME="b_search" VALUE="Back">
    			 	   </CENTER>
    			 </FORM>
        <CENTER><H5>This page has been loaded</H5> <h6><dymo:counter/></h6> <H5>times.</H5></CENTER>
    </body>
</html>
