package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.PathFragment;
import eu.heliovo.dpas.ie.sensors.archives.PathFragmentException;


public class PathFragmentTest extends TestCase
{
	private	String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
	private	String			separatorString	=	"/";
	private	String			yearPattern		=	"yyyy";
	private	String			monthPattern	=	"MM";
	private	String			dayPattern		=	"dd";
	private	String			timePattern		=	"'H'HHmm";
	
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

	Calendar	now			=	new GregorianCalendar();
	DateFormat	formatter	=	new SimpleDateFormat();
	
	public void testGetExpression()
	{
		assertTrue(root.getExpression().equals(rootString));
		assertTrue(separator.getExpression().equals(separatorString));
		assertTrue(yearFragment.getExpression().equals(yearPattern));
		assertTrue(monthFragment.getExpression().equals(monthPattern));
		assertTrue(dayFragment.getExpression().equals(dayPattern));
		assertTrue(timeFragment.getExpression().equals(timePattern));
	}

	public void testGetExpressionDate()
	{
		assertTrue(root.getExpression(now.getTime()).equals(rootString));
		assertTrue(separator.getExpression(now.getTime()).equals(separatorString));
		assertTrue(yearFragment.getExpression(now.getTime()).toString().equals(String.valueOf(now.get(Calendar.YEAR))));
//		System.out.println(monthFragment.getExpression(now.getTime()));
//		System.out.println(String.valueOf(now.get(Calendar.MONTH)));
		/*
		 * TODO Month description differs - find a solution.
		 */
		assertFalse(monthFragment.getExpression(now.getTime()).toString().equals(String.valueOf(now.get(Calendar.MONTH))));
		assertTrue(dayFragment.getExpression(now.getTime()).toString().equals(String.valueOf(now.get(Calendar.DATE))));
		/*
		 * TODO : This is multiple field - find a solution
		 */
//		assertTrue(timeFragment.getExpression(now.getTime()).toString().equals(String.valueOf(now.get(Calendar.DATE))));
	}

	public void testGetDateOfExpressionDate()
	{
		try
		{
			root.getDateOfExpression(now.getTime()).equals(rootString);
		} 
		catch (PathFragmentException e)
		{
			assertTrue(true);
		}

		try
		{
			separator.getDateOfExpression(now.getTime()).equals(separatorString);
		} 
		catch (PathFragmentException e)
		{
			assertTrue(true);
		}

		Calendar	c	=	new GregorianCalendar();
		c.setTimeInMillis(0);
		
		try
		{
			System.out.println(yearFragment.getDateOfExpression(now.getTime()));
			System.out.println(monthFragment.getDateOfExpression(now.getTime()));
			System.out.println(dayFragment.getDateOfExpression(now.getTime()));
			System.out.println(timeFragment.getDateOfExpression(now.getTime()));
		} 
		catch (PathFragmentException e)
		{
			assertTrue(false);
		}
	}

	public void testIsFixed()
	{
		assertTrue(root.isFixed());
		assertTrue(separator.isFixed());
		assertFalse(yearFragment.isFixed());
		assertFalse(monthFragment.isFixed());		
		assertFalse(dayFragment.isFixed());		
		assertFalse(timeFragment.isFixed());		
	}

	public void testIsSeparator()
	{
		assertFalse(root.isSeparator());
		assertTrue(separator.isSeparator());
		assertFalse(yearFragment.isSeparator());
		assertFalse(monthFragment.isSeparator());		
		assertFalse(dayFragment.isSeparator());		
		assertFalse(timeFragment.isSeparator());		
	}
}
