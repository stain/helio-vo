package eu.heliovo.registryclient.impl;

import java.io.IOException;
import java.util.List;

import uk.ac.starlink.registry.BasicResource;

/**
 * Write the registry to a local place
 * @author MarcoSoldati
 *
 */
public interface RegistryPersister {
    /**
     * Serialize the registry to disk
     * @param allServices
     */
    public void persistRegistry(List<BasicResource> allServices) throws IOException;
    
    /**
     * Load the registry from disk
     * @return the registry content, never null.
     * @throws IOException in case the registry cannot be read or if it does not exist.
     */
    public List<BasicResource> loadRegistry() throws IOException;
    
}
