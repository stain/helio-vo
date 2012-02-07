package eu.heliovo.hfe.model.param

import org.codehaus.groovy.grails.validation.Validateable

import eu.heliovo.hfe.model.security.User

/**
 * Parameter holding a collection of time ranges.
 * @author MarcoSoldati
 *
 */
class TimeRangeParam extends AbstractParam {
    /**
     * Wire the spring security service.
     */
    transient springSecurityService
    
    /**
     * list of time ranges.
     */
    List<TimeRange> timeRanges

    static constraints = {
        timeRanges validator : { it.every{it?.validate() }}
    }
	
    static hasMany = [
        timeRanges : TimeRange    
    ]

    /**
     * Add the date range to the current date parameter.
     * @param startTime the start date
     * @param endTime the end date.
     */
    public void addTimeRange(Date startTime, Date endTime) {
        def tr = new TimeRange(startTime : startTime, endTime: endTime)
        tr.save()
        this.addToTimeRanges(tr)
    }

    public String toString() {
        getClass().getSimpleName() + ": " + "Name: " + name + ", TimeRanges: " + timeRanges
    }

    /**
     * Get the latest end date
     */
    public Date findMax() {
        def max
        for(timeRange in timeRanges) {
            if (!max) {
                max = timeRange.end
            } else {
                max = max.before(timeRange.end) ? timeRange.end : max
            }
        }
        return max
    }

    /**
     * Get the earliest start date
     */
    public Date findMin() {
        def min
        for(timeRange in timeRanges) {
            if (!min) {
                min = timeRange.start
            } else {
                min = min.after(timeRange.start) ? timeRange.start : min
            }
        }
        return min
    }
    
    /**
    * Assign user if required.
    */
   def beforeValidate() {
       if (!owner) {
           owner = User.get(springSecurityService.principal.id)
       }
   }
}
