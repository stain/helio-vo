package eu.heliovo.hfe.service

import java.util.concurrent.TimeUnit

import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.processing.ProcessingResult
import eu.heliovo.clientapi.processing.hps.CmePropagationModel
import eu.heliovo.clientapi.processing.hps.CmePropagationModel.CmeProcessingResultObject
import eu.heliovo.clientapi.processing.hps.impl.CmePropagationModelImpl
import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability

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
        
        // create the model
        CmePropagationModel cmeModel =
                helioClient.getServiceInstance(HelioServiceName.HPS,
                ServiceCapability.HELIO_PROCESSING_SERVICE,
                CmePropagationModelImpl.SERVICE_VARIANT);

        def timeRanges = task.inputParams.timeRanges.timeRanges
        def paramSet = task.inputParams.paramSet.params

        // populate the cmeModel (start time is hardcoded)
		BeanWrapper beanWrapper = new BeanWrapperImpl(cmeModel)
        beanWrapper.setPropertyValue("startTime", timeRanges[0].start)
		beanWrapper.setPropertyValues(paramSet)
		
        ProcessingResult<CmeProcessingResultObject> result = cmeModel.execute();
        CmeProcessingResultObject resultObject = result.asResultObject(60, TimeUnit.SECONDS);
        
        // wrap the resultObject ...
        beanWrapper = new BeanWrapperImpl(resultObject)
        
        def model = [:]
        
        // create the models for the template
        model.plotResults = []
        model.votableResults = []
        
        // ... and add the results to the task
        taskDescriptor.outputParams.each {
            if (it.value.type == "votable") {
                // get the url and wrap into votable object
                def url = beanWrapper.getPropertyValue(it.value.id)
                def votable = new RemoteVOTableResult(url: url);
                votable.save()
                task.outputParams.put(it.value.id, votable)
                model.votableResults.push ([id: it.value.id, label: it.value.label, value : votable])
            }  else if (it.value.type == "url"){
                def url = beanWrapper.getPropertyValue(it.value.id)
                def plot = new RemotePlotResult(url : url)
                plot.save()
                task.outputParams.put(it.value.id, plot)
                model.plotResults.push ([id: it.value.id, label: it.value.label, value : plot])
            }
        }
        // update task status
        task.lastKnownStatus = HelioWorkerServiceHandler.Phase.COMPLETED
        task.save()
        
        // return the model
        model
    }
}
