package ch.i4ds

/**
 * Store the content of the Memory bar
 * @author MarcoSoldati
 */
class HelioMemoryBar {
    /**
     * The UID of the user session
     */
    String hUID;
    
    /**
     * Content of the memory bar stored in HTML
     * TODO: Use a model to store content.
     */
    String html;

    static constraints = {
         id column:'hUID'
    }
}
