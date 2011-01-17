/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.uoc.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import eu.heliovo.dpas.ie.services.common.utils.ConstantKeywords;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;


public class UocUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	
		  
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
	  

				
}
