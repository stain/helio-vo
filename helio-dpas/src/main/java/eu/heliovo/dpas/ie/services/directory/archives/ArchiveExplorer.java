/*
 * 
 */
package eu.heliovo.dpas.ie.services.directory.archives;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import eu.heliovo.dpas.ie.services.directory.dao.exception.NewPathException;
import eu.heliovo.dpas.ie.services.directory.dao.exception.NewPathFragmentException;
import eu.heliovo.dpas.ie.services.directory.dao.exception.PathBuilderException;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DebugUtilities;

public class ArchiveExplorer
{
	private	DebugUtilities		debugUtils	=	new DebugUtilities();
	private NewPath 			path 		= null;
	/**
	 * Instantiates a new new archive explorer.
	 * 
	 * @param path
	 *            the path
	 */
	public ArchiveExplorer(NewPath path)
	{
		super();
		this.path = path;
	}

	
	public LinkedList<DPASResultItem> query(Date from, Date to)
		throws NewPathException, MalformedURLException, IOException, PathBuilderException, NewPathFragmentException,Exception
	{
		LinkedList<DPASResultItem> 	results = new LinkedList<DPASResultItem>();
		LinkedList<String>			allUrls = new LinkedList<String>();
		LinkedList<String> 			tmpUrls = new LinkedList<String>();

//		DPASResultItem				currDpasResult	=	new DPASResultItem();
//		Calendar					currCalendar	=	new GregorianCalendar();
		
		int currDepth = 1;
		/*
		 * Add the url at depth 1
		 */
		allUrls.add(path.dateToPath(from, currDepth));
//		debugUtils.printLog(this.getClass().getName(), "Looking for data within " + from + " and " + to);

		while (currDepth < path.getMaxDepth())
		{
//			debugUtils.printLog(this.getClass().getName(), "Looking for data within " + from + " and " + to
//					+ " at level " + currDepth + " of " + path.getMaxDepth());
			/*
			 * Now iterating through all the urls
			 */
			Iterator<String> iter = allUrls.iterator();
			/*
			 * Clear the temporary list of Urls
			 */
			tmpUrls.clear();
			while (iter.hasNext())
			{
				String currUrl = iter.next();

				debugUtils.printLog(this.getClass().getName(), currDepth + " of " + path.getMaxDepth()	+ " ---> " + currUrl);

				if (currDepth < path.getMaxDepth() - 1)
				{
//					tmpUrls.addAll(getAllDirsWithin(from, to, currUrl, currDepth));
					if(path.getFragmentAtDepth(currDepth).isFixed())
						tmpUrls.addAll(getAllDirsMatching(
								path.getFragmentAtDepth(currDepth).getExpression(),
								currUrl, currDepth));
					else
						tmpUrls.addAll(getAllDirsWithin(from, to, currUrl, currDepth));
						
				}
				else if (currDepth == path.getMaxDepth() - 1)
				{
					results.addAll(getAllResultsWithin(from, to, currUrl, currDepth));
					debugUtils.printLog(this.getClass().getName(), "Results " + from + " and " + to
						+ " at level " + currDepth + " of " + path.getMaxDepth() + " are " + results.size());
					
//					/*
//					 * This is to debug the results
//					 */
//					for(int k = 0; k < results.size(); k++)
//					{
//						debugUtils.printLog(this.getClass().getName(), k + " --> " + 
//								results.get(k).measurementStart.getTime() + ", " + results.get(k).instrument + ", " + results.get(k).urlFITS);
//					}
				}
//				System.out.println("These are the urls within " + from	+ " and " + to + " : ");
//				System.out.println(tmpUrls);
			}
			allUrls.clear();
			allUrls.addAll(tmpUrls);
			currDepth++;
		}
//		/*
//		 * This is to debug the results
//		 */
//		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
//		for(int k = 0; k < results.size(); k++)
//		{
//			debugUtils.printLog(this.getClass().getName(), k + " --> " + 
//					results.get(k).measurementStart.getTime() + ", " + results.get(k).instrument + ", " + results.get(k).urlFITS);
//		}
//		System.out.println("--------------------------------------------------------------------------------------------------------------------------");

		return results;
	}

