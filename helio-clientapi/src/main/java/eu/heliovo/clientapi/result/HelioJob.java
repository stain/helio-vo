package eu.heliovo.clientapi.result;

import java.util.concurrent.Future;

/**
 * Future that provides control over asynchronous calls to Helio. 
 * @author marco soldati at fhnw ch
 */
public interface HelioJob extends Future<HelioResultSet> {
	
	/**
	 * Return the unique id of this result set. This id can be further used to query
	 * the system for meta information.
	 * @return
	 */
	public String getId();

}
