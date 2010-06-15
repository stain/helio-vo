/*
 * 
 */
package eu.heliovo.dpas.ie.sensors.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Phoenix2FileFragment implements NewPathFragment
{
	String	formatter	=	"'_UNDEFINED_OF_X_'HHmmss'_UNDEFINED_OF_X_.fit.gz'";

	@Override
	public String dateToFragment(Date d) throws NewPathFragmentException
	{
		Calendar	c	=	new GregorianCalendar();
		c.setTime(d);
		DateFormat f	=	null;
		/*
		 * Check if the year is after 1978
		 */
		try
		{
			f = new SimpleDateFormat(formatter);
		} 
		catch (Exception e)
		{
			throw new NewPathFragmentException();
		}
		return f.format(d);			
	}

	@Override
	public Date fragmentToDate(String fragment) throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);
		/*
		 * Get the ending of the file name
		 * 
		 * http://helene.ethz.ch/rapp/observations/1991/07/03/910703123533i.fit.gz, or,
		 * http://helene.ethz.ch/rapp/observations/1999/02/01/19990201083000p.fit.gz, or,
		 * http://helene.ethz.ch/rapp/observations/2008/03/03/BLEN7M_20080303_073000i.fit.gz.
		 * http://helene.ethz.ch/rapp/observations/2008/03/03/BLEN7M_20080303_073000i._otherstuff_.fit.gz.
		 * http://helene.ethz.ch/rapp/observations/2005/01/25/20050125154500p.fit.gz
		 */		
		/*
		 * Find the first occurrence of "."
		 */
		
		int	stopAt	=	fragment.indexOf(".");
//		System.out.println("Hour  	= " + fragment.substring(stopAt-3, stopAt-1));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(stopAt-3, stopAt-1)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(stopAt-5, stopAt-3));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(stopAt-5, stopAt-3)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(stopAt-7, stopAt-5));
		date.set(Calendar.HOUR, Integer.valueOf(fragment.substring(stopAt-7, stopAt-5)));
//		System.out.println(date.getTime());


		
		return date.getTime();
	}

	@Override
	public Date fragmentToDate(String fragment, Date d)
			throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTime(d);
		/*
		 * Get the ending of the file name
		 * 
		 * http://helene.ethz.ch/rapp/observations/1991/07/03/910703123533i.fit.gz, or,
		 * http://helene.ethz.ch/rapp/observations/1999/02/01/19990201083000p.fit.gz, or,
		 * http://helene.ethz.ch/rapp/observations/2008/03/03/BLEN7M_20080303_073000i.fit.gz.
		 * http://helene.ethz.ch/rapp/observations/2008/03/03/BLEN7M_20080303_073000i._otherstuff_.fit.gz.
		 * 
		 */		
		
		int	stopAt	=	fragment.indexOf(".");
//		System.out.println("Hour  	= " + fragment.substring(stopAt-3, stopAt-1));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(stopAt-3, stopAt-1)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(stopAt-5, stopAt-3));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(stopAt-5, stopAt-3)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(stopAt-7, stopAt-5));
		date.set(Calendar.HOUR, Integer.valueOf(fragment.substring(stopAt-7, stopAt-5)));
//		System.out.println(date.getTime());
		
		return date.getTime();
	}

	@Override
	public boolean isFixed()
	{
		return false;
	}

	@Override
	public int numberOfInternalSeparators(char separator)
	{
		return 0;
	}
}
