package eu.heliovo.clientapi.processing.hps.impl;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import eu.heliovo.clientapi.processing.ProcessingResult;
import eu.heliovo.clientapi.processing.hps.AbstractHelioProcessingServiceImpl;
import eu.heliovo.clientapi.processing.hps.SepBackwardPropagationModel;
import eu.heliovo.clientapi.processing.hps.SepPropagationModel.SepProcessingResultObject;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.hps.server.ApplicationParameter;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.shared.props.HelioFileUtil;
import eu.heliovo.shared.util.AssertUtil;
import eu.heliovo.shared.util.DateUtil;

/**
 * Implementation of the CME propagation model.
 * @author MarcoSoldati
 *
 */
public class SepBackwardPropagationModelImpl extends AbstractHelioProcessingServiceImpl<SepProcessingResultObject> implements SepBackwardPropagationModel {

    /**
     * the application id.
     */
    private static final String APPLICATION_ID = "pm_sep_back";

    /**
     * Name of the variant
     */
    public static final String SERVICE_VARIANT = HelioServiceName.HPS.getServiceId() + "/" + APPLICATION_ID;

    /**
     * Create the propagation model.
     */
    public SepBackwardPropagationModelImpl(HelioServiceName serviceName, String serviceVariant) {
        super(HelioServiceName.HPS, SERVICE_VARIANT);
        AssertUtil.assertArgumentEquals(HelioServiceName.HPS, serviceName, "serviceName");
        AssertUtil.assertArgumentEquals(serviceVariant, SERVICE_VARIANT, "serviceVariant");
    }


    /**
     * Start time
     */
    private Date startTime;
    
    /**
     * The hit object
     */
    private String hitObject;
    
    /**
     * Speed
     */
    private float speed;
    
    /**
     * Error
     */
    private float speedError;
    
    /**
     * Fraction of light speed.
     */
    private float beta;
    
    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public String getHitObject() {
        return hitObject;
    }

    @Override
    public void setHitObject(String hitObject) {
        this.hitObject = hitObject;
    }
    
    @Override
    public void setBeta(float beta) {
        this.beta = beta;
        
    }

    @Override
    public float getBeta() {
        return beta;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeedError(float speedError) {
        this.speedError = speedError;
    }

    @Override
    public float getSpeedError() {
        return speedError;
    }
    
    @Override
    public ProcessingResult<SepProcessingResultObject> execute(Date startTime, String hitObject, Float speed, Float speedError, Float beta) throws JobExecutionException {
        this.setStartTime(startTime);
        this.setHitObject(hitObject);
        this.setBeta(beta);
        this.setSpeed(speed);
        this.setSpeedError(speedError);
        return execute();
    }

    /**
     * Output names 
     */
    private static final List<String> OUTPUT_NAMES = Arrays.asList("votable_url", "outerplot_url", "innerplot_url");

    @Override
    protected SepProcessingResultObject createResultObject(final AccessInterface accessInterface, final String executionId) {
        // FIXME: hardcoded
        final String baseUrl = "http://cagnode58.cs.tcd.ie/output_dir/" + executionId + "/";
        
        return new SepProcessingResultObject() {
            
            @Override
            public URL getVoTableUrl() {
                return HelioFileUtil.asURL(baseUrl + "sep_pm.votable");
            }
            
            @Override
            public URL getOuterPlotUrl() {
                return HelioFileUtil.asURL(baseUrl + "sep_pm_outer.png");
            }
            
            @Override
            public URL getInnerPlotUrl() {
                return HelioFileUtil.asURL(baseUrl + "sep_pm_inner.png");
            }
            
            @Override
            public List<String> getOutputNames() throws JobExecutionException {
                return OUTPUT_NAMES;
            }

            @Override
            public Object getOutput(String outputName) throws JobExecutionException {
                int output = OUTPUT_NAMES.indexOf(outputName);
                switch (output) {
                case 0:
                    return getVoTableUrl();
                case 1:
                    return getOuterPlotUrl();
                case 2:
                    return getInnerPlotUrl();
                default:
                    throw new IllegalArgumentException("Unknown output name: " + outputName);
                }
            }
        };
    }

    @Override
    protected String getApplicationId() {
        return APPLICATION_ID;
    }

    @Override
    protected void setParameter(ApplicationParameter param) {
        String name = param.getName();
        if (name.contains("time")) {
            checkType("startTime", "String", "String");
            param.setValue(DateUtil.toIsoDateString(getStartTime()));
        } else if (name.contains("hit object")) {
            checkType("hitObject", "String", "String");
            checkValueDomain("hitObject", getHitObject(), Arrays.asList(HIT_OBJECT_DOMAIN));
            param.setValue(""+getHitObject());
        } else if (name.contains("starting speed")) {
            checkType("speed", "Float", "Float");
            param.setValue(""+getSpeed());
        } else if (name.contains("error speed")) {
            checkType("speedError", "Float", "Float");
            param.setValue(""+getSpeedError());
        } else if (name.contains("Beta")) {
            checkType("beta", "Float", "Float");
            param.setValue(""+getBeta());
        } else {
            throw new JobExecutionException("Internal Error: Unknown parameter found: " + param.getName());
        }
    }

}
