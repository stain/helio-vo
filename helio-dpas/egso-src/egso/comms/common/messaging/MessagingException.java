/*
 * Created on Mar 1, 2004
 */
package org.egso.comms.common.messaging;

/**
 * Exception supertype for package.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class MessagingException extends Exception {

    public MessagingException() {
        super();
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingException(Throwable cause) {
        super(cause);
    }

}