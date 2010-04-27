package org.egso.common.services.provider;

import javax.activation.DataHandler;


/**
 * Get the response of the provider.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   2.0 - 27/09/2004 [27/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
*/
public interface ResponseServiceProvider {

	/**
	 * Name of the avalon Role
	 */
	public final static String ROLE = ResponseServiceProvider.class.getName();


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

