package eu.heliovo.registryclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Properties for the HELIO registry
 * @author MarcoSoldati
 *
 */
public class RegistryProperties extends Properties {
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
    public RegistryProperties(String appId) {
        File propertiesDir = new HelioFileUtil(appId).getHelioHomeDir("registry");
        File propertiesFile = new File(propertiesDir, REGISTRY_FILE_NAME);
        
        if (propertiesFile.exists()) {
            try {
                this.load(new FileReader(propertiesFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Unable to load properties file " + e.getMessage(), e);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load properties file " + e.getMessage(), e);
            }            
        }
    }
}
