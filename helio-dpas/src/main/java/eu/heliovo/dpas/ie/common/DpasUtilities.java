package eu.heliovo.dpas.ie.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DpasUtilities 
{
	DateFormat 			helioFormatter 	= 	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public DpasUtilities() 
	{
		super();
		helioFormatter.setLenient(false);
	}

	public	String	dateToHELIOTime(Date d)
	{
		helioFormatter.setLenient(false);
		return helioFormatter.format(d);
	}

	public	String	calendarToHELIOTime(Calendar c)
	{
		helioFormatter.setLenient(false);
		return helioFormatter.format(c.getTime());
	}

	public	Date	HELIOTimeToDate(String s) throws ParseException
	{
		helioFormatter.setLenient(false);
		return helioFormatter.parse(s);
	}
	
	public	Calendar	HELIOTimeToCalendar(String s) throws ParseException
	{
		Calendar 	c	=	Calendar.getInstance();
		c.setTime(HELIOTimeToDate(s));
	    c.set(Calendar.MILLISECOND,0);
	    c.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    return c;
	}
	
	public Date createRandomDateBetween(Date startDate, Date stopDate) throws ParseException, DpasUtilitiesException 
	{
		/*
		 * Check that startDate is before StopDate
		 */
		if(startDate.after(stopDate))
			throw new DpasUtilitiesException();
		
		Date result	=	new Date();
		result.setTime(RandomUtilities.createRandomLong(startDate.getTime(),
				stopDate.getTime()));
		/*
		 * Check that randomDate is between startDate and stopDate
		 */
		if(!(result.after(startDate) && result.before(stopDate)))
			throw new DpasUtilitiesException();
			
		
		return result;
	}

	public String createRandomDateBetween(String startDate, String stopDate) throws ParseException, DpasUtilitiesException 
	{
		return this.dateToHELIOTime(createRandomDateBetween(
				HELIOTimeToDate(startDate),
				HELIOTimeToDate(stopDate)));
	}
}
