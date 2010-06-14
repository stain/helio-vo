/*
 * 
 */
package eu.heliovo.dpas.ie.sensors.archives;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import eu.heliovo.dpas.ie.internalData.DPASResultItem;

public class NewArchiveExplorer
{

	private NewPath path = null;

	/**
	 * Instantiates a new new archive explorer.
	 * 
	 * @param path
	 *            the path
	 */
	public NewArchiveExplorer(NewPath path)
	{
		super();
		this.path = path;
	}

	
	public LinkedList<DPASResultItem> query(Date from, Date to)
		throws NewPathException, MalformedURLException, IOException, PathBuilderException, NewPathFragmentException
	{
		LinkedList<DPASResultItem> 	results = new LinkedList<DPASResultItem>();
		LinkedList<String>			allUrls = new LinkedList<String>();
		LinkedList<String> 			tmpUrls = new LinkedList<String>();

		DPASResultItem				currDpasResult	=	new DPASResultItem();
		Calendar					currCalendar	=	new GregorianCalendar();
		
		int currDepth = 1;
		/*
		 * Add the url at depth 1
		 */
		allUrls.add(path.dateToPath(from, currDepth));
		System.out.println("Looking for data within " + from + " and " + to);

		while (currDepth < path.getMaxDepth())
		{
			System.out.println("Looking for data within " + from + " and " + to
					+ " at level " + currDepth + " of " + path.getMaxDepth());
			/*
			 * Now iterating through all the urls
			 */
			Iterator iter = allUrls.iterator();
			/*
			 * Clear the temporary list of Urls
			 */
			tmpUrls.clear();
			while (iter.hasNext())
			{
				String currUrl = (String) iter.next();

				System.out.println(currDepth + " of " + path.getMaxDepth()	+ " ---> " + currUrl);

				if (currDepth < path.getMaxDepth() - 1)
					tmpUrls.addAll(getAllDirsWithin(from, to, currUrl, currDepth));
				else if (currDepth == path.getMaxDepth() - 1)
					results.addAll(getAllResultsWithin(from, to, currUrl, currDepth));

				System.out.println("These are the urls within " + from	+ " and " + to + " : ");
				System.out.println(tmpUrls);
			}
			allUrls.clear();
			allUrls.addAll(tmpUrls);
			currDepth++;
		}
		return results;
	}
	/**
	 * The number of urls that are present within the start and stop date.
	 * 
	 * @param start
	 *            the start
	 * @param stop
	 *            the stop
	 * @return the int
	 */
	public int arePresentWithin(Date start, Date stop)
	{
		return 0;
	}

	/**
	 * Gets the all the urls within the given dates.
	 * 
	 * @param url
	 *            the url
	 * @param from
	 *            the start date
	 * @param to
	 *            the stop date
	 * @param depth
	 *            the depth of the current query
	 * @return the list of all the urls within the given dates
	 * @throws NewPathException
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws NewPathFragmentException
	 * @throws PathBuilderException
	 */
	public LinkedList<String> getAllWithin(Date from, Date to)
			throws NewPathException, MalformedURLException, IOException,
			PathBuilderException, NewPathFragmentException
	{
		LinkedList<String> result = new LinkedList<String>();
		LinkedList<String> allUrls = new LinkedList<String>();
		LinkedList<String> tmpUrls = new LinkedList<String>();

		int currDepth = 1;
		/*
		 * Add the url at depth 1
		 */
		allUrls.add(path.dateToPath(from, currDepth));

		System.out.println("Looking for data within " + from + " and " + to);

		while (currDepth < path.getMaxDepth())
		{
			System.out.println("Looking for data within " + from + " and " + to
					+ " at level " + currDepth + " of " + path.getMaxDepth());
			/*
			 * Now iterating through all the urls
			 */
			Iterator iter = allUrls.iterator();
			/*
			 * Clear the temporary list of Urls
			 */
			tmpUrls.clear();
			while (iter.hasNext())
			{
				String currUrl = (String) iter.next();

//				System.out.println(currDepth + " of " + path.getMaxDepth()
//						+ " ---> " + currUrl);

				if (currDepth < path.getMaxDepth() - 1)
					tmpUrls.addAll(getAllDirsWithin(from, to, currUrl, currDepth));
				else if (currDepth == path.getMaxDepth() - 1)
					tmpUrls.addAll(getAllFilesWithin(from, to, currUrl, currDepth));

//				System.out.println("These are the urls within " + from
//						+ " and " + to + " : ");
//				System.out.println(tmpUrls);
			}
			allUrls.clear();
			allUrls.addAll(tmpUrls);
			currDepth++;
		}
//		System.out.println(" * RETURNING : " + tmpUrls);
		return allUrls;
	}
	
