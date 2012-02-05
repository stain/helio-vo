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
import eu.heliovo.clientapi.query.HelioQueryService
import eu.heliovo.hfe.model.cart.DataCart
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.param.EventListParam;
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.security.Role
import eu.heliovo.hfe.model.security.User
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability
import grails.converters.JSON
import grails.util.GrailsUtil

class BootStrap {
    
    def voTableService;

    def init = { servletContext ->
        // setup roles and register temp user filter
        setupSecurity()

        initMarshallers()

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
                if (!servletContext.eventListModel) {
                    //servletContext.eventListDescriptors = initEventListDescriptors(helioClient)
                    servletContext.eventListModel = initEventListModel(helioClient)
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
        HelioQueryService service = helioClient.getServiceInstance(HelioServiceName.HEC, ServiceCapability.SYNC_QUERY_SERVICE,null )
        HelioQueryResult hecQueryResult = service.query(Arrays.asList("1900-01-01T00:00:00"), Arrays.asList("3000-12-31T00:00:00"),
                Arrays.asList("hec_catalogue"), null, 0, 0, null)

        int timeout = 60

        // TODO: replace by Hibernate objects.
        VOTABLE voTable = hecQueryResult.asVOTable(timeout, TimeUnit.SECONDS)
        ResultVT resvt = new ResultVT(voTable, hecQueryResult.getUserLogs())
        return resvt
    }

    /**
     * Init the event list descriptors
     * @param servletContext the current servletContext
     * @param helioClient the helioClient
     * @return
     */
    private def initEventListModel(HelioClient helioClient) {
        // init the HEC configuration
        HelioQueryService service = helioClient.getServiceInstance(HelioServiceName.HEC, ServiceCapability.SYNC_QUERY_SERVICE,null )
        HelioQueryResult hecQueryResult = service.query(Arrays.asList("1900-01-01T00:00:00"), Arrays.asList("3000-12-31T00:00:00"),
                Arrays.asList("hec_catalogue"), null, 0, 0, null)

        int timeout = 60

        URL votableUrl = hecQueryResult.asURL(timeout, TimeUnit.SECONDS)
        def model = voTableService.createVOTableModel(new RemoteVOTableResult(url: votableUrl.toString()))
        assert model.tables[0], "Failed to load HEC list catalogue"
        
        model.tables[0]
    }

    private def initMarshallers() {
        JSON.registerObjectMarshaller DataCart, {
            def returnArray = [:]
            returnArray['id'] = it.id
            returnArray['lastUpdated'] = it.lastUpdated
            returnArray['cartItems'] = it.cartItems
            return returnArray
        }
        JSON.registerObjectMarshaller TimeRange, {
            def returnArray = [:]
            returnArray['startTime'] = it.startTime
            returnArray['endTime'] = it.endTime
            return returnArray
        }
        JSON.registerObjectMarshaller TimeRangeParam, {
            def returnArray = marshalAbstractParam([:], it)
            returnArray['timeRanges'] = it.timeRanges
            return returnArray
        }
        JSON.registerObjectMarshaller InstrumentParam, {
            def returnArray = marshalAbstractParam([:], it)
            returnArray['instruments'] = it.instruments
            return returnArray
        }
        JSON.registerObjectMarshaller ParamSet, {
            def returnArray = marshalAbstractParam([:], it)
            returnArray['params'] = it.params
            returnArray['config'] = it.config
            return returnArray
        }
        JSON.registerObjectMarshaller EventListParam, {
            def returnArray = marshalAbstractParam([:], it)
            returnArray['listNames'] = it.listNames
            return returnArray
        }
    }

    /**
     * Marshal the common attributes of an AbstractParam
     * @param map the map to populate. must not be null
     * @param param the param
     * @return the map
     */
    private def marshalAbstractParam(Map map, AbstractParam param) {
        map['class'] = param.class
        map['id'] = param.id
        map['dateCreated'] = param.dateCreated
        map['lastUpdated'] = param.lastUpdated
        map['taskName'] = param.taskName
        map['name'] = param.name
        return map;
    }
}