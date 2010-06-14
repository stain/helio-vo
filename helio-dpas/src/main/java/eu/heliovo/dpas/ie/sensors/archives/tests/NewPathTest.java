package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPath;
import eu.heliovo.dpas.ie.sensors.archives.NewPathException;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;
import eu.heliovo.dpas.ie.sensors.archives.PathBuilderException;
import eu.heliovo.dpas.ie.sensors.archives.XRTFileFragment;


public class NewPathTest extends TestCase
{
	NewPath	path	=	new NewPath();
	
	private	String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
	private	String			yearPattern		=	"yyyy";
	private	String			monthPattern	=	"MM";
	private	String			dayPattern		=	"dd";
	private	String			timePattern		=	"'H'HHmm";
	private	String			testUrl			=	"http://www.sdc.uio.no/vol/fits/xrt/level0/2007/01/01/H0000/XRT20070707_000707.3.fits.gz";
	
	private	NewPathFragment	rootFragment	=	new GenericNewPathFragment(
			rootString,
			true,
			null,
			null);

	private	NewPathFragment	yearFragment	=	new GenericNewPathFragment(
			yearPattern,
			false,
			Calendar.YEAR,
			yearPattern);

	private	NewPathFragment	monthFragment	=	new GenericNewPathFragment(
			monthPattern,
			false,
			Calendar.MONTH,
			monthPattern);

	private	NewPathFragment	dayFragment	=	new GenericNewPathFragment(
			dayPattern,
			false,
			Calendar.DATE,
			dayPattern);

	int[]		timeFields			=	{Calendar.HOUR_OF_DAY, Calendar.MINUTE};
	String[]	timeFieldFormatters	=	{"HH", "mm"};
	
	private	NewPathFragment	timeFragment	=	new GenericNewPathFragment(
			timePattern,
			false,
			timeFields,
			timeFieldFormatters);
	
	private	NewPathFragment	fileFragment	=	new XRTFileFragment();

	public void testDateToPath()
	{
		Date now	=	new Date();
		initialize();

		try
		{
			System.out.println(now + " --> " + path.dateToPath(now));
		} 
		catch (NewPathException e)
		{
			assertTrue(false);
		}		
	}

	public void testPathToDate()
	{
		initialize();
		try
		{
			System.out.println(testUrl + " --> " + path.pathToDate(testUrl));
		} 
		catch (PathBuilderException e)
		{
			assertTrue(false);
		} 
		catch (NewPathFragmentException e)
		{
			assertTrue(false);
		}		
	}

	private	void initialize()
	{
		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(timeFragment);
		path.add(fileFragment);	
	}
}
