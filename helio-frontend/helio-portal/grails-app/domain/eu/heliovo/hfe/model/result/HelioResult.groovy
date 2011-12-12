package eu.heliovo.hfe.model.result
import uk.ac.starlink.table.StarTable;
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*
import eu.heliovo.clientapi.utils.STILUtils;
import eu.heliovo.hfe.model.security.User;

/**
 * Abstract base class for stored VoTables.
 * This can be persisted in a local database.
 * @author MarcoSoldati
 *
 */
class HelioResult {
    /**
     * The user this result belongs to
     */
    User user
        
    static constraints = {
        user nullable : false;
    }
}
