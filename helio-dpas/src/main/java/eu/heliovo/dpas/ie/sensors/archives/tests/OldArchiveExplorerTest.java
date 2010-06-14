package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.OldArchiveExplorer;


public class OldArchiveExplorerTest extends TestCase
{
	OldArchiveExplorer	explorer	=	null;
	
	String			root		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
	String			format		=	"yyyy/MM/dd/'H'HHmm";
	String			localFormat	=	"yyyy-MM-dd HH:mm:ss";
	
	Date			date		=	new Date();
	
//	public void testIsPresent()
//	{
//		try
//		{
//			initialize();
//		} 
//		catch (MalformedURLException e1)
//		{
//			e1.printStackTrace();
//		}
//		
//        DateFormat formatter = new SimpleDateFormat(localFormat);
//    
//        System.out.println("Date is : " + formatter.format(date));
//
//		if(explorer.isPresent(date))
//			System.out.println(explorer.createPathFor(date) + " exists ! ");
//		else
//		{
//			System.out.println(explorer.createPathFor(date) + " DOES NOT exist ! ");				
//			explorer.getFirstBefore(date);
//		}
//	}

	public void testGetAllWithin()
	{
		try
		{
			initialize();
		} 
		catch (MalformedURLException e1)
		{
			e1.printStackTrace();
		}
		
        DateFormat formatter = new SimpleDateFormat(localFormat);    
        System.out.println("Date is : " + formatter.format(date));

		try
		{
			explorer.getAllWithin(date, date);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() throws MalformedURLException
	{
		explorer	=	new OldArchiveExplorer("http://www.sdc.uio.no/vol/fits/xrt/level0",
				"yyyy/MM/dd/'H'HHmm");	
	}
}
