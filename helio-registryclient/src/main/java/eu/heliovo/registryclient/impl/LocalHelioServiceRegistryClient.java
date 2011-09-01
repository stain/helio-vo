package eu.heliovo.registryclient.impl;

import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Data access object to get access a local search registry implementation.
 * The local implementation is configured through a properties file.
 * @author MarcoSoldati
 *
 */
class LocalHelioServiceRegistryClient extends AbstractHelioServiceRegistryClient {
    /**
	 * Public constructor is package local for testing
	 */
	LocalHelioServiceRegistryClient() {
	    init();
	}

	/**
	 * Init the registry. Try to load local configuration file, if this does not exist create it
	 * with default values and then load it. 
	 */
	private void init() {
	    // populate registry
	    register(HelioServiceName.HEC, ServiceCapability.ASYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-hec/HelioLongQueryService?wsdl");
	    register(HelioServiceName.HEC, ServiceCapability.SYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-hec/HelioService?wsdl");

	    register(HelioServiceName.UOC, ServiceCapability.ASYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-uoc/HelioLongQueryService?wsdl");
	    register(HelioServiceName.UOC, ServiceCapability.SYNC_QUERY_SERVICE, "http://festung1.oats.inaf.it:8080/helio-uoc/HelioService?wsdl");

	    register(HelioServiceName.DPAS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioLongQueryService?wsdl");
	    register(HelioServiceName.DPAS, ServiceCapability.SYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioService?wsdl");

	    register(HelioServiceName.ICS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-ics/HelioLongQueryService?wsdl");
	    register(HelioServiceName.ICS, ServiceCapability.SYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-ics/HelioService?wsdl");

	    register(HelioServiceName.ILS, ServiceCapability.ASYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-ils/HelioLongQueryService?wsdl");
	    register(HelioServiceName.ILS, ServiceCapability.SYNC_QUERY_SERVICE, "http://msslxw.mssl.ucl.ac.uk:8080/helio-ils/HelioService?wsdl");

	    register(HelioServiceName.MDES, ServiceCapability.ASYNC_QUERY_SERVICE, "http://manunja.cesr.fr/Amda-Helio/WebServices/HelioLongQueryService.wsdl");      
	    register(HelioServiceName.MDES, ServiceCapability.SYNC_QUERY_SERVICE, "http://manunja.cesr.fr/Amda-Helio/WebServices/HelioService.wsdl");       
	}

    private void register(HelioServiceName serviceName, ServiceCapability capability, String wsdlFile) {
        registerServiceInstance(serviceName, null, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, capability, HelioFileUtil.asURL(wsdlFile)));
    }
}
