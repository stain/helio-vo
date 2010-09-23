package eu.heliovo.dpas.ie.services.common.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;
 
public class CommonAction  extends ActionSupport implements  ServletRequestAware
{	
	
    private static final long serialVersionUID = 1L;
    protected final  Logger logger = Logger.getLogger(this.getClass());
  
    
	//Directing to index page.
	public String indexPage() throws SQLException{
		
		return "SUCCESS";
	}
	
	public String display() throws SQLException{ 
		String sReturnStatus="ERROR";
		
    	return sReturnStatus;
    }
	
	public String showProviderUploadPage () throws SQLException, IOException
	{
		String sReturnStatus="SUCCESS";
		
		return sReturnStatus;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	
       
}
