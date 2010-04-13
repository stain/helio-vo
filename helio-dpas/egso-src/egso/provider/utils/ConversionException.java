package org.egso.provider.utils;


/**
 * The class ConversionException is thrown when an error occurs during a date or
 * time conversion operation.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.1-jd - 29/11/2003 [04/03/2002]
 */
@SuppressWarnings("serial")
public class ConversionException extends RuntimeException {

	/**
	 * Builds a ConversionException.
	 */
	public ConversionException() {
		super();
	}


	/**
	 * Builds a ConversionException with a specific error message.
	 *
	 * @param message  The error message.
	 */
	public ConversionException(String message) {
		super(message);
	}

}