	private LinkedList<String> getAllDirsMatching(String expression,
			String url, int currDepth) throws MalformedURLException, IOException
	{
	LinkedList<String> result = new LinkedList<String>();

	String line = null;
	URLConnection c = null;
	BufferedReader in = null;

//	debugUtils.printLog(this.getClass().getName(), "Looking for directories within " + from + " and "
//			+ to + " in " + url);

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
		if ((line.matches(".*href=.*") || (line.matches(".*HREF=.*")) && 
				(line.matches(".*DIR.*") || line.matches(".*dir.*")) && 
				line.contains(expression) && 
				!line.matches(".*Parent Directory.*")))
		{
			/*
			 * Extract the date information
			 */
			int startAt = line.indexOf("href=");
			if(startAt < 0)
				startAt = line.indexOf("HREF=");

			int stopAt = line.indexOf("/", startAt);
			String tmp = line.substring(startAt + 6, stopAt);
//			debugUtils.printLog(this.getClass().getName(), "Pattern information " + tmp);
			/*
			 * Check here if the time information is within the start and
			 * stop dates.
			 */

				/*
				 * Check if the pattern matches
				 */
				if (tmp.equals(expression))
				{
					if (url.endsWith("/"))
					{
//						debugUtils.printLog(this.getClass().getName(), "Adding " + url + tmp); 
						result.add(url + tmp);
					}
					else
					{
//						debugUtils.printLog(this.getClass().getName(), "Adding " + url + "/" + tmp); 
						result.add(url + "/" + tmp);
					}
				} 
				else
				{
					debugUtils.printLog(this.getClass().getName(), "Skipping " + url + tmp); 
				}
			} 
		}

	return result;

	}


//	/**
//	 * Gets the all the urls within the given dates.
//	 * 
//	 * @param url
//	 *            the url
//	 * @param from
//	 *            the start date
//	 * @param to
//	 *            the stop date
//	 * @param depth
//	 *            the depth of the current query
//	 * @return the list of all the urls within the given dates
//	 * @throws NewPathException
//	 * @throws IOException
//	 * @throws MalformedURLException
//	 * @throws MalformedURLException
//	 *             the malformed url exception
//	 * @throws IOException
//	 *             Signals that an I/O exception has occurred.
//	 * @throws NewPathFragmentException
//	 * @throws PathBuilderException
//	 */
//	public LinkedList<String> getAllWithin(Date from, Date to)
//			throws NewPathException, MalformedURLException, IOException,
//			PathBuilderException, NewPathFragmentException
//	{
//		LinkedList<String> result = new LinkedList<String>();
//		LinkedList<String> allUrls = new LinkedList<String>();
//		LinkedList<String> tmpUrls = new LinkedList<String>();
//
//		int currDepth = 1;
//		/*
//		 * Add the url at depth 1
//		 */
//		allUrls.add(path.dateToPath(from, currDepth));
//
////		debugUtils.printLog(this.getClass().getName(), "Looking for data within " + from + " and " + to);
//
//		while (currDepth < path.getMaxDepth())
//		{
////			debugUtils.printLog(this.getClass().getName(), "Looking for data within " + from + " and " + to
////					+ " at level " + currDepth + " of " + path.getMaxDepth() + " in " + currUrl);
//			/*
//			 * Now iterating through all the urls
//			 */
//			Iterator<String> iter = allUrls.iterator();
//			/*
//			 * Clear the temporary list of Urls
//			 */
//			tmpUrls.clear();
//			while (iter.hasNext())
//			{
//				String currUrl = iter.next();
//
////				debugUtils.printLog(this.getClass().getName(), "Looking for data within " + from + " and " + to
////						+ " at level " + currDepth + " of " + path.getMaxDepth() + " in " + currUrl);
//
////				System.out.println(currDepth + " of " + path.getMaxDepth()
////						+ " ---> " + currUrl);
//
//				if (currDepth < path.getMaxDepth() - 1)
//					tmpUrls.addAll(getAllDirsWithin(from, to, currUrl, currDepth));
//				else if (currDepth == path.getMaxDepth() - 1)
//					tmpUrls.addAll(getAllFilesWithin(from, to, currUrl, currDepth));
//
////				System.out.println("These are the urls within " + from
////						+ " and " + to + " : ");
////				System.out.println(tmpUrls);
//			}
//			allUrls.clear();
//			allUrls.addAll(tmpUrls);
//			currDepth++;
//		}
////		System.out.println(" * RETURNING : " + tmpUrls);
//		return allUrls;
//	}
	
