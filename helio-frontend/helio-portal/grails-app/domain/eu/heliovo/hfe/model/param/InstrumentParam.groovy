package eu.heliovo.hfe.model.param

import java.util.Date
import java.util.List

import eu.heliovo.hfe.model.security.User

/**
 * Parameter for a list of instruments.
 * @author MarcoSoldati
 *
 */
class InstrumentParam extends AbstractParam {
    /**
     * Wire the spring security service.
     */
    transient springSecurityService
    
    /**
     * instruments
     */
    List<String> instruments = new ArrayList<String>()

    static mapping = {
        tablePerHierarchy false
    }
    
    static hasMany = [
        instruments : String
    ]

    public String toString() {
        getClass().getSimpleName() + ": " + "Name: " + name + ", Instruments: " + instruments
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
