package eu.heliovo.hfe.model.result

import java.net.URL;

import uk.ac.starlink.table.StarTable;

/**
 * A voTable that is stored in some remote location an accessible through an URL.
 * @author MarcoSoldati
 *
 */
class RemoteVOTableResult extends HelioResult {
   /**
    * The URL where the result is coming from.
    * Unique
    */
   URL url;
   
   static constraints = {
       
   }
}
