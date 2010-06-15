package eu.heliovo.dpas.ie.sensors.archives;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class NewPath.
 */
public class NewPath
{
	/*
	 * The internal representation of the path
	 */
	/** The representation. */
	private LinkedList<NewPathFragment> representation = new LinkedList<NewPathFragment>();
	/*
	 * The separator character, it usually is '\'
	 */
	/** The separator. */
	private char separator = '/';

	/**
	 * Adds a new fragment to the path.
	 * 
	 * @param fragment
	 *            the path fragment
	 */
	public void add(NewPathFragment fragment)
	{
		representation.add(fragment);
	}
	/**
	 * Date to path.
	 * 
	 * @param d
	 *            the date used to evaluate the path
	 * @return the string that represents the path at that date
	 * 
	 * @throws NewPathException
	 *             the new path exception
	 */
	public String dateToPath(Date d) throws NewPathException
	{
		String path = new String();
		Iterator<NewPathFragment> iter = representation.iterator();
		while (iter.hasNext())
		{
			try
			{
				path += iter.next().dateToFragment(d) + separator;
			} catch (NewPathFragmentException e)
			{
				throw new NewPathException();
			}
		}

		return path;
	}

	/**
	 * Date to path up to a certain depth
	 * 
	 * @param d
	 *            the date used to evaluate the path
	 * @return the string that represents the path at that date
	 * 
	 * @throws NewPathException
	 *             the new path exception
	 */
	public String dateToPath(Date date, int maxDepth) throws NewPathException
	{
		String 	path 		= new String();
		int		currDepth	=	0;
		Iterator<NewPathFragment> iter = representation.iterator();
		while (iter.hasNext() && (currDepth < maxDepth))
		{
			try
			{
				path += iter.next().dateToFragment(date) + separator;
			} 
			catch (NewPathFragmentException e)
			{
				throw new NewPathException();
			}
			currDepth++;
		}

		return path;
	}

	/**
	 * Path to date.
	 * 
	 * @param url
	 *            the url
	 * @return the date
	 * @throws PathBuilderException
	 *             the path builder exception
	 * @throws NewPathFragmentException
	 */
	public Date pathToDate(String url) throws PathBuilderException,
			NewPathFragmentException
	{
		/*
		 * This is the calendar that will be updated with the fields.
		 */
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTimeInMillis(0);
		/*
		 * This is the current url fragment
		 */
		String currUrlFragment = null;
		/*
		 * This is the current depth in the path
		 */
		int currDepth = 0;
		/*
		 * This is the current path fragment
		 */
		NewPathFragment currFragment = null;
		/*
		 * Until the entire url is being evaluated
		 */
		while (currDepth < representation.size())
		{
			if (!representation.get(currDepth).isFixed())
			{
				/*
				 * Get the element of this depth
				 */
				currFragment = representation.get(currDepth);
				currUrlFragment = getUrlFragmentAtDepth(url, currDepth);
//				System.out.println(currDepth + " --> " + currFragment + " -- "
//						+ currUrlFragment);

				currCalendar.setTime(currFragment.fragmentToDate(
						currUrlFragment, currCalendar.getTime()));

//				System.out
//						.println(currDepth + " --> " + currCalendar.getTime());
			}
			currDepth++;
		}
		return currCalendar.getTime();
	}

