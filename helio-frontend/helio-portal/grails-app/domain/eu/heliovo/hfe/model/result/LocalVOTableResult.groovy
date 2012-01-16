package eu.heliovo.hfe.model.result

import java.net.URL

import uk.ac.starlink.table.StarTable
import eu.heliovo.clientapi.utils.STILUtils

/**
 * A local copy of a VOTable.
 * @author MarcoSoldati
 *
 */
class LocalVOTableResult extends HelioResult {   
    /**
     * Original name of the file
     */
    String originalFileName;
    
    /**
     * The content of the VoTable stored in a large blob
     */
    String voTableContent;

    static constraints = {
        voTableContent nullable: false
    }

    static mapping = { 
        voTableContent type: 'text' 
    }
}
