package eu.heliovo.shared.util;

import java.util.Collection;

/**
 * Utility methods for validity checks of method calls.  
 * @author marco soldati at fhnw ch
 *
 */
public class AssertUtil {
    /**
     * message for arguments that are null.
     */
	private static final String ASSERT_ARGUMENT_NOT_NULL = "Argument '%1$s' must not be null.";
	/**
	 * message for arguments that have no text.
	 */
	private static final String ASSERT_ARGUMENT_HAS_TEXT = "Argument '%1$s' must not be empty.";
	/**
	 * message for arguments that are empty.
	 */
	private static final String ASSERT_ARGUMENT_NOT_EMPTY = "Argument '%1$s' must not be null or empty.";
	/**
	 * message for arguments that are not equal.
	 */
    private static final String ASSERT_ARGUMENT_NOT_EQUAL = "Argument '%1$s' must be equal to '%2$s', but is '%3$s'.";
	
    /**
     * Throw an {@link IllegalArgumentException} if 'argument' is null.
     * @param argument the argument.
     * @param argumentName the name of the argument for user feedback.
     * @throws IllegalArgumentException if argument is null.
     */
    public static void assertArgumentNotNull(Object argument, String argumentName) throws IllegalArgumentException {
        if (argument == null)
            throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_NOT_NULL, argumentName));
    }
    
    /**
     * Check if 'text' contains anything.
     * @param text the text to check
     * @param argumentName name of the argument to check for user feedback.
     * @throws IllegalArgumentException if(text == null || text.isEmpty())
     */
    public static void assertArgumentHasText(String text, String argumentName) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) 
            throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_HAS_TEXT, argumentName));
    }

    /**
     * Check if a collection is neither null nor empty.
     * @param collection the collection to check.
     * @param argumentName the name of the argument.
     */
	public static void assertArgumentNotEmpty(Collection<?> collection, String argumentName) {
		assertArgumentNotNull(collection, argumentName);
		if (collection.size() == 0) {
			throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_NOT_EMPTY, argumentName));
		}
	}

	/**
	 * Assure that an array is neither null nor empty.
	 * @param array the array to check
	 * @param argumentName the name of the arguemnt.
	 */
	public static void assertArgumentNotEmpty(Object[] array, String argumentName) {
		assertArgumentNotNull(array, argumentName);
		if (array.length == 0) {
			throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_NOT_EMPTY, argumentName));
		}	
		
	}

	/**
	 * Assure that an argument equals a specific value
	 * @param expected 
	 * @param actual
	 * @param argumentName
	 */
    public static void assertArgumentEquals(Object expected, Object actual, String argumentName) {
        if (expected == null) {
            if (actual == null) {
                return;
            } else {
                throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_NOT_EQUAL, argumentName, expected, actual));
            }
        } else if (!expected.equals(actual)) {
            throw new IllegalArgumentException(String.format(ASSERT_ARGUMENT_NOT_EQUAL, argumentName, expected, actual));            
        }
    }
}
