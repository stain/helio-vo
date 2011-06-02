package eu.heliovo.registryclient.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.ServiceDescriptor;
import eu.heliovo.shared.util.AssertUtil;

/**
 * Describe a service in general (i.e. not a particular instance of the service). 
 * The description can be retrieved through the {@link HelioServiceRegistryDao}.
 * The business key of this object is the "name". Thus the name must be unique. 
 * @author MarcoSoldati
 *
 */
public class GenericServiceDescriptor implements ServiceDescriptor {
	
	/**
	 * A unique identifier of the service
	 */
	private final String name;
	
	/**
	 * A label for the service
	 */
	private final String label;
	
	/**
	 * A description of the service
	 */
	private final String description;

	/**
	 * The type of this service
	 */
	private final Set<ServiceCapability> capabilities = new HashSet<ServiceCapability>();
	
	/**
	 * Create a descriptor for a service.
	 * @param name the name of the service, must not be null.
	 * @param label the label of the service. Will match the name if null.
	 * @param description the description of the service. May be null.
	 * @param capabilities the capabilities assigned with this service. Ignored if null. More capabilities can be added later.
	 * 
	 */
	public GenericServiceDescriptor(String name, String label, String description, ServiceCapability ... capabilities) {
		AssertUtil.assertArgumentHasText(name, "name");
		this.name = name;
		this.label = label == null ? name : label;
		this.description = description;
		if (capabilities != null && capabilities.length > 0) {
		    Collections.addAll(this.capabilities, capabilities);
		}
	}

	/**
	 * Create a descriptor for a service.
	 * @param name the name of the service, must not be null.
	 * @param label the label of the service. Will match the name if null.
	 * @param description the description of the service. May be null.
	 * @param capabilities the capabilities assigned with this service. Ignored if null.
	 */
	public GenericServiceDescriptor(String name, String label, String description, Collection<ServiceCapability> capabilities) {
	    AssertUtil.assertArgumentHasText(name, "name");
	    this.name = name;
	    this.label = label == null ? name : label;
	    this.description = description;
	    if (capabilities != null && !capabilities.isEmpty()) {
            this.capabilities.addAll(capabilities);
        }
	}
	
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}
	
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#getType()
	 */
	@Override
	public Set<ServiceCapability> getCapabilities() {
		return Collections.unmodifiableSet(capabilities);
	}
	
	   
    /**
     * Add a capability to this entry.
     * @param capability the capability to add
     * @return true if the capability did not already exist, false otherwise.
     */
    @Override
    public boolean addCapability(ServiceCapability capability) {
        if (capability != null) {
            return this.capabilities.add(capability);
        }
        return false;
    }
    
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GenericServiceDescriptor))
			return false;
		return name.equals(((GenericServiceDescriptor)obj).name);
	}
	
	/* (non-Javadoc)
	 * @see eu.heliovo.clientapi.registry.HelioServiceDescriptor#hashCode()
	 */
	@Override
	public int hashCode() {
		return 37 * name.hashCode();
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("GenericHelioServiceDescriptor: [");
	    sb.append("name: ").append(name);
	    if (label != null) {
	        sb.append(", label: ").append(label);
	    }
	    sb.append(", capabilities: " + capabilities);
	    sb.append("]");
	    return sb.toString();
	}
}