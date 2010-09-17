/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.cdaweb.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.common.ConstantKeywords;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;


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
	  
				
}
