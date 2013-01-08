package eu.heliovo.hfe.model.param

import java.util.List;
import java.util.Map;

/**
 * Container in an Event list to hold one listName-WhereClause tupple. 
 * @author MarcoSoldati
 *
 */
class EventListParamEntry {

    /**
     * Hold the params
     */
    String listName

    /**
     * where clause, may be null
     */
    ParamSet whereClause

    static constraints = {
        whereClause(nullable:true)
    }

    static belongsTo = [
        eventListParam : EventListParam
    ]
}
