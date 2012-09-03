package eu.heliovo.hfe.controller

import java.awt.event.ItemEvent;

import eu.heliovo.clientapi.model.field.Operator;
import eu.heliovo.registryclient.HelioServiceName;
import grails.converters.JSON;

/**
 * Return a JSON map containing the configuration of the value domains of the services.
 * @author MarcoSoldati
 *
 */
class ConfigController {
    /**
     * Auto wire the instrument descriptor dao
     */
    def instrumentDescriptorDao;
    
    /**
     * Auto wire the hec event list descriptor dao
     */
    def eventListDescriptorDao;
    
    def transient taskDescriptorService
    
    def index() { 
        def config = [:]
        
        config['EventList'] = getHecConfig()
        config['Instrument'] = getInstrumentConfig()
        config['ParamSet'] = getParamSetConfig()
        config['Operator'] = getOperatorConfig()
        render config as JSON
    }
    
    private Map getHecConfig() {
        // get the property descriptor for the from value of the current service
        def eventListDescriptors = eventListDescriptorDao?.getDomainValues()
        
        def hec = eventListDescriptors.collectEntries{[it.value, getEventListDescriptor(it)]}
        return hec;
    }
    
    private Map getInstrumentConfig() {
        // get the property descriptor for the from value of the current service
        def instrumentDescriptors = instrumentDescriptorDao?.getDomainValues()
        
        def instruments = new TreeMap(instrumentDescriptors.collectEntries{[it.value, getInstrumentDescriptor(it)]})
        return instruments;
    }
    
    /**
     * Get all tasks that have a param set and add the labels.
     * @return the param 
     */
    private Map getParamSetConfig() {
        def paramSets = taskDescriptorService.findParamSetConfig()
        return paramSets.collectEntries{[it.key, getParamSetDescriptors(it.value)]}
    }
    
    /**
     * Get single event list descriptor
     * @param desc the descriptor to check.
     * @return the descriptor map.
     */
    private getEventListDescriptor(desc) {
        def descriptor = [:]
        descriptor['id'] = desc.value;
        descriptor['label'] = desc.label;
        descriptor['description'] = desc.description;
        descriptor += desc.fieldDescriptors.collectEntries{[it.id, getFieldDescriptor(it)]}
        return descriptor;
    }
    
    /**
     * Get single instrument descriptor
     * @param desc the descriptor to check.
     * @return the instrument descriptor map
     */
    private getInstrumentDescriptor(desc) {
        def descriptor = [:]
        descriptor['id'] = desc.value;
        descriptor['label'] = desc.observatoryName + ": " + desc.label + " (" + desc.name + ")";
        descriptor['description'] = desc.description;
        descriptor['isInPat'] = desc.isInPat;
        
        return descriptor;
    }
    
    private getFieldDescriptor(hfd) {
        def fd = [:]
        fd['id'] = hfd.id
        fd['label'] = hfd.label
        if (hfd.description) fd['description'] = hfd.description
        if (hfd.type.utype) fd['utype'] = hfd.type.utype
        if (hfd.type.ucd) fd['ucd'] = hfd.type.ucd
        if (hfd.type.unit) fd['unit'] = hfd.type.unit
        return fd
    }
    
    /**
     * Iterate the paramSet descriptors of one task
     * and reduce to a map.
     * @param descs the descriptors
     * @return map of paramName-descriptor entries. 
     */
    private getParamSetDescriptors(descs) {
        descs.collectEntries{[it.key, getParamSetDescriptor(it)]}
    }
    
    private getParamSetDescriptor(desc) {
        def descriptor = [:]
        descriptor['id'] = desc.key;
        descriptor['label'] = desc.value.label;
        descriptor['description'] = desc.value.description;
        return descriptor
    }
    
    private getOperatorConfig() {
        Operator.values().collectEntries{[it.toString(), ['id' : it.toString(), 'symbol' : it.symbol]]}
    }
}
