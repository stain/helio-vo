package eu.heliovo.clientapi.registry;

import java.net.URL;


public interface HelioServiceRegistry {
	/**
	 * Get a list of service descriptors.
	 * @return a read-only list of all service descriptors in no particular order.
	 */
	public HelioServiceDescriptor[] getServiceDescriptors();
	
	/**
	 * Get a service descriptor by name
	 * @param name the name of the descriptor to lookup. Must not be null.
	 * @param type the type of the descriptor to lookup. Must not be null.
	 * @return the descriptor or null if none was found.
	 */
	public HelioServiceDescriptor getServiceDescriptor(String name, HelioServiceType type);
	
	/**
	 * Get all endpoints for an instance of a service. 
	 * @param descriptor the descriptor to use
	 * @return an array of URL pointing to the WSDL files.
	 * @throws ServiceResolutionException
	 */
	public URL[] getAllEndpoints(HelioServiceDescriptor descriptor) throws ServiceResolutionException;
	
	/**
	 * Get the endpoint for the "best" instance of a service. 
	 * @param descriptor the descriptor to use
	 * @return the URL pointing to the WSDL files.
	 * @throws ServiceResolutionException
	 */
	public URL getBestEndpoint(HelioServiceDescriptor descriptor) throws ServiceResolutionException;
	
	/**
	 * 
	 * @return
	 * @throws ServiceResolutionException
	 */
	public URL getHfc() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the HEC.
	 * 
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getHec() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the UOC.
	 * 
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getUoc() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the DPAS.
	 * 
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getDpas() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the ICS.
	 * 
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getIcs() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the ILS.
	 * 
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getIls() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the CEA.
	 * @return URL to the "best" service instance.
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getCea() throws ServiceResolutionException;

	/**
	 * Get the "best" URL for the MDES.
	 * @return URL to the "best" service instance.
	 * @throws ServiceResolutionException if the service cannot be found.
	 */
	public URL getMdes() throws ServiceResolutionException;
}
