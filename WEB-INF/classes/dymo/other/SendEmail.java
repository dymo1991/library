package dymo.other;

import org.apache.commons.mail.*;
import java.sql.*;
import dymo.SQL.*;

/* 
 * Class by which forgotten password sent to the email reader
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */
public class SendEmail {
	
	/** 
     * Send forgotten password to the specified email
     */
	public void sendPassword(String email) {
		String empt = "";
		try{
	   	   	SimpleEmail mail = new SimpleEmail();
	   	   	
	   	   	// Specifies the name of SMTP-server
	   	   	mail.setHostName("smtp.inbox.ru");
	   		
	   	   	// We set message recipient		   	              
	   	   	mail.addTo(email, "Some user");
	   	   	
	   		// We set sender
	   	   	mail.setFrom("dymotest@mail.ru", "Me1");
	   	   	
	   	   	// Specify a name and password for SMTP-server
	   	   	mail.setAuthentication("dymotest@mail.ru", "dymodymo");
	   	   	
	   	   	// We set the message subject	   	   	
	   	   	mail.setSubject("Reminding password");
	   	   	
	   	   	// Specify the message bodyand in him forgotten password	   	   		   	  
	   	   	mail.setMsg("Your password to enter library:" + showResult(email));
	   	   	
	   	   	/* 
	   	   	 * If a reader with the given email registered in the library 
			 * send a letter
	   	   	 */
	   	   	if(!empt.equals(showResult(email))) {
	   	   		mail.send();
	   	   	}
		} catch (EmailException e) {
   		   	e.printStackTrace();
	   	}
	}
   
	/** 
      * Get password reader who is registered under the specified email
      * @return string password reader
     */
	private String showResult(String email){
		Connection con;
		String sql, pass="";
		PreparedStatement ps;
		ResultSet rs;
		PoolConnection pool = new PoolConnection(1);
		
		try {
			con = pool.getConnection();
		    sql= "SELECT password FROM reader WHERE email=?";
   		   	ps = con.prepareStatement(sql);
   		   	ps.setString(1, email);
   		   	rs=ps.executeQuery();
   		   	while (rs.next()) {
   		   		pass=rs.getString("password");
   		   		break;
   		   	}
		} catch(SQLException e) { 
			e.printStackTrace(); 
		} finally { 
		    pool.close(); 
		}
		
   	   	return pass;
	}
}