package eu.heliovo.hfe.model

/**
 * Store the content of an HTML query 
 * @author MarcoSoldati
 *
 */
class HelioQuery {
    /**
     * Id of the users session
     */
    String hUID;
    
    /**
     * Name of the query task
     */
    String taskName;
    
    /**
     * Html that stores the tasks content.
     * TODO: replace by a proper model
     */
    String html;

    static constraints = {
        id column:'hUID'
    }
}
