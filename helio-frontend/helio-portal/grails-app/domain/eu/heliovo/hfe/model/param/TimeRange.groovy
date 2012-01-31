package eu.heliovo.hfe.model.param

import java.util.Date;

/**
 * Container to hold a time range.
 * @author MarcoSoldati
 *
 */
class TimeRange {
    /**
     * Start date
     */
    Date startTime

    /**
     * End dateTime
     */
    Date endTime

    static constraints = {
        startTime (validator: { val, obj ->
            // ensure the start date is  before the end date.
            if (val.after(obj.endTime)) {
                return ['endtime.before.starttime']
            }
            return true
        })
    }
    
    static belongsTo = [
        timeRangeParam : TimeRangeParam
    ]
    
    public String toString() {
        return "" + startTime + "-" + endTime
    }
}
