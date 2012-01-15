package eu.heliovo.clientapi.processing.context;

import java.util.Date;

import eu.heliovo.clientapi.processing.ProcessingResult;
import eu.heliovo.clientapi.processing.ProcessingService;
import eu.heliovo.clientapi.processing.UrlProcessingResultObject;

/**
 * Context service to access flare plots
 * @author MarcoSoldati
 *
 */
public interface FlarePlotterService extends ProcessingService<UrlProcessingResultObject> {

    /**
     * Get the  date to use
     * @return the start date
     */
    public Date getDate(); 

    /**
     * Set the  date.
     * @param date the date
     */
    public void setDate(Date date);

    /**
     * Convenience method to create a flare plot. This method can be used instead of calling setter methods and then execute.
     * @param date the date of the flare.
     * @return an object to access the result.
     */
    public ProcessingResult<UrlProcessingResultObject> flarePlot(Date date);

}