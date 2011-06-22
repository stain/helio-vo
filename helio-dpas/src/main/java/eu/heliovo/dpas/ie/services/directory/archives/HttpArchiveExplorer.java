/*
 * 
 */
package eu.heliovo.dpas.ie.services.directory.archives;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import eu.heliovo.dpas.ie.services.directory.transfer.HttpDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DateIterator;
import eu.heliovo.dpas.ie.services.directory.utils.HttpUtils;

public class HttpArchiveExplorer
{
	private HttpDataTO 			httpTO 		= null;
	/**
	 * Instantiates a new new archive explorer.
	 * 
	 * @param path
	 *            the path
	 */
	public HttpArchiveExplorer(HttpDataTO httpTO)
	{
		super();
		this.httpTO = httpTO;
	}

	/**
	 * @param from
	 * @param to
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	public LinkedList<DPASResultItem> query(Date from, Date to) throws MalformedURLException,IOException,Exception
	{
		
    	try{
    		SimpleDateFormat df=new SimpleDateFormat(httpTO.getYearPattern()+"/"+httpTO.getMonthPattern());
    		httpTO.setDateValueTo(to);
    		httpTO.setDateValueFrom(from);
    		System.out.println(httpTO.getYearPattern()+httpTO.getMonthPattern());
    		String workingDir=httpTO.getWorkingDir();
        	if(workingDir!=null){
        		String[] dirArray=workingDir.split("::");
    	    	for(int count=0;count<dirArray.length;count++){
    	    		DateIterator i = new DateIterator(from, to,"d");
    	    		while(i.hasNext())
    		    	{
    		    		Date date = i.next();
    		    		String formatDate=df.format(date);
    		    		System.out.println("------------> for"+formatDate);
    		    		//
    		    		httpTO.setWorkingDir("http://"+httpTO.getHttpHost()+"/"+dirArray[count]+formatDate+httpTO.getEndUrl());
    		    		HttpUtils.getHttpFileDetails(httpTO);
    		    	}
    	    	}
        	}
  		}
		catch(Exception e)
		{
			throw new Exception("Exception occurred while getting details from http. ");
		}
		
		finally
		{
			
		}
    	//
		return HttpUtils.returnDPASResultItem();
	}
}
