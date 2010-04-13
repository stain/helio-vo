package org.egso.comms.nds.conf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.substitution.MultiVariableExpander;
import org.apache.commons.digester.substitution.VariableSubstitutor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Factory class for <code>Configuration</code> objects.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class ConfigurationFactory {

    // Logging

    private static Logger logger = LogManager.getLogger(ConfigurationBase.class);

    // Constants
    
    private static final String NDS_CONFIGURATION = "nds.xml";

    // Factory methods

    public static synchronized Configuration createConfiguration() throws ConfigurationException {
        URL configurationURL = ConfigurationBase.class.getResource(NDS_CONFIGURATION);
        if (configurationURL == null) {
            throw new ConfigurationException("Failed to resolve configuration URL, could not get resource, resource: " + NDS_CONFIGURATION);
        } else {
            return createConfiguration(configurationURL);
        }
    }

    public static synchronized Configuration createConfiguration(URL configurationURL) throws ConfigurationException {
        ConfigurationBase configuration = new ConfigurationBase();
        Digester digester = new Digester();
        MultiVariableExpander expander = new MultiVariableExpander();
        expander.addSource("$", System.getProperties());
        digester.setSubstitutor(new VariableSubstitutor(expander));
        digester.setNamespaceAware(true);
        Converter converter = new Converter();
        ConvertUtils.register(converter, File.class);
        ConvertUtils.register(converter, URI.class);
        ConvertUtils.register(converter, URL.class);
        digester.push(configuration);
        digester.addBeanPropertySetter("configuration/primaryNDS/endpoint", "primaryNDSEndpoint");
        digester.addBeanPropertySetter("configuration/primaryNDS/updateInterval", "primaryNDSUpdateInterval");
        digester.addBeanPropertySetter("configuration/hibernate/connection/url", "hibernateConnectionURL");
        digester.addBeanPropertySetter("configuration/hibernate/connection/username", "hibernateConnectionUsername");
        digester.addBeanPropertySetter("configuration/hibernate/connection/password", "hibernateConnectionPassword");
        digester.addBeanPropertySetter("configuration/hibernate/configurationURL", "hibernateConfigurationURL");
        try {
            logger.info("Parsing configuration, configuration URL: " + configurationURL);
            digester.parse(configurationURL.openStream());
            return configuration;
        } catch (IOException e) {
            throw new ConfigurationException("Failed to parse configuration, configuration URL: " + configurationURL, e);
        } catch (SAXException e) {
            throw new ConfigurationException("Failed to parse configuration, configuration URL: " + configurationURL, e);
        }
    }

    protected static class Converter implements org.apache.commons.beanutils.Converter {

        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            try {
                if (type == File.class) {
                    return new File((String) value);
                } else if (type == URI.class) {
                    return new URI((String) value);
                } else if (type == URL.class) {
                    return new URL((String) value);
                } else {
                    throw new ConversionException("Failed to convert, unsupported type: " + type.getName());
                }
            } catch (MalformedURLException e) {
                throw new ConversionException("Failed to convert URL", e);
            } catch (URISyntaxException e) {
                throw new ConversionException("Failed to convert URI", e);
            }

        }

    }

}