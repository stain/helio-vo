package eu.heliovo.dpas.ie.providers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import eu.heliovo.dpas.ie.sensors.archives.NewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragmentException;


public class XRTTimeFragment implements NewPathFragment
{
	String	formatter	=	"'H'HHmm";

	@Override
	public String dateToFragment(Date d) throws NewPathFragmentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date fragmentToDate(String fragment) throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);

//		System.out.println("Hour  	= " + fragment.substring(12, 14));
		date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fragment.substring(1, 3)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(14, 16));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(3, 5)));
//		System.out.println(date.getTime());
		
		return date.getTime();	
		}

	@Override
	public Date fragmentToDate(String fragment, Date d)
			throws NewPathFragmentException
	{
		Calendar date = new GregorianCalendar();
		date.setTime(d);

//		System.out.println("* Hour  	= " + fragment.substring(1, 3));
//		System.out.println(date.getTime());
		date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(fragment.substring(1, 3)));
//		System.out.println(date.getTime());

//		System.out.println("Minutes = " + fragment.substring(3, 5));
		date.set(Calendar.MINUTE, Integer.valueOf(fragment.substring(3, 5)));
//		System.out.println(date.getTime());

		return date.getTime();	
	}

	@Override
	public boolean isFixed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numberOfInternalSeparators(char separator)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getExpression()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
