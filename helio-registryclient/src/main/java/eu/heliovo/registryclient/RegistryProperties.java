package eu.heliovo.registryclient;

import java.util.Map;

/**
 * The registry properties is just a simple map
 * @author MarcoSoldati
 *
 */
public interface RegistryProperties extends Map<String, String> {
    /**
     * Load a property value
     * @param property the property key.
     * @return the value or null if not found.
     */
    public String getProperty(String property);
    
    /**
     * Load a property value
     * @param property the property key
     * @param defaultValue the default value if property cannot be found
     * @return the value or the default value
     */
    public String getProperty(String property, String defaultValue);
}
