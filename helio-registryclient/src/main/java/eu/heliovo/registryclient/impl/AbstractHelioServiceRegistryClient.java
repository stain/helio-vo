package eu.heliovo.registryclient.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.ServiceDescriptor;
import eu.heliovo.registryclient.ServiceRegistryClient;
import eu.heliovo.registryclient.ServiceResolutionException;
import eu.heliovo.shared.util.AssertUtil;

/**
 * Abstract base class for Registry clients.
 * This implementation uses an internal set to store the registered service instances.
 * @author MarcoSoldati
 *
 */
public abstract class AbstractHelioServiceRegistryClient implements
		ServiceRegistryClient {

	/**
	 * The logger for this registry impl
	 */
	protected final Logger _LOGGER = Logger.getLogger(getClass());

	/**
	 * Store the registered service descriptors.
	 */
	protected final Set<ServiceDescriptor> serviceDescriptors = new HashSet<ServiceDescriptor>();

	/**
	 * Store the registered service instance descriptor
	 */
	protected final Map<ServiceDescriptor, Set<HelioServiceInstanceDescriptor>> instanceDescriptors = new HashMap<ServiceDescriptor, Set<HelioServiceInstanceDescriptor>>();

	@Override
    public ServiceDescriptor registerServiceDescriptor(
			ServiceDescriptor helioServiceDescriptor) {
		ServiceDescriptor ret;
		boolean exists = serviceDescriptors.add(helioServiceDescriptor);
		if (!exists) {
			ret = null;
			for (ServiceDescriptor currentDescriptor : serviceDescriptors) {
				if (currentDescriptor.equals(helioServiceDescriptor)) {
					ret = currentDescriptor;
					break;
				}
			}

			if (ret == null) {
				throw new RuntimeException(
						"Internal Error: unable to register "
								+ helioServiceDescriptor + ".");
			}

			if (_LOGGER.isDebugEnabled()) {
				_LOGGER.debug("Service descriptor '"
						+ ret
						+ "' has been previously registered. This is probably fine.");
			}
		} else {
		    if (_LOGGER.isDebugEnabled()) {
    			_LOGGER.debug("Service descriptor '" + helioServiceDescriptor
    					+ "' has been previously registered.");
		    }
			ret = helioServiceDescriptor;
		}
		return ret;
	}
	
	/**
	 * init method to be called after configuring the registry client.
	 */
	abstract public void init();

	/**
	 * Convenience method to register a capability for a given service.
	 * 
	 * @param serviceName
	 *            name of the service.
	 * @param description
	 *            the description of the service.
	 * @param accessInterface
	 *            the access interface pointing to valid endpoints for this
	 *            service.
	 */
	protected void registerServiceInstance(HelioServiceName serviceName,
			String description, AccessInterface accessInterface) {
		// create the descriptor
		ServiceDescriptor serviceDescriptor = new GenericServiceDescriptor(
				serviceName, description, (ServiceCapability[]) null);
		registerServiceInstance(serviceDescriptor, accessInterface);
	}


	@Override
    public boolean registerServiceInstance(ServiceDescriptor serviceDescriptor,
			AccessInterface accessInterface) {
		// check if service descriptor is already registered, if not do so.
		serviceDescriptor = registerServiceDescriptor(serviceDescriptor);
		// sanity check
		if (serviceDescriptor == null) {
			throw new ServiceResolutionException(
					"Internal Error: Unable to register service descriptor "
							+ serviceDescriptor + ".");
		}

		// add capabilty if necessary
		serviceDescriptor.addCapability(accessInterface.getCapability());

		Set<HelioServiceInstanceDescriptor> currentInstanceDescriptors = instanceDescriptors
				.get(serviceDescriptor);

		// create hash set for descriptor if not yet existing.
		if (currentInstanceDescriptors == null) {
			currentInstanceDescriptors = new LinkedHashSet<HelioServiceInstanceDescriptor>();
			instanceDescriptors.put(serviceDescriptor,
					currentInstanceDescriptors);
		}

		HelioServiceInstanceDescriptor instanceDescriptor = new HelioServiceInstanceDescriptor(
				serviceDescriptor, accessInterface);

		boolean ret = currentInstanceDescriptors.add(instanceDescriptor);
		if (!ret) {
			_LOGGER.warn("Service instance descriptor '" + instanceDescriptor
					+ "' has been previously registered.");
		} else {
		    if (_LOGGER.isDebugEnabled()) {
    			_LOGGER.debug("Register access interface '" + accessInterface
    					+ "' for service '" + serviceDescriptor + "' ");
		    }
		}
		return ret;
	}

	@Override
	public ServiceDescriptor[] getAllServiceDescriptors() {
		return serviceDescriptors
				.toArray(new ServiceDescriptor[serviceDescriptors.size()]);
	}

	@Override
	public ServiceDescriptor getServiceDescriptor(HelioServiceName name) {
		ServiceDescriptor ret = null;
		for (ServiceDescriptor currentDescriptor : serviceDescriptors) {
			if (currentDescriptor.getName().equals(name)) {
				ret = currentDescriptor;
				break;
			}
		}
		return ret;
	}

	@Override
	public AccessInterface[] getAllEndpoints(ServiceDescriptor descriptor,
			ServiceCapability capability, AccessInterfaceType type) {
		AssertUtil.assertArgumentNotNull(descriptor, "descriptor");
		Set<HelioServiceInstanceDescriptor> serviceInstances = instanceDescriptors
				.get(descriptor);
		if (serviceInstances == null) {
			return new AccessInterface[0];
		}

		// fill in array of access interfaces.
		List<AccessInterface> ret = new ArrayList<AccessInterface>();
		for (HelioServiceInstanceDescriptor helioServiceInstanceDescriptor : serviceInstances) {
			if ((capability == null || capability
					.equals(helioServiceInstanceDescriptor.getAccessInterface()
							.getCapability()))
					&& (type == null || type
							.equals(helioServiceInstanceDescriptor
									.getAccessInterface().getInterfaceType()))) {
				ret.add(helioServiceInstanceDescriptor.getAccessInterface());
			}
		}
		return ret.toArray(new AccessInterface[ret.size()]);
	}

	@Override
	public AccessInterface getBestEndpoint(HelioServiceName serviceName,
			ServiceCapability capability, AccessInterfaceType type) {
		ServiceDescriptor serviceDescsriptor = getServiceDescriptor(serviceName);
		if (serviceDescsriptor == null) {
			throw new ServiceResolutionException("No service found with name "
					+ serviceName + ".");
		}
		AccessInterface bestEndpoint = getBestEndpoint(serviceDescsriptor,
				capability, type);
		if (bestEndpoint == null) {
			throw new ServiceResolutionException(
					"No service instance found for " + serviceDescsriptor);
		}
		return bestEndpoint;
	}

	/**
	 * This implementation just takes the first match.
	 */
	@Override
	public AccessInterface getBestEndpoint(ServiceDescriptor descriptor,
			ServiceCapability capability, AccessInterfaceType type) {
		AssertUtil.assertArgumentNotNull(descriptor, "descriptor");
		AssertUtil.assertArgumentNotNull(capability, "capability");
		AssertUtil.assertArgumentNotNull(type, "type");
		Set<HelioServiceInstanceDescriptor> serviceInstances = instanceDescriptors
				.get(descriptor);
		if (serviceInstances == null || serviceInstances.isEmpty()) {
			return null;
		}

		// just take the first entry that matches the capability
		for (HelioServiceInstanceDescriptor helioServiceInstanceDescriptor : serviceInstances) {
			if (helioServiceInstanceDescriptor.getAccessInterface()
					.getCapability().equals(capability)
					&& type.equals(helioServiceInstanceDescriptor
							.getAccessInterface().getInterfaceType())) {
				return helioServiceInstanceDescriptor.getAccessInterface();
			}
		}

		return null;
	}

	/**
	 * The default constructor
	 */
	public AbstractHelioServiceRegistryClient() {
		super();
	}
}

