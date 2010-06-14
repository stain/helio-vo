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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class OldArchiveExplorer
{
	private	OldPathBuilder	path		=	null;
	private	String			separator	=	"/";
	private	DateFormat		format		=	null;
	private	URL				root		=	null;
	
	public OldArchiveExplorer(String root, String format) throws MalformedURLException
	{
		super();
		initialize(root, format);
	}

	/**
	 * Creates the path for.
	 *
	 * @param d the date
	 * @return the string that represents the date
	 */
	public 	String	createPathFor(Date d)
	{
		return path.getPathFor(d);
	}

	/**
	 * Checks if is present.
	 *
	 * @param d the date
	 * @return true, if the page is actually present
	 */
	public boolean isPresent(Date d)
	{
		return	pageExists(createPathFor(d));		
	}

	/**
	 * Gets the all the urls within the given dates.
	 *
	 * @param url the url
	 * @param from the start date
	 * @param to the stop date 
	 * @param depth the depth of the current query
	 * @return the list of all the urls within the given dates
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public	LinkedList<String> getAllWithin(Date from, Date to) throws MalformedURLException, IOException
	{
		LinkedList<String>	tmpList	=	new LinkedList<String>();
		/*
		 * First, find the first url after the date...
		 */
		/*
		 * Get the list from depth 0
		 */
		int currentDepth	=	0;
		
		System.out.println(path.getPattern(0));
		tmpList	=	getAllIn(path.getPattern(0));
		System.out.println(tmpList);
		tmpList	=	select(tmpList, from, to, currentDepth);
		
		
		
		return null;
	}

	private LinkedList<String> select(LinkedList<String> tmpList, Date from,
			Date to, int currentDepth)
	{
		LinkedList<String>	result	=	new LinkedList<String>();
		
		Iterator<String>	i	=	tmpList.iterator();

//		System.out.println(path.getPathFor(from, currentDepth+2));
//		System.out.println(path.getPathFor(to, currentDepth+2));
//		System.out.println(path.getPattern(currentDepth+2));

		try
		{
			Date	currFrom	=	path.evaluateElementAt(from, currentDepth+2);
			Date	currTo		=	path.evaluateElementAt(from, currentDepth+2);
			
			System.out.println(path.evaluateElementAt(from, currentDepth+2));
		} 
		catch (PathBuilderException e)
		{
			e.printStackTrace();
		}
		
//		while(i.hasNext())
//		{
//			String	currentElement	=	i.next();	
//			System.out.println("Analyzing " + currentElement);
//		}
		
		return result;
	}

	/**
	 * Gets the first valid entry after the specified date.
	 *
	 * @param d the date
	 * @return the first valid url after the specified date
	 */
	public String getFirstAfter(Date d)
	{
		/*
		 * First, check if the date is present
		 */
		if(isPresent(d))
			return createPathFor(d);
		else
		{
			
		}
		return null;
	}
	/**
	 * Gets the first valid entry before the specified date.
	 *
	 * @param d the date
	 * @return the first valid url before the specified date
	 */
	public String getFirstBefore(Date d)
	{
		/*
		 * First, check if the date is present
		 */
		if(isPresent(d))
			return createPathFor(d);
		else
		{
			int		currentDepth	=	path.maxDepth-1;
			Date	currentDate		=	d;
			boolean	pageFound		=	false;
			/*
			 * Until a page is found
			 */
			while(!pageExists(path.getPathFor(currentDate, currentDepth)))
			{
				System.out.println(path.getPathFor(currentDate, currentDepth));
				System.out.println(path.getPattern(currentDepth));

				currentDepth--;
			}
			/*
			 * Parse the parent url
			 */
			try
			{
				LinkedList<String>	content	=	getAllIn(path.getPathFor(currentDate, currentDepth));
				System.out.println(content);
			} 
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			pageFound	=	true;
		}
		return null;
	}

	private void initialize(String root, String format) throws MalformedURLException
		{
			path	=	new OldPathBuilder();
			/*
			 * Setting the root and the format
			 */
			this.root	=	new URL(root);
			this.format	=	new SimpleDateFormat(format);
			/*
			 * Adding the root
			 */
			path.add(new PathStep(root, true, true));
			/*
			 * Adding the first separator
			 */
			path.add(new PathStep(separator, true, false));
			/*
			 * Now, parsing the format string for the separator character
			 */
			String	tmp	=	format;
			int		at	=	-1;
			
			while(tmp.contains(separator))
			{
	//			System.out.println("Looking for " + separator + " in " + tmp);
				at	=	tmp.indexOf(separator);
				if((at >= 0) && (at <= tmp.length()))
				{
	//				System.out.println("Adding " + tmp.substring(0, at));
					path.add(new PathStep(tmp.substring(0, at), false, false));
					path.add(new PathStep(separator, true, false));
					tmp	=	tmp.substring(at+1, tmp.length());
				}
			}
			/*
			 * If the remaining string is not empty, add it.
			 */
			if(!tmp.isEmpty())
			{
				path.add(new PathStep(tmp, false, false));
				path.add(new PathStep(separator, true, false));
			}
		}

	/*
	 * Utilities
	 */
	/**
	 * Page exists.
	 *
	 * @param urlName the url name
	 * @return true, if successful
	 */
	private boolean pageExists(String urlName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(urlName)
					.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the all urls within the date.
	 *
	 * @param d the date
	 * @return the all urls before the specified date
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private	LinkedList<String> getAllIn(String url) throws MalformedURLException, IOException
	{
		LinkedList<String>	result	=	new LinkedList<String>();
		String	line	=	null;
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
//			System.out.println("Line is : " + line);
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
//					int timeInfo = Integer.parseInt(tmp);
//					System.out.println("Time information is : " + timeInfo);
			}
		}
		return result;
	}
	/*
	 * Getters and Setters
	 */
	/**
	 * Gets the archive format.
	 *
	 * @return the archive format
	 */
	public DateFormat getArchiveFormat()
	{
		return format;
	}

	
	/**
	 * Sets the archive format.
	 *
	 * @param archiveFormat the new archive format
	 */
	public void setArchiveFormat(DateFormat archiveFormat)
	{
		this.format = archiveFormat;
	}

	/**
	 * Sets the archive format.
	 *
	 * @param archiveFormat the new archive format
	 */
	public void setArchiveFormat(String archiveFormat)
	{
		this.format = new SimpleDateFormat(archiveFormat);
	}


	/**
	 * Sets the root.
	 *
	 * @param root the new root
	 */
	public void setRoot(URL root)
	{
		this.root = root;
	}

	/**
	 * Sets the root.
	 *
	 * @param root the new root
	 * @throws MalformedURLException the malformed url exception
	 */
	public void setRoot(String root) throws MalformedURLException
	{
		this.root = new URL(root);
	}


	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public URL getRoot()
	{
		return root;
	}


	@Override
	public String toString()
	{
		String	res	=	new String("Url : ");
		if(root == null)
			res	+=	null;
		else
			res	+=	root;

		res	+=	" -- ";
		
		if(format == null)
			res	+=	null;
		else
			res	+=	format;
		
		return res;
	}	
	
}
