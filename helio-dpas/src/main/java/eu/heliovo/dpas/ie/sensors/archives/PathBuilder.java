package eu.heliovo.dpas.ie.sensors.archives;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

public class PathBuilder
{
	int		maxDepth										=	0;
	private	LinkedList<PathFragment>	representation		=	new LinkedList<PathFragment>();
	
	public	void	add(PathFragment fragment)
	{
		representation.add(fragment);
		/*
		 * If it is not a separator, increment the depth
		 */
		if(!fragment.isSeparator())
			maxDepth++;
	}
	
	public	String	dateToPath(Date d)
	{
		String	pattern	=	new String();
		Iterator<PathFragment>	iter	=	representation.iterator();
		while(iter.hasNext())			
			pattern	+=	iter.next().getExpression(d);

		return pattern;
	}

	public	Date	pathToDate(String url) throws PathBuilderException
	{
		int				currDepth		=	0;
		boolean			completed		=	false;
		PathFragment	currFragment	=	null;
		String			currValue		=	null;
		int				currPosition	=	-1;
		int				currStartAt		=	-1;
		int				currStopAt		=	-1;
		/*
		 * This is the calendar that will be updated with the fields.
		 */
		Calendar		currCalendar	=	new GregorianCalendar();
		currCalendar.setTimeInMillis(0);
		/*
		 * Until the entire url is being evaluated
		 */
		while(!completed)
		{
			/*
			 * Get the element of this depth
			 */
			currFragment	=	getPathFragmentAtDepth(currDepth);
			/*
			 * If the fragment is fixed
			 */
			if(currFragment.isFixed())
			{
				/*
				 * If the fixed fragment is not present, there is 
				 * something seriously wrong !
				 */
				if(!url.contains(currFragment.getExpression()))
					throw new PathBuilderException();
				
				currPosition	=	url.indexOf(currFragment.getExpression());
				currPosition	+=	currFragment.getExpression().length();
				
			}
			/*
			 * The fragment is not fixed, it must be evaluated ! 
			 */
			else
			{
				/*
				 * Get the pattern fragment
				 */
				currStartAt		=	url.indexOf("/", currPosition);
				currStopAt		=	url.indexOf("/", currStartAt + 1);
				
				currValue		=	url.substring(currStartAt+1, currStopAt);
				/*
				 * Now evaluate the bloody thing !!! 
				 */
				/*
				 * Get the date fields that are present
				 * 
				 * If it is just one field, it's easy.
				 */
				if(currFragment.dateFields.length == 1)
				{
					/*
					 * This disgusting thing is necessary because 
					 * months in calendar start in 0
					 */
					if(currFragment.dateFields[0] == Calendar.MONTH)
						currCalendar.set(currFragment.dateFields[0], 
								Integer.valueOf(currValue)-1);
					else	
						currCalendar.set(currFragment.dateFields[0], 
							Integer.valueOf(currValue));					
				}
				/*
				 * If it is more than just one field, it's a bit more complicated
				 * 
				 * TODO : This is a hack for this specific archive ! 
				 */
				else
				{
					for(int currIndex = 0; currIndex < currFragment.dateFields.length; currIndex++)
					{
//						System.out.println(currValue);
//						System.out.println(currFragment.getExpression());

						currCalendar.set(currFragment.dateFields[0], 
								Integer.valueOf(currValue.substring(1, 3)));

						currCalendar.set(currFragment.dateFields[1], 
								Integer.valueOf(currValue.substring(3, 5)));

						
//						currFragment.dateFields[currIndex];
					}
					System.out.println(currCalendar.getTime());
				}
				/*
				 * Update the current position in the string 
				 */
				currPosition	=	currStopAt;
			}
			currDepth++;
			if(currDepth >= maxDepth)
				completed	=	true;
		}
		
		return currCalendar.getTime();
	}

	private String getUrlFragmentAtDepth(String url, int depth)
	{
		int 	localDepth	=	0;
		
		
		
		
		System.out.println(getPathFragmentAtDepth(localDepth));
		
		return null;
	}

	private PathFragment getPathFragmentAtDepth(int depth)
	{
		int 	localDepth	=	0;
		
		Iterator<PathFragment>	iter			=	representation.iterator();
		PathFragment			currFragment	=	null;
		
		while(iter.hasNext() && (localDepth <= depth))
		{
			currFragment	=	iter.next();
			if(!currFragment.isSeparator())
				localDepth++;			
		}
		return currFragment;
	}

	public	String	dateToPath(Date d, int depth)
	{
		int				localDepth		=	0;
		String			pattern			=	new String();
		PathFragment	currFragment	=	null;
		
		Iterator<PathFragment>	iter	=	representation.iterator();
		while(iter.hasNext() && (localDepth <= depth))
		{
			currFragment	=	iter.next();
			pattern	+=	currFragment.getExpression(d);
			if(!currFragment.isSeparator())
				localDepth++;			
		}
		return pattern;
	}

	String	getPattern()
	{
		String	pattern					=	new String();
		Iterator<PathFragment>	iter	=	representation.iterator();
		while(iter.hasNext())			
			pattern	+=	iter.next().getExpression();

		return pattern;
	}

	String	getPattern(int depth)
	{
		int				localDepth		=	0;
		boolean			maxDepthReached	=	false;
		String			pattern			=	new String();
		PathFragment	currStep		=	null;
		
		Iterator<PathFragment>	iter	=	representation.iterator();
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
		
		PathFragment	currStep		=	getElementAt(depth);
		String			currExpression	=	currStep.expression;

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

	/**
	 * Gets the max depth.
	 *
	 * @return the max depth
	 */
	public int getMaxDepth()
	{
		return maxDepth;
	}

	/**
	 * Gets the element at.
	 *
	 * @param depth the depth
	 * @return the element at
	 */
	private	PathFragment	getElementAt(int depth)
	{
		int				localDepth		=	0;
		boolean			maxDepthReached	=	false;
		PathFragment	currStep		=	null;
		boolean			found			=	false;
		
		Iterator<PathFragment>	iter	=	representation.iterator();
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
