package eu.heliovo.dpas.ie.services.directory.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import eu.heliovo.dpas.ie.services.directory.transfer.HttpDataTO;

public class HttpUtils {
	private static LinkedList<DPASResultItem> 	results = new LinkedList<DPASResultItem>();
	/**
	 * 
	 * @param host
	 * @param user
	 * @param pass
	 * @throws IOException
	 */
	public HttpUtils() throws IOException
	{
		
	}
	
	public static LinkedList<DPASResultItem> returnDPASResultItem()
	{
		return results;
	}
	
	
	
	/**
	 * @param httpTO
	 * @return
	 * @throws Exception
	 */
	public static  DPASResultItem getHttpFileDetails(HttpDataTO httpTO) throws Exception
	{
		DPASResultItem	currDpasResult	=	new DPASResultItem();
		Calendar		currCalendar	=	null;
		Calendar    	fromDate=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Calendar		toDate=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		fromDate.setTime(httpTO.getDateValueFrom());
		toDate.setTime(httpTO.getDateValueTo());
		Elements links=getHrefValueElementList(httpTO.getWorkingDir());
		//
		if(links!=null){
			for (Element link : links) {
		        currDpasResult	=	new DPASResultItem();
				currCalendar	=	new GregorianCalendar();
				System.out.println(link.attr("href"));
					httpTO.setHttpFileName(link.attr("href"));
					//Getting actual date value
					httpTO.setHttpDateFileName(getFileNameBasedOnPattern(httpTO));
					//Setting time
					Date date=convertDateFormatBasedOnProvider(httpTO);
					if(date!=null)
						currCalendar.setTime(date);
					//System.out.println("FTPFile: " + ftpFile.getName() +  ";"+ftpFile.getTimestamp().getTime()+" : "+ FileUtils.byteCountToDisplaySize(ftpFile.getSize()));
					System.out.println("fromDate  "+fromDate.getTime()+"  currCalendar "+currCalendar.getTime()+" toDate "+toDate.getTime());
					if(currCalendar.after(fromDate) && currCalendar.before(toDate)){
						currDpasResult.urlFITS	= httpTO.getWorkingDir()+"/"+httpTO.getHttpFileName();
						currDpasResult.measurementStart	=currCalendar;
						currDpasResult.fileSize =	"";
						results.add(currDpasResult);
					}
				}
			}
		return null;
	}
	
	/**
	 * 
	 * @param httpTO
	 * @return
	 */
	public static Calendar getProviderDateBasedOnFormat(HttpDataTO httpTO)
	{
		return null;
	}
	
	/**
	 * 
	 * @param httpTO
	 * @return
	 * @throws Exception
	 */
	private static String getFileNameBasedOnPattern(HttpDataTO httpTO) throws Exception
	{
		String sDateValue="";
		try{
			Matcher m = Pattern.compile(httpTO.getHttpPattern()).matcher(httpTO.getHttpFileName());
			if(m.find()){
					sDateValue=httpTO.getHttpFileName().substring(m.start(), m.end());
			}
		}catch(Exception e)
		{
			
		}
		System.out.println("   :  Value from file  :   "+sDateValue);
		return sDateValue;
	}
	
	/**
	 * 
	 * @param httpTO
	 * @return
	 */
	private static Date convertDateFormatBasedOnProvider(HttpDataTO ftpTO)
	{
		try
		{
			Date date=null;
			//Parse the orignal date
			SimpleDateFormat sdfSource = new SimpleDateFormat(ftpTO.getHttpDateFormat());
			//Parse string date to Date object
			System.out.println(ftpTO.getHttpDateFileName());
			if(ftpTO.getHttpDateFileName()!=null && !ftpTO.getHttpDateFileName().trim().equals(""))
				date=sdfSource.parse(ftpTO.getHttpDateFileName());
			return date;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static Elements getHrefValueElementList(String url)
	{
		try{
			Document doc = Jsoup.connect(url).get();
			return doc.select("a");
		}catch(Exception e)
		{
			
		}
		return null;
	}
	 
}
