package eu.heliovo.clientapi.processing.context.impl.des;

import eu.heliovo.clientapi.processing.context.DesPlotterService;
import eu.heliovo.clientapi.processing.context.impl.AbstractDesPlotterServiceImpl;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.HelioServiceName;

/**
 * Create a plotter service for mission "WIND".
 * @author MarcoSoldati
 *
 */
public class WindPlotterServiceImpl extends AbstractDesPlotterServiceImpl {
    
    /**
     * The name of the service variant
     */
    public static final String SERVICE_VARIANT =  DesPlotterService.SERVICE_VARIANT + "/wind";
    
    /**
     * the wind mission
     */
    public static final String MISSION = "WIND";

    /**
     * Create the WIND plotter service
     * @param accessInterfaces the interfaces to use.
     */
    public WindPlotterServiceImpl(AccessInterface[] accessInterfaces) {
        super(HelioServiceName.DES, SERVICE_VARIANT, MISSION, null, accessInterfaces);
    }
}
