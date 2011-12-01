package eu.heliovo.hfe.model.result

import java.net.URL;

import uk.ac.starlink.table.StarTable;

/**
 * A voTable that is stored in some remote location an accessible through an URL.
 * @author MarcoSoldati
 *
 */
class RemoteHelioResult extends HelioResult {
    /**
    * The URL where the result is coming from.
    * Unique
    */
   URL url;
   
   /**
    * Transient property of the stil model
    */
   StarTable[] starTableModel;

   
   static transients =  ['starTableModel']
       
   static constraints = {
       
   }
   
   /**
    * Get the star model. load it if required.
    * @return the starModel
    */
   StarTable[] getStarTableModel() {
       // load if required
       if (this.starTableModel == null) {
           this.starTableModel = STILUtils.load(this.url);
       }
       return this.starTableModel;
   }
}
