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
1.4 - 08/09/2004:
	Adding throws Exception in query().
1.3 - 07/09/2004:
	Remove the service calls, now in the class ServiceProvider.
1.2 - 03/09/2004:
	Addition of a return value, a String that is the ID of the query or service call.
1.1 - 16/06/2004:
	New definition of the interface.
*/
public interface QueryProvider {

	/**
	 * Name of the avalon Role.
	 */
	public final static String ROLE = QueryProvider.class.getName();


	/**
	 * Sends a query to a provider.
	 *
	 * @param query          The query to be sent.
	 * @param context        Context of the query.
	 * @return               ID of the call.
	 * @exception Exception  If something wrong occurs during the execution of the
	 *      query.
	 */
	public String query(String context, String query)
		throws Exception;

}

