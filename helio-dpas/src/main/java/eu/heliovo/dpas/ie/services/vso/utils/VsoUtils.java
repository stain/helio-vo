/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.vso.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.common.ConstantKeywords;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;


public class VsoUtils {
	
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
	   * Append the parameter or URL.
	   * @param url
	   * @param fileId
	   * @param provider
	   * @param status
	   * @return
	   */
	  public static String appendParamtersForUrl(String url,String fileId,String provider,String status)
	  {
		 if(status!=null && !status.trim().equals(""))
			 url=url+"ID="+fileId+"&PROVIDER="+provider;
		 else
			 url=url+"ID="+fileId+"&PROVIDER="+provider;
		 return url;
	  }
	  
	  /**
	   * 
	   * @param date
	   * @return
	   */
	  public static String getDateFormat(String date){
		
		 try
          {
			SimpleDateFormat sdf = new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
			Date dt=sdf.parse(date.replace("T", " "));
            //Converting back the format
            DateFormat dateFormat = new SimpleDateFormat(ConstantKeywords.VSODATEFORMAT.getDateFormat());
   	     	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));	
   	     	String datetime=dateFormat.format(dt);
   	     	System.out.println(" Date and time "+datetime);
  	     	return datetime;
         } catch (Exception e)
		 {
		    e.printStackTrace();
		 }
		  return null;
	  }
	  
	  /**
	   * 
	   * @param resp
	   * @return
	   */
	  public static boolean getProviderResultCount(ProviderQueryResponse[]	resp)
	  {
		  boolean status=false;
		  if(resp[0]!=null && resp[0].getRecord()!=null){
			  status=true;
		  }
		  return status;
	  }
				
}
