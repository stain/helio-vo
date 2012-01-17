package eu.heliovo.hfe.model.param

import java.util.Date;

import eu.heliovo.hfe.model.security.User;

/**
 * Abstract base class of any input parameter
 * @author MarcoSoldati
 *
 */
class AbstractParam {
    /**
     * Wire the spring security service.
     */
    transient springSecurityService
    
    /**
     * Creation date will be automatically set by GORM
     */
    Date dateCreated

    /**
     * Last update date will be automatically managed by GORM
     */
    Date lastUpdated

    /**
     * user defined name of the param
     */
    String name

    /**
     * owner of this parameter. Defaults to currently logged in user.
     */
    User owner
	
	static mapping = {
		tablePerHierarchy false
	}

    static constraints = {
        name nullable : true
        owner nullable : false
    }

    /**
     * Assign user if required.
     */
    def beforeValidate() {
        if (!owner) {
            owner = User.get(springSecurityService.principal.id)
        }
    }
}
