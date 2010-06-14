package eu.heliovo.dpas.ie.sensors.archives;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import eu.heliovo.dpas.ch.ResultItem;

public class OldPathBuilder
{
	private static final long 		serialVersionUID 	= -3108986316277691582L;
	int		maxDepth									=	0;
	private	LinkedList<PathStep>	representation		=	new LinkedList<PathStep>();
	
	public	void	add(PathStep step)
	{
		representation.add(step);
		/*
		 * If it is not a separator, increment the depth
		 */
		if(!step.isSeparator())
			maxDepth++;
//		/*
//		 * This is for debug only
//		 */
//		System.out.println(getPattern());
//		System.out.println(getPattern(3));
//		System.out.println(getPathFor(new Date()));
//		System.out.println(getPathFor(new Date(), 3));
	}
	
	String	getPathFor(Date d)
	{
		String	pattern	=	new String();
		Iterator<PathStep>	iter	=	representation.iterator();
		while(iter.hasNext())			
			pattern	+=	iter.next().getExpression(d);

		return pattern;
	}

	String	getPathFor(Date d, int depth)
	{
		int			localDepth		=	0;
		boolean		maxDepthReached	=	false;
		String		pattern			=	new String();
		PathStep	currStep		=	null;
		
		Iterator<PathStep>	iter	=	representation.iterator();
		while(iter.hasNext() && !maxDepthReached)
		{
			currStep	=	iter.next();
			pattern	+=	currStep.getExpression(d);
			if(!currStep.isSeparator())
				localDepth++;
			
			if(localDepth >= depth)
				maxDepthReached	=	true;
		}
		return pattern;
	}

	String	getPattern()
	{
		String	pattern	=	new String();
		Iterator<PathStep>	iter	=	representation.iterator();
		while(iter.hasNext())			
			pattern	+=	iter.next().getExpression();

		return pattern;
	}

	String	getPattern(int depth)
	{
		int			localDepth		=	0;
		boolean		maxDepthReached	=	false;
		String		pattern			=	new String();
		PathStep	currStep		=	null;
		
		Iterator<PathStep>	iter	=	representation.iterator();
		while(iter.hasNext() && !maxDepthReached)
		{
			currStep	=	iter.next();
			pattern	+=	currStep.getExpression();
			if(!currStep.isSeparator())
				localDepth++;
			
			if(localDepth >= depth)
				maxDepthReached	=	true;
		}
		return pattern;
	}

	public	Date	evaluateElementAt(Date d, int depth) throws PathBuilderException
	{
		Calendar	result			=	new GregorianCalendar();
		result.setTimeInMillis(0);
		
		Calendar	date			=	new GregorianCalendar();
		date.setTime(d);
		
		PathStep	currStep		=	getElementAt(depth);
		String		currExpression	=	currStep.expression;

		/*
		 * The format is in years, evaluate the years
		 */
		if(currExpression.equals("yyyy"))
		{			
			System.out.println(result.getTime());
			result.set(Calendar.YEAR, date.get(Calendar.YEAR));
			System.out.println(result.getTime());
		}
		else
		{
			throw new PathBuilderException();
		}
		return result.getTime();
	}

	public	Date	evaluateUrlAt(int depth) throws PathBuilderException
	{
//		Calendar	result			=	new GregorianCalendar();
//		result.setTimeInMillis(0);
//			
//		int			localDepth		=	0;
//		boolean		maxDepthReached	=	false;
//		PathStep	currStep		=	null;
//		
//		Iterator<PathStep>	iter	=	representation.iterator();
//		while(iter.hasNext() && !maxDepthReached)
//		{
//			currStep	=	iter.next();
//			if(!currStep.isSeparator())
//				localDepth++;
//
//			if(!currStep.isFixed())
//			{			
//				System.out.println(result.getTime());
//				result.set(Calendar.YEAR, date.get(Calendar.YEAR));
//				System.out.println(result.getTime());
//			}
//
//			
//			if(localDepth >= depth)
//				maxDepthReached	=	true;
//		}
//		return pattern;
//
//		
//		
//		PathStep	currStep		=	getElementAt(depth);
//		String		currExpression	=	currStep.expression;
//
//		/*
//		 * The format is in years, evaluate the years
//		 */
//		if(currExpression.equals("yyyy"))
//		{			
//			System.out.println(result.getTime());
//			result.set(Calendar.YEAR, date.get(Calendar.YEAR));
//			System.out.println(result.getTime());
//		}
//		else
//		{
//			throw new PathBuilderException();
//		}
//		return result.getTime();
		return null;
	}

	private	PathStep	getElementAt(int depth)
	{
		int			localDepth		=	0;
		boolean		maxDepthReached	=	false;
		PathStep	currStep		=	null;
		boolean		found			=	false;
		
		Iterator<PathStep>	iter	=	representation.iterator();
		while(iter.hasNext() && !maxDepthReached && !found)
		{
			currStep	=	iter.next();
			if(localDepth == depth)
				found = true;
			
			if(!currStep.isSeparator())
				localDepth++;
			
			if(localDepth >= depth)
				maxDepthReached	=	true;
		}
		return currStep;
	}
}
