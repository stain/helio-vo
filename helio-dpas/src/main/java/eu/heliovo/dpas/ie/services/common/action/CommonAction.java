package eu.heliovo.dpas.ie.services.common.action;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
 
public class CommonAction  extends ActionSupport
{	
	
    private static final long serialVersionUID = 1L;
    protected final  Logger logger = Logger.getLogger(this.getClass());
    
    public boolean statusDisplay;
    
	public boolean isStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(boolean statusDisplay) {
		this.statusDisplay = statusDisplay;
	}	
	
	public String showConfigurationDetials()
	{
		return "SUCCESS";
	}
	//Directing to index page.
	public String indexPage() throws SQLException{
		
		return "SUCCESS";
	}
	
	public String display() throws SQLException{ 
		String sReturnStatus="ERROR";
		Connection con=null;
		try{
			
		}catch (Exception e) {
			if(con!=null){
				con.close();
				con=null;
			}
		}
		finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
    	return sReturnStatus;
    }
	

	/*
	 * Configuration of database table.
	 */
	public String getDatabaseConfigurationPage() throws SQLException
	{
		Connection con=null;
		String sReturnStatus="ERROR";
		try{
		
		}catch (Exception e) {
			if(con!=null){
				con.close();
				con=null;
			}
		}
		finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
		return sReturnStatus;
	}
       
}
