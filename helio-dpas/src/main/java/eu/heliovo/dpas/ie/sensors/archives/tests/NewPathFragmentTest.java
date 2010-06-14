package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;
import eu.heliovo.dpas.ie.sensors.archives.XRTFileFragment;

/*
 * XRT20070707_040030
 * 
 * http://www.sdc.uio.no/vol/fits/xrt/level0/2007/07/07/H0400/XRT20070707_040030.3.fits.gz
 */

public class NewPathFragmentTest extends TestCase
{
	private	String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
	private	String			yearPattern		=	"yyyy";
	private	String			monthPattern	=	"MM";
	private	String			dayPattern		=	"dd";
	private	String			timePattern		=	"'H'HHmm";
	private String			filePattern		=	"'XRT'20070707'_'040030'.3.fits.gz'";
	
	private	NewPathFragment	root	=	new GenericNewPathFragment(
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

	int[]		fileFields			=	{Calendar.YEAR, 
			Calendar.MONTH,
			Calendar.DATE,
			Calendar.HOUR,
			Calendar.MINUTE,
			Calendar.SECOND};
	String[]	fileFieldFormatters	=	{"yyyy", 
			"MM",
			"dd",
			"HH",
			"mm",
			"ss"};
	
	private	NewPathFragment	fileFragment	=	new XRTFileFragment();

	
	Calendar	now			=	new GregorianCalendar();
	DateFormat	formatter	=	new SimpleDateFormat();
	

	public void testDateToFragment()
	{
		performDateToFragmentTest(root, rootString);
		performDateToFragmentTest(yearFragment, yearPattern);
		performDateToFragmentTest(monthFragment, monthPattern);
		performDateToFragmentTest(dayFragment, dayPattern);
		performDateToFragmentTest(timeFragment, timePattern);		
		performDateToFragmentTest(fileFragment, filePattern);		
	}

	public void testFragmentToDate()
	{
		performFragmentToDateTest(root, rootString);
		performFragmentToDateTest(yearFragment, yearPattern);
		performFragmentToDateTest(monthFragment, monthPattern);
		performFragmentToDateTest(dayFragment, dayPattern);
		performFragmentToDateTest(timeFragment, timePattern);		
		performFragmentToDateTest(fileFragment, filePattern);		
	}

	public void testFragmentToDateDate()
	{
		performFragmentToDateTest(root, rootString, new Date());
		performFragmentToDateTest(yearFragment, yearPattern, new Date());
		performFragmentToDateTest(monthFragment, monthPattern, new Date());
		performFragmentToDateTest(dayFragment, dayPattern, new Date());
		performFragmentToDateTest(timeFragment, timePattern, new Date());		
		performFragmentToDateTest(fileFragment, filePattern, new Date());		
	}

	
	private void performDateToFragmentTest(NewPathFragment f, String pattern)
	{
		if(f.isFixed())
		{
			try
			{
				System.out.println(f.dateToFragment(now.getTime()) + " <--> " + pattern);
				assertTrue(f.dateToFragment(now.getTime()).equals(pattern));
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(false);
			}
		}
		else
		{
			formatter	=	new SimpleDateFormat(pattern);
			try
			{
				String	sValue	=	f.dateToFragment(now.getTime());
				System.out.println(sValue + " <--> " + formatter.format(now.getTime()));
				/*
				 * If the strings contains undefined values, it will not be identical.
				 */
				if(!sValue.contains("UNDEFINED_"))
					assertTrue(f.dateToFragment(now.getTime()).equals(formatter.format(now.getTime())));
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(false);
			}
		}
	}
	
	/**
	 * Perform fragment to date test.
	 *
	 * @param f the f
	 * @param pattern the pattern
	 */
	private void performFragmentToDateTest(NewPathFragment f, String pattern)
	{
		String fragment		=	null;

		if(f.isFixed())
		{
			try
			{
				root.fragmentToDate(rootString);
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(true);
			}
		}
		else
		{
			formatter	=	new SimpleDateFormat(pattern);
			fragment	=	formatter.format(now.getTime());
			try
			{				
				System.out.println(formatter.format(f.fragmentToDate(fragment)) + " <--> " + formatter.format(now.getTime()));
				assertTrue(formatter.format(f.fragmentToDate(fragment)).equals(formatter.format(now.getTime())));
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(false);
			}
		}
	}

	private void performFragmentToDateTest(NewPathFragment f, String pattern, Date d)
	{
		String fragment		=	null;

		if(f.isFixed())
		{
			try
			{
				root.fragmentToDate(rootString);
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(true);
			}
		}
		else
		{
			formatter	=	new SimpleDateFormat(pattern);
			fragment	=	formatter.format(now.getTime());
			try
			{				
				System.out.println(formatter.format(f.fragmentToDate(fragment, d)) + " <--> " + formatter.format(now.getTime()));
				assertTrue(formatter.format(f.fragmentToDate(fragment, d)).equals(formatter.format(now.getTime())));
			} 
			catch (NewPathFragmentException e)
			{
				assertTrue(false);
			}
		}
	}

}
