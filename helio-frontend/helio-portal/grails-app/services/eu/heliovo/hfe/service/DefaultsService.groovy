package eu.heliovo.hfe.service

import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.security.User
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.shared.util.DateUtil
import grails.validation.ValidationException

/**
 * Some service utilities to load default values.
 * @author MarcoSoldati
 *
 */
class DefaultsService {

    static transactional = false

    /**
     * Auto-wire the springSecurityService
     */
    def springSecurityService
    
    def taskDescriptorService

    /**
     * Create a TimeRangeParam with default values, but do not store it in the database.
     * @return the default time range.
     */
    def TimeRangeParam createDefaultTimeRange(taskName) {
        def timeRangeParam = new TimeRangeParam(taskName : taskName);
        timeRangeParam.addTimeRange(DateUtil.fromIsoDate("2011-02-14T00:00:00"), DateUtil.fromIsoDate("2011-02-16T00:00:00"))
        timeRangeParam
    }

    /**
     * Create a new param set instance but do not save it to the database.
     * @param taskName the name of the task this paramSet belongs to.
     * @param params the default params of this set
     * @return the created paramSet
     * @throws ValidationException in case the created params object is not valid.
     */
    def newParamSet(taskName, params) throws ValidationException {
        def paramSet = new ParamSet(taskName: taskName)
        paramSet.params = params
        if (!paramSet.validate()) {
            throw new ValidationException("ParamSet is not valid", paramSet.errors)
        }
        paramSet
    }

    def loadTask(taskName) {
        // load previous task from database
        def user = User.get(springSecurityService.principal.id)
        def task = Task.find(
            "from " + Task.getSimpleName() + " AS t WHERE t.owner=:owner AND t.taskName=:taskName ORDER BY lastUpdated desc", 
            [owner:user, taskName: taskName])

        // create a new temporary task to be used as model
        if (!task) {
            def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName);
            if (!taskDescriptor) {
                throw new RuntimeException("Unknown task: " + taskName); 
            }
            
            task = new Task(taskName : taskName)
            
            if (taskDescriptor.inputParams) {
                def paramMap = [:]
                taskDescriptor.inputParams.paramSet.each {
                    paramMap[it.key] = ""+it.value.defaultValue 
                }
                
                def inputParams = [
                    timeRange: createDefaultTimeRange(taskName),
                    paramSet: newParamSet(taskName, paramMap)
                ]
                task.inputParams = inputParams
            }
        }
        task
    }
}
