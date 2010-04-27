/*
 * Created on Mar 1, 2004
 */
package org.egso.comms.eis.adapter;

/**
 * Exception superclass for package.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class AdapterException extends Exception {

    public AdapterException() {
        super();
    }

    public AdapterException(String message) {
        super(message);
    }

    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdapterException(Throwable cause) {
        super(cause);
    }

}