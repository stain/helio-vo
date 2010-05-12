package eu.heliovo.clientapi.query;

import eu.heliovo.clientapi.result.HelioJob;
import eu.heliovo.clientapi.result.HelioResultSet;

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
	public HelioResultSet querySync(String select, String from, String where);
	
	/**
	 * 
	 * @param select
	 * @param from
	 * @param where
	 * @return
	 */
	public HelioJob queryASync(String select, String from, String where);
}
