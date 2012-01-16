package eu.heliovo.hfe.service

import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.processing.ProcessingResult
import eu.heliovo.clientapi.processing.hps.CmePropagationModel
import eu.heliovo.clientapi.processing.hps.CmePropagationModel.CmeProcessingResultObject
import eu.heliovo.clientapi.processing.hps.impl.CmePropagationModelImpl
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability
import grails.validation.ValidationException;

class ProcessingService {

    def helioClient = new HelioClient()

    static transactional = true

    def propagationModel(Task task) {
        //println task
        
        // create the model
        CmePropagationModel cmeModel =
                helioClient.getServiceInstance(HelioServiceName.HPS,
                ServiceCapability.HELIO_PROCESSING_SERVICE,
                CmePropagationModelImpl.SERVICE_VARIANT);

        def timeRanges = task.inputParams.timeRanges.timeRanges
        def paramSet = task.inputParams.paramSet.params
        println timeRanges
        println paramSet

        ProcessingResult<CmeProcessingResultObject> result = cmeModel.execute(timeRanges[0].start, (float)paramSet.longitude, (float)paramSet.width, (float)paramSet.speed, (float)paramSet.speedError);
        CmeProcessingResultObject resultObject = result.asResultObject(60, TimeUnit.SECONDS);

        return resultObject
    }
}
