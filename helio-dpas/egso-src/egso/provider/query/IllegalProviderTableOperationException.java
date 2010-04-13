package org.egso.provider.query;


@SuppressWarnings("serial")
public class IllegalProviderTableOperationException extends RuntimeException {

	public IllegalProviderTableOperationException() {
		super();
	}


	public IllegalProviderTableOperationException(String message) {
		super(message);
	}


	public IllegalProviderTableOperationException(Throwable cause) {
		super(cause);
	}


	public IllegalProviderTableOperationException(String message, Throwable cause) {
		super(message, cause);
	}


}

