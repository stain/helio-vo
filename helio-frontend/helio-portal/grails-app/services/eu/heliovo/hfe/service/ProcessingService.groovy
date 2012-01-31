package eu.heliovo.hfe.service

import java.util.concurrent.TimeUnit

import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.processing.ProcessingResult
import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task

class ProcessingService {

    def helioClient = new HelioClient()

    static transactional = true

    /**
     * Execute the processing service and get back the results.
     * @param task the task to execute. The results will be stored to the task.
     * @return a map containing the results, ready to be passed to the view.
     */
    def propagationModel(Task task) {
        //get the task descriptor
        def taskDescriptor = task.findTaskDescriptor()
                   
        def propagationService = helioClient.getServiceInstance(
            taskDescriptor.serviceName,
            taskDescriptor.serviceCapability,
            taskDescriptor.serviceVariant)

        def timeRanges = task.inputParams.timeRanges.timeRanges

        // populate the propagationService (start time is hardcoded)
		BeanWrapper beanWrapper = new BeanWrapperImpl(propagationService)
        if (beanWrapper.isWritableProperty("startTime")) {
            beanWrapper.setPropertyValue("startTime", timeRanges[0].startTime)
        }
        if (beanWrapper.isWritableProperty("date")) {
            beanWrapper.setPropertyValue("date", timeRanges[0].startTime)
        }
        if (beanWrapper.isWritableProperty("endTime")) {
            beanWrapper.setPropertyValue("endTime", timeRanges[0].endTime)
        }
        if (taskDescriptor.inputParams.paramSet) {
            def paramSet = task.inputParams.paramSet.params
            beanWrapper.setPropertyValues(paramSet)
        }
        
        // create the models for the template
        def model = [:]
        model.plotResults = []
        model.votableResults = []
        
        // execute the service
        ProcessingResult result = propagationService.execute();
        try {
            // get the result
            def resultObject = result.asResultObject(60, TimeUnit.SECONDS);
            // wrap the resultObject ...
            beanWrapper = new BeanWrapperImpl(resultObject)
    
            
            // ... and add the results to the task
            taskDescriptor.outputParams.each {
                if (it.value.type == "votable") {
                    // get the url and wrap into votable object
                    def url = beanWrapper.getPropertyValue(it.value.id)
                    def votable = new RemoteVOTableResult(url: url.toString());
                    votable.save()                
                    task.outputParams.put(it.value.id, votable)
                    model.votableResults.push ([id: it.value.id, label: it.value.label, value : votable])
                }  else if (it.value.type == "url"){
                    def url = beanWrapper.getPropertyValue(it.value.id)
                    def plot = new RemotePlotResult(url : url.toString())
                    plot.save()
                    task.outputParams.put(it.value.id, plot)
                    model.plotResults.push ([id: it.value.id, label: it.value.label, value : plot])
                }
            }
            // update task status
            task.lastKnownStatus = HelioWorkerServiceHandler.Phase.COMPLETED
            task.save()
            model.status = "Data sucessfully loaded!"
        } catch (Exception e) {
            model.status = "Exception while loading data: " + e.getMessage() + ". Check the logs for more information."
        } finally {
            model.userLogs = result.userLogs
        }
        
        // return the model
        model
    }
 
}
