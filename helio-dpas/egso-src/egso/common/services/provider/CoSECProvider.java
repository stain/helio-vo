package org.egso.common.services.provider;


/**
 * Invoke a service to the provider. The result will be returned synchronously.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   1.1 - 08/09/2004 [07/09/2004]
 */
/*
1.1 - 08/09/2004:
	Adding throws Exception in all methods.
1.0 - 07/09/2004:
	Creation of this class, previously part of the QueryProviderAsync.
	Addition of the CoSEC services methods.
*/
public interface CoSECProvider {

	/**
	 * Name of the avalon Role.
	 */
	public final static String ROLE = CoSECProvider.class.getName();


	/**
	 * Asks the GOES Xrays Plot for a given date interval.<BR>
	 * This method is synchronous.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @return           A link (URL) to the image plot. If CoSEC is down (or a
	 *      problem occured during the processing), a link to a basic image of the
	 *      sun will be returned.
	 */
	public String cosecPlotGoesXrays(String startDate, String endDate)
		throws Exception;


	/**
	 * Asks the GOES Protons Plot for a given date interval.<BR>
	 * This method is synchronous.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @return           A link (URL) to the image plot. If CoSEC is down (or a
	 *      problem occured during the processing), a link to a basic image of the
	 *      sun will be returned.
	 */
	public String cosecPlotGoesProtons(String startDate, String endDate)
		throws Exception;

}

