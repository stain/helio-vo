package eu.heliovo.clientapi.service;

import eu.heliovo.clientapi.query.HelioParameter;

/**
 * Describe a query service in a way such that it can provide useful feedback for end users. 
 * @author marco soldati at fhnw ch
 *
 */
public interface QueryServiceDescriptor {
	/**
	 * Get the name of the query service.
	 * @return the query service name
	 */
	public String getServiceName();
	
	/**
	 * Get a description of the query service
	 * @return the description of a query service.
	 */
	public String getDescription();
		
	/**
	 * Get the list of parameters that are valid for this service.
	 * @return the list of parameters
	 */
	public HelioParameter[] getHelioParameters();
}
