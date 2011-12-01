package eu.heliovo.hfe.model.result

import java.net.URL

import uk.ac.starlink.table.StarTable
import eu.heliovo.clientapi.utils.STILUtils

/**
 * A local copy of a VOTable.
 * @author MarcoSoldati
 *
 */
class LocalHelioResult extends HelioResult {
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

    /**
     * Get the star model. load it if required.
     * @return the starModel
     */
    StarTable[] getStarTableModel() {
        // load if required
        if (this.starTableModel == null) {
            this.starTableModel = STILUtils.load(new ByteArrayInputStream(this.voTableContent.getBytes()));
        }
        return this.starTableModel;
    }
}