	private LinkedList<String> getAllFilesWithin(Date from, Date to,
			String currUrl, int currDepth) throws MalformedURLException,
			IOException
	{
		LinkedList<String> result = new LinkedList<String>();

		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

		System.out.println("Looking for files within " + from + " and " + to + " in " + currUrl);

		c = new URL(currUrl).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		while ((line = in.readLine()) != null)
		{
			/*
			 * Parse the line to see if it matches ...href="timeInfo/"....
			 */
			if (line.matches(".*href=.*")
					&& !line.matches(".*Parent Directory.*")
					&& !line.contains("C=N;O=D"))
			{
				Date currDate = null;
				/*
				 * Extract the date information
				 */
				int startAt = line.indexOf("href=");
				int stopAt = line.indexOf(">", startAt);
				String tmp = line.substring(startAt + 6, stopAt - 1);
				// System.out.println("Time information is : " + tmp);
				/*
				 * Check here if the time information is within the start and
				 * stop dates.
				 */
				try
				{
					/*
					 * TODO : The problem is here !
					 */
					// System.out.println(" --> " + url + " and " + tmp);

					if (currUrl.endsWith("/"))
					{
						// System.out.println(" --> " + currUrl + tmp);
						currDate = path.pathToDate(currUrl + tmp + "/",
								currDepth);
					} else
					{
						// System.out.println(" --> " + currUrl + "/" + tmp);
						currDate = path.pathToDate(currUrl + "/" + tmp + "/",
								currDepth);
					}

					if ((currDate.after(from) || currDate.equals(from))
							&& (currDate.before(to) || currDate.equals(to)))
					{
//						System.out.println(currDate + "(" + currUrl + "/" + tmp
//								+ ") is within " + from + " and " + to
//								+ " adding it...");

						if (currUrl.endsWith("/"))
							result.add(currUrl + tmp);
						else
							result.add(currUrl + "/" + tmp);
					}
				} 
				catch (PathBuilderException e)
				{
					e.printStackTrace();
				} 
				catch (NewPathFragmentException e)
				{
					e.printStackTrace();
				}
			}
		}
//		System.out.println("*** Returning the urls within " + from + " and " + to + " : ");
//		System.out.println(result);

		return result;
	}

	public LinkedList<String> getAllDirsWithin(Date from, Date to, String url,
			int currDepth) throws MalformedURLException, IOException
	{
		LinkedList<String> result = new LinkedList<String>();

		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

		System.out.println("Looking for directories within " + from + " and "
				+ to + " in " + url);

		c = new URL(url).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		while ((line = in.readLine()) != null)
		{
			/*
			 * Parse the line to see if it matches ...href="timeInfo/"....
			 */
			if (line.matches(".*href=.*") && line.matches(".*DIR.*")
					&& !line.matches(".*Parent Directory.*"))
			{
				Date currDate = null;
				/*
				 * Extract the date information
				 */
				int startAt = line.indexOf("href=");
				int stopAt = line.indexOf("/", startAt);
				String tmp = line.substring(startAt + 6, stopAt);
				/*
				 * Check here if the time information is within the start and
				 * stop dates.
				 */
				try
				{
					/*
					 * TODO : Find a better solution for this hack !
					 */
					if (url.endsWith("/"))
						currDate = path.pathToDate(url + tmp + "/", currDepth);
					else
						currDate = path.pathToDate(url + "/" + tmp + "/",
								currDepth);

					if ((currDate.after(from) || currDate.equals(from))
							&& (currDate.before(to) || currDate.equals(to)))
					{
//						System.out.println(currDate + " is within " + from
//								+ " and " + to + " adding it...");
						if (url.endsWith("/"))
						{
							result.add(url + tmp);
						}
						else
						{
							result.add(url + "/" + tmp);
						}
					} else
					{
//						System.out.println(currDate + " is external to " + from
//								+ " and " + to + " skipping it...");
					}
				} catch (PathBuilderException e)
				{
					e.printStackTrace();
				} catch (NewPathFragmentException e)
				{
					e.printStackTrace();
				}
			}
		}
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Within " + from + " and " + to + " there are " + result.size() + " results : ");
		System.out.println(result);
		System.out.println("-----------------------------------------------------------------------------");

		return result;

	}

