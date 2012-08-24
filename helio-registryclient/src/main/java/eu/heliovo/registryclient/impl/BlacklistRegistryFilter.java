package eu.heliovo.registryclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import eu.heliovo.registryclient.RegistryProperties;


/**
 * Filters the registry based on a black list, read from the properties.
 * @author MarcoSoldati
 *
 */
public class BlacklistRegistryFilter implements RegistryEntryFilter {

    /**
     * Comma separated list of regular expressions to filter access urls.
     */
    static final String ACCESS_URL_BLACKLIST = "eu.heliovo.registryclient.access_url_blacklist";
    
    /**
     * Properties file for the registry
     */
    private RegistryProperties registryProperties;
        
    /**
     * List of resource patterns
     */
    private List<Pattern> accessUrlBlackList = new ArrayList<Pattern>();

    @Override
    public boolean filterAccessUrl(String accessUrl) {
        for (Pattern pattern : accessUrlBlackList) {
            if (pattern.matcher(accessUrl).matches()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * To be called after all properties have been set.
     */
    public void init() {
        if (registryProperties != null) {
            if (registryProperties.containsKey(ACCESS_URL_BLACKLIST)) {
                String accessUrlBlackListString = (String)registryProperties.get(ACCESS_URL_BLACKLIST);
                String[] accessUrlBlackListStrings = accessUrlBlackListString.split(",");
                for (int i = 0; i < accessUrlBlackListStrings.length; i++) {
                    Pattern pattern = Pattern.compile(accessUrlBlackListStrings[i], Pattern.CASE_INSENSITIVE);
                    accessUrlBlackList.add(pattern);
                }
            }
        } else {
            throw new IllegalStateException("Property 'registryProperties' has not been set.");
        }
    }
    
    /**
     * @return the registryProperties
     */
    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    /**
     * @param registryProperties the registryProperties to set
     */
    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }
}
