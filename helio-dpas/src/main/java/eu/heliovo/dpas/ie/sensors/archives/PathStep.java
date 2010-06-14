package eu.heliovo.dpas.ie.sensors.archives;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathStep
{
	String		expression		=	null;
	boolean		fixed			=	false;
	boolean		separator		=	false;
    DateFormat 	formatter 		= 	null;
	
	
	public PathStep(String expression, boolean fixed, boolean separator)
	{
		super();
		this.expression = expression;
		this.fixed 		= fixed;
		this.separator 	= separator;
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
