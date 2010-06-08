package eu.heliovo.mockclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.model.infrastructure.HelioService;
import eu.heliovo.mockclient.query.DpasMockParamQueryImpl;
import eu.heliovo.mockclient.query.HecMockParamQueryImpl;
import eu.heliovo.mockclient.query.IlsMockParamQueryImpl;

public class MockHelioClient implements HelioClient {
	/**
	 * The Map of registered query services.
	 */
	private final Map<String, HelioService> SERVICES = new HashMap<String, HelioService>();

	public MockHelioClient() {
		initialize();
	}
	
	/**
	 * Initialize the mock param query impl with some static data.
	 */
	private void initialize() {
		HecMockParamQueryImpl hec = new HecMockParamQueryImpl();
		SERVICES.put(hec.getName(), hec);
		
		DpasMockParamQueryImpl dpas = new DpasMockParamQueryImpl();
		SERVICES.put(dpas.getName(), dpas);
		
		IlsMockParamQueryImpl ils = new IlsMockParamQueryImpl();
		SERVICES.put(ils.getName(), ils);
	}

	@Override
	public HelioService[] getServices() {
		return SERVICES.values().toArray(new HelioService[SERVICES.values().size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] getServices(Class<T> clazz) throws IllegalArgumentException {
		if (clazz == null) {
			throw new IllegalArgumentException("Argument 'clazz' must not be null.");
		}		
		List<T> services = new ArrayList<T>();
		for (HelioService helioService : SERVICES.values()) {
			if (clazz.isAssignableFrom(helioService.getClass())) {
				services.add((T) helioService);
			}
		}
		return (T[]) services.toArray(new Object[services.size()]);
	}

	@Override
	public HelioService getService(String serviceName) throws IllegalArgumentException {	
		if (serviceName == null) {
			throw new IllegalArgumentException("Argument 'serviceName' must not be null.");
		}		

		return SERVICES.get(serviceName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getService(String serviceName, Class<T> clazz) throws IllegalArgumentException {
		if (clazz == null) {
			throw new IllegalArgumentException("Argument 'clazz' must not be null.");
		}		

		HelioService service = getService(serviceName);
		if (service == null) {
			return null;
		} else if (clazz.isAssignableFrom(service.getClass())) {
			return (T)service;
		} 
		return null;
	}
}