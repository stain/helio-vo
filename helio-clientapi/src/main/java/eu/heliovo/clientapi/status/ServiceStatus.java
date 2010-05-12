package eu.heliovo.clientapi.status;

/**
 * Data container that holds status information from the monitor.
 * @author marco soldati at fhnw ch
 *
 */
public interface ServiceStatus {
	
	/**
	 * Return the id of the monitored service
	 * @return the service id. Must not be null.
	 */
	public String getServiceId();
	
	/**
	 * the service.
	 */
}
