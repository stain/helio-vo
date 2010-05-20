package eu.heliovo.clientapi;

import java.util.List;

import eu.heliovo.clientapi.result.HelioJob;
import eu.heliovo.clientapi.status.ServiceStatus;

public interface HelioAPI
{
  /**
   * Login with the default credentials.
   * Credentials can be stored in a configuration file. If no credentials
   * are stored the anonymous identity will be used.
   */
  public void login();
  
  /**
   * Login as a specific user.
   * 
   * @param username
   * @param password
   */
  public void login(String username,String password);
  
  /**
   * Get the list of active Helio jobs of this user.
   * Active jobs are either still running or have been finished, but the 
   * result has not yet been collected.
   * @return List of Jobs
   */
  public List<HelioJob> getHelioJobs();
  
  /**
   * Gets a specific long-running job
   * @param id the id of the job to retreive.
   */
  public HelioJob getHelioJob(int id);
  
  /**
   * Get the status of a specific service.
   * @param serviceId the id of the service to get the status for. Must not be null or an empty String. 
   * @return the status or null if the status cannot be loaded.
   * @throws IllegalArgumentException if the service with id 'serviceId' does not exist or if serviceId is null.
   */
  public ServiceStatus getStatus(String serviceId) throws IllegalArgumentException; 
}
