package org.egso.common.services.broker;


/**
 * Service that contains methods to send a query to the broker.
 *
 * @author    Marco Soldati - Romain Linsolas (linsolas@gmail.com).
 * @version   2.0 - 23/09/2004 [01/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
1.1 - 15/06/2004 [Romain Linsolas (linsolas@gmail.com)] :
	Add the Context in the query method.
*/
public interface QueryBrokerSync {

	/**
	 * Avalon role name
	 */
	public final static String ROLE = QueryBrokerSync.class.getName();


	/**
	 * Sends a query to the broker. The query is formatted in the EGSO like query
	 * style.
	 *
	 * @param query                The query.
	 * @param context              The Context of the Query.
	 * @return                     A VOTable document (as a String representation).
	 */
	public String query(String context, String query);
}

