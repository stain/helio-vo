package eu.heliovo.registryclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import eu.heliovo.shared.util.AssertUtil;

/**
 * Dynamic enumeration of well known HELIO service names. The enum can be
 * extended at runtime (e.g through the registry)
 * 
 * @author MarcoSoldati
 * 
 */
public class HelioServiceName implements Iterable<HelioServiceName> {

	/**
	 * Hold the registered service capabilities. Key is the name property.
	 */
	private static final Map<String, HelioServiceName> enumMap = new HashMap<String, HelioServiceName>();

	/**
	 * The context service.
	 */
	public static final HelioServiceName CXS = register("CXS", "ivo://helio-vo.eu/cxs");
	
	/**
	 * The data provider service.
	 */
	public static final HelioServiceName DPAS = register("DPAS", "ivo://helio-vo.eu/dpas");
	
	/**
	 * The Data evaluation service.
	 */
	public static final HelioServiceName DES = register("DES", "ivo://helio-vo.eu/mdes");
	
	/**
	 * The HELIO event catalogue.
	 */
	public static final HelioServiceName HEC = register("HEC", "ivo://helio-vo.eu/hec");
	
	/**
	 * The HELIO feature catalogue.
	 */
	public static final HelioServiceName HFC = register("HFC", "ivo://helio-vo.eu/hfc");
	
	/**
	 * The instrument coverage service.
	 */
	public static final HelioServiceName ICS = register("ICS", "ivo://helio-vo.eu/ics");
	
	/**s
	 * The instrument location service.
	 */
	public static final HelioServiceName ILS = register("ILS", "ivo://helio-vo.eu/ils");
	
	/**
	 * The Unified Observation catalogue.
	 */
	public static final HelioServiceName UOC = register("UOC", "ivo://helio-vo.eu/uoc");
	
	/**
	 * The semantic mapping services
	 */
	public static final HelioServiceName SMS = register("SMS", "ivo://helio-vo.eu/sms");
	/**
	 * The Link Provider Service
	 */
	public static final HelioServiceName LPS = register("LPS", "ivo://helio-vo.eu/lps");
	/**
	 * The HELIO Processing Service
	 */
	public static final HelioServiceName HPS = register("HPS", "ivo://helio-vo.eu/hps");

	/**
	 * Name of the service
	 */
	private final String serviceName;

	/**
	 * Unique identifier for the service.
	 */
	private final String serviceId;

	/**
	 * Create the HelioServiceName constant
	 * 
	 * @param serviceName name of the service
	 * @param serviceId internal id of the service (usually starting with 'ivo://helio-vo.eu/'). 
	 *         
	 */
	private HelioServiceName(String serviceName, String serviceId) {
		this.serviceName = serviceName;
		this.serviceId = serviceId;
	}

	/**
	 * Register a new service name if not already existing. Otherwise return the
	 * existing.
	 * 
	 * @param name
	 *            short name of the service (HEC, DPAS, HFC)
	 * @param serviceId
	 *            id of the service. This is considered the key of the service.
	 * @return the existing or new HelioServiceName object.
	 */
	public static HelioServiceName register(String name, String serviceId) {
		AssertUtil.assertArgumentHasText(name, "name");
		AssertUtil.assertArgumentHasText(serviceId, "serviceId");
		HelioServiceName serviceName = enumMap.get(serviceId);
		if (serviceName == null) {
			serviceName = new HelioServiceName(name, serviceId);
			enumMap.put(serviceId, serviceName);
		}
		return serviceName;
	}

	/**
	 * Get all registered values.
	 * @return the values as collection.
	 */
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
	 * 
	 * @return the name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Internal id of the service (such as ivo://helio-vo.eu/hec, ...)
	 * 
	 * @return the id
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * Find a service by its name (e.g. HEC, HFC, ...)
	 * 
	 * @param serviceName
	 * @return Enumerated name.
	 */
	public static HelioServiceName valueOf(String serviceName) {
		AssertUtil.assertArgumentHasText(serviceName, "serviceName");
		for (HelioServiceName helioServiceName : enumMap.values()) {
			if (serviceName.equals(helioServiceName.serviceName)) {
				return helioServiceName;
			}
		}
		return null;
	}
}
