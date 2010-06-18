package eu.heliovo.dpas.ie.archives;

import java.util.Date;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;
import eu.heliovo.dpas.ie.sensors.archives.XRTFileFragment;


public class XRTFileFragmentTest extends TestCase
{	
	public void testFragmentToDate()
	{
		String			sFragment	=	"XRT20070707_040030.3.fits.gz";
		XRTFileFragment	fragment	=	new XRTFileFragment();
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
		XRTFileFragment	fragment	=	new XRTFileFragment();
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
