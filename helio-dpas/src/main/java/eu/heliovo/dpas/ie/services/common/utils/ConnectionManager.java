package eu.heliovo.dpas.ie.services.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	/**
	 * @return
	 * This method returns connection to the data source
	 */
	public static Connection getConnection() { 		
		java.sql.Connection con = null;
		boolean noConnection = true;		
		int i = 0;
		int count=0;
		String jdbcString = "org.hsqldb.jdbcDriver";
	    String jdbcURL = getHsqlDBEmbeddedDatabasePath("/HelioDB/testdb");
	    String user = "sa";
	    String passwd = "";
	    System.out.println(" Driver Name : "+jdbcString+" Database URL  : "+jdbcURL+" User : "+user+" Password  "+passwd);
		while(noConnection){
			try {
				  System.out.println(" Driver manager tring to get connection --> count "+i);
	              Class.forName(jdbcString);                  
			      con = DriverManager.getConnection(jdbcURL,user,passwd);     
			      System.out.println(" Connected succesfully !!!");
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
	
	//To get HSQL DB embedded database path.
	private static String getHsqlDBEmbeddedDatabasePath(String url){
		if(url!=null && InstanceHolders.getInstance().getProperty("hsqldb.database.path")!=null){
			url="jdbc:hsqldb:file:"+InstanceHolders.getInstance().getProperty("hsqldb.database.path")+url+";hsqldb.default_table_type=cached;shutdown=true" ;
		}
		return url;
	}	
}