//	private LinkedList<String> getAllFilesWithin(Date from, Date to,
//			String currUrl, int currDepth) throws MalformedURLException,
//			IOException
//	{
//		LinkedList<String> result = new LinkedList<String>();
//
//		String line = null;
//		URLConnection c = null;
//		BufferedReader in = null;
//
////		debugUtils.printLog(this.getClass().getName(), "Looking for files within " + from + " and " + to + " in " + currUrl);
//
//		c = new URL(currUrl).openConnection();
//		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
//		/*
//		 * Parse the top directory
//		 */
//		while ((line = in.readLine()) != null)
//		{
//			/*
//			 * Parse the line to see if it matches ...href="timeInfo/"....
//			 */
//			if (line.matches(".*href=.*")
//					&& !line.matches(".*Parent Directory.*")
//					&& !line.contains("C=N;O=D"))
//			{
//				Date currDate = null;
//				/*
//				 * Extract the date information
//				 */
//				int startAt = line.indexOf("href=");
//				int stopAt = line.indexOf(">", startAt);
//				String tmp = line.substring(startAt + 6, stopAt - 1);
//				// System.out.println("Time information is : " + tmp);
//				/*
//				 * Check here if the time information is within the start and
//				 * stop dates.
//				 */
//				try
//				{
//					/*
//					 * TODO : The problem is here !
//					 */
//					// System.out.println(" --> " + url + " and " + tmp);
//
//					if (currUrl.endsWith("/"))
//					{
//						// System.out.println(" --> " + currUrl + tmp);
//						currDate = path.pathToDate(currUrl + tmp + "/",
//								currDepth);
//					} else
//					{
//						// System.out.println(" --> " + currUrl + "/" + tmp);
//						currDate = path.pathToDate(currUrl + "/" + tmp + "/",
//								currDepth);
//					}
//
//					if ((currDate.after(from) || currDate.equals(from))
//							&& (currDate.before(to) || currDate.equals(to)))
//					{
////						System.out.println(currDate + "(" + currUrl + "/" + tmp
////								+ ") is within " + from + " and " + to
////								+ " adding it...");
//
//						if (currUrl.endsWith("/"))
//							result.add(currUrl + tmp);
//						else
//							result.add(currUrl + "/" + tmp);
//					}
//				} 
//				catch (PathBuilderException e)
//				{
//					e.printStackTrace();
//				} 
//				catch (NewPathFragmentException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
////		System.out.println("*** Returning the urls within " + from + " and " + to + " : ");
////		System.out.println(result);
//
//		return result;
//	}

	public LinkedList<String> getAllDirsWithin(Date from, Date to, String url,
			int currDepth) throws MalformedURLException, IOException,Exception
	{
		LinkedList<String> result = new LinkedList<String>();

		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

//		debugUtils.printLog(this.getClass().getName(), "Looking for directories within " + from + " and "
//				+ to + " in " + url);

		c = new URL(url).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		while ((line = in.readLine()) != null)
		{
//			System.out.println(line);
			/*
			 * Parse the line to see if it matches ...href="timeInfo/"....
			 */
			if ((line.matches(".*href=.*") || line.matches(".*HREF=.*")) && 
					(line.matches(".*DIR.*") || line.matches(".*dir.*")) && 
				 !line.matches(".*Parent Directory.*"))
			{
//				System.out.println(line);
				
				Date currEarliestDate = null;
				Date currLatestDate = null;
				/*
				 * Extract the date information
				 */
				int startAt = line.indexOf("href=");
				if(startAt < 0)
					startAt = line.indexOf("HREF=");
					
				int stopAt = line.indexOf("/", startAt);
				String tmp = line.substring(startAt + 6, stopAt);
//				debugUtils.printLog(this.getClass().getName(), "Time information " + tmp);
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
					{
//						debugUtils.printLog(this.getClass().getName(), "Extracting current date from " + url + tmp + "/");
						currEarliestDate = path.pathToEarliestDate(url + tmp + "/", currDepth);
						currLatestDate = path.pathToLatestDate(url + tmp + "/", currDepth);					
					}
					else
					{
//						debugUtils.printLog(this.getClass().getName(), "Extracting current date from " + url + "/" + tmp + "/");
						currEarliestDate = path.pathToEarliestDate(url + "/" + tmp + "/",
								currDepth);
						currLatestDate = path.pathToLatestDate(url + "/" + tmp + "/",
								currDepth);
					}

//					debugUtils.printLog(this.getClass().getName(), "Checking if current Range (" + 
//							currEarliestDate + " --> " + 
//							currLatestDate + ") is within with (" + 
//							from + " --> " + to + ")");
					/*
					 * Check if the ranges overlap
					 */
					if (((currEarliestDate.before(to) || currEarliestDate.equals(to)))
							&&
						((currLatestDate.after(from) || currLatestDate.equals(from))
									))
					{
//						debugUtils.printLog(this.getClass().getName(), "Current Range (" + 
//								currEarliestDate + " --> " + 
//								currLatestDate + ") overlaps with (" + 
//								from + " --> " + to + ") adding it...");

						if (url.endsWith("/"))
						{
							result.add(url + tmp);
						}
						else
						{
							result.add(url + "/" + tmp);
						}
					} 
					else
					{
//						debugUtils.printLog(this.getClass().getName(), "Current Range (" + 
//								currEarliestDate + " --> " + 
//								currLatestDate + ") does not overlap with (" + 
//								from + " --> " + to + ") skipping it...");
					}
				} 
				catch (PathBuilderException e)
				{
					e.printStackTrace();
					throw new Exception(" Archive path exception",e);
				} 
				catch (NewPathFragmentException e)
				{
					e.printStackTrace();
				} 
				catch (NewPathException e)
				{
					if (url.endsWith("/") && tmp!=null && !tmp.trim().equals(""))
					{
						result.add(url + tmp);
					}
					else if(tmp!=null && !tmp.trim().equals(""))
					{
						result.add(url + "/" + tmp);
					}else{
						debugUtils.printLog(this.getClass().getName(), tmp + " is not a Date Format NOT VALID skipping it...");
					}
				}
			}
		}
