package eu.heliovo.hfe.service.util

import org.codehaus.groovy.grails.commons.metaclass.GroovyDynamicMethodsInterceptor
import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.InstrumentParam;
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
    def bindEventList(eventListJson, EventListParam eventListParam) {
        if (!eventListParam) {
            eventListParam = new EventListParam();
        }
        
        if (!eventListJson['entries']) {
            throw new IllegalArgumentException("Argument 'eventListJson' should conain an entry called 'entries'")
        }
        
        // do the automatic bindings
        bindData(eventListParam, eventListJson)
        
        // do some manual bindings
        eventListParam.listNames = eventListJson.entries.keySet() as List
        
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
}
