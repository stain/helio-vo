package eu.heliovo.hfe.service

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;

import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.security.User
import grails.plugins.springsecurity.SpringSecurityService;
import grails.test.*

class ParamServiceTests extends GrailsUnitTestCase {
    def paramService
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    /**
     * Test teh fin latest time range function
     */
    void testFindLatestTimeRangeParam() {
        new TimeRangeParam([timeRanges: [new TimeRange(startTime : new Date()-50, endTime : new Date()-45)]]).save()
        new TimeRangeParam([timeRanges: [new TimeRange(startTime : new Date()-200, endTime : new Date()-199)]]).save()
        def latest = new TimeRangeParam([timeRanges: [new TimeRange(startTime : new Date()-100, endTime : new Date()-95)]])
        latest.save()
        
        def latest2 =  paramService.findLatestTimeRangeParam()
        
        assertEquals latest, latest2
    }
}