//		System.out.println("-----------------------------------------------------------------------------");
//		debugUtils.printLog(this.getClass().getName(), "Within " + from + " and " + to + " there are " + result.size() + " results : ");
//		System.out.println("-----------------------------------------------------------------------------");

		return result;

	}

	public LinkedList<DPASResultItem> getAllResultsWithin(Date from, Date to, String currUrl,
			int currDepth) throws MalformedURLException, IOException
	{
		LinkedList<DPASResultItem> 	results			= 	new LinkedList<DPASResultItem>();
		DPASResultItem				currDpasResult	=	new DPASResultItem();
		Calendar					currCalendar	=	null;
		String						currFileUrl		=	new String();
		
		String line = null;
		URLConnection c = null;
		BufferedReader in = null;

//		debugUtils.printLog(this.getClass().getName(), "Looking for files within " + from + " and " + to + " in " + currUrl);

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
				if(startAt < 0)
					startAt = line.indexOf("HREF=");

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
					} 
					else
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

						currDpasResult	=	new DPASResultItem();
						currCalendar	=	new GregorianCalendar();
						currCalendar.setTime(currDate);

						currDpasResult.urlFITS	=	currFileUrl;
						currDpasResult.measurementStart	=	currCalendar;
//						debugUtils.printLog(this.getClass().getName(), "Adding " + currDpasResult.urlFITS + " with calendar " + currDpasResult.measurementStart.getTime());

						results.add(currDpasResult);
						
