/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.hqi.utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.common.utils.ConstantKeywords;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0_1.HelioQueryServiceService;

public class HqiUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	 private static final QName SERVICE_NAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServiceService");
	 
	 /**
	  * Get Date format and parse date to String.
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
	 
	 
	 

	  public static String getInstrumentWhereClause(String type,String instrument) throws Exception{
		System.out.println("where call: " + "query.where."+type.toLowerCase() + "." + instrument);
		  return ConfigurationProfiler.getInstance().getProperty("query.where."+type.toLowerCase() + "." + instrument);

	  }				
	 
	 
	  
	 /**
	  * Getting working URL for HQL,based on Type.
	  * @param type
	  * @return
	  * @throws Exception
	  */
	  public static String getWorkingHQIURLBasedType(String type) throws Exception{
		  boolean noConnection = true;
		  int count=0;
		  String urlValue="";
		  java.net.URLConnection urlc = null;
		  //HelioQueryServiceService ss =null;
		  try{
			  String sHQIValues=ConfigurationProfiler.getInstance().getProperty("query.url."+type.toLowerCase());
			  String[] arrayHQI=sHQIValues.split("::");
			  if(arrayHQI.length>0)
				  urlValue=arrayHQI[count];
			  //While loop
			  while(noConnection){
				  try{
					  //ss = new HelioQueryServiceService(new URL(urlValue), SERVICE_NAME);
					  urlc = new URL(urlValue).openConnection();
					  noConnection=false;
				  }catch(Exception e)
				  {
					  count++;
					  if(arrayHQI.length>count){
						  urlValue=arrayHQI[count];
					  }else{
						  break;
					  }
				  }
			  }
		  }finally{
			  if(urlc != null) {
				  urlc = null;
			  }
			  //if(ss!=null)
				  //ss=null;
			  if(noConnection==true)
				  throw new Exception(type+" is not running,please check the hql-config.txt file.");
		  }
		  
		return urlValue;
	  }				
}
