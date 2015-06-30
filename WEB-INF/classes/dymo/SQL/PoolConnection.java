package dymo.SQL;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS; 
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;      
import javax.sql.DataSource; 
import java.sql.Connection; 
import java.sql.SQLException; 
     
/** 
 * Class that implements a pool of connections to the database 
 * @version 1.00
 * @author dymo
 * Copyright 2012 The Android Open Source Project
 */ 
public class PoolConnection {
	
	/** 
	 * The maximum number of active connections
	 * that can be used simultaneously
	 * -1 - unlimited number of connections 
     */ 
    private static final int MAX_ACTIVE = 100; 
    
    /** 
      * The maximum number of milliseconds that the pool will wait
      * (when there is no available connections) before it generated an exception
      * -1 - waiting will last an indefinite amount of time
     */ 
    private static final int MAX_WAIT = 100; 
   
    /** 
      * Connect to the database 
      */ 
    private Connection conn = null; 
    
    /** 
      * Source of data 
      */ 
    private static DataSource ds = null; 
    
    /** 
      * Constructor that defines the variables for the database connection 
      */ 
    public PoolConnection(int i) { 
    	if (ds == null) { 
    		try { 
    			
    			// Adapter for JDBC-driver. Saves the settings to connect to the database 
        	   	DriverAdapterCPDS pcds = new DriverAdapterCPDS(); 
        	   	pcds.setDriver("com.mysql.jdbc.Driver"); 
                pcds.setUrl("jdbc:mysql://localhost/library" +
                   			"?autoReconnect=true&useUnicode=true&characterEncoding=utf8"); 
                
                /**
		  * The constructor initialization value 0, appeals to the database
                  * is due to control an administrator, or -
                  * control over user account
                 */
                if (i == 0) {
                	pcds.setUser("root"); 
                   	pcds.setPassword("supercvs"); 
                } else {
                   	pcds.setUser("client"); 
                   	pcds.setPassword("read"); 
                }
                
                //Implementation of DataSource interface for connection pooling, distributed 
                SharedPoolDataSource tds = new SharedPoolDataSource(); 
                tds.setConnectionPoolDataSource(pcds); 
                tds.setMaxActive(MAX_ACTIVE); 
                tds.setMaxWait(MAX_WAIT); 
                ds = tds; 
        	} catch (ClassNotFoundException e) { 
                e.printStackTrace(); 
        	} 
        } 
    } 
    
    /** 
     * Returns a connection to the database 
     */ 
    public Connection openConnection() throws SQLException { 
    	if (conn == null) 
			conn = ds.getConnection(); 
        return conn;  
    } 
    
    /** 
      * Returns the number of active connections to the database
      * @return the number of active connections to the database
      */ 
    public static int getActiveConnection() { 
       	return ((SharedPoolDataSource)ds).getNumActive(); 
    } 
    
    /** 
      * If the connection is open, it returns the connection to the database 
      */ 
    public Connection getConnection() throws SQLException { 
       	return openConnection(); 
    } 
    
    /** 
      * Close the connection 
      */ 
    public void close() { 
    	try { 
         	if (conn != null) { 
                conn.close(); 
                conn = null; 
            } 
        } catch (SQLException e) { 
          	e.printStackTrace(); 
        } 
    } 
} 
  