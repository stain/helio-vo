package eu.heliovo.hfe.service.util

import org.codehaus.groovy.grails.commons.metaclass.GroovyDynamicMethodsInterceptor
import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

import eu.heliovo.clientapi.model.field.Operator
import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.EventListParamEntry;
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.ParamSetEntry
import eu.heliovo.hfe.model.param.QueryParamSet
import eu.heliovo.hfe.model.param.TimeRange;
import eu.heliovo.hfe.model.param.TimeRangeParam;
import eu.heliovo.shared.util.DateUtil;
import grails.validation.ValidationException

class JsonToGormBindingService {

    static transactional = true
    
    /**
     * Add the bindData-methods to this service (thanks to http://nerderg.com/Grails).
     */
    JsonToGormBindingService() {
        GroovyDynamicMethodsInterceptor i = new GroovyDynamicMethodsInterceptor(this)
        i.addDynamicMethodInvocation(new BindDynamicMethod())
    }
    
    /**
     * Bind the jsonBindings to an EventListParam object
     * @param jsonBindings is the map created by parsing the JSONized helio.EventList from helio-model.js.
     * @param eventListParam the event list param to populate. If null a new param will be created, otherwise the 
     * existing param will be updated.
     * @return the created or updated eventListParam. The created param will be saved and validated.
     */
    public EventListParam bindEventList(eventListJson, EventListParam eventListParam) {
        if (!eventListParam) {
            eventListParam = new EventListParam();
        }
        
        if (!eventListJson['entries']) {
            throw new IllegalArgumentException("Argument 'eventListJson' should contain an entry called 'entries'")
        }
        
        // do the automatic bindings
        bindData(eventListParam, eventListJson, [exclude :['entries']])
        
        // do some manual bindings
        eventListJson.entries.each {
            EventListParamEntry entry = new EventListParamEntry()
            entry.listName = it.key
            if (it.value['whereClause'] && it.value.whereClause.entries) {
                ParamSet queryParamSet = bindParamSet(it.value.whereClause, new ParamSet(it.value))
                entry.whereClause = queryParamSet
            }
            entry.save(flush: true)
            eventListParam.addToEntries(entry);
        }
        
        // and now the validation        
        if (!eventListParam.validate()) {
            throw new ValidationException ("Invalid param set", eventListParam.errors)
        }
        eventListParam.save()
        eventListParam
    }
    
    /**
     * Bind the jsonBindings to an InstrumentParam object
     * @param jsonBindings is the map created by parsing the JSONized helio.Instrument from helio-model.js.
     * @param instrumentParam the instrumentParam to populate. If null a new param will be created, otherwise the 
     * existing param will be updated.
     * @return the created or updated instrumentParam. The created param will be saved and validated.
     */
    def bindInstrument(instrumentJson, InstrumentParam instrumentParam) {
        if (!instrumentParam) {
            instrumentParam = new InstrumentParam();
        }
        
        if (!instrumentJson['instruments']) {
            throw new IllegalArgumentException("Argument 'instrumentJson' should conain an entry called 'instrument'")
        }
        
        // do the automatic bindings
        bindData(instrumentParam, instrumentJson)
        
        // do some manual bindings
        instrumentParam.instruments = instrumentJson.instruments.keySet() as List
        
        // and now the validation        
        if (!instrumentParam.validate()) {
            throw new ValidationException ("Invalid InstrumentParam object: ", instrumentParam.errors)
        }
        instrumentParam.save()
        instrumentParam
    }
    /**
     * Bind the jsonBindings to an TimeRangeParam object
     * @param jsonBindings is the map created by parsing the JSONized helio.TimeRange from helio-model.js.
     * @param timeRangeParam the timeRangeParam to populate. If null a new param will be created, otherwise the 
     * existing param will be updated.
     * @return the created or updated timeRangeParam. The param will be saved and validated.
     */
    def bindTimeRange(timeRangeJson, TimeRangeParam timeRangeParam) {
        if (!timeRangeParam) {
            timeRangeParam = new TimeRangeParam();
        }
        
        if (!timeRangeJson['timeRanges']) {
            throw new IllegalArgumentException("Argument 'timeRangeJson' should conain an entry called 'timeRanges'")
        }
        
        // do the automatic bindings
        bindData(timeRangeParam, timeRangeJson, [exclude :['timeRanges']])
        
        // do some manual bindings
        timeRangeJson.timeRanges.each{ tr->
            timeRangeParam.addTimeRange(DateUtil.fromIsoDate(tr.startTime), DateUtil.fromIsoDate(tr.endTime))
        }
        
        // and now the validation        
        if (!timeRangeParam.validate()) {
            throw new ValidationException ("Invalid TimeRangeParam object: ", timeRangeParam.errors)
        }
        timeRangeParam.save()
        timeRangeParam
    }
    
    /**
     * Bind the jsonBindings to an ParamSet object
     * @param paramSetJson is the map created by parsing the JSONized helio.ParamSet from helio-model.js.
     * @param paramSet the ParamSet to populate. If null a new param will be created, otherwise the 
     * existing param will be updated.
     * @return the created or updated param. The created param will be saved and validated.
     */
    public ParamSet bindParamSet(paramSetJson, ParamSet paramSet) {
        println "paramSetJson: " + paramSetJson
        if (!paramSet) {
            paramSet = new ParamSet();
        }
        
        if (!paramSetJson.containsKey('entries')) {
            throw new IllegalArgumentException("Argument 'paramSetJson' should conain an entry called 'entries', but is: " + paramSetJson)
        }

        // do the automatic bindings
        bindData(paramSet, paramSetJson, [exclude :['entries']])
        
        // do some manual bindings
        paramSetJson.entries?.each{ entry ->
            paramSet.addToEntries(new ParamSetEntry(paramName : entry.paramName, operator : Operator.valueOf(entry.operator), paramValue : entry.paramValue))
        }

        if (!paramSet.validate()) {
            throw new ValidationException ("Invalid param set", paramSet.errors)
        }
        paramSet.save()
        paramSet
    }
}
