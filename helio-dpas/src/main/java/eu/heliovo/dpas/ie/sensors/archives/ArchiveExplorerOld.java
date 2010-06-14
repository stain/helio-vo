/*
 * 
 */
package eu.heliovo.dpas.ie.sensors.archives;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArchiveExplorerOld
{
	private	DateFormat	format	=	null;
	private	URL			root	=	null;
	
	/**
	 * Checks if is present.
	 *
	 * @param d the d
	 * @return true, if is present
	 */
	public boolean isPresent(Date d)
	{
		return	pageExists(createPathFor(d));		
	}
	
	/*
	 * Utilities
	 */
	/**
	 * Creates the path for.
	 *
	 * @param d the d
	 * @return the string
	 */
	public	String	createPathFor(Date d)
	{
		return root.toString() + format.format(d);
	}
	
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
