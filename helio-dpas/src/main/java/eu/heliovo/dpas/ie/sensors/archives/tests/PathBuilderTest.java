package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.PathBuilder;
import eu.heliovo.dpas.ie.sensors.archives.PathBuilderException;
import eu.heliovo.dpas.ie.sensors.archives.PathFragment;


public class PathBuilderTest extends TestCase
{
	PathBuilder		path	=	new PathBuilder();

	private	String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
	private	String			separatorString	=	"/";
	private	String			yearPattern		=	"yyyy";
	private	String			monthPattern	=	"MM";
	private	String			dayPattern		=	"dd";
	private	String			timePattern		=	"'H'HHmm";
	private	String			url				=	"http://www.sdc.uio.no/vol/fits/xrt/level0/2007/07/07/H1734/";

	private	PathFragment	root	=	new PathFragment(
			rootString,
			null,
			true,
			false);

	private	PathFragment	separator	=	new PathFragment(
			separatorString,
			null,
			true,
			true);

	private	PathFragment	yearFragment	=	new PathFragment(
			yearPattern,
			Calendar.YEAR,
			false,
			false);

	private	PathFragment	monthFragment	=	new PathFragment(
			monthPattern,
			Calendar.MONTH,
			false,
			false);

	private	PathFragment	dayFragment	=	new PathFragment(
			dayPattern,
			Calendar.DATE,
			false,
			false);

	int[]	timeFields	=	{Calendar.HOUR_OF_DAY, Calendar.MINUTE};
	
	private	PathFragment	timeFragment	=	new PathFragment(
			timePattern,
			timeFields,
			false,
			false);

	
	public void testDateToPathDate()
	{
		System.out.println("testDateToPathDate()...");
		build();
		Date now	=	new Date();
		System.out.println(now + " --> " + path.dateToPath(now));
		System.out.println("... done");
	}

	public void testDateToPathDateInt()
	{
		System.out.println("testDateToPathDateInt()...");
		build();
		Date now	=	new Date();
		for(int depth = 0; depth <= path.getMaxDepth(); depth++)
		{
			System.out.println(now + "[" + depth + "] --> " + path.dateToPath(new Date(), depth));			
		}
		System.out.println("... done");
	}

	public void testPathToDate()
	{
		System.out.println("testPathToDate()...");
		build();
		try
		{
			System.out.println(url + " --> " + path.pathToDate(url));
		} 
		catch (PathBuilderException e)
		{
			e.printStackTrace();
		}
		System.out.println("... done");
	}

	public void testEvaluateElementAt()
	{
		fail("Not yet implemented");
	}

	public void testEvaluateUrlAt()
	{
		fail("Not yet implemented");
	}

	/*
	 * This test site is of the format : yyyy/MM/dd/'H'HHmm
	 */
	private	void build()
	{
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
	}
}
