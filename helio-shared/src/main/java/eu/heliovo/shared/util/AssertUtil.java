package eu.heliovo.shared.util;

/**
 * Utility methods for validity checks of method calls.  
 * @author marco soldati at fhnw ch
 *
 */
public class AssertUtil {
    /**
     * Throw an {@link IllegalArgumentException} if 'object' is null.
     * @param object the object.
     * @param objectName the name of the object for user feedback.
     * @throws IllegalArgumentException if object is null.
     */
    public static void assertNotNull(Object object, String objectName) throws IllegalArgumentException {
        if (object == null)
            throw new IllegalArgumentException("Configuration Error: " + objectName + " must not be null");
    }

    /**
     * Throw an {@link IllegalArgumentException} if 'argument' is null.
     * @param argument the argument.
     * @param argumentName the name of the argument for user feedback.
     * @throws IllegalArgumentException if argument is null.
     */
    public static void assertArgumentNotNull(Object argument, String argumentName) throws IllegalArgumentException {
        if (argument == null)
            throw new IllegalArgumentException("Argument '" + argumentName + "' must not be null");
    }
    
    /**
     * Check if 'text' contains anything.
     * @param text the text to check
     * @param propertyName name of the property to check for user feedback.
     * @throws IllegalArgumentException if(text == null || text.isEmpty())
     */
    public static void assertHasText(String text, String propertyName) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) 
            throw new IllegalArgumentException("Configuration Error: " + propertyName + " must not be empty.");
    }

}
