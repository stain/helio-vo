package org.egso.provider.query;



/**
 * Superclass of classes used for a description of an archive-specific query.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 26/11/2003 [26/11/2003]
 */
public class ArchiveQuery {

	/**
	 * Code for a query for an undefined archive.
	 */
	public final static int NOT_DEFINED = 0;
	/**
	 * Code for a query for a FTP archive.
	 */
	public final static int FTP_ARCHIVE = 1;
	/**
	 * Code for a query for a SQL archive.
	 */
	public final static int SQL_ARCHIVE = 2;
	/**
	 * Code for a query for a Web-Service archive.
	 */
	public final static int WEB_SERVICE_ARCHIVE = 3;
	/**
	 * Code for a query for a HTTP archive.
	 */
	public final static int HTTP_ARCHIVE = 4;
	/**
	 * Type of the query.
	 */
	protected int queryType;


	/**
	 * Constructor for the ArchiveQuery.
	 *
	 * @param type  Type of the query.
	 */
	public ArchiveQuery(int type) {
		queryType = type;
	}


	/**
	 * Gets the type of the query.
	 *
	 * @return   The type of the query.
	 */
	public int getType() {
		return (queryType);
	}


	/**
	 * Tests if the query is a FTP query or not.
	 *
	 * @return   true if the query is a FTP query, else otherwise.
	 */
	public boolean isFTP() {
		return (queryType == FTP_ARCHIVE);
	}


	/**
	 * Tests if the query is a SQL query or not.
	 *
	 * @return   true if the query is a SQL query, else otherwise.
	 */
	public boolean isSQL() {
		return (queryType == SQL_ARCHIVE);
	}


	/**
	 * Tests if the query is a Web-Service query or not.
	 *
	 * @return   true if the query is a Web-Service query, else otherwise.
	 */
	public boolean isWebService() {
		return (queryType == WEB_SERVICE_ARCHIVE);
	}


	/**
	 * Tests if the query is a HTTP query or not.
	 *
	 * @return   true if the query is a HTTP query, else otherwise.
	 */
	public boolean isHTTP() {
		return (queryType == HTTP_ARCHIVE);
	}

}

