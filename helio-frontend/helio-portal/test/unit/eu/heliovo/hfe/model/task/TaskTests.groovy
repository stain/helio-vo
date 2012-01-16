package eu.heliovo.hfe.model.task

import eu.heliovo.hfe.model.param.TimeRangeParam;
import eu.heliovo.hfe.model.result.LocalVOTableResult;
import eu.heliovo.hfe.model.security.User;
import grails.plugins.springsecurity.SpringSecurityService;
import grails.test.*

class TaskTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateTask() {
        mockDomain(Task)
        mockDomain(TimeRangeParam)
        mockDomain(LocalVOTableResult)
        
        def task = new Task()
        task.owner = new User(username: "a.b@c.de",
            password: "password.123", accountLocked: false, enabled: true)
        task.taskName = "testTask"
        def timeRangeParam = new TimeRangeParam()
        timeRangeParam.addTimeRange(new Date()-5, new Date()-3)
        timeRangeParam.addTimeRange(new Date()-2, new Date())
        task.inputParams.put("timeRanges", timeRangeParam)
        
        task.outputParams.put("result", new LocalVOTableResult(owner: task.owner))
        
        assertTrue task.validate()
        assertNotNull task.save()
    }
}
