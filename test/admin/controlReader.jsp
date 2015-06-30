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
                    <TD>ID</TD>
                    <TD>Surname</TD>
                    <TD>Name</TD>
                    <TD>Year</TD>
	   							         <TD>Email</TD>
                    <TD>Active</TD>
                    <TD>Click the button to lock / unlock the reader</TD>
                </TR>                                     
                <%@ page import="dymo.reader.*" %>
                <%
                    ReaderControl readerControl = new ReaderControl();
                    out.println(readerControl.showResult());
                %>
            </TABLE>
            <CENTER>
    			 	       <INPUT TYPE="SUBMIT" NAME="controlAdmin" VALUE="Back">
    			 	   </CENTER>
    			 </FORM>
    </body>
</html>