/**
 * Descriptor of a concrete instance of a service. Do not instantiate this class
 * outside of {@link AbstractHelioServiceRegistryClient}. It's public for the
 * XMLEncoder
 * 
 * @author MarcoSoldati
 * 
 */
class HelioServiceInstanceDescriptor {
	/**
	 * The assigned service descriptor
	 */
	private final ServiceDescriptor serviceDescriptor;

	/**
	 * Pointer to the access interface
	 */
	private final AccessInterface accessInterface;

	/**
	 * Container to hold the reference to a specific instance of a service.
	 * 
	 * @param serviceDescriptor
	 *            the service descriptor
	 * @param accessInterface
	 *            the URL and interface type assigned with this descriptor.
	 */
	public HelioServiceInstanceDescriptor(ServiceDescriptor serviceDescriptor,
			AccessInterface accessInterface) {
		AssertUtil
				.assertArgumentNotNull(serviceDescriptor, "serviceDescriptor");
		AssertUtil.assertArgumentNotNull(accessInterface, "accessInterface");
		this.serviceDescriptor = serviceDescriptor;
		this.accessInterface = accessInterface;
	}

	/**
	 * Get the descriptor of the service.
	 * 
	 * @return the service descriptor.
	 */
	public ServiceDescriptor getServiceDescriptor() {
		return serviceDescriptor;
	}

	/**
	 * Get a pointer to the access interface.
	 * 
	 * @return the access interface
	 */
	public AccessInterface getAccessInterface() {
		return accessInterface;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof HelioServiceInstanceDescriptor)) {
			return false;
		}

		return serviceDescriptor
				.equals(((HelioServiceInstanceDescriptor) other).serviceDescriptor)
				&& accessInterface
						.equals(((HelioServiceInstanceDescriptor) other).accessInterface);

	}

	@Override
	public int hashCode() {
		return accessInterface.hashCode() * 13 + serviceDescriptor.hashCode()
				* 11;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(serviceDescriptor);
		sb.append(", accessInterface=").append(accessInterface);
		return sb.toString();
	}
}