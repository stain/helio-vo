package eu.heliovo.hfe.model.result
import uk.ac.starlink.table.StarTable;
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*
import eu.heliovo.clientapi.utils.STILUtils;
import eu.heliovo.hfe.model.security.User;

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
