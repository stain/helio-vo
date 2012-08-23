package eu.heliovo.clientapi.config.catalog.propertyhandler;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import eu.heliovo.clientapi.config.ConfigurablePropertyDescriptor;
import eu.heliovo.clientapi.config.HelioPropertyHandler;
import eu.heliovo.clientapi.model.DomainValueDescriptor;
import eu.heliovo.clientapi.model.DomainValueDescriptorUtil;
import eu.heliovo.clientapi.model.service.HelioService;
import eu.heliovo.clientapi.query.QueryService;
import eu.heliovo.registryclient.HelioServiceName;

/**
 * Property handler for the HEC from value.
 * @author MarcoSoldati
 *
 */
public class IcsFromPropertyHandler implements HelioPropertyHandler {
    
    /**
     * Name of the service
     */
    private static final HelioServiceName SERVICE_NAME = HelioServiceName.ICS;
    
    /**
     * Name of the service variant
     */
    private static final String SERVICE_VARIANT = null;

    /**
     * Name of the property
     */
    private static final String PROPERTY_NAME = "from";
    
    /**
     * Cache the loaded property descriptor.
     */
    private ConfigurablePropertyDescriptor<List<String>> propertyDescriptor;
    
    @Override
    public HelioServiceName getHelioServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public String getServiceVariant() {
        return SERVICE_VARIANT;
    }

    @Override
    public String getPropertyName() {
        return PROPERTY_NAME;
    }

    // init the HEC configuration
    @Override
    public void init() {
        ConfigurablePropertyDescriptor<List<String>> propDescriptor;
        try {
            propDescriptor = new ConfigurablePropertyDescriptor<List<String>>(getPropertyName(), QueryService.class);
        } catch (IntrospectionException e) {
            throw new RuntimeException("Internal Error: Unable to create 'from' property: " + e.getMessage(), e);
        }
        
        Collection<DomainValueDescriptor<String>> domainValues = new HashSet<DomainValueDescriptor<String>>();
        domainValues.add(DomainValueDescriptorUtil.asDomainValue("instrument", "Instrument", null));
        domainValues.add(DomainValueDescriptorUtil.asDomainValue("observatory", "Observatory", null));
        domainValues.add(DomainValueDescriptorUtil.asDomainValue("flybys", "Flybys", null));
        propDescriptor.setValueDomain(domainValues);
        this.propertyDescriptor = propDescriptor;
    }
    
    @Override
    public ConfigurablePropertyDescriptor<List<String>> getPropertyDescriptor(HelioService helioService) {
        return propertyDescriptor;
    }
}
