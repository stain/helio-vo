package eu.heliovo.hfe.model.cart

import java.util.Date

import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.security.User

/**
 * Keep the input data for a specific user.
 * @author MarcoSoldati
 *
 */
class DataCart {
    /**
     * Wire the spring security service
     */
    transient springSecurityService

    /**
     * Last update date will be automatically managed by GORM
     */
    Date lastUpdated

    /**
     * Link to the currently logged in user.
     */
    User owner

    /**
     * List of params stored in the data cart. 
     */
    List<AbstractParam> cartItems = new ArrayList<AbstractParam>()

    static constraints = { 
        owner nullable : false 
    }
    
    static hasMany = [
        cartItems : AbstractParam
    ]
    
    static mapping = {
        cartItems cascade: "all-delete-orphan"
    }

    /**
     * Assign user if required.
     * @return nothing
     */
    def beforeValidate() {
        if (!owner)
            owner = User.get(springSecurityService.principal.id)
    }
}