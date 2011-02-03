package eu.heliovo.registryclient;

/**
 * Simple exception that indicates something went wrong when looking things up
 * in the registry.
 * 
 * @author Donal Fellows
 */
public class LookupFailedException extends Exception {
	public LookupFailedException(String reason) {
		super(reason);
	}

	public LookupFailedException(String reason, Throwable cause) {
		super(reason, cause);
	}
}