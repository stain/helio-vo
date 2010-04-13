/*
 * Created on May 4, 2004
 */
package org.egso.comms.eis.adapter;

/**
 * Exception indicating a server did not respond to a
 * <code>Request</code> within a time bound.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class TimeoutException extends AdapterException {

    public TimeoutException() {
        super();
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

}