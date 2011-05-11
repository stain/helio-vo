package eu.heliovo.clientapi.registry;


/**
 * Types of known HELIO Services
 * @author MarcoSoldati
 *
 */
public enum HelioServiceCapability {
    
	/**
	 * Services that implement the long running query service interface
	 */
	ASYNC_QUERY_SERVICE ("longhqi:0.2", "ivo://helio-vo.eu/std/LongFullQuery/v0.2"),

	/**
	 * Synchronous helio query services
	 */
	SYNC_QUERY_SERVICE("hqi:0.2","ivo://helio-vo.eu/std/FullQuery/v0.2"),

	/**
	 * provider of a VOSI cababilites table.
	 */
	VOSI_CAPABILITIES(null, "ivo://ivoa.net/std/VOSI#capabilities"),
	
	/**
	 * Provider of a VOSI tables table.
	 */
	VOSI_TABLES(null, "ivo://org.astrogrid/std/VOSI/v0.3#tables"),
	
	/**
	 * CEA service
	 */
	COMMON_EXECUTION_ARCHITECTURE_SERVICE("cea:1.0", "ivo://org.astrogrid/std/CEA/v1.0"),
	
	/**
	 * A registry service
	 */
	REGISTRY_SERVICE("registry", "ivo://ivoa.net/std/Registry"),
	
	/**
	 * Unknown service type
	 */
	UNKNOWN (null, null);
		
	/**
	 * Id of the capability.
	 */
	private final String capabilityId;

	/**
	 * Create a service capability.
	 * @param capabilityShort the short form of the capability.
	 * @param capabilityId the identifier of the capability.
	 */
    private HelioServiceCapability(String capabilityShort, String capabilityId) {
        this.capabilityId = capabilityId;
    }
    
    /**
     * Get the capabilities by id.
     * @param capabilityId
     * @return
     */
    public static HelioServiceCapability findCapabilityById(String capabilityId) {
        for (HelioServiceCapability cap : values()) {
            if (capabilityId.equals(cap.capabilityId)) {
                return cap;
            }
        }
        return null;
    }
}
