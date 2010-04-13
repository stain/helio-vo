/*
 * Created on Jul 2, 2004
 */
package org.egso.comms.eis.adapter;

/**
 * Exception indicating a <code>RequestHandler</code> failed to
 * process a <code>Request</code>.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.RequestHandler
 */
@SuppressWarnings("serial")
public class RequestHandlerException extends AdapterException {

    public RequestHandlerException() {
        super();
    }

    public RequestHandlerException(String message) {
        super(message);
    }

    public RequestHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestHandlerException(Throwable cause) {
        super(cause);
    }

}