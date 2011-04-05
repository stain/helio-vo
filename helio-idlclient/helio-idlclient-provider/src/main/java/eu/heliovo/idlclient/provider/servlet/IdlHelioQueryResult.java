package eu.heliovo.idlclient.provider.servlet;

import java.util.logging.LogRecord;

/**
 * Container object for a set of results for IDL.
 * @author Matthias Meyer
 *
 */
public class IdlHelioQueryResult {
	
	private String url;
	
	private LogRecord[] log;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setLog(LogRecord[] log) {
		this.log = log;
	}

	public LogRecord[] getLog() {
		return log;
	}
}
