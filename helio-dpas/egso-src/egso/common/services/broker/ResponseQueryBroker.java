package org.egso.common.services.broker;

import javax.activation.DataHandler;


/**
 * Service that contains methods for broker responses.
 *
 * @author    Marco Soldati - Romain Linsolas (linsolas@gmail.com)
 * @version   2.0 - 23/09/2004 [01/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
1.4 - 13/09/2004:
	New definition of the interface, using only DataHandler.
1.3 - 09/09/2004:
	Adding the throws Exception in the sendResponse(...) method.
1.2 - 03/09/2004:
	Addition of a return value, a String that represents the ID of the call.
1.1 - 16/06/2004:
	New definition of the interface.
*/
public interface ResponseQueryBroker {

	/**
	 * Avalon role name
	 */
	public final static String ROLE = ResponseQueryBroker.class.getName();


	/**
	 * Returns a response (results or files) from the provider.
	 *
	 * @param context        Context of the results.
	 * @param results        Results or files to be returned.
	 * @return               ID of the call.
	 * @exception Exception  If something goes wrong...
	 */
	public String sendResponse(String context, DataHandler results)
		throws Exception;


}

