<%@page contentType="text/html; charset=windows-1251" %>
<html>
    <HEAD>
        <TITLE>Page of library administrator</TITLE>
        <link href="admin.css" rel="stylesheet" type="text/css">
				</HEAD>
    <body>
        <H1>Page of library administrator</H1>
						  <H2>Viewing orders</H2>
	   				<FORM ACTION='/library/test/admin/adminController'>
            <TABLE>
                <TR class="trt">
                    <TD>ID</TD>
                    <TD>Author</TD>
                    <TD>Name</TD>
                    <TD>Year</TD>
                    <TD>Section</TD>
                    <TD>Shelve</TD>
                    <TD>Row</TD>
                </TR>
                <%@ page import="dymo.ord.*" %>
                <%
                    OrderControl orderControl = new OrderControl();
                    out.println(orderControl.showResult());
                %>
            </TABLE>
    			 	   <CENTER>
    			 	       <INPUT TYPE="SUBMIT" NAME="executeOrder" VALUE="Execute">
    			 	   </CENTER>
    			 </FORM>
    </body>
</html>
