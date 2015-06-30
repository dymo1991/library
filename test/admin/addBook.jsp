<!-- version 1.01 -->
<!-- author dymo -->

<%@page contentType="text/html; charset=windows-1251" %>
<HTML>
<HEAD>
    <TITLE>Page of library administrator</TITLE>
    <link href="admin.css" rel="stylesheet" type="text/css">
</HEAD>

<H1>Page of library administrator</H1>
<H2>Add book</H2>

<FORM ACTION='/library/test/admin/adminController'>
    <BR>
    <TABLE>
        <TR>
            <TD><H4>Author: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="author" VALUE=""></TD>
        </TR>
        <TR>
            <TD><H4>Title: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="title" VALUE=""></TD>
        </TR>
        <TR>
            <TD><H4>Year: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="year" VALUE=""></TD>
        </TR>
        <TR>
            <TD><H4>Section: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="section" VALUE=""></TD>
        </TR>
        <TR>
            <TD><H4>Shelve: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="shelf" VALUE=""></TD>
        </TR>
        <TR>
            <TD><H4>Row: </H4></TD>
            <TD><INPUT TYPE="TEXT" NAME="row" VALUE=""></TD>
        </TR>
        <TR>
	    <TD><H4>Cover: </H4></TD>
	    <TD><INPUT TYPE="SUBMIT" NAME="downloadCover" VALUE="Download" class="b1"></TD>
    </TABLE>
    <CENTER>
	<P><%
		String filePath = (String) session.getAttribute("filePath");
		out.println(filePath);
	     %>
	</P>
        <INPUT TYPE="CHECKBOX" NAME="home">Check the box that the book was only available in the reading room<P>
        <H3>The book will be added to the library automatically after you click "Add"</H3>              
        <INPUT TYPE="SUBMIT" NAME="addBook" VALUE="Add" class="b1">
        <INPUT TYPE="SUBMIT" NAME="controlAdmin" VALUE="Back to Control" class="b1">
    </CENTER>
</FORM>
</BODY>
</HTML>
