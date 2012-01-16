package eu.heliovo.hfe.test

import eu.heliovo.hfe.service.VoTableService;
import grails.test.*

class VoTableServiceTests extends GrailsUnitTestCase {
    def voTableService
    
    protected void setUp() {
        voTableService = new VoTableService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
    }
}
