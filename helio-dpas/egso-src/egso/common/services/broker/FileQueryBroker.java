package org.egso.common.services.broker;


/**
 * Service that contains methods to send a query to the broker.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   2.0 - 09/09/2004 [01/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
*/
public interface FileQueryBroker {

	/**
	 * Avalon role name
	 */
	public final static String ROLE = FileQueryBroker.class.getName();


	/**
	 * Sends a query to a provider for files request.
	 *
	 * @param context        Context of the query.
	 * @param query        Query containing the list of requested files.
	 * @return               ID of the call.
	 * @exception Exception  If something goes wrong...
	 */
	public String fetchFiles(String context, String query)
		throws Exception;

}
