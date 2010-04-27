package org.egso.common.services.provider;

/**
 * Sends a query to the provider and wait for a result. It also allows the
 * invokation of CoSEC services (temporary solution).
 *
 * @author    Marco Soldati & Romain Linsolas (linsolas@gmail.com)
 * @version   1.1.1-jd - 15/06/2004 [27/11/2003]
 */
/*
2.0 - 23/09/2004:
	Definition of the new homogeneized interface.
1.1.1 - 08/09/2004 [Romain Linsolas (linsolas@gmail.com)]:
	Removing the getFiles method, which has never been implemented in a SYNCHRONOUS mode.
1.1 - 15/06/2004 [Romain Linsolas (linsolas@gmail.com)]:
	Integration of the Context.
*/
public interface QueryProviderSync {

	/**
	 * Defines the ROLE of this class.
	 */
	public final static String ROLE = QueryProviderSync.class.getName();


	/**
	 * Query the EGSO Provider.
	 *
	 * @param query    The query.
	 * @param context  The Context of the query.
	 * @return         A VOTable document that contains all results.
	 */
	public String query(String context, String query);


	/**
	 * Asks the GOES Xrays Plot for a given date interval.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @return           A link (URL) to the image plot. If CoSEC is down (or a
	 *      problem occured during the processing), a link to a basic image of the
	 *      sun will be returned.
	 */
	public String cosecPlotGoesXrays(String startDate, String endDate);


	/**
	 * Asks the GOES Protons Plot for a given date interval.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @return           A link (URL) to the image plot. If CoSEC is down (or a
	 *      problem occured during the processing), a link to a basic image of the
	 *      sun will be returned.
	 */
	public String cosecPlotGoesProtons(String startDate, String endDate);

}

