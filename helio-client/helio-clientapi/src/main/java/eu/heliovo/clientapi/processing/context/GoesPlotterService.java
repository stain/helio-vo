package eu.heliovo.clientapi.processing.context;

import java.util.Date;

import eu.heliovo.clientapi.processing.ProcessingResult;
import eu.heliovo.clientapi.processing.ProcessingService;
import eu.heliovo.clientapi.processing.UrlProcessingResultObject;

/**
 * Context service to access goes plots
 * @author MarcoSoldati
 *
 */
public interface GoesPlotterService extends ProcessingService<UrlProcessingResultObject> {

    /**
     * Get the start date to use
     * @return the start date
     */
    public Date getStartTime(); 

    /**
     * Set the start date.
     * @param startTime the start date
     */
    public void setStartTime(Date startTime);

    /**
     * Get the end date
     * @return the end date
     */
    public Date getEndTime();

    /**
     * Set the end date
     * @param endTime the end date.
     */
    public void setEndTime(Date endTime);
    

    /**
     * Set the plot type
     * @param plotType the plot type.
     */
    public void setPlotType(PlotType plotType);
    
    /**
     * Get the plot type.
     * @return the plot type.
     */
    public PlotType getPlotType();

    
    /**
     * Convenience method to create a goes plot. This method can be used instead of calling setter methods and then execute.
     * @param startTime the start date.
     * @param endTime the end date.
     * @Param plotType the plot type.
     * @return an object to access the result.
     */
    public ProcessingResult<UrlProcessingResultObject> goesPlot(Date startTime, Date endTime, PlotType plotType);
    
    /**
     * Type of the plot
     * @author MarcoSoldati
     *
     */
    public static enum PlotType {
        PROTON ("Proton"),
        XRAY ("Xray"),
        BOTH ("Both");
        
        /**
         * The label of the plot type
         */
        private final String label;

        private PlotType(String label) {
            this.label = label;
        }
        
        /**
         * Get the label of this type.
         * @return the plot type label.
         */
        public String getLabel() {
            return label;
        }
    }

}