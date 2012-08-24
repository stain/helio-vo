package eu.heliovo.registryclient.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eu.heliovo.registryclient.RegistryProperties;
import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Properties for the HELIO registry
 * @author MarcoSoldati
 *
 */
public class DefaultRegistryProperties extends HashMap<String, String> implements RegistryProperties {
    /**
     * 
     */
    private static final long serialVersionUID = -6794302295278644678L;

    /**
     * the properties file of the registry.
     */
    private static final String REGISTRY_FILE_NAME = "registry.properties";

    /**
     * Hide the default constructor
     */
    public DefaultRegistryProperties(String appId) {
        
        // load the properties from a properties file.
        Properties props = new Properties();
        File propertiesDir = new HelioFileUtil(appId).getHelioHomeDir("registry");
        File propertiesFile = new File(propertiesDir, REGISTRY_FILE_NAME);
        
        if (propertiesFile.exists()) {
            try {
                props.load(new FileReader(propertiesFile));
                // add loaded props to this map.
                for (Map.Entry<Object, Object> prop : props.entrySet()) {
                    this.put((String)prop.getKey(), (String)prop.getValue());
                }
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("Unable to load properties file " + e.getMessage(), e);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load properties file " + e.getMessage(), e);
            }            
        }
    }
    
    @Override
    public String getProperty(String property) {
        return getProperty(property, null);
    }
    
    @Override
    public String getProperty(String property, String defaultValue) {
        String val = get(property);
        return val == null ? defaultValue : val;
    }
}