	/**
	 * Path to date.
	 * 
	 * @param url
	 *            the url
	 * @return the date
	 * @throws PathBuilderException
	 *             the path builder exception
	 * @throws NewPathFragmentException
	 */
	public Date pathToDate(String url, int maxDepth) throws PathBuilderException,
			NewPathFragmentException
	{
		/*
		 * This is the calendar that will be updated with the fields.
		 */
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.setTimeInMillis(0);
		/*
		 * This is the current url fragment
		 */
		String currUrlFragment = null;
		/*
		 * This is the current depth in the path
		 */
		int currDepth = 0;
		/*
		 * This is the current path fragment
		 */
		NewPathFragment currFragment = null;
		/*
		 * Until the entire url is being evaluated
		 */
		while (currDepth < representation.size() && currDepth <= maxDepth)
		{
			if (!representation.get(currDepth).isFixed())
			{
				/*
				 * Get the element of this depth
				 */
				currFragment = representation.get(currDepth);
				currUrlFragment = getUrlFragmentAtDepth(url, currDepth);

//				System.out.println(currDepth + " --> " + currFragment + " -- "
//						+ currUrlFragment);

				
				try
				{
					currCalendar.setTime(currFragment.fragmentToDate(
							currUrlFragment, currCalendar.getTime()));
				} 
				catch (Exception e)
				{
				}

//				System.out.println(currDepth + " --> " + currCalendar.getTime());
			}
			currDepth++;
		}
//		System.out.println(url + " ===> " + currCalendar.getTime());
//		System.out.println(url + " ===> " + currCalendar.get(Calendar.HOUR));
		return currCalendar.getTime();
	}

	/**
	 * Path to earliest date.
	 * 
	 * @param url
	 *            the url
	 * @return the earliest date
	 * @throws PathBuilderException
	 *             the path builder exception
	 * @throws NewPathFragmentException
	 * @throws NewPathException 
	 * @throws NewPathException 
	 */
	public Date pathToEarliestDate(String url, int maxDepth) throws PathBuilderException,
			NewPathFragmentException, NewPathException
	{
		/*
		 * This is the calendar that will be updated with the fields.
		 */
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.set(Calendar.YEAR, 1900);
		currCalendar.set(Calendar.MONTH, Calendar.JANUARY);
		currCalendar.set(Calendar.DAY_OF_MONTH, 1);
		currCalendar.set(Calendar.HOUR, 0);
		currCalendar.set(Calendar.MINUTE, 0);
		currCalendar.set(Calendar.SECOND, 0);
		currCalendar.set(Calendar.MILLISECOND, 0);
		/*
		 * This is the current url fragment
		 */
		String currUrlFragment = null;
		/*
		 * This is the current depth in the path
		 */
		int currDepth = 0;
		/*
		 * This is the current path fragment
		 */
		NewPathFragment currFragment = null;
		/*
		 * Until the entire url is being evaluated
		 */
		while (currDepth < representation.size() && currDepth <= maxDepth)
		{
			if (!representation.get(currDepth).isFixed())
			{
				/*
				 * Get the element of this depth
				 */
				currFragment = representation.get(currDepth);
				currUrlFragment = getUrlFragmentAtDepth(url, currDepth);

//				System.out.println(currDepth + " --> " + currFragment + " -- "
//						+ currUrlFragment);

				
				try
				{
					currCalendar.setTime(currFragment.fragmentToDate(
							currUrlFragment, currCalendar.getTime()));
				} 
				catch (Exception e)
				{
					throw new NewPathException();
				}

//				System.out.println(currDepth + " --> " + currCalendar.getTime());
			}
			currDepth++;
		}
//		System.out.println(url + " ===> " + currCalendar.getTime());
//		System.out.println(url + " ===> " + currCalendar.get(Calendar.HOUR));
		return currCalendar.getTime();
	}

