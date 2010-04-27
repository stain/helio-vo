package org.egso.common;

import org.apache.commons.configuration.Configuration;
import org.egso.common.configuration.NamedConfigurationFactory;

/**
 * Set of Constants that are used by multiple subpackages of common. 
 * @author Marco Soldati
 * @created 21.12.2004
 */
public class CommonConfig
{
    public static final String COMMON_CONFIG = "common-utils";
    
    /**
     * get the configuration object
     */
    public static final Configuration configuration = NamedConfigurationFactory.addNamedConfiguration(
            CommonConfig.COMMON_CONFIG,
            "/org/egso/common/conf", 
            "common", 
            "utils"); 
    
}
