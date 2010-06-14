package eu.heliovo.dpas.ie.sensors.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class XRTFileFragment implements NewPathFragment
{
	String	formatter	=	"'XRT'yyyyMMdd'_'HHmmss'.UNDEFINED_OF_X_.fits.gz'";
	/*
	 * Example of the file: 
	 * XRT20070707_040030.3.fits.gz
	 */
	@Override
	public Date fragmentToDate(String fragment) throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);

//		System.out.println("Year    = " + fragment.substring(3, 7));
		date.set(Calendar.YEAR, Integer.valueOf(fragment.substring(3, 7)));
//		System.out.println(date.getTime());

//		System.out.println("Month   = " + fragment.substring(7, 9));
		date.set(Calendar.MONTH, Integer.valueOf(fragment.substring(7, 9))-1);
//		System.out.println(date.getTime());

//		System.out.println("Day     = " + fragment.substring(9, 11));
		date.set(Calendar.DATE, Integer.valueOf(fragment.substring(9, 11)));
//		System.out.println(date.getTime());

//		System.out.println("Hour  	= " + fragment.substring(12, 14));
		date.set(Calendar.HOUR, Integer.valueOf(fragment.substring(12, 14)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(14, 16));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(14, 16)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(16, 18));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(16, 18)));
//		System.out.println(date.getTime());
		
		return date.getTime();
	}

	@Override
	public Date fragmentToDate(String fragment, Date d)
			throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTime(d);

//		System.out.println("Year    = " + fragment.substring(3, 7));
		date.set(Calendar.YEAR, Integer.valueOf(fragment.substring(3, 7)));
//		System.out.println(date.getTime());

//		System.out.println("Month   = " + fragment.substring(7, 9));
		date.set(Calendar.MONTH, Integer.valueOf(fragment.substring(7, 9))-1);
//		System.out.println(date.getTime());

//		System.out.println("Day     = " + fragment.substring(9, 11));
		date.set(Calendar.DATE, Integer.valueOf(fragment.substring(9, 11)));
//		System.out.println(date.getTime());

//		System.out.println("Hour  	= " + fragment.substring(12, 14));
		date.set(Calendar.HOUR, Integer.valueOf(fragment.substring(12, 14)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(14, 16));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(14, 16)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(16, 18));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(16, 18)));
//		System.out.println(date.getTime());
	
		return date.getTime();
	}

	@Override
	public String dateToFragment(Date d) throws NewPathFragmentException
	{
		DateFormat f;
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
	public boolean isFixed()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return formatter;
	}

	@Override
	public int numberOfInternalSeparators(char separator)
	{
		return 0;
	}
}
