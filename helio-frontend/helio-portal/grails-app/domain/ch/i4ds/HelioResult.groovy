package ch.i4ds
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*

/**
 * Store the result from a CatalogQuery.
 * TODO: Consider moving to StilUtils.
 * @author MarcoSoldati
 *
 */
class HelioResult {
    /**
     * VOTable a String
     */
    String result;
    
    static constraints = {
        
    }
}
