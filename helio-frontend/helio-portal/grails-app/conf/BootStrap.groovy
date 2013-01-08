import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import eu.heliovo.clientapi.model.field.Operator;
import eu.heliovo.hfe.model.cart.DataCart
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.EventListParamEntry;
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.ParamSetEntry;
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.security.Role
import eu.heliovo.hfe.model.security.User
import grails.converters.JSON
import grails.util.GrailsUtil

class BootStrap {

	//    def voTableService;

	/**
	 * Auto-wire the helio client
	 */
	//    def helioClient;

	/**
	 * Auto wire the hec event list descriptor dao
	 * Used to warm up the system (i.e. to load the hec data from remote)
	 */
	def eventListDescriptorDao

	/**
	 * Get a reference to the grails application to register Spring aliases.
	 */
	def grailsApplication


	def init = { servletContext ->
		setSystemWideDefaultTimeZone()

		// setup roles and register temp user filter
		setupSecurity()

		initJSONMarshallers()

		switch(GrailsUtil.environment){
			case "development":
			//org.hsqldb.util.DatabaseManager.main()
			case "production":
				break
			case "test":
				def testUser = new User(username: "a.b@c.de",
				password: "password.123", accountLocked: false, enabled: true)
				testUser.save()
				SpringSecurityUtils.reauthenticate("a.b@c.de", "password.123")
				break
		}

		// warmup system
		eventListDescriptorDao.getDomainValues()
	}

	def destroy = {
	}


	private def setSystemWideDefaultTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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

	private def initJSONMarshallers() {
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
			returnArray['entries'] = it.entries
			returnArray['config'] = it.config
			return returnArray
		}
		JSON.registerObjectMarshaller ParamSetEntry, {
			def returnArray = [:]
			returnArray['paramName'] = it.paramName
			returnArray['operator'] = it.operator
			returnArray['paramValue'] = it.paramValue
			return returnArray
		}
		JSON.registerObjectMarshaller EventListParam, {
			def returnArray = marshalAbstractParam([:], it)
			returnArray['entries'] = it.entries
			return returnArray
		}
		JSON.registerObjectMarshaller EventListParamEntry, {
			def returnArray = [:]
			returnArray['listName'] = it.listName
			returnArray['whereClause'] = it.whereClause
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