package eu.heliovo.hfe.controller

import grails.converters.JSON;

class DesController {

    def desConfiguration
    
    def paramFieldConfig = {
        def missionId = params.missionId
        def functionId = params.functionId
        
        // create an model containing the param options, and their values or default values.
        def ret = [:]
        
        if (missionId && functionId) {
            def mission = desConfiguration.getMissionById(missionId)
            def function = desConfiguration.getFunctionById(functionId)
            def desParams = desConfiguration.getParamsByMissionAndFunction(missionId, functionId)
            if (desParams) {
                ret['function'] = function
                ret['mission'] = mission 
                ret['params'] = desParams
            }
        }
        render ret as JSON
    }    
}
