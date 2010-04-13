/*
 * Created on May 4, 2004
 */
package org.egso.comms.eis.adapter;

/**
 * Exception indicating an expected <code>RequestHandler</code> was
 * missing.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class MissingHandlerException extends AdapterException {

    public MissingHandlerException() {
        super();
    }

    public MissingHandlerException(String message) {
        super(message);
    }

    public MissingHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingHandlerException(Throwable cause) {
        super(cause);
    }

}