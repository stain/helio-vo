/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.cdaweb.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import eu.heliovo.dpas.ie.services.common.utils.ConstantKeywords;

public class CdaWebUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	
	  /**
	   * Reveals the base URI of the web application.
	   *
	   * @return The URI.
	   */
	  public static String getUrl(HttpServletRequest req) { 
		  String scheme = req.getScheme(); // http 
		  String serverName = req.getServerName(); // hostname.com 
		  int serverPort = req.getServerPort(); // 80 
		  String contextPath = req.getContextPath(); // mywebapp 
		  String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/FitsResponseServlet?"; 
		  return url; 
	  }	 
	  
	  /**
	   * 
	   * @param date
	   * @return
	   */
	  public static Calendar getDateFormat(String date){
		
		 try
          {
			SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
		    Date parseDate  = (Date)formatter.parse(date.replace("T", " ")); 
		    Calendar cal=Calendar.getInstance();
		    cal.setTime(parseDate);
	     	return cal;
         } catch (Exception e)
		 {
		    e.printStackTrace();
		 }
		  return null;
	  }
	  
	  
	  public static FileDescription[] addArrays(FileDescription[] first, FileDescription[] second) {
		     FileDescription[] both = new FileDescription[first.length + second.length];  
		     System.arraycopy(first, 0, both, 0, first.length);  
		     System.arraycopy(second, 0, both, first.length, second.length);
		    
		    return both;
	  }
	  
	  public static String[] addArrays(String[] first, String[] second) {
		     String[] both = new String[first.length + second.length];  
		     System.arraycopy(first, 0, both, 0, first.length);  
		     System.arraycopy(second, 0, both, first.length, second.length);
		    
		    return both;
	  }
	  
	  /**
	   * 
	   * @param dataSetId
	   * @param length
	   * @return
	   */
	  public static String[] getDataSetArray(String dataSetId,int length)
	  {
		  String[] dataSetIdArray=new String[length];
		  Arrays.fill(dataSetIdArray, dataSetId);
		  return dataSetIdArray;
	  }
	  
	  public static String convertCalendarToString(Calendar date){
			
			 try
	          {
				String strDate="";
				SimpleDateFormat formatter = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
				if (date != null) 
					strDate = formatter.format(date.getTime());
				//Adding 'T' for date
				if(strDate!=null && !strDate.equals(""))
					strDate=strDate.replace(" ", "T");
		     	return strDate;
	         } catch (Exception e)
			 {
			    e.printStackTrace();
			 }
			  return null;
		  }		
}
