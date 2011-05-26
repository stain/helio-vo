package model;

import java.util.logging.LogRecord;

import eu.heliovo.clientapi.query.HelioQueryResult;

/**
 * Container object for a set of results for IDL.
 * @author Matthias Meyer
 *
 */
public class IdlHelioQueryResult {
	
	private final HelioQueryResult helioQueryResult;

	public IdlHelioQueryResult(HelioQueryResult helioQueryResult) {
		this.helioQueryResult = helioQueryResult;
	}
	
	public String getUrl() {
		return helioQueryResult.asURL().toString();
	}

	public LogRecord[] getLog() {
		getUrl();
		return helioQueryResult.getUserLogs();
	}
}
