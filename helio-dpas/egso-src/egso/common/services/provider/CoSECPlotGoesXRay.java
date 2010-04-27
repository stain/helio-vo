package org.egso.common.services.provider;

/**
 * Get a GOES XRay plot from the CoSEC server. 
 * The result will be returned synchronously.
 */
public interface CoSECPlotGoesXRay 
{
	/**
	 * service Type
	 */
	public final static String serviceType = CoSECPlotGoesXRay.class.getName();

	/**
	 * Get the GOES XRay Plot for a given date interval.<BR>
	 * This method is synchronous.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @return              A link (URL) to the image plot. 
     * @exception Exception If CoSEC is down (or a problem occured during processing),
     *                      an Exception will be thrown.
	 */
	public String cosecPlotGoesXRay(String startDate, String endDate)
		throws Exception;

    /**
	 * Get the GOES XRay Plot for a given date interval.<BR>
	 * This method is synchronous.
	 *
	 * @param startDate  Start date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS").
	 * @param endDate    End date for the plot (format accepted: "YYYY-MM-DD" or
	 *      "YYYY-MM-DD HH:MM:SS"). 
	 * @param extra      Extra parameters as defined by cosec. This is just a ,-separated
     * list of key=value pairs
     * 
	 * @return              A link (URL) to the image plot. 
     * @exception Exception If CoSEC is down (or a problem occured during processing),
     *                      an Exception will be thrown.
	 */
	public String cosecPlotGoesXRay(String startDate, String endDate, String extra)
		throws Exception;
}