//						System.out.println("-----------------------------------------------------------------------------");
//						debugUtils.printLog(this.getClass().getName(), "Within " + from + " and " + to + " there are " + results.size() + " results : ");
						/*
						 * This is to debug the results
						 */
//						for(int j = 0; j < results.size(); j++)
//						{
//							debugUtils.printLog(this.getClass().getName(), j + " --> " + 
//									results.get(j).measurementStart.getTime() + ", " + results.get(j).instrument + ", " + results.get(j).urlFITS);
//						}
//						System.out.println("-----------------------------------------------------------------------------");

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

		
		
//		System.out.println("-----------------------------------------------------------------------------");
//		debugUtils.printLog(this.getClass().getName(), "Within " + from + " and " + to + " there are " + results.size() + " results : ");
//		/*
//		 * This is to debug the results
//		 */
//		for(int k = 0; k < results.size(); k++)
//		{
//			debugUtils.printLog(this.getClass().getName(), k + " --> " + 
//					results.get(k).measurementStart.getTime() + ", " + results.get(k).instrument + ", " + results.get(k).urlFITS);
//		}
//		System.out.println("-----------------------------------------------------------------------------");

		return results;
	}

//	/*
//	 * Utilities
//	 */
//	/**
//	 * Page exists.
//	 * 
//	 * @param urlName
//	 *            the url name
//	 * @return true, if successful
//	 */
//	private boolean pageExists(String urlName)
//	{
//		try
//		{
//			HttpURLConnection.setFollowRedirects(false);
//			HttpURLConnection con = (HttpURLConnection) new URL(urlName)
//					.openConnection();
//			con.setInstanceFollowRedirects(false);
//			con.setRequestMethod("HEAD");
//			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * Gets the all urls that are present as childs of the directory.
//	 * 
//	 * @param d
//	 *            the date
//	 * @return the all urls before the specified date
//	 * @throws IOException
//	 * @throws MalformedURLException
//	 */
//	private LinkedList<String> getAllIn(String url)
//			throws MalformedURLException, IOException
//	{
//		LinkedList<String> result = new LinkedList<String>();
//		String line = null;
//		URLConnection c = null;
//		BufferedReader in = null;
//
//		c = new URL(url).openConnection();
//		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
//		/*
//		 * Parse the top directory
//		 */
//		// use the readLine method of the BufferedReader to read one line at
//		// a time.
//		// the readLine method returns null when there is nothing else to
//		// read.
//		while ((line = in.readLine()) != null)
//		{
//			System.out.println("Line is : " + line);
//			/*
//			 * Parse the line to see if it matches ...href="timeInfo/"....
//			 */
//			if (line.matches(".*href=.*") && line.matches(".*DIR.*")
//					&& !line.matches(".*Parent Directory.*"))
//			{
//
//				/*
//				 * Extract the date information
//				 */
//				int startAt = line.indexOf("href=");
//				int stopAt = line.indexOf("/", startAt);
//				String tmp = line.substring(startAt + 6, stopAt);
//				result.add(tmp);
//				// int timeInfo = Integer.parseInt(tmp);
//				// System.out.println("Time information is : " + timeInfo);
//			}
//		}
//		return result;
//	}
}
