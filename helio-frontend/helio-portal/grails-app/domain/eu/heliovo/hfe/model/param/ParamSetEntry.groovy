package eu.heliovo.hfe.model.param

import eu.heliovo.clientapi.model.field.Operator;

/**
 * One entry in a ParamSet
 * @author MarcoSoldati
 *
 */
class ParamSetEntry {
    /**
     * Name of the parameter
     */
    String paramName
    
    /**
     * Operator to use
     */
    Operator operator
    
    /**
     * Stringified values. Multiple values are separated by a comma.
     */
    String paramValue
    
    static belongsTo = [ paramSet : ParamSet ]
    
    def String toString() {
        paramName + operator.symbol + paramValue
    }
}
