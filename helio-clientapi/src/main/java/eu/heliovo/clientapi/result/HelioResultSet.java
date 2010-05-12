package eu.heliovo.clientapi.result;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.logging.LogRecord;

/**
 * Container object for a result set. 
 */
public interface HelioResultSet {

	/**
	 * Get the id of this transaction. 
	 * @return
	 */
	public String getTransactionId();
	
	/**
	 * Get the URI of this result set.  The URI uniquely points to the 
	 * result. The URI   
	 * @return
	 */
	public URI getURI();
	
	/**
	 * Download the data as VOTable 
	 * @return the data as voTable
	 */
	public InputStream asVOTable();
	
	/**
	 * Download the data as VOTable and covert it to the Helio object model
	 * @return the data as object model.
	 */
	public Collection<Object> asObjectModel();
	
	/**
	 * Get log messages that are or particular interest for the user.
	 * This information is not intended for tracing and debugging and should be keept small and clean. 
	 * @return the user log messages.
	 */
	public LogRecord[] getUserLogs();


}
