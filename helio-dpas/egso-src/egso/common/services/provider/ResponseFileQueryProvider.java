package org.egso.common.services.provider;

import javax.activation.DataHandler;


/**
 * Get the response of the provider.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   2.0 - 23/09/2004 [27/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
*/
public interface ResponseFileQueryProvider {

	/**
	 * Name of the avalon Role
	 */
	public final static String ROLE = ResponseFileQueryProvider.class.getName();


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
