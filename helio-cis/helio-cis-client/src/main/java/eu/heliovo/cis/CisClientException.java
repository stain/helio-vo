package eu.heliovo.cis;

/**
 * Wrapper exception for all exception that may be thrown by the {@link CisClient}. 
 *
 */
public class CisClientException extends Exception 
{
    /**
     * the serial id.
     */
    private static final long serialVersionUID = 2812265647490833828L;

    /**
     * Default constructor for a client exception.
     */
    public CisClientException() {
    }
    
    /**
     * Provide a messag.e
     * @param message the message.
     */
    public CisClientException(String message) {
        super(message);
    }
    
    /**
     * Wrap underlying exception
     * @param e the wrapped exception
     */
    public CisClientException(Exception e) {
        super(e);
    }

    /**
     * Provide message and wrapped exception
     * @param message the message
     * @param e the wrappend exception
     */
    public CisClientException(String message, Exception e) {
        super(message, e);
    }
}
