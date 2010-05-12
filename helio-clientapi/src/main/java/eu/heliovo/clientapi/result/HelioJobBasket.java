package eu.heliovo.clientapi.result;

/**
 * Container that provides access to long running queries as well as results of previous queries.
 */
public interface HelioJobBasket {
	/**
	 * Get the list of active Helio jobs.
	 * Active jobs are either still running or have been finished, but the 
	 * result has not been collected.
	 * @return 
	 */
	public HelioJob[] getHelioJobs();

}
