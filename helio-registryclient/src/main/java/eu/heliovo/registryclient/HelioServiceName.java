package eu.heliovo.registryclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Dynamic enumeration of well known HELIO service names.
 * The enum can be extended at runtime (e.g through the registry)
 * @author MarcoSoldati
 *
 */
public class HelioServiceName implements Iterable<HelioServiceName> {

    /**
     * Hold the registered service capabilities. Key is the name property.
     */
    private static final Map<String, HelioServiceName> enumMap = new HashMap<String, HelioServiceName>();

    public static final HelioServiceName CXS   = register("CXS", "ivo://helio-vo.eu/cxs");
    public static final HelioServiceName DPAS  = register("DPAS", "ivo://helio-vo.eu/dpas");
    public static final HelioServiceName DES   = register("DES", "ivo://helio-vo.eu/des");
    public static final HelioServiceName HEC   = register("HEC", "ivo://helio-vo.eu/hec");
    public static final HelioServiceName HFC   = register("HFC", "ivo://helio-vo.eu/hfc");
    public static final HelioServiceName ICS   = register("ICS", "ivo://helio-vo.eu/ics");
    public static final HelioServiceName ILS   = register("ILS", "ivo://helio-vo.eu/ils");
    public static final HelioServiceName MDES  = register("MDES", "ivo://helio-vo.eu/mdes");
    public static final HelioServiceName UOC   = register("UOC", "ivo://helio-vo.eu/uoc");
    public static final HelioServiceName SMS   = register("SMS", "ivo://helio-vo.eu/sms");
    
    /**
     * Name of the service
     */
    private final String serviceName;

    /**
     * Unique idenfier for the service.
     */
    private final String serviceId;
    
    /**
     * Create the HelioServiceName constant
     * @param serviceName name of the service
     */
    private HelioServiceName(String serviceName, String serviceId) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }
    
    /**
     * Register a new service name if not already existing. Otherwise return the existing.
     * @param name short name of the service (HEC, DPAS, HFC)
     * @param serviceId id of the service. This is considered the key of the service.
     * @return the existing or new HelioServiceName object.
     */
    public static HelioServiceName register(String name, String serviceId) {
        HelioServiceName serviceName = enumMap.get(serviceId);
        if (serviceName == null) {
            serviceName = new HelioServiceName(name, serviceId);
            enumMap.put(serviceId, serviceName);
        }
        return serviceName;
    }
    
    public static Collection<HelioServiceName> values() {
        return enumMap.values();
    }

    @Override
    public Iterator<HelioServiceName> iterator() {
        return enumMap.values().iterator();
    }
    
    @Override
    public String toString() {
        return serviceName;
    }
    
    /**
     * Common name of the service (such as HEC, DPAS, ...)
     * @return the name
     */
    public String getServiceName() {
        return serviceName;
    }
    
    /**
     * Internal id of the service (such as ivo://helio-vo.eu/hec, ...)
     * @return the id
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Find a service by its name (e.g. HEC, HFC, ...)
     * @param serviceName
     * @return
     */
    public static HelioServiceName valueOf(String serviceName) {
        
        return null;
    }
}
