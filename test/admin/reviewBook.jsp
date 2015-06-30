<%@page contentType="text/html; charset=windows-1251" %>
<html>
    <HEAD>
        <TITLE>Page of library administrator</TITLE>
								<link href="admin.css" rel="stylesheet" type="text/css">
	   </HEAD>
    <body>
        <H1>Page of library administrator</H1>
							 <H2>Manage readers</H2>
	   				<FORM ACTION='/library/test/admin/adminController'>
            <TABLE>
                <TR class="trt">
                    <TD>Author</TD>
                    <TD>Title</TD>
                    <TD>Year</TD>
					<TD>QuantityWithoutReadingRoom</TD>
                    <TD>AllQuantity</TD>
                </TR>                                     
                <%@ page import="dymo.book.*" %>
                <%
                    BookControl bookControl = new BookControl();
                    out.println(bookControl.showAllBooks());
                %>
            </TABLE>
            <CENTER>
    			 	       <INPUT TYPE="SUBMIT" NAME="controlAdmin" VALUE="Back">
    			 	   </CENTER>
    			 </FORM>
    </body>
</html>
