package eu.heliovo.registryclient.impl;


/**
 * Component that can filter certain registry entries to be banned from a specific application.
 * @author MarcoSoldati
 *
 */
public interface RegistryEntryFilter {
    /**
     * Put a veto on the registration of a given capability
     * @param capability the capability to be checked
     * @return true if the capability should be filtered, false if it can be used.
     */
    public boolean filterAccessUrl(String accessUrl);
    
}
