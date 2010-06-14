package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.common.DpasUtilities;
import eu.heliovo.dpas.ie.sensors.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewArchiveExplorer;
import eu.heliovo.dpas.ie.sensors.archives.NewPath;
import eu.heliovo.dpas.ie.sensors.archives.NewPathException;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;
import eu.heliovo.dpas.ie.sensors.archives.PathBuilderException;
import eu.heliovo.dpas.ie.sensors.archives.Phoenix2FileFragment;
import eu.heliovo.dpas.ie.sensors.archives.RhessiFileFragment;
import eu.heliovo.dpas.ie.sensors.archives.XRTFileFragment;


public class NewArchiveExplorerTest extends TestCase
{
	NewArchiveExplorer	explorer	=	null;
	Date				startDate	=	null;
	Date				stopDate	=	null;
	String				sStartDate	=	"2003-03-28 00:00:00";
	String				sStopDate	=	"2003-04-11 00:00:00";
	DpasUtilities		dpasUtils	=	new DpasUtilities();
	
	public void testArePresentWithin()
	{
		try
		{
			initializeXRT();
		} 
		catch (ParseException e)
		{
			assertTrue(false);
		}
	}

	public void testGetAllWithin()
	{
		LinkedList<String>	results	=	new LinkedList<String>();
		
//		try
//		{
//			initializeXRT();
//		} 
//		catch (ParseException e)
//		{
//			assertTrue(false);
//		}
//		try
//		{
//			System.out.println("===  XRT - START  =======================================================");
//			results	=	explorer.getAllWithin(startDate, stopDate);
//			System.out.println("= There are " + results.size() + " results between + " + startDate + " and " + stopDate + " =");
//			for(int n = 0; n < results.size(); n++)				
//				System.out.println("= results["+ n + "] = " + results.get(n));			
//			System.out.println("===  XRT - STOP   =======================================================");
//		} 
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (PathBuilderException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathFragmentException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		}
//
//		try
//		{
//			initializeRhessi();
//		} 
//		catch (ParseException e)
//		{
//			assertTrue(false);
//		}
//		try
//		{
//			System.out.println("===  RHESSI - START  =======================================================");
//			results	=	explorer.getAllWithin(startDate, stopDate);
//			System.out.println("= There are " + results.size() + " results between + " + startDate + " and " + stopDate + " =");
//			for(int n = 0; n < results.size(); n++)				
//				System.out.println("= results["+ n + "] = " + results.get(n));			
//			System.out.println("===  RHESSI - STOP   =======================================================");
//		} 
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (PathBuilderException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathFragmentException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		}

//		try
//		{
//			initializePhoenix2();
//		} 
//		catch (ParseException e1)
//		{
//			e1.printStackTrace();
//			assertTrue(false);
//		}
//		
//		try
//		{
//			System.out.println("===  PHOENIX2 - START  =======================================================");
//			results	=	explorer.getAllWithin(startDate, stopDate);
//			System.out.println("= There are " + results.size() + " results between + " + startDate + " and " + stopDate + " =");
//			for(int n = 0; n < results.size(); n++)				
//				System.out.println("= results["+ n + "] = " + results.get(n));			
//			System.out.println("===  PHOENIX2 - STOP   =======================================================");
//		} 
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (PathBuilderException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		} 
//		catch (NewPathFragmentException e)
//		{
//			e.printStackTrace();
//			assertTrue(false);
//		}

		try
		{
			initializeRhessi();
		} 
		catch (ParseException e1)
		{
			e1.printStackTrace();
			assertTrue(false);
		}
		
		try
		{
			System.out.println("===  RHESSI - START  =======================================================");
			results	=	explorer.getAllWithin(startDate, stopDate);
			System.out.println("= There are " + results.size() + " results between + " + startDate + " and " + stopDate + " =");
			for(int n = 0; n < results.size(); n++)				
				System.out.println("= results["+ n + "] = " + results.get(n));			
			System.out.println("===  RHESSI - STOP   =======================================================");
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (NewPathException e)
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (PathBuilderException e)
		{
			e.printStackTrace();
			assertTrue(false);
		} 
		catch (NewPathFragmentException e)
		{
			e.printStackTrace();
			assertTrue(false);
		}

	}
	

	private	void initializeXRT() throws ParseException
	{
		/*
		 * Initialiazing the start and stop dates
		 */
		startDate	=	dpasUtils.HELIOTimeToDate(sStartDate);
		stopDate	=	dpasUtils.HELIOTimeToDate(sStopDate);
		/*
		 * Initialiazing the path for the archive explorer
		 */
		NewPath	path	=	new NewPath();
		
		String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";
		String			dayPattern		=	"dd";
		String			timePattern		=	"'H'HHmm";
		String			testUrl			=	"http://www.sdc.uio.no/vol/fits/xrt/level0/2007/07/07/H0700/XRT20070707_070916.3.fits.gz";
		
		NewPathFragment	rootFragment	=	new GenericNewPathFragment(
				rootString,
				true,
				null,
				null);

		NewPathFragment	yearFragment	=	new GenericNewPathFragment(
				yearPattern,
				false,
				Calendar.YEAR,
				yearPattern);

		NewPathFragment	monthFragment	=	new GenericNewPathFragment(
				monthPattern,
				false,
				Calendar.MONTH,
				monthPattern);

		NewPathFragment	dayFragment	=	new GenericNewPathFragment(
				dayPattern,
				false,
				Calendar.DATE,
				dayPattern);

		int[]		timeFields			=	{Calendar.HOUR_OF_DAY, Calendar.MINUTE};
		String[]	timeFieldFormatters	=	{"HH", "mm"};
		
		NewPathFragment	timeFragment	=	new GenericNewPathFragment(
				timePattern,
				false,
				timeFields,
				timeFieldFormatters);
		
		NewPathFragment	fileFragment	=	new XRTFileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(timeFragment);
		path.add(fileFragment);	
		
		explorer	=	new NewArchiveExplorer(path);
	}

	private	void initializeRhessi() throws ParseException
	{
		/*
		 * Initialiazing the start and stop dates
		 */
		startDate	=	dpasUtils.HELIOTimeToDate(sStartDate);
		stopDate	=	dpasUtils.HELIOTimeToDate(sStopDate);
		/*
		 * Initialiazing the path for the archive explorer
		 */
		NewPath	path	=	new NewPath();
		
		String			rootString		=	"http://hesperia.gsfc.nasa.gov/hessidata/";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";
		String			dayPattern		=	"dd";
		String			testUrl			=	"http://hesperia.gsfc.nasa.gov/hessidata/2000/06/19/hsi_20000619_192700_000.fits";
		
		NewPathFragment	rootFragment	=	new GenericNewPathFragment(
				rootString,
				true,
				null,
				null);

		NewPathFragment	yearFragment	=	new GenericNewPathFragment(
				yearPattern,
				false,
				Calendar.YEAR,
				yearPattern);

		NewPathFragment	monthFragment	=	new GenericNewPathFragment(
				monthPattern,
				false,
				Calendar.MONTH,
				monthPattern);

		NewPathFragment	dayFragment	=	new GenericNewPathFragment(
				dayPattern,
				false,
				Calendar.DATE,
				dayPattern);

		NewPathFragment	fileFragment	=	new RhessiFileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(fileFragment);	
		
		explorer	=	new NewArchiveExplorer(path);
	}

	private	void initializePhoenix2() throws ParseException
	{
		/*
		 * Initialiazing the start and stop dates
		 */
		startDate	=	dpasUtils.HELIOTimeToDate(sStartDate);
		stopDate	=	dpasUtils.HELIOTimeToDate(sStopDate);
		/*
		 * Initialiazing the path for the archive explorer
		 */
		NewPath	path	=	new NewPath();
		
		String			rootString		=	"http://helene.ethz.ch/rapp/observations/";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";
		String			dayPattern		=	"dd";
		String			testUrl			=	"http://helene.ethz.ch/rapp/observations/1978/05/29/780529101651i.fit.gz";
		
		NewPathFragment	rootFragment	=	new GenericNewPathFragment(
				rootString,
				true,
				null,
				null);

		NewPathFragment	yearFragment	=	new GenericNewPathFragment(
				yearPattern,
				false,
				Calendar.YEAR,
				yearPattern);

		NewPathFragment	monthFragment	=	new GenericNewPathFragment(
				monthPattern,
				false,
				Calendar.MONTH,
				monthPattern);

		NewPathFragment	dayFragment	=	new GenericNewPathFragment(
				dayPattern,
				false,
				Calendar.DATE,
				dayPattern);

		NewPathFragment	fileFragment	=	new Phoenix2FileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(fileFragment);	
		
		explorer	=	new NewArchiveExplorer(path);
	}
}
