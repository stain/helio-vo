package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.cart.DataCart
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.security.User
import grails.converters.JSON
import grails.validation.ValidationException

class DataCartController {
    
    /**
     * Wire the spring security service
     */
    transient springSecurityService
    
    /**
     * Load the current dataCart for the current user.
     * Create the data cart if it does not exist yet.
     */
    def load = {
        // load the data cart
        User owner = User.get(springSecurityService.principal.id)
        DataCart dataCart = DataCart.findByOwner(owner)
        
        // create cart if required
        if (dataCart == null) {
            dataCart = new DataCart()
            def timeRanges = new TimeRangeParam(name : "testTimeRange")
            timeRanges.addTimeRange(new Date(), new Date() + 60)
            if (!timeRanges.validate()) {
                throw new ValidationException("Failed to validate time ranges", timeRanges.errors);
            }
            dataCart.addToCartItems(timeRanges)
            
            def inst = new InstrumentParam(name : "testInstrument")
            inst.addToInstruments "rhessi_sxr"
            inst.addToInstruments "goes_xray"
            if (!inst.validate()) {
                throw new ValidationException("Failed to validate time ranges", inst.errors);
            }
            dataCart.addToCartItems(inst)

            def paramSet = new ParamSet(taskName: "goesplot", name : "testParamSet")
            paramSet.params = [:]
            paramSet.params.put("test", "1")
            paramSet.params.put("test2", "chabis")
            if (!paramSet.validate()) {
                throw new ValidationException("Failed to validate time ranges", paramSet.errors);
            }
            dataCart.addToCartItems(paramSet)
            
            if (!dataCart.validate()) {
                dataCart.errors.each {println it}
                throw new ValidationException ("Unable to validate dataCart", dataCart.errors)
            }
            
            dataCart.save(flush: true)
        }
        render dataCart as JSON
    }

    /**
     * Add a new data item to the data cart and return the list of current items.
     */
    def create = { 
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."
        
        println params
        // create new data item
        
        
        // add to data cart
        
        // save data cart
        
        render dataCart as JSON
    }
    
    /**
     * Update an existing item
     */
    def update = {
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."

        def param = AbstractParam.get(params.id);
        assert param != null, "Cannot find param with id " + params.id;
        assert dataCart.cartItems.contains(param), "Param must be in DataCart of current user"

        // parse data item
        
        
        // update param
        
        // save data cart
        
        render dataCart as JSON
    }
    
    /**
     * Remove a data item from the data cart.
     */
    def delete = { 
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."

        def param = AbstractParam.get(params.id);
        assert param != null, "Cannot find param with id " + params.id;
        assert dataCart.cartItems.contains(param), "Param must be in DataCart of current user"

        dataCart.removeFromCartItems(param)
        param.remove()

        render dataCart as JSON
    }
}
