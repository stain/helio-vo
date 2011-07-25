package eu.heliovo.registryclient;

/**
 * Enumeration of well known HELIO service names.
 * @author MarcoSoldati
 *
 */
public enum HelioServiceName {
    CXS  ("ivo://helio-vo.eu/cxs"),
    DPAS ("ivo://helio-vo.eu/dpas"),
    DES  ("ivo://helio-vo.eu/des"),
    HEC  ("ivo://helio-vo.eu/hec"),
    HFC  ("ivo://helio-vo.eu/hfc"),
    ICS  ("ivo://helio-vo.eu/ics"),
    ILS  ("ivo://helio-vo.eu/ils"),
    MDES ("ivo://helio-vo.eu/mdes"),
    UOC  ("ivo://helio-vo.eu/uoc"),
    SMS  ("ivo://helio-vo.eu/sms"),
    
    ;
    
    /**
     * Name of the service
     */
    private final String serviceName;

    /**
     * Create the HelioServiceName constant
     * @param serviceName name of the service
     */
    private HelioServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    @Override
    public String toString() {
        return serviceName;
    }

    public String getName() {
        return serviceName;
    }
}