	public LinkedList<DPASResultItem> getAllResultsWithin(Date from, Date to, String currUrl,
			int currDepth) throws MalformedURLException, IOException
	{
		LinkedList<DPASResultItem> result = new LinkedList<DPASResultItem>();
		DPASResultItem				currDpasResult	=	new DPASResultItem();
		Calendar					currCalendar	=	new GregorianCalendar();
		String						currFileUrl		=	new String();
		
		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

		System.out.println("Looking for files within " + from + " and " + to + " in " + currUrl);

		c = new URL(currUrl).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		while ((line = in.readLine()) != null)
		{
			/*
			 * Parse the line to see if it matches ...href="timeInfo/"....
			 */
			if (line.matches(".*href=.*")
					&& !line.matches(".*Parent Directory.*")
					&& !line.contains("C=N;O=D"))
			{
				Date currDate = null;
				/*
				 * Extract the date information
				 */
				int startAt = line.indexOf("href=");
				int stopAt = line.indexOf(">", startAt);
				String tmp = line.substring(startAt + 6, stopAt - 1);
				// System.out.println("Time information is : " + tmp);
				/*
				 * Check here if the time information is within the start and
				 * stop dates.
				 */
				try
				{
					/*
					 * TODO : The problem is here !
					 */
					// System.out.println(" --> " + url + " and " + tmp);

					if (currUrl.endsWith("/"))
					{
						// System.out.println(" --> " + currUrl + tmp);
						currDate = path.pathToDate(currUrl + tmp + "/",
								currDepth);
					} else
					{
						// System.out.println(" --> " + currUrl + "/" + tmp);
						currDate = path.pathToDate(currUrl + "/" + tmp + "/",
								currDepth);
					}

					if ((currDate.after(from) || currDate.equals(from))
							&& (currDate.before(to) || currDate.equals(to)))
					{
//						System.out.println(currDate + "(" + currUrl + "/" + tmp
//								+ ") is within " + from + " and " + to
//								+ " adding it...");

						if (currUrl.endsWith("/"))
							currFileUrl	=	new String(currUrl + tmp);
						else
							currFileUrl	=	new String(currUrl + "/" + tmp);
						
						currCalendar.setTime(currDate);
						currDpasResult.urlFITS	=	currFileUrl;
						currDpasResult.measurementStart	=	currCalendar;
						
						result.add(currDpasResult);
					}
				} 
				catch (PathBuilderException e)
				{
					e.printStackTrace();
				} 
				catch (NewPathFragmentException e)
				{
					e.printStackTrace();
				}
			}
		}

		
		
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Within " + from + " and " + to + " there are " + result.size() + " results : ");
		System.out.println(result);
		System.out.println("-----------------------------------------------------------------------------");

		return result;
	}

	/*
	 * Utilities
	 */
	/**
	 * Page exists.
	 * 
	 * @param urlName
	 *            the url name
	 * @return true, if successful
	 */
	private boolean pageExists(String urlName)
	{
		try
		{
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(urlName)
					.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the all urls that are present as childs of the directory.
	 * 
	 * @param d
	 *            the date
	 * @return the all urls before the specified date
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private LinkedList<String> getAllIn(String url)
			throws MalformedURLException, IOException
	{
		LinkedList<String> result = new LinkedList<String>();
		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

		c = new URL(url).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		// use the readLine method of the BufferedReader to read one line at
		// a time.
		// the readLine method returns null when there is nothing else to
		// read.
		while ((line = in.readLine()) != null)
		{
			System.out.println("Line is : " + line);
			/*
			 * Parse the line to see if it matches ...href="timeInfo/"....
			 */
			if (line.matches(".*href=.*") && line.matches(".*DIR.*")
					&& !line.matches(".*Parent Directory.*"))
			{

				/*
				 * Extract the date information
				 */
				int startAt = line.indexOf("href=");
				int stopAt = line.indexOf("/", startAt);
				String tmp = line.substring(startAt + 6, stopAt);
				result.add(tmp);
				// int timeInfo = Integer.parseInt(tmp);
				// System.out.println("Time information is : " + timeInfo);
			}
		}
		return result;
	}
}
