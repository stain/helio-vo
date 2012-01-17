package eu.heliovo.hfe.model.result

import java.net.URL;

/**
 * Pointer to a plot from a remote host.
 * @author MarcoSoldati
 *
 */
class RemotePlotResult extends HelioResult {
   /**
    * The URL where the result is coming from.
    */
   URL url;
   
   static constraints = {
   }
   

}
