package eu.heliovo.dpas.ie.services.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
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
		String fileName=InstanceHolders.getInstance().getProperty("patFileName");
		if(fileName!=null && !fileName.trim().equals("")){
			setStatusDisplay(true);
			setUploadedFileName(fileName);
		}else{
			setStatusDisplay(false);
		}	
		return "SUCCESS";
	}
	
	public String display() throws SQLException{ 
		String sReturnStatus="ERROR";
		
    	return sReturnStatus;
    }
	
	
	private String showLogs;
	
	public String getShowLogs() {
		return showLogs;
	}

	public void setShowLogs(String showLogs) {
		this.showLogs = showLogs;
	}
	
	@SuppressWarnings("unchecked")
	public String showLogFile()
	{
		String status="SUCCESS";
		Map<String, String> hmbDt=CommonUtils.getLogLocations();
		Iterator it = hmbDt.entrySet().iterator();
		String logFileName="";
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println("Log file name  = " + pairs.getValue());
	        if(pairs!=null){
	        	logFileName=pairs.getValue().toString();
	    	} 
	        break;
	    }
	    //
	    try {
			FileInputStream fileInputStream=new FileInputStream(new File(logFileName));
			StringBuffer sb=CommonUtils.readInputStreamAsString(fileInputStream);
			System.out.println(" -----------> String Buffer ---------->"+sb);
			setShowLogs(sb.toString());
			//Setting flag to true, database details are correct.
			setStatusDisplay(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	private boolean statusDisplay;
	private String uploadedFileName;
	
	public boolean isStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(boolean statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
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
