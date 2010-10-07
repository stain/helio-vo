package eu.heliovo.dpas.ie.services.directory.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import eu.heliovo.dpas.ie.services.directory.archives.NewPathFragment;
import eu.heliovo.dpas.ie.services.directory.dao.exception.NewPathFragmentException;



public class SOT_SPFileFragment implements NewPathFragment
{
	String	formatter	=	"'XRT'yyyyMMdd'_'HHmmss'.UNDEFINED_OF_X_.fits.gz'";
	/*
	 * Example of the file: 
	 * SP4D20091114_120312.4.fits.gz
	 */
	@Override
	public Date fragmentToDate(String fragment) throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);

		int startAt	=	fragment.indexOf("_");
////		System.out.println("Year    = " + fragment.substring(3, 7));
//		date.set(Calendar.YEAR, Integer.valueOf(fragment.substring(3, 7)));
////		System.out.println(date.getTime());
//
////		System.out.println("Month   = " + fragment.substring(7, 9));
//		date.set(Calendar.MONTH, Integer.valueOf(fragment.substring(7, 9))-1);
////		System.out.println(date.getTime());
//
////		System.out.println("Day     = " + fragment.substring(9, 11));
//		date.set(Calendar.DATE, Integer.valueOf(fragment.substring(9, 11)));
////		System.out.println(date.getTime());

//		System.out.println("Hour  	= " + fragment.substring(12, 14));
		date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fragment.substring(startAt+1, startAt+3)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(14, 16));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(startAt+3, startAt+5)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(16, 18));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(startAt+5, startAt+7)));
//		System.out.println(date.getTime());
		
		return date.getTime();
	}

	@Override
	public Date fragmentToDate(String fragment, Date d)
			throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTime(d);

////		System.out.println("Year    = " + fragment.substring(3, 7));
//		date.set(Calendar.YEAR, Integer.valueOf(fragment.substring(3, 7)));
////		System.out.println(date.getTime());
//
////		System.out.println("Month   = " + fragment.substring(7, 9));
//		date.set(Calendar.MONTH, Integer.valueOf(fragment.substring(7, 9))-1);
////		System.out.println(date.getTime());
//
////		System.out.println("Day     = " + fragment.substring(9, 11));
//		date.set(Calendar.DATE, Integer.valueOf(fragment.substring(9, 11)));
////		System.out.println(date.getTime());

		int startAt	=	fragment.indexOf("_");

//		System.out.println("Hour  	= " + fragment.substring(12, 14));
		date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fragment.substring(startAt+1, startAt+3)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(14, 16));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(startAt+3, startAt+5)));
//		System.out.println(date.getTime());

//		System.out.println("Seconds = " + fragment.substring(16, 18));
		date.set(Calendar.SECOND, Integer.valueOf(fragment.substring(startAt+5, startAt+7)));
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

	@Override
	public String getExpression()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
