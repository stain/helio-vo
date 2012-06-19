package eu.heliovo.registryclient.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.shared.props.HelioFileUtil;
import eu.heliovo.shared.util.DataReaderUtil;
import eu.heliovo.shared.util.FileUtil;

/**
 * Registry client that reads the registry values from a delimited file.
 * @author MarcoSoldati
 *
 */
class ConfigFileServiceRegistryClient extends AbstractHelioServiceRegistryClient {
    /**
     * name of the file that defines the registry content.
     */
    private static final String REGISTRY_TXT = "registry.txt";
    private final String appId;

    /**
	 * Public constructor is package local for testing
	 * @param appId the id of the application. 
	 */
	ConfigFileServiceRegistryClient(String appId) {
        this.appId = appId;
	}

	/**
	 * Init the registry. Try to load local configuration file, if this does not exist create it
	 * with default values and then load it. 
	 */
	public void init() {
	    File registryDir = new HelioFileUtil(appId).getHelioHomeDir("registry");
	    File registryFile = new File(registryDir, REGISTRY_TXT);
	    if (!registryFile.exists()) {
	        throw new RuntimeException("Unable to locate registry file " + registryFile);
	    }
	    FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(registryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to locate registry file " + registryFile + ". Cause: " + e.getMessage(), e);
        }
        
	    DataReaderUtil util = new DataReaderUtil(fileInputStream);
	    for (Object[] config : util.getDataCollection()) {
	        register(config);
        };
	    
	    // populate registry
	    register(HelioServiceName.HEC, ServiceCapability.ASYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-hec/HelioLongQueryService?wsdl");
	    register(HelioServiceName.HEC, ServiceCapability.SYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-hec/HelioService?wsdl");

	    register(HelioServiceName.UOC, ServiceCapability.ASYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-uoc/HelioLongQueryService?wsdl");
	    register(HelioServiceName.UOC, ServiceCapability.SYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-uoc/HelioService?wsdl");

	    register(HelioServiceName.DPAS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://localhost:8080/helio-dpas/HelioLongQueryService?wsdl");
	    register(HelioServiceName.DPAS, ServiceCapability.SYNC_QUERY_SERVICE, "http://localhost:8080/helio-dpas/HelioService?wsdl");

	    register(HelioServiceName.ICS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://localhost:8080/helio-ics/HelioLongQueryService?wsdl");
	    register(HelioServiceName.ICS, ServiceCapability.SYNC_QUERY_SERVICE, "http://localhost:8080/helio-ics/HelioService?wsdl");

	    register(HelioServiceName.ILS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://localhost:8080/helio-ils/HelioLongQueryService?wsdl");
	    register(HelioServiceName.ILS, ServiceCapability.SYNC_QUERY_SERVICE, "http://localhost:8080/helio-ils/HelioService?wsdl");

	    register(HelioServiceName.DES, ServiceCapability.ASYNC_QUERY_SERVICE, "http://manunja.cesr.fr/Amda-Helio/WebServices/HelioLongQueryService.wsdl");      
	    register(HelioServiceName.DES, ServiceCapability.SYNC_QUERY_SERVICE, "http://manunja.cesr.fr/Amda-Helio/WebServices/HelioService.wsdl");       
	}

	/**
	 * Delegates registration to {@link #register(HelioServiceName, ServiceCapability, String)}.
	 * @param config the config to register.
	 */
	private void register(Object[] config) {
	    register(HelioServiceName.valueOf((String)config[0]), ServiceCapability.valueOf((String)config[1]), (String)config[2]);
	}
	
	/**
	 * Register a standalone service.
	 * @param serviceName the name of the service
	 * @param capability the capability
	 * @param wsdlFile the WSDL file.
	 */
    private void register(HelioServiceName serviceName, ServiceCapability capability, String wsdlFile) {
        registerServiceInstance(serviceName, null, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, capability, FileUtil.asURL(wsdlFile)));
    }
}
