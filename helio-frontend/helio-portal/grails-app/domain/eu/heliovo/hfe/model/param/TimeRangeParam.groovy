package eu.heliovo.hfe.model.param

import org.codehaus.groovy.grails.validation.Validateable

/**
 * Parameter holding a collection of time ranges.
 * @author MarcoSoldati
 *
 */
class TimeRangeParam extends AbstractParam {
    /**
     * Start dates list
     */
    List<TimeRange> timeRanges = new ArrayList<TimeRange>()

    static constraints = {
        timeRanges validator : { it.every{it?.validate() }}
    }

	 static mapping = {
	     tablePerHierarchy false
	 }
	
    /**
     * Default constructor
     */
    public TimeRangeParam() {
    }

    /**
     * Convenience constructor for a time range param
     */
    public TimeRangeParam(Date startDate, Date endDate) {
        this()
        addTimeRange(startDate, endDate)
    }

    /**
     * Add the date range to the current date parameter.
     * @param startDate the start date
     * @param endDate the end date.
     */
    public void addTimeRange(Date startDate, Date endDate) {
        def tr = new TimeRange(startDate, endDate)
        timeRanges.add(tr)
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
}

/**
 * Container to hold a time range.
 * @author MarcoSoldati
 *
 */
@Validateable
class TimeRange {
    /**
     * The id of this time range
     */
    Long id
    
    /**
     * The version counter for the time range.
     */
    Long version

    /**
     * Start date
     */
    Date start

    /**
     * End date
     */
    Date end

    /**
     * Default constructor
     */
    TimeRange() {

    }

    /**
     * Create a time range.
     * @param start the start date.
     * @param end the end date.
     */
    TimeRange(Date start, Date end) {
        this.start = start
        this.end = end
    }

    static constraints = {
        start (validator: { val, obj ->
            // ensure the start date is  before the end date.
            if (val.after(obj.end)) {
                return ['enddate.before.startdate']
            }
            return true
        })
    }

    public String toString() {
        return "" + start + "-" + end
    }
}
