
package eu.heliovo.ics.common.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;

public class ConnectionManager {
	
	//static Logger logger = Logger.getLogger(ConnectionManager.class.getName());	
	/**
	 * @return
	 * This method returns connection to the data source
	 */
	public static Connection getConnection() { 
		javax.sql.DataSource ds = null;
		InitialContext initCtx = null;
		java.sql.Connection con = null;
		boolean noConnection = true;
		String url=null;
		int i = 0;
		int count=0;
		while(noConnection){
			try {
	              Class.forName("com.mysql.jdbc.Driver"); 
                  url = "jdbc:mysql://msslxt.mssl.ucl.ac.uk:3306/helio"; 
			      con = DriverManager.getConnection(url,"helio_admin","majorca");     
			      noConnection = false;    
			}catch(SQLException sqlex) {  
				//logger.info(" SQLException Occoured in Connection Manager :getConnection()"+sqlex.getMessage()+" trying to reconeect for "+i+" time ");
				if(i < 3){
					i++;
					noConnection = true;
				}else {
					try {
						break;
					} catch (Exception e) {
						//logger.info(" SQLException Occoured in Connection Manager :getConnection()"+e.getMessage()+" trying to reconeect for "+i+" time ");
						e.printStackTrace();
					}        
				}
			}catch(ClassNotFoundException classexe) {				
				try {					
					count++;
					if(count<3){
						throw new Exception(classexe.toString());
					}else{
						noConnection=false;
						throw new Exception(classexe.toString());
					}    
				} catch (Exception e) {
					//logger.info("NamingException Occoured in Connecton manager class getConnection() "+e.getMessage()+" trying to reconeect for "+count+" time ");
					e.printStackTrace(); 
				}
			}
		}	
		return con;
	} 
	
}