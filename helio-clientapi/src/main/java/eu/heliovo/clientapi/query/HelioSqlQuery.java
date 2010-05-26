package eu.heliovo.clientapi.query;

import eu.heliovo.clientapi.result.HelioResultSetFuture;

/**
 * Perform advanced queries by issuing SQL statements.
 * @author marco soldati at fhnw ch
 *
 */
public interface HelioSqlQuery {
	
	/**
	 * 
	 * @param select
	 * @param from
	 * @param where
	 * @return
	 */
	public HelioResultSetFuture querySync(String select, String from, String where);
	// vs public HelioResultSet querySync(String select, String from, String where);
	
	/**
	 * 
	 * @param select
	 * @param from
	 * @param where
	 * @return
	 */
	public HelioResultSetFuture queryAsync(String select, String from, String where);
}
