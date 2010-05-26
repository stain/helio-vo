package eu.heliovo.clientapi.result;

import java.io.InputStream;
import java.util.logging.LogRecord;

/**
 * Container object for a result set. 
 */
public interface HelioResultSet {

	/**
	 * Return the unique id of this result set. This id can be further used to query
	 * the system for meta information.
	 * @return the id. 
	 */
	public ResultId getResultId();
	
	/**
	 * Download the data as VOTable 
	 * @return the data as voTable
	 */
	public InputStream asVOTable();
	
	/**
	 * Download the data as VOTable and covert it to the Helio object model
	 * @return the data as object model. TODO: change return type to object model root. 
	 */
	public Object asObjectModel();
	
	/**
	 * Get log messages that are or particular interest for the user.
	 * This information is not intended for tracing and debugging and should be keept small and clean. 
	 * @return the user log messages.
	 */
	public LogRecord[] getUserLogs();


}
