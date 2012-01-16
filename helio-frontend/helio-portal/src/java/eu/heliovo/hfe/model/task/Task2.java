package eu.heliovo.hfe.model.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler;
import eu.heliovo.hfe.model.param.AbstractParam;
import eu.heliovo.hfe.model.result.HelioResult;
import eu.heliovo.hfe.model.security.User;
import eu.heliovo.hfe.utils.TaskDescriptor;

/**
 * Configuration of a concrete instance of a HELIO task. A task basically consists of a 
 * collection on inputParameters and once exectued of a collection of outputParameters.
 * @author MarcoSoldati
 *
 */
@Entity
public class Task2 {
    /**
     * The id of the task
     */
    private Long id;

    /**
     * Creation date will be automatically set by GORM
     */
    private Date dateCreated;
  
    /**
     * Last update date will be automatically managed by GORM
     */
    private Date lastUpdated;
    
    /**
     * Name of the task.
     */
    private String taskName;
   
    /**
     * Name and concrete instance of the input parameters for a task.
     */
    private Map<String, AbstractParam> inputParams = new HashMap<String, AbstractParam>();
    
    /**
     * Name and concrete instance of the output parameters of a task.
     */
    private Map<String, HelioResult> outputParams = new HashMap<String, HelioResult>();
    
    /**
     * This is the last known status of the task. It may be that the actual task implementation changed it's status without
     * being updated here. Still this property can be used to query for tasks in a specific status.
     */
    private HelioWorkerServiceHandler.Phase lastKnownStatus = HelioWorkerServiceHandler.Phase.PENDING;
   
    /**
      * The user this result belongs to
      */
    private User owner;
    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the lastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the inputParams
     */
    public Map<String, AbstractParam> getInputParams() {
        return inputParams;
    }

    /**
     * @param inputParams the inputParams to set
     */
    public void setInputParams(Map<String, AbstractParam> inputParams) {
        this.inputParams = inputParams;
    }

    /**
     * @return the outputParams
     */
    public Map<String, HelioResult> getOutputParams() {
        return outputParams;
    }

    /**
     * @param outputParams the outputParams to set
     */
    public void setOutputParams(Map<String, HelioResult> outputParams) {
        this.outputParams = outputParams;
    }

    /**
     * @return the lastKnownStatus
     */
    public HelioWorkerServiceHandler.Phase getLastKnownStatus() {
        return lastKnownStatus;
    }

    /**
     * @param lastKnownStatus the lastKnownStatus to set
     */
    public void setLastKnownStatus(HelioWorkerServiceHandler.Phase lastKnownStatus) {
        this.lastKnownStatus = lastKnownStatus;
    }

    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
    * Load the task description from the config
    * @return config object of the task
    */
   public Map<String, Object> findTaskDescriptor() {
       return TaskDescriptor.getTaskDescriptor().get(taskName);
   }
   
   public String toString() {
       return "Task: [taskName:" + taskName + ",\n    inputParams: " + inputParams + ",\n    outputParams: " + outputParams +"\n]";
   }
}
