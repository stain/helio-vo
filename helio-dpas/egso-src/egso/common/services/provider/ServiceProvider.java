package org.egso.common.services.provider;

import javax.activation.DataHandler;


/**
 * Invoke a service to the provider. The result will be returned asynchronously.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   1.1 - 08/09/2004 [07/09/2004]
 */
/*
1.1 - 08/09/2004:
	Adding the throws Exception in all service(...) methods.
1.0 - 07/09/2004:
	Creation of this class, previously part of the QueryProviderAsync.
*/
public interface ServiceProvider {

	/**
	 * Name of the avalon Role.
	 */
	public final static String ROLE = ServiceProvider.class.getName();


	/**
	 * Invokes a service.
	 *
	 * @param context        Context of the invocation.
	 * @param query          The query.
	 * @return               ID of the service invocation.
	 * @exception Exception  If something wrong occured during the service
	 *      execution.
	 */
	public String service(String context, String query)
			 throws Exception;


	/**
	 * Invokes a service that needs a (set of) file(s).
	 *
	 * @param context        Context of the invokation.
	 * @param query          The query.
	 * @param file           The files needed by the service.
	 * @return               ID of the service invocation.
	 * @exception Exception  If something wrong occured during the service
	 *      execution.
	 */
	public String service(String context, String query, DataHandler file)
			 throws Exception;

}

