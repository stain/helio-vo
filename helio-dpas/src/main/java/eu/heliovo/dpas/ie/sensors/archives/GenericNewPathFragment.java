/*
 * 
 */
package eu.heliovo.dpas.ie.sensors.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericNewPathFragment.
 */
public class GenericNewPathFragment implements NewPathFragment
{
	/*
	 * http://www.sdc.uio.no/vol/fits/xrt/level0/2007/07/07/H0400/XRT20070707_040030.3.fits.gz
	 */
	/*
	 * The expression of the fragment, it can be:
	 * 
	 * A fixed expression - fields is null - fixed is true
	 * 
	 * A formatter that expresses a date: - dateFields contains all the date
	 * fields that comprise the expression - dateFieldFormatters contains all
	 * the date formatters that comprise the expression - fixed is false
	 */
	/** The expression. */
	private String expression = null;
	/*
	 * The date fields that define the expression
	 */
	/** The date fields. */
	private int[] dateFields = null;
	/*
	 * The converters for the date fields
	 */
	/** The date field formatters. */
	private String[] dateFieldFormatters = null;
	/*
	 * Boolean is true if the fragment is fixed
	 */
	/** The fixed. */
	boolean fixed = false;
	
	
	/**
	 * Instantiates a new generic new path fragment.
	 *
	 * @param expression the expression
	 * @param fixed the fixed
	 * @param dateFields the date fields
	 * @param dateFieldFormatters the date field formatters
	 */
	public GenericNewPathFragment(String expression, boolean fixed, int[] dateFields,
			String[] dateFieldFormatters)
	{
		super();
		this.expression = expression;
		this.dateFields = dateFields;
		this.dateFieldFormatters = dateFieldFormatters;
		this.fixed = fixed;
	}

	/**
	 * Instantiates a new generic new path fragment.
	 *
	 * @param expression the expression
	 * @param fixed the fixed
	 * @param dateField the date field
	 * @param dateFieldFormatter the date field formatter
	 */
	public GenericNewPathFragment(String expression, boolean fixed, int dateField,
			String dateFieldFormatter)
	{
		super();
		this.expression = expression;
		this.dateFields = new int[1];
		this.dateFields[0] = dateField;
		this.dateFieldFormatters = new String[1];
		this.dateFieldFormatters[0] = dateFieldFormatter;
		this.fixed = fixed;
	}

	@ Override
	public String dateToFragment(Date d)
	{
		if (fixed)
			return expression;
		else
		{
			DateFormat formatter = new SimpleDateFormat(expression);
			return formatter.format(d);
		}
	}

