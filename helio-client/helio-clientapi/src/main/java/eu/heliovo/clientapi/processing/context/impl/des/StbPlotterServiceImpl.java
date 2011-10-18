package eu.heliovo.clientapi.processing.context.impl.des;

import eu.heliovo.clientapi.processing.context.DesPlotterService;
import eu.heliovo.clientapi.processing.context.impl.AbstractDesPlotterServiceImpl;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.HelioServiceName;

/**
 * Create a plotter service for mission "STB".
 * @author MarcoSoldati
 *
 */
public class StbPlotterServiceImpl extends AbstractDesPlotterServiceImpl {
    
    /**
     * The name of the service variant
     */
    public static final String SERVICE_VARIANT =  DesPlotterService.SERVICE_VARIANT + "/stb";
    
    /**
     * the wind mission
     */
    public static final String MISSION = "STEREO-B";

    /**
     * Create the STB plotter service
     * @param accessInterfaces the interfaces to use.
     */
    public StbPlotterServiceImpl(AccessInterface[] accessInterfaces) {
        super(HelioServiceName.DES, SERVICE_VARIANT, MISSION, null, accessInterfaces);
    }
}
