package eu.heliovo.dpas.ie.sensors.archives.tests;

import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;
import eu.heliovo.dpas.ie.sensors.archives.RhessiFileFragment;


public class RhessiFileFragmentTest extends TestCase
{	
	public void testFragmentToDate()
	{
		String			sFragment	=	"hsi_20000619_191200_000.fits";
		RhessiFileFragment	fragment	=	new RhessiFileFragment();

		try
		{
			System.out.println(sFragment + " --> " + fragment.fragmentToDate(sFragment));
		} 
		catch (NewPathFragmentException e)
		{
			assertTrue(false);
		}
	}

	public void testDateToFragment()
	{
		RhessiFileFragment	fragment	=	new RhessiFileFragment();
		Date	now	=	new Date();
		
		try
		{
			System.out.println(now + " --> " + fragment.dateToFragment(now));
		} 
		catch (NewPathFragmentException e)
		{
			e.printStackTrace();
		}
	}
}