	/**
	 * Path to earliest date.
	 * 
	 * @param url
	 *            the url
	 * @return the earliest date
	 * @throws PathBuilderException
	 *             the path builder exception
	 * @throws NewPathFragmentException
	 */
	public Date pathToLatestDate(String url, int maxDepth) throws PathBuilderException,
			NewPathFragmentException
	{
		/*
		 * This is the calendar that will be updated with the fields.
		 */
		Calendar currCalendar = new GregorianCalendar();
		currCalendar.set(Calendar.YEAR, 2020);
		currCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
		currCalendar.set(Calendar.DAY_OF_MONTH, 30);
		currCalendar.set(Calendar.HOUR, 23);
		currCalendar.set(Calendar.MINUTE, 59);
		currCalendar.set(Calendar.SECOND, 59);
		currCalendar.set(Calendar.MILLISECOND, 999);
		/*
		 * This is the current url fragment
		 */
		String currUrlFragment = null;
		/*
		 * This is the current depth in the path
		 */
		int currDepth = 0;
		/*
		 * This is the current path fragment
		 */
		NewPathFragment currFragment = null;
		/*
		 * Until the entire url is being evaluated
		 */
		while (currDepth < representation.size() && currDepth <= maxDepth)
		{
			if (!representation.get(currDepth).isFixed())
			{
				/*
				 * Get the element of this depth
				 */
				currFragment = representation.get(currDepth);
				currUrlFragment = getUrlFragmentAtDepth(url, currDepth);

//				System.out.println(currDepth + " --> " + currFragment + " -- "
//						+ currUrlFragment);

				try
				{
					currCalendar.setTime(currFragment.fragmentToDate(
							currUrlFragment, currCalendar.getTime()));
				} 
				catch (Exception e)
				{
				}

//				System.out.println(currDepth + " --> " + currCalendar.getTime());
			}
			currDepth++;
		}
//		System.out.println(url + " ===> " + currCalendar.getTime());
//		System.out.println(url + " ===> " + currCalendar.get(Calendar.HOUR));
		return currCalendar.getTime();
	}

	/*
	 * Utilities
	 */

	/**
	 * Gets the url fragment at depth.
	 * 
	 * @param url
	 *            the url
	 * @param depth
	 *            the depth
	 * @return the url fragment at depth
	 */
	private String getUrlFragmentAtDepth(String url, int depth)
	{
		String urlFragment = null;
		int pathIndex = 0;
		int separatorsNumber = 0;
		int urlStartAt = 0;
		int urlStopAt = 0;
		/*
		 * Get the number of separators in the fragment
		 */
		while (pathIndex <= depth)
		{
			separatorsNumber = representation.get(pathIndex)
					.numberOfInternalSeparators(separator);
			// System.out.println(representation.get(pathIndex) + " contains " +
			// separatorsNumber + " of " + separator);

			if (separatorsNumber > 0)
				urlStopAt = skipNumberOfSeparatorsInUrl(url, separatorsNumber);

			urlStopAt = url.indexOf(separator, urlStopAt + 1);
			if (urlStopAt > 0)
			{
				// System.out.println(pathIndex + " --> " +
				// url.substring(urlStartAt, urlStopAt));
				urlFragment = url.substring(urlStartAt, urlStopAt);
			} else
			{
				// System.out.println(pathIndex + " --> " +
				// url.substring(urlStartAt));
				urlFragment = url.substring(urlStartAt);
			}

			urlStartAt = urlStopAt + 1;
			pathIndex++;
		}

		return urlFragment;
	}

	/**
	 * Skip number of separators in url.
	 * 
	 * @param url
	 *            the url
	 * @param separatorsNumber
	 *            the separators number
	 * @return the int
	 */
	private int skipNumberOfSeparatorsInUrl(String url, int separatorsNumber)
	{
		int n = 0;
		int at = 0;

		while (n < separatorsNumber)
		{
			at = url.indexOf(separator, at + 1);
			n++;
		}
		return at;
	}

	/*
	 * Getters and Setters
	 */
	/**
	 * Gets the separator.
	 * 
	 * @return the separator
	 */
	public char getSeparator()
	{
		return separator;
	}
	/**
	 * Sets the separator.
	 * 
	 * @param separator
	 *            the new separator
	 */
	public void setSeparator(char separator)
	{
		this.separator = separator;
	}
	/**
	 * Gets the maximum depth of the path
	 * 
	 * @return the separator
	 */
	public int getMaxDepth()
	{
		return representation.size();
	}
}
