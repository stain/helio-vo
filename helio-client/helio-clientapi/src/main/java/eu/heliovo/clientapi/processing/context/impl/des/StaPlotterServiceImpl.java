package eu.heliovo.clientapi.processing.context.impl.des;

import eu.heliovo.clientapi.processing.context.DesPlotterService;
import eu.heliovo.clientapi.processing.context.impl.AbstractDesPlotterServiceImpl;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.shared.util.AssertUtil;

/**
 * Create a plotter service for mission "STA".
 * @author MarcoSoldati
 *
 */
public class StaPlotterServiceImpl extends AbstractDesPlotterServiceImpl {
    
    /**
     * The name of the service variant
     */
    public static final String SERVICE_VARIANT =  DesPlotterService.SERVICE_VARIANT + "/sta";
    
    /**
     * the wind mission
     */
    public static final String MISSION = "STEREO-A";

    /**
     * Create the STA plotter service
     * @param accessInterfaces the interfaces to use.
     */
    public StaPlotterServiceImpl(HelioServiceName serviceName, String serviceVariant) {
        super(HelioServiceName.DES, SERVICE_VARIANT, MISSION);
        AssertUtil.assertArgumentEquals(HelioServiceName.DES, serviceName, "serviceName");
        AssertUtil.assertArgumentEquals(serviceVariant, SERVICE_VARIANT, "serviceVariant");
    }
}
