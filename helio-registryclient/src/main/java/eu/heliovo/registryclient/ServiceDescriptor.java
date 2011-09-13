package eu.heliovo.registryclient;

import java.util.Set;

/**
 * Describe a service in general (i.e. not a particular instance of the
 * service). This object connects the service name with the service capability. 
 * It can be retrieved through the
 * {@link ServiceRegistryClient}. The business key of this object is the
 * "name - type" tuple.
 * 
 * @author marco soldati at fhnw ch
 * 
 */
public interface ServiceDescriptor {
	/**
	 * Return the name of the service
	 * 
	 * @return the name
	 */
	HelioServiceName getName();

	/**
	 * Get the label for the service.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Get a short description for the service.
	 * 
	 * @return the description
	 */
	String getDescription();

	/**
	 * Get the capabilities of a service
	 * 
	 * @return the type of a service.
	 */
	Set<ServiceCapability> getCapabilities();

	/**
	 * Check if the name and capabilties are equal
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Create the hash code from name and capabilities
	 * 
	 * @return the hashcode.
	 */
	@Override
	int hashCode();

	/**
	 * Add a capability to this service descriptor.
	 * 
	 * @param capability
	 *            the capability to add.
	 * @return true if the capability did not already exist, false otherwise.
	 */
	boolean addCapability(ServiceCapability capability);
}