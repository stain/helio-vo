package eu.heliovo.dpas.ie.sensors.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PathFragment
{
	/*
	 * The expression of the fragment, it can be:
	 * 
	 * A fixed expression
	 *  - fields is null
	 *  - fixed is true
	 *  
	 * A formatter that expresses a date: 
	 *  - fields contains all the date fields that comprise the expression
	 *  - fixed is false
	 */
	String		expression		=	null;
	/*
	 * The date fields that define the expression
	 */
    int[]	 	dateFields 		= 	null;
    /*
     * Booelan
     */
	boolean		fixed			=	false;
	boolean		separator		=	false;

	public PathFragment(String expression, int[] dateFields, boolean fixed,
			boolean separator)
	{
		super();
		this.expression = expression;
		this.dateFields = dateFields;
		this.fixed = fixed;
		this.separator = separator;
	}

	public PathFragment(String expression, int dateField, boolean fixed,
			boolean separator)
	{
		super();
		this.expression = expression;
		this.dateFields = new int[1];
		this.dateFields[0]	=	dateField;
		this.fixed = fixed;
		this.separator = separator;
	}

	public String getExpression()
	{
		return expression;
	}

	public String getExpression(Date d)
	{
		if(fixed)
			return expression;
		else
		{
	        DateFormat formatter = new SimpleDateFormat(expression);
	        return formatter.format(d);
		}
	}


	public Date getDateOfExpression(Date d) throws PathFragmentException
	{
		if(fixed)
			throw new PathFragmentException("Cannot get a date of a fixed field");
		else
		{
			Calendar	date	=	new GregorianCalendar();
			date.setTime(d);
			Calendar	res		=	new GregorianCalendar();
			res.setTimeInMillis(0);
			
			for(int n = 0; n < dateFields.length; n++)
			{
				res.set(dateFields[n], date.get(dateFields[n]));
			}			
	        return res.getTime();
		}
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	
	public boolean isFixed()
	{
		return fixed;
	}

	
	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	public boolean isSeparator()
	{
		return separator;
	}

	public void setSeparator(boolean separator)
	{
		this.separator = separator;
	}
	
	@Override
	public String toString()
	{
		return expression;
	}
}
