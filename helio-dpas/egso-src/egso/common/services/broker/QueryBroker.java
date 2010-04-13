package org.egso.common.services.broker;


/**
 * Service that contains methods to send a query to the broker.
 *
 * @author    Marco Soldati - Romain Linsolas (linsolas@gmail.com)
 * @version   2.0 - 23/09/2004 [01/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
1.5 - 23/09/2004:
	Adding the fetchFiles method.
1.3 - 09/09/2004:
	Adding the throws Exception in the methods of the class.
1.2 - 03/09/2004:
	Addition of a return value, a String that represents the ID of the call.
1.1 - 16/06/2004:
	New definition of the interface.
*/
public interface QueryBroker {

	/**
	 * Avalon role name
	 */
	public final static String ROLE = QueryBroker.class.getName();


	/**
	 * Sends a query to the broker. The query is formatted in the EGSO like query
	 * style.
	 *
	 * @param context  Context of the query.
	 * @param query    The query.
	 * @return         ID of the call.
	 */
	public String query(String context, String query)
		throws Exception;


}

