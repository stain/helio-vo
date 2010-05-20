package eu.heliovo.clientapi.query;

import eu.heliovo.clientapi.result.HelioJob;

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
	public HelioJob querySync(String select, String from, String where);
	
	/**
	 * 
	 * @param select
	 * @param from
	 * @param where
	 * @return
	 */
	public HelioJob queryAsync(String select, String from, String where);
}