	public Date fragmentToDate(String fragment) throws NewPathFragmentException
	{
		if (fixed)
			throw new NewPathFragmentException();
		/*
		 * Initialize a calendar at the start
		 */
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);
		/*
		 * If it is a simple fragment (only one field).
		 * 
		 * The the fragment contains only one formatter.
		 */
		if (dateFields.length == 1)
		{
			/*
			 * This is a necessary hack because in Calendar, months start from 0
			 */
			if(dateFields[0] == Calendar.MONTH)
				date.set(dateFields[0], Integer.valueOf(fragment)-1);
			else
				date.set(dateFields[0], Integer.valueOf(fragment));
		} 
		else if (dateFields.length > 1)
		{
			int startAt			=	-1;
			int stopAt			=	-1;
			int	controlChars	=	0;
			
			for (int index = 0; index < dateFields.length; index++)
			{
//				System.out.println("Analyzing " + dateFields[index] + " , "
//						+ dateFieldFormatters[index] + " in " + fragment + " <--> " + expression);
//				System.out.println("Looking for " + dateFieldFormatters[index] + " in " + expression);

				/*
				 *	Checking the amount 
				 * of control characters (') in the fragment string.
				 * 
				 * Example 'H'HHmm, there are two control characters
				 */
				startAt	=	expression.indexOf(dateFieldFormatters[index]);
				controlChars	=	findNumberOfControlCharsIn(expression.substring(0, startAt));								
				startAt	-=	controlChars;
				stopAt	=	startAt + dateFieldFormatters[index].length();
							
//				System.out.println(expression.substring(startAt, stopAt));
//				System.out.println(startAt + "," + stopAt);			
//				System.out.println(expression.substring(startAt, stopAt));
//				System.out.println(fragment.substring(startAt, stopAt));
				
				date.set(dateFields[index], Integer.valueOf(fragment.substring(startAt, stopAt)));
//				System.out.println(date.getTime());				
			}
		}
		return date.getTime();
	}

	public Date fragmentToDate(String fragment, Date d) throws NewPathFragmentException
	{
		boolean	valid	=	true;
		
		if (fixed)
			throw new NewPathFragmentException();
		/*
		 * Initialize a calendar at the start
		 */
		Calendar date = new GregorianCalendar();
		date.setTime(d);
		/*
		 * If it is a simple fragment (only one field).
		 * 
		 * The the fragment contains only one formatter.
		 */
//		System.out.println("Analyzing " + fragment + " fragment");
		/*
		 * Check if it is a number
		 */
		try
		{
			Integer.valueOf(fragment);
		} 
		catch (NumberFormatException e)
		{
			throw new NewPathFragmentException();
		}
			
		if(valid)
		{
		if (dateFields.length == 1)
		{
//			System.out.println("*** Date is " + date.getTime());
			/*
			 * This is a necessary hack because in Calendar, months start from 0
			 */
			if(dateFields[0] == Calendar.MONTH)
				date.set(dateFields[0], Integer.valueOf(fragment)-1);
			else
				date.set(dateFields[0], Integer.valueOf(fragment));
			
			
//			System.out.println("*** Now Date is " + date.getTime());

		} 
		else if (dateFields.length > 1)
		{
			int startAt			=	-1;
			int stopAt			=	-1;
			int	controlChars	=	0;
			
			for (int index = 0; index < dateFields.length; index++)
			{
//				System.out.println("Analyzing " + dateFields[index] + " , "
//						+ dateFieldFormatters[index] + " in " + fragment + " <--> " + expression);
//				System.out.println("Looking for " + dateFieldFormatters[index] + " in " + expression);

				/*
				 *	Checking the amount 
				 * of control characters (') in the fragment string.
				 * 
				 * Example 'H'HHmm, there are two control characters
				 */
				startAt	=	expression.indexOf(dateFieldFormatters[index]);
				controlChars	=	findNumberOfControlCharsIn(expression.substring(0, startAt));								
				startAt	-=	controlChars;
				stopAt	=	startAt + dateFieldFormatters[index].length();
							
//				System.out.println(expression.substring(startAt, stopAt));
//				System.out.println(startAt + "," + stopAt);			
//				System.out.println(expression.substring(startAt, stopAt));
//				System.out.println(fragment.substring(startAt, stopAt));
				
				date.set(dateFields[index], Integer.valueOf(fragment.substring(startAt, stopAt)));
//				System.out.println(date.getTime());				
			}
		}
		}
		return date.getTime();
	}

	/*
	 * Setters and Getters
	 */
	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public String getExpression()
	{
		return expression;
	}

	/**
	 * Sets the expression.
	 *
	 * @param expression the new expression
	 */
	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	/* (non-Javadoc)
	 * @see sensors.archives.NewPathFragment#isFixed()
	 */
	public boolean isFixed()
	{
		return fixed;
	}

	/**
	 * Sets the fixed.
	 *
	 * @param fixed the new fixed
	 */
	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return expression;
	}
	
	/**
	 * Find number of control chars in.
	 *
	 * @param s the s
	 * @return the int
	 */
	private	int	findNumberOfControlCharsIn(String s)
	{
		return findNumberOfCharsIn('\'', s);
	}

	/**
	 * Find number of chars in.
	 *
	 * @param s the s
	 * @return the int
	 */
	private	int	findNumberOfCharsIn(char c, String s)
	{
		int occurs = 0;

		for(int i = 0; i < s.length(); i++) 
		{
			char next = s.charAt(i);
			if(next == c) 
				occurs++;
		}
		return occurs;
	}

	@Override
	public int numberOfInternalSeparators(char separator)
	{
		return findNumberOfCharsIn(separator, expression);
	}
}
