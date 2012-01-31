package eu.heliovo.hfe.service

import eu.heliovo.hfe.model.result.HelioResult;

/**
 * Service to prepare the extraction of parameters from a votable
 * @author MarcoSoldati
 *
 */
class ExtractParamsService {
    /**
     * Nothing is being saved in here.
     */
    static transactional = false

    def voTableService
    
    /**
     * Create the model for the extraction dialog
     * @param votableModel the votable model result object.
     * @param tableId the id of the table to process
     * @return a map containing the information to render the extractParamDialog
     */
    def createExtractionModel(HelioResult votableResult, int tableId) {
        def tableModel = voTableService.createVOTableModel(votableResult)
        
        assert tableModel.tables.length() > tableId, "Table id should be smaller than tables length: " + tableId + "<" + tableModel.tables.length()
        
        def table = tableModel.tables[tableId]
        
        def model = [:]
        model.paramTypes = [:]
        
        model.paramTypes.instrument = handleInstrumentParamType(table)
        model.paramTypes.instrument = handleInstrumentParamType(table)
        
        
    }
    
    def handleInstrumentParamType(table) {
        def paramType = [:]
        paramType.fields = handleInstrumentFields(table.fields.collect { field -> field.name == 'instrument' })
        paramType.time = handleTimeFields(table.fields.collect { field -> field.name == 'start_time' || field.name == 'end_time' })
    }
    
    /**
     * Create the ids of a collection of fields.
     * @param fields the fields from the voTableModel
     * @return
     */
    def handleInstrumentFields(fields) {
        def retFields = handleParamType([:], 'instrument', 'Instrument', 'Inst', 'circle_inst', ['', 'Instrument'])
        fields.each { field -> 
            def newField = field.clone()
            newField.mappedTo = 'Instrument'
        }
    }

    /**
     * Create the ids of a collection of fields.
     * @param fields the fields from the voTableModel
     * @return
     */
    def handleTimeFields(fields) {
        def retFields = handleParamType([:], 'dateRange', 'Time Range', 'time', 'circle_time', ['', 'start_time', 'end_time'])
        fields.each { field -> 
            def newField = field.clone()
            newField.mappedTo = newField.name == 'start_time' ? 'start_time' : newField.name == 'end_time' ? 'end_time' : ''
        }
    }
    
    /**
     * Populate the paramType with attributes
     * @param paramType the paramType
     * @param id the id 
     * @param label the label
     * @param shortName a short name used for the generated label
     * @param iconName the icon name 
     * @param mappedToRange
     * @return
     */
    def handleParamType(Map paramType, String id, String label, String shortName, String iconName, List mappedToRange) {
        paramType.id = id
        paramType.label = label
        paramType.shortName = shortName
        paramType.icon = iconName
        paramType.mappedToRange = mappedToRange
        return paramType
    }  
}
