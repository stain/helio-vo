package eu.heliovo.hfe.model.result
import eu.heliovo.hfe.model.security.User

/**
 * Abstract base class for stored results. Can be binary or textual data.
 * This can be persisted in a local database.
 * @author MarcoSoldati
 *
 */
class HelioResult {
    
    /**
     * Auto-wire the spring security service.
     */
    transient springSecurityService
    
    /**
     * The user this result belongs to
     */
    User owner
        
    static constraints = {
        owner nullable : false;
    }
    
   /**
    * Assign user if required.
    * @return nothing
    */
    def beforeValidate() {
        if (!owner)
            owner = User.get(springSecurityService.principal.id)
    }
}
