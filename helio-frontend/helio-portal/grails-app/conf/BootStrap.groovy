import java.util.concurrent.TimeUnit

import net.ivoa.xml.votable.v1.VOTABLE

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.frontend.ResultVT
import eu.heliovo.clientapi.model.catalog.HelioCatalogDao
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory
import eu.heliovo.clientapi.model.field.DomainValueDescriptor
import eu.heliovo.clientapi.model.field.HelioField
import eu.heliovo.clientapi.query.HelioQueryResult
import eu.heliovo.clientapi.query.asyncquery.AsyncQueryService
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.hfe.model.security.Role
import eu.heliovo.hfe.model.security.User
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability
import grails.util.GrailsUtil
import grails.validation.ValidationException

class BootStrap {

    def init = { servletContext ->
        // setup roles and register temp user filter
        setupSecurity()
        
        switch(GrailsUtil.environment){
            case "development":
            //org.hsqldb.util.DatabaseManager.main()
            case "production":
                // fire up HELIO Backend
                def helioClient = new HelioClient()
                // init descriptors
                if (!servletContext.instrumentDescriptors) {
                    servletContext.instrumentDescriptors = initDpasConfig(helioClient)
                }
                if (!servletContext.eventListDescriptors) {
                    servletContext.eventListDescriptors = initEventListDescriptors(helioClient)
                }

                break
            case "test":
                def testUser = new User(username: "a.b@c.de",
                password: "password.123", accountLocked: false, enabled: true)
                testUser.save()
                SpringSecurityUtils.reauthenticate("a.b@c.de", "password.123")
                break
        }
    }
    def destroy = {
    }


    private def setupSecurity() {
        // init roles
        def userRole = Role.findByAuthority("ROLE_USER")
        if (!userRole) {
            userRole = new Role(authority: 'ROLE_USER').save(flush: true)
        }

        // add temp user filter
        SpringSecurityUtils.clientRegisterFilter(
                'tempUserFilter', SecurityFilterPosition.REMEMBER_ME_FILTER.order + 20)
    }

    private def initDpasConfig(HelioClient helioClient) {
        // init catalog list for DPAS GUI
        HelioCatalogDao dpasDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.DPAS)
        if (dpasDao == null) {
            throw new NullPointerException("Unable to find service DPAS")
        }
        HelioField<String> instrumentDescriptorsField = dpasDao.getCatalogById('dpas').getFieldById('instrument')
        DomainValueDescriptor<String>[] instrumentDescriptors = instrumentDescriptorsField.getValueDomain()
    }

    /**
     * Init the event list descriptors
     * @param servletContext the current servletContext
     * @param helioClient the helioClient
     * @return
     */
    private def initEventListDescriptors(HelioClient helioClient) {
        // init the HEC configuration
        AsyncQueryService service = (AsyncQueryService)helioClient.getServiceInstance(HelioServiceName.HEC, ServiceCapability.ASYNC_QUERY_SERVICE,null )
        HelioQueryResult hecQueryResult = service.query(Arrays.asList("1900-01-01T00:00:00"), Arrays.asList("3000-12-31T00:00:00"),
                Arrays.asList("hec_catalogue"), null, 0, 0, null)

        int timeout = 300

        // TODO: replace by Hibernate objects.
        VOTABLE voTable = hecQueryResult.asVOTable(timeout, TimeUnit.SECONDS)
        ResultVT resvt = new ResultVT(voTable, hecQueryResult.getUserLogs())
        return resvt
    }
}