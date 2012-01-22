package eu.heliovo.clientapi.processing.context;

import java.util.Date;

import eu.heliovo.clientapi.processing.ProcessingResult;
import eu.heliovo.clientapi.processing.ProcessingService;
import eu.heliovo.clientapi.processing.UrlProcessingResultObject;

/**
 * Context service to access the parker model.
 * @author MarcoSoldati
 *
 */
public interface SimpleParkerModelService extends ProcessingService<UrlProcessingResultObject> {

    /**
     * Get the start date to use
     * @return the start date
     */
    public Date getStartTime(); 

    /**
     * Set the start date.
     * @param startTime the date
     */
    public void setStartTime(Date startTime);
    
    /**
     * Convenience method to create a flare plot. This method can be used instead of calling setter methods and then execute.
     * @param date the date of the flare.
     * @return an object to access the result.
     */
    public ProcessingResult<UrlProcessingResultObject> parkerModel(Date startTime);

    public abstract void setInner(int inner);

    public abstract int getInner();

    public abstract void setOuter(int outer);

    public abstract int getOuter();

    public abstract void setVelocity(int velocity);

    public abstract int getVelocity();

}