/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.common.ConstantKeywords;
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
		
}
