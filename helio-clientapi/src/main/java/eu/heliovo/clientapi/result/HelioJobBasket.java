package eu.heliovo.clientapi.result;

/**
 * Container that provides access to long running queries as well as results of previous queries.
 */
public interface HelioJobBasket {
	  
	/**
	 * Gets a specific long-running job.
	 * @param id the id of the job to retreive.
	 */
	public HelioResultSetFuture getHelioJob(String id);
	
	
	/**
	 * Get the list of active Helio jobs.
	 * Active jobs are either still running or have been finished, but the 
	 * result has not been collected.
	 * @return 
	 */
	public HelioResultSetFuture[] getHelioJobs();

}
