package org.egso.common.services.provider;


/**
 * Send a query to the provider. The result will be returned asynchronously.
 *
 * @author    Marco Soldati - Romain Linsolas (linsolas@gmail.com).
 * @version   2.0 - 23/09/2004 [27/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
*/
public interface FileQueryProvider {

	/**
	 * Name of the avalon Role.
	 */
	public final static String ROLE = FileQueryProvider.class.getName();


	/**
	 * Sends a query to a provider for files request.
	 *
	 * @param context        Context of the query.
	 * @param results        Query containing the list of requested files.
	 * @return               ID of the call.
	 * @exception Exception  If something goes wrong...
	 */
	public String fetchFiles(String context, String results)
		throws Exception;

}
