package org.egso.common.services.broker;

import javax.activation.DataHandler;


/**
 * Service that contains methods for broker responses.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   2.0 - 23/09/2004 [01/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
*/
public interface ResponseFileQueryBroker {

	/**
	 * Avalon role name
	 */
	public final static String ROLE = ResponseFileQueryBroker.class.getName();


	/**
	 * Returns the information for downloading requested files.
	 *
	 * @param context        Context of the results.
	 * @param results        List of requested files with information for download.
	 * @return               ID of the call.
	 * @exception Exception  If something goes wrong...
	 */
	public String sendFilesList(String context, DataHandler results)
		throws Exception;

}
