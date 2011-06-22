/*
 * 
 */
package eu.heliovo.dpas.ie.services.directory.archives;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DateIterator;
import eu.heliovo.dpas.ie.services.directory.utils.FtpUtils;

public class FtpArchiveExplorer
{
	private FtpDataTO 			ftpTO 		= null;
	/**
	 * Instantiates a new new archive explorer.
	 * 
	 * @param path
	 *            the path
	 */
	public FtpArchiveExplorer(FtpDataTO ftpTO)
	{
		super();
		this.ftpTO = ftpTO;
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
		System.out.println("   TO    "+to+"   FROM   "+from);
		SimpleDateFormat df=null;
		FtpUtils  ftpUtils=null;
		String pvdrSrc=ftpTO.getProviderSource();
		ftpTO.setDateValueTo(to);
		ftpTO.setDateValueFrom(from);
		System.out.println(ftpTO.getYearPattern()+ftpTO.getMonthPattern());
		if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
			df=new SimpleDateFormat(ftpTO.getYearPattern()+ftpTO.getMonthPattern()+"/"+ftpTO.getYearPattern()+ftpTO.getMonthPattern()+"dd");
		else if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
			df=new SimpleDateFormat(ftpTO.getYearPattern());
		else
			df=new SimpleDateFormat(ftpTO.getYearPattern()+ftpTO.getMonthPattern());
		try{
			
		ftpUtils=new FtpUtils(ftpTO.getFtpHost(),ftpTO.getFtpUser(),ftpTO.getFtpPwd());	
		String workingDir=ftpTO.getWorkingDir();
    	if(workingDir!=null){
    		String[] dirArray=workingDir.split("::");
	    	for(int count=0;count<dirArray.length;count++){
	    		Iterator<Date> i =null;
	    		if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
	    			i = new DateIterator(from, to,"d");
	    		else if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("goes"))
	    			i = new DateIterator(from, to,"y");
	    		else
	    			i = new DateIterator(from, to,"m");
		    	while(i.hasNext())
		    	{
		    		Date date = i.next();
		    		String formatDate=df.format(date);
		    		System.out.println("------------> for"+formatDate);
		    		//
		    		ftpTO.setWorkingDir(dirArray[count]+formatDate);
		    		ftpUtils.getFtpFileDetails(ftpTO);
		    	}
	    	}
    	}
		}
		catch(IOException e)
		{
			throw new Exception("Exception occurred while conneting ftp server ");
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		finally
		{
			if (ftpUtils.getFtpConection()!=null && ftpUtils.getFtpConection().isConnected())
            {
                try
                {
                	ftpUtils.getFtpConection().disconnect();
                }
                catch (IOException f)
                {
                    f.printStackTrace();
                }
            }
		}
    	//
		return ftpUtils.returnDPASResultItem();
	}
}
