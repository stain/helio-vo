package eu.heliovo.hfe.service

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.LogRecord

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.model.field.HelioFieldQueryTerm;
import eu.heliovo.clientapi.model.field.Operator;
import eu.heliovo.clientapi.model.field.descriptor.HelioFieldDescriptor;
import eu.heliovo.clientapi.query.HelioQueryResult
import eu.heliovo.clientapi.query.QueryService;
import eu.heliovo.clientapi.query.WhereClause;
import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.shared.util.DateUtil

class CatalogService {

    def helioClient

    static transactional = true

    /**
     * Execute the processing service and get back the results.
     * @param task the task to execute. The results will be stored to the task.
     * @return a map containing the results, ready to be passed to the view.
     */
    def queryCatalog(Task task) {
        //get the task descriptor
        def timeRanges = task.inputParams.timeRanges.timeRanges

        int maxrecords = 0;
        int startindex = 0;

        def startTime = timeRanges.collect{ it.startTime}
        def endTime = timeRanges.collect{ it.endTime}
        
        def taskDescriptor = task.findTaskDescriptor()
        def from = getFrom(task)

        //lists need to come in as a 1 to 1 relation between date and from, permuteLists makes sure this relation 
		//is kept by padding lists with the required elements.
        List<String>[] permuted = DateUtil.permuteLists(startTime,from)
        startTime = permuted[0];
        permuted = DateUtil.permuteLists(endTime,from)
        endTime = permuted[0];
        from  = permuted[1];
        
        //log.info("queryService  ::" + task.taskName + ", " + startTime+", "+endTime+", "+from+", " + where);

        // create the models for the template
        def model = [:]
        model.votableResults = []
        model.userLogs = []

		QueryService catalogService = helioClient.getServiceInstance(
			taskDescriptor.serviceName,
			taskDescriptor.serviceVariant,
			taskDescriptor.serviceCapability)

        // execute the service
        catalogService.setStartTime(startTime)
        catalogService.setEndTime(endTime)
        catalogService.setFrom(from)
        //catalogService.whereClauses
        catalogService.setMaxRecords(maxrecords)
        catalogService.setStartIndex(startindex)

        // handle the where clauses for event list queries
        if (taskDescriptor.inputParams.eventList) {
			task.inputParams.eventList.entries.each {
				eventListEntry -> 
				// the target whereClause to populate
				WhereClause whereClauseTarget = catalogService.getWhereClauseByCatalogName(eventListEntry.listName)
				
				// the source 
				def whereClauseSrc = eventListEntry.whereClause
				
				// map the WhereClause src to the target
				if (whereClauseSrc) {				
					whereClauseSrc.entries.each {
						HelioFieldDescriptor fieldDescriptor = whereClauseTarget.findFieldDescriptorById(it.paramName)
						if (fieldDescriptor) {
					        whereClauseTarget.setQueryTerm(fieldDescriptor, 
								new HelioFieldQueryTerm(fieldDescriptor, it.operator, it.paramValue));
						} else {
							model.userLogs.add(new LogRecord(Level.WARNING, "Unable to set parameter '" + it.paramName 
								+ "' for catalogue " + eventListEntry.listName))
						}            
					}
				}
			}
        }
        
        HelioQueryResult result = catalogService.execute();
        try {
            // get the result
            def url = result.asURL(taskDescriptor.timeout ?: 120, TimeUnit.SECONDS);
            // wrap the resultObject ...
            def votable = new RemoteVOTableResult(url: url.toString());
            votable.save()
            task.outputParams.put(taskDescriptor.outputParams.voTableUrl.id, votable)
            model.votableResults.push ([id: taskDescriptor.outputParams.voTableUrl.id, label: taskDescriptor.outputParams.voTableUrl.label, value : votable])
    
            // update task status
            task.lastKnownStatus = HelioWorkerServiceHandler.Phase.COMPLETED
            task.save()
            model.status = "Data successfully loaded!"
        } catch (Exception e) {
            model.status = "Exception while loading data: " + e.getMessage() + ". Check the logs for more information."
            model.userLogs.add(new LogRecord(Level.WARNING, e.getClass().getSimpleName() + ": " + e.getMessage()))
        } finally {
            model.userLogs += result.userLogs
        }

        // return the model
        model
    }

	private List getFrom(Task task) {
		def taskDescriptor = task.findTaskDescriptor()
		def from;
		if (taskDescriptor.from) {
			from = [taskDescriptor.from]
		} else if (taskDescriptor.inputParams.eventList) {
			from = task.inputParams.eventList.entries.collect { it.listName }
		} else if (taskDescriptor.inputParams.instruments) {
			from = task.inputParams.instruments.instruments
		} else {
			throw new RuntimeException("Internal Error: unable to determine parameter 'from' for task " + task)
		}
		return from
	}
}
