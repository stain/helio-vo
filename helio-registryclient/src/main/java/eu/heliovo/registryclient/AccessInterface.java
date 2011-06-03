package eu.heliovo.registryclient;

import java.net.URL;

/**
 * Descriptor of an access interface for a given service.
 * @author MarcoSoldati
 *
 */
public interface AccessInterface {
    /**
     * Get the URL assigned with this access interface
     * @return
     */
    public URL getUrl();
    
    /**
     * Get the type of the interface.
     * @return the interface type.
     */
    public AccessInterfaceType getInterfaceType();
    
    /**
     * Get the capability associated with this access interface.
     * @return the service capability
     */
    public ServiceCapability getCapability();
}
