package eu.heliovo.clientapi.model.infrastructure;

import eu.heliovo.clientapi.model.HelioResource;

/**
 * Description of a Helio service.
 * @author marco soldati at fhnw ch
 *
 */
public interface HelioService extends HelioResource {
	
	public interface HelioServiceMethod {
		
		public interface HelioMethodParam{
			public String getParamName();
		}
		
		public String getMethodName();
	}
	
	/**
	 * The name of the service
	 */
	public String getServiceName();
	
	public HelioServiceMethod[] getParameter();
}
