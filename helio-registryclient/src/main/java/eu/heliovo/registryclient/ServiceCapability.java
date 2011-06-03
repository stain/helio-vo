package eu.heliovo.registryclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import eu.heliovo.shared.util.AssertUtil;


/**
 * Capabilities of HELIO Services.
 * @author MarcoSoldati
 *
 */
public class ServiceCapability implements Iterable<ServiceCapability> {
    /**
     * Hold the registered service capabilities. Key is the name property.
     */
    private static final Map<String, ServiceCapability> enumMap = new HashMap<String, ServiceCapability>();

    /**
     * Services that implement the long running query service interface
     */
    public static final ServiceCapability ASYNC_QUERY_SERVICE = register("ASYNC_QUERY_SERVICE", "ivo://helio-vo.eu/std/LongFullQuery/Soap/v1.0");

    /**
     * Synchronous helio query services
     */
    public static final ServiceCapability SYNC_QUERY_SERVICE = register("SYNC_QUERY_SERVICE","ivo://helio-vo.eu/std/FullQuery/Soap/v1.0");

    /**
     * provider of a VOSI cababilites table.
     */
    public static final ServiceCapability VOSI_CAPABILITIES = register("VOSI_CAPABILITIES", "ivo://ivoa.net/std/VOSI#capabilities");
    
    /**
     * Provider of a VOSI tables table.
     */
    public static final ServiceCapability VOSI_TABLES = register("VOSI_TABLES", "ivo://org.astrogrid/std/VOSI/v0.3#tables");
    
    /**
     * CEA service
     */
    public static final ServiceCapability COMMON_EXECUTION_ARCHITECTURE_SERVICE = register("COMMON_EXECUTION_ARCHITECTURE_SERVICE", "ivo://org.astrogrid/std/CEA/v1.0");
    
    /**
     * A registry service
     */
    public static final ServiceCapability REGISTRY_SERVICE = register("REGISTRY_SERVICE", "ivo://ivoa.net/std/Registry");
    
    /**
     * Unknown capability.
     */
    public static final ServiceCapability UNDEFINED = register("UNDEFINED", null);

    
    /**
     * Name of the capability . The name must be unique and is a human readable form of the standardId.
     */
    private final String name;


    /**
     * Id of the capability.
     */
    private final String standardId;

    /**
     * Create a service capability.
     * @param capabilityShort the short form of the capability.
     * @param standardId the identifier of the capability.
     */
    private ServiceCapability(String name, String standardId) {
        AssertUtil.assertArgumentNotNull(name, "name");
        this.name = name;
        this.standardId = standardId;
    }
    
    /**
     * Get the internal name of the service capability.
     * @return the internal name of the service capability.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the standard id of a service as defined in the registry.
     * @return the standard id.
     */
    public String getStandardId() {
        return standardId;
    }
    
    /**
     * Register a service capability if it's not already there.
     * @param name the name of the capability. must not be null. primary key.
     * @param standardId the id of the capability. May be null.
     * @return the service capability either from the map or newly created.
     */
    public static synchronized ServiceCapability register(String name, String standardId) {
        ServiceCapability serviceCapability = enumMap.get(name);
        if (serviceCapability == null) {
            serviceCapability = new ServiceCapability(name, standardId);
            enumMap.put(name, serviceCapability);
        }
        return serviceCapability;
    }

    /**
     * Find the service capability by standardId.
     * @param standardId the standardId
     * @return the service capability.
     */
    public static ServiceCapability findServiceCapabilityByStandardId(String standardId) {
        if (standardId == null) {
            return UNDEFINED;
        }
        
        for (ServiceCapability cap : values()) {
            if (standardId.equals(cap.standardId)) {
                return cap;
            }
        }
        return null;
    }

    public static Collection<ServiceCapability> values() {
        return enumMap.values();
    }

    @Override
    public Iterator<ServiceCapability> iterator() {
        return enumMap.values().iterator();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
