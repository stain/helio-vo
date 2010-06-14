package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.ArchiveExplorer;
import eu.heliovo.dpas.ie.sensors.archives.PathBuilder;
import eu.heliovo.dpas.ie.sensors.archives.PathFragment;


public class ArchiveExplorerTest extends TestCase
{
	ArchiveExplorer	explorer	=	null;
	
	String			root		=	"http://www.sdc.uio.no/vol/fits/xrt/level0/";
	String			format		=	"yyyy/MM/dd/'H'HHmm";
	String			localFormat	=	"yyyy-MM-dd HH:mm:ss";
	
	Date			date		=	new Date();
	
	public void testIsPresent()
	{
		System.out.println("testIsPresent()...");

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

		if(explorer.isPresent(date))
			System.out.println(explorer.createPathFor(date) + " exists ! ");
		else
		{
			System.out.println(explorer.createPathFor(date) + " DOES NOT exist ! ");				
		}
		System.out.println("... done");
	}

	public void testGetAllWithin()
	{
		System.out.println("testGetAllWithin()...");

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
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("... done");
	}

	private void initialize() throws MalformedURLException
	{
		String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
		String			separatorString	=	"/";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";
		String			dayPattern		=	"dd";
		String			timePattern		=	"'H'HHmm";
		
		PathBuilder	path	=	new PathBuilder();
		PathFragment	root	=	new PathFragment(
				rootString,
				null,
				true,
				false);

		PathFragment	separator	=	new PathFragment(
				separatorString,
				null,
				true,
				true);

		PathFragment	yearFragment	=	new PathFragment(
				yearPattern,
				Calendar.YEAR,
				false,
				false);

		PathFragment	monthFragment	=	new PathFragment(
				monthPattern,
				Calendar.MONTH,
				false,
				false);

		PathFragment	dayFragment	=	new PathFragment(
				dayPattern,
				Calendar.DATE,
				false,
				false);

		int[]	timeFields	=	{Calendar.HOUR_OF_DAY, Calendar.MINUTE};
		
		PathFragment	timeFragment	=	new PathFragment(
				timePattern,
				timeFields,
				false,
				false);
		/*
		 * Adding the root
		 */
		path.add(root);
		/*
		 * Adding the first separator
		 */
		path.add(separator);
		/*
		 * Adding the year
		 */
		path.add(yearFragment);
		/*
		 * Adding the separator
		 */
		path.add(separator);
		/*
		 * Adding the month
		 */
		path.add(monthFragment);
		/*
		 * Adding the separator
		 */
		path.add(separator);
		/*
		 * Adding the day of the month
		 */
		path.add(dayFragment);
		/*
		 * Adding the separator
		 */
		path.add(separator);
		/*
		 * Adding the hour and minute
		 */
		path.add(timeFragment);
		/*
		 * Adding the separator
		 */
		path.add(separator);

		explorer	=	new ArchiveExplorer(path);	
	}
}
