package eu.heliovo.hfe.utils

import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import org.springframework.beans.PropertyAccessorFactory;

import eu.heliovo.hfe.model.task.Task;

class BeanPopulationUtil {

    /**
     * Wrap a service bean and populate it with the current task values 
     * @param service the service to populate
     * @param task the task to read the data from.
     * @return the bean wrapper that was used to populate the service.
     */
    static populateService(service, Task task) {
        def taskDescriptor = task.findTaskDescriptor()
        def timeRanges = task.inputParams?.timeRanges.timeRanges
                
        // populate the service (start and end time are hardcoded)
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(service)
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
            task.inputParams.paramSet.entries.each { entry -> 
                beanWrapper.setPropertyValue(entry.paramName, entry.paramValue);
            }
        }
        
        return beanWrapper;
    }
}
