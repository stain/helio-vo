package eu.heliovo.hfe.model.param

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;

import grails.test.*
import eu.heliovo.hfe.model.security.User

class TimeRangeParamTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAddTimeRange() {
        mockDomain(TimeRangeParam)
        mockDomain(TimeRange)
        def timeRange = new TimeRangeParam()
        timeRange.owner = new User(username: "a.b@c.de",
                password: "password.123", accountLocked: false, enabled: true)

        Calendar cal = Calendar.getInstance()
        def startDate = cal.getTime()
        cal.add(Calendar.DAY_OF_MONTH, 1)
        def endDate = cal.getTime()
        timeRange.addTimeRange(startDate, endDate)

        timeRange.name="my name"

        def val = timeRange.validate()
        if (!val)
            timeRange.errors.each {println it}
        assertTrue val
        assertNotNull timeRange.save()
        //println timeRange.save()
    }

    void testAddMultipleTimeRange() {
        mockDomain(TimeRangeParam)
        mockDomain(TimeRange)
        def timeRange = new TimeRangeParam()
        timeRange.owner = new User(username: "a.b@c.de",
                password: "password.123", accountLocked: false, enabled: true)
        Calendar cal = Calendar.getInstance()
        cal.set(2011, 10, 10, 0, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)

        def startDate = cal.getTime()
        cal.add(Calendar.DAY_OF_MONTH, 1)
        def endDate = cal.getTime()
        timeRange.addTimeRange(startDate, endDate)

        cal.add(Calendar.MONTH, 1)
        startDate = cal.getTime()
        cal.add(Calendar.DAY_OF_MONTH, 1)
        endDate = cal.getTime()
        timeRange.addTimeRange(startDate, endDate)


        assertTrue timeRange.validate()
        assertNotNull timeRange.save()
        //println timeRange.save()
    }

    void testInvalidTimeRangeManipulations() {
        mockDomain(TimeRangeParam)
        mockDomain(TimeRange)
        def timeRange = new TimeRangeParam()
        timeRange.owner = new User(username: "a.b@c.de",
                password: "password.123", accountLocked: false, enabled: true)

        Calendar cal = Calendar.getInstance()
        def startDate = cal.getTime()
        // end date before start date
        cal.add(Calendar.DAY_OF_MONTH, -1)
        def endDate = cal.getTime()
        timeRange.addTimeRange(startDate, endDate)

        assertFalse timeRange.validate()
        assertEquals(1, timeRange.errors.allErrors.size())
        assertEquals("timeRanges", timeRange.errors.allErrors[0].field)
        //timeRange.errors.each { println it }
        //println timeRange.save()
    }
}
