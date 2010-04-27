package org.egso.votable.element;


/**
 *  InvalidValueException occures when an invalid value is set for an Attribute.
 *
 * @author     Romain Linsolas (linsolas@gmail.com)
 * @version 1.0
 * @created    24 October 2003
 */
public class InvalidValueException extends RuntimeException {

	/**
	 *  Construction of an InvalidValueException.
	 *
	 * @param  message Description of the exception.
	 */
	public InvalidValueException(String message) {
		super(message);
	}

}
