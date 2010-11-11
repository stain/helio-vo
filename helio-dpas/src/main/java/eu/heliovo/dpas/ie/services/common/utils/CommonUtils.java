/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.common.ConstantKeywords;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;


public class CommonUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	
	 public static void copyFile(File src, File dst) throws IOException {
      	InputStream in = new FileInputStream(src); 
      	OutputStream out = new FileOutputStream(dst); 
      	// Transfer bytes from in to out 
      	byte[] buf = new byte[1024]; 
      	int len; while ((len = in.read(buf)) > 0) {
      		out.write(buf, 0, len); 
      		} 
      	in.close(); 
      	out.close();
      } 
	 
	 
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
		  String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/HelioQueryServlet?STARTTIME="; 
		  return url; 
	  }	 
	  
	  /**
	   * 
	   * @param a
	   * @param separator
	   * @return
	   */
	  public static String arrayToString(String[] a, String separator) {
		    String result = "";
		    if (a.length > 0) {
		        result = a[0];    // start with the first element
		        for (int i=1; i<a.length; i++) {
		            result = result + separator + a[i];
		        }
		    }
		    return result;
		}
		
	 /**
	  * 
	  * @param commonTO
	  * @return
	  */
	 public static String getFullRequestUrl(CommonTO commonTO){
		 String sActualUrl=commonTO.getContextUrl()+commonTO.getAllDateFrom()+"&ENDTIME="+commonTO.getAllDateTo()+"&INSTRUMENT="+commonTO.getAllInstrument();
		 return sActualUrl;
	 }
	 
	 /**
	  * 
	  * @param commonTO
	  * @return
	  */
	 public static String getRequestUrl(CommonTO commonTO){
		 String sActualUrl=commonTO.getContextUrl()+commonTO.getDateFrom()+"&ENDTIME="+commonTO.getDateTo()+"&INSTRUMENT="+commonTO.getHelioInstrument();
		 return sActualUrl;
	 }
}
