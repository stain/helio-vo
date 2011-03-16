/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.FileResultTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.soda.dao.interfaces.SoteriaQueryDao;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;

public class CommonUtils {
	
	 protected final  Logger logger = Logger.getLogger(this.getClass());
	 static SimpleDateFormat df=new SimpleDateFormat(ConstantKeywords.ORGINALDATEFORMAT.getDateFormat());
	 
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
	   * Reveals the base URI of the web application.
	   *
	   * @return The URI.
	   */
	  public static  String getUrl(HttpServletRequest req,String job_Id) { 
		  
		  String scheme = req.getScheme(); // http 
		  String serverName = req.getServerName(); // hostname.com 
		  int serverPort = req.getServerPort(); // 80 
		  String contextPath = req.getContextPath(); // mywebapp 
		  String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/ServiceJobStatus?MODE=file&amp;ID="+job_Id; 
		 		  
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
	 
	 public static void genegrateVotableBasedOnCondition(CommonTO commonTO) throws Exception
	 {
		 //getting details from Provider access table
		 ResultTO[] resultTo=HsqlDbUtils.getInstance().getAccessTableBasedOnInst(commonTO.getParaInstrument());
		 System.out.println("---------->"+resultTo+"------->"+resultTo.length+"------>"+resultTo[0]);
		 if(resultTo!=null && resultTo.length>0 && resultTo[0]!=null){
	    	 commonTO.setHelioInstrument(resultTo[0].getHelioInst());
	    	 System.out.println(" : Helio Instrument : "+resultTo[0].getHelioInst());
	    	 commonTO.setWhichProvider(resultTo[0].getProviderName());
	    	 System.out.println(" : Provider Type : "+resultTo[0].getProviderName());
	    	 commonTO.setInstrument(resultTo[0].getInst());
	    	 System.out.println(" : Instrument : "+resultTo[0].getInst());
	    	 commonTO.setProviderType(resultTo[0].getProviderType());
	    	 System.out.println(" : Instrument : "+resultTo[0].getProviderType());
	    	 commonTO.setMissionName(resultTo[0].getObsId());
	    	 commonTO.setProviderSource(resultTo[0].getProviderSource());
		     //Calling DAO factory to connect PROVIDERS
		     if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
		    	 System.out.println("--->  VSO Provider intiated--->");
		    	 System.out.println(" : VSO Provider Name : "+resultTo[0].getProviderSource());
		    	 commonTO.setVotableDescription("VSO query response "+ resultTo[0].getProviderSource());
		    	 commonTO.setUrl(VsoUtils.getUrl(commonTO.getRequest()));
		    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
	         	 vsoQueryDao.query(commonTO);
		     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
		    	 commonTO.setVotableDescription("UOC query response");
		    	 System.out.println("--->  UOC Provider intiated--->");
		    	 System.out.println(" : Table name for UOC  : "+resultTo[0].getObsId());
		    	 commonTO.setInstrument(resultTo[0].getObsId());
		    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
		    	 uocQueryDao.query(commonTO);
		     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
		    	 System.out.println("--->  CDAWEB Provider intiated--->");
		    	 System.out.println(" : Mission name for CDAWEB  : "+resultTo[0].getObsId());
		    	 commonTO.setVotableDescription("CDAWEB query response "+resultTo[0].getProviderSource());
		    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
		    	 cdaWebQueryDao.query(commonTO);
		     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof DirQueryDao ){
		    	 commonTO.setVotableDescription("Archive query response "+resultTo[0].getProviderSource());
		    	 System.out.println("--->  Directory Provider intiated--->");
		    	 DirQueryDao dirQueryDao=(DirQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
		    	 dirQueryDao.query(commonTO);
		     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof SoteriaQueryDao ){
		    	 commonTO.setVotableDescription("Soteria query response "+resultTo[0].getProviderSource());
		    	 System.out.println("--->  Soteria Provider intiated--->");
		    	 SoteriaQueryDao soteriaQueryDao=(SoteriaQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
		    	 soteriaQueryDao.query(commonTO);
		     }
		 }
		 else{
			 //commonTO.setBufferOutput(new BufferedWriter(pw));
	    	 commonTO.setVotableDescription("Error, no data for "+commonTO.getWhichProvider()+" provider");
	    	 commonTO.setQuerystatus("ERROR");
	    	 commonTO.setQuerydescription(" Provider access table is not configured for Instrument : "+commonTO.getParaInstrument());
			 VOTableCreator.writeErrorTables(commonTO);
		 }
	 }
	 
	 /*
		 * create Xml for webservice request.
		 */
		 public static String createXmlForWebService(FileResultTO fileTO) throws Exception {
			 StringBuilder xmlString = new StringBuilder();
			 String Status=fileTO.getStatus();
			 //URL 
			 String sUrl=fileTO.getsUrl();
			 if((Status==null || Status.trim().equals("")) && (sUrl==null || sUrl.trim().equals(""))){
				 xmlString.append("<ID>");
				 xmlString.append(fileTO.getRandomUUIDString());
				 xmlString.append("</ID>");
			 }
			 String sDes=null;
			 String statusArray[]=null;
			 if(sUrl==null || sUrl.trim().equals("")){
				 //Status of completion.
				 if(Status!=null && !Status.trim().equals(""))
					 statusArray=Status.split("::");
				 //Description if the error occured
				 if(statusArray!=null && statusArray.length>1)
					 sDes=statusArray[1];
				 //Status for the service
				 if(Status!=null && !Status.trim().equals("")){
					 xmlString.append("<Status>");
					 xmlString.append("<ID>");
					 xmlString.append(fileTO.getRandomUUIDString());
					 xmlString.append("</ID>");
					 xmlString.append("<status>");
					 xmlString.append(statusArray[0]);
					 xmlString.append("</status>");
				 
					// Des for the file location.
					 if(sDes!=null && !sDes.trim().equals("")){
						 xmlString.append("<statusdescription>");
						 xmlString.append(sDes);
						 xmlString.append("</statusdescription>");
					 }
					 //
					 xmlString.append("</Status>");
				 }
			 }
			 // Url for the file location.
			 if(sUrl!=null && !sUrl.trim().equals("")){
			     //
				 xmlString.append("<ResultInfo>");
				 xmlString.append("<ID>");
				 xmlString.append(fileTO.getRandomUUIDString());
				 xmlString.append("</ID>");
				 xmlString.append("<resultURI>");
				 xmlString.append(sUrl);
				 xmlString.append("</resultURI>");
				 //
			    String fileInfo=fileTO.getFileInfo();
			    //Result info
			    if(fileInfo!=null && !fileInfo.trim().equals("")){
				 xmlString.append("<fileInfo>");
				 xmlString.append(fileInfo);
				 xmlString.append("</fileInfo>");
			    }
			    
			    if(Status!=null && !Status.trim().equals(""))
				   statusArray=Status.split("::");
				   //Description if the error occured
				 if(statusArray!=null && statusArray.length>1)
					 sDes=statusArray[1];
				 //Status for the service
				 if(Status!=null && !Status.trim().equals("")){
					 xmlString.append("<status>");
					 xmlString.append(statusArray[0]);
					 xmlString.append("</status>");
	  				 // Des for the file location.
					 if(sDes!=null && !sDes.trim().equals("")){
						 xmlString.append("<description>");
						 xmlString.append(sDes);
						 xmlString.append("</description>");
					 }
			    }
				
				//
				xmlString.append("</ResultInfo>");
		    }
		     return xmlString.toString();
	      }
		 
		 
		  @SuppressWarnings("unchecked")
		  public static Map<String,String> getLogLocations() {

		    Collection<Logger> allLoggers = new ArrayList<Logger>();
		    Logger rootLogger = Logger.getRootLogger();
		    allLoggers.add(rootLogger);
		    for (Enumeration<Logger> loggers =rootLogger.getLoggerRepository().getCurrentLoggers() ;loggers.hasMoreElements() ; ) {
		      allLoggers.add(loggers.nextElement());
		    }
		    
		    Set<FileAppender> fileAppenders =new LinkedHashSet<FileAppender>();

		    for (Logger logger : allLoggers) {
		      for (Enumeration<Appender> appenders =
		              logger.getAllAppenders() ;
		              appenders.hasMoreElements() ; ) {

		        Appender appender = appenders.nextElement();
		        if (appender instanceof FileAppender) {

		          fileAppenders.add((FileAppender) appender);
		        }
		      }
		    }

		    Map<String, String> locations =new LinkedHashMap<String,String>();

		    for (FileAppender appender : fileAppenders) {
		      locations.put(appender.getName(), appender.getFile());
		    }

		    return locations;
		  }
		  
		  public static StringBuffer readInputStreamAsString(InputStream in) throws IOException {
			StringBuffer sb=new StringBuffer();
		    BufferedInputStream bis = new BufferedInputStream(in);
		    ByteArrayOutputStream buf = new ByteArrayOutputStream();
		    int result = bis.read();
		    while(result != -1) {
		      byte b = (byte)result;
		      buf.write(b);
		      result = bis.read();
		    }        
		    return sb.append(buf.toString());
		}
		  
		public static String changeDateFormat(String formatString,Date date){ 
				String str=""; 
				try{ 
					DateFormat sd1 = new SimpleDateFormat(formatString); 
					str=sd1.format(date); 
				}catch(Exception e){ 
					//Exception
				} 
				return str; 
		}
		
		public static GregorianCalendar stringToGregorianCalendar(String stringDate) throws ParseException
		{
			Calendar calendar = Calendar.getInstance();
			//Initializing GregorianCalendar
			GregorianCalendar gregorianCal = new GregorianCalendar(calendar.getTimeZone());
			Date date=df.parse(stringDate.replaceAll("T", " "));
			gregorianCal.setTime(date);
			return gregorianCal;
		}
		
		/**
		 * 
		 * @param s
		 * @return
		 * @throws ParseException
		 */
		public static Calendar dateString2Calendar(String s) throws ParseException {
		    Calendar cal=Calendar.getInstance();
		    Date d1=df.parse(s);
		    cal.setTime(d1);
		    return cal;
		  }
		
		/**
		 * 
		 * @param date
		 * @return
		 */
		public static String date2String(Date date)
		{
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();       
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String reportDate = df.format(today);
			
			return reportDate;
		}
		
		/**
		 * 
		 * @param sDate
		 * @return
		 * @throws ParseException
		 */
		public static int getNoOfMonths(String sDate) throws ParseException
		{
			int diff=DifferenceInMonths(dateString2Calendar(sDate),Calendar.getInstance());
			System.out.println(" :  No of months between dates  :  "+diff);
			return diff;
		}
		
		public static void deleteFile(String fileName) throws Exception{
			System.out.println("Deleting saved VOTABLE "+fileName);
			 // A File object to represent the filename
			 File f = new File(fileName);
			 // Make sure the file or directory exists and isn't write protected
			 if (!f.exists())
			      throw new IllegalArgumentException(
			          "Delete: no such file or directory: " + fileName);
	    	 // If it is a directory, make sure it is empty
			 if (f.isDirectory()) {
			      String[] files = f.list();
			      if (files.length > 0)
			        throw new IllegalArgumentException("Delete: directory not empty: " + fileName);
			 }
			 // Attempt to delete it
			 boolean success = f.delete();
			 if (!success)
			      throw new IllegalArgumentException("Delete: deletion failed");
		}
		
		
		/**
		 * 
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static int DifferenceInMonths(Calendar date1, Calendar date2)
	    {
		return (int)Math.round(DifferenceInYears(date1, date2) * 12);
	    }
		
		/**
		 * 
		 * @param date1
		 * @param date2
		 * @return
		 */
	    public static double DifferenceInYears(Calendar date1, Calendar date2)
	    {
		double days = DifferenceInDays(date1, date2);
		return  days / 365.2425;
	    }
	    /**
	     * 
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static double DifferenceInDays(Calendar date1, Calendar date2)
	    {
		return DifferenceInHours(date1, date2) / 24.0;
	    }
	    
	    /**
	     * 
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static double DifferenceInHours(Calendar date1, Calendar date2)
	    {
		return DifferenceInMinutes(date1, date2) / 60.0;
	    }
	    
	    /**
	     * 
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static double DifferenceInMinutes(Calendar date1, Calendar date2)
	    {
		return DifferenceInSeconds(date1, date2) / 60.0;
	    }
	    /**
	     * 
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static double DifferenceInSeconds(Calendar date1, Calendar date2)
	    {
		return DifferenceInMilliseconds(date1, date2) / 1000.0;
	    }
	    /**
	     * 
	     * @param date1
	     * @param date2
	     * @return
	     */
	    private static double DifferenceInMilliseconds(Calendar date1, Calendar date2)
	    {
		return Math.abs(GetTimeInMilliseconds(date1) - GetTimeInMilliseconds(date2));
	    }
	    /**
	     * 
	     * @param cal
	     * @return
	     */
	    private static long GetTimeInMilliseconds(Calendar cal)
	    {
		return cal.getTimeInMillis() + cal.getTimeZone().getOffset(cal.getTimeInMillis());
	    }
		
}
