package eu.heliovo.hfe.model.param

import grails.test.*

class ParamSetTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    /**
     * Test creation of a param set.
     */
    void testCreateParamSet() {
        def instances = [ 
            new ParamSet(params : [start : 1, end: 2, color : "green"]) 
        ]
        
        mockDomain(ParamSet, instances)
        
        assertEquals 1, ParamSet.count()
    }
}
