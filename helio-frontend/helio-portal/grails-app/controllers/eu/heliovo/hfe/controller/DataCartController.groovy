package eu.heliovo.hfe.controller

import eu.heliovo.shared.util.DateUtil;
import eu.heliovo.hfe.model.cart.DataCart
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.security.User
import eu.heliovo.shared.util.DateUtil
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
            dataCart.save(flush: true)
        }
        render dataCart as JSON
    }

    /**
     * Add a new data item to the data cart and return the list of current items.
     */
    def create = {
        User owner = User.get(springSecurityService.principal.id)
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."

        def data = JSON.parse(params.data)

        // create new data item
        def param;
        if (data.timeRanges) {
            param = new TimeRangeParam()
            // parse data item
            bindData(param, data)
    
            // bind time ranges
            data.timeRanges?.each{ timeRange->
                param.addTimeRange(DateUtil.fromIsoDate(timeRange.startTime), DateUtil.fromIsoDate(timeRange.endTime))
            }
        } else if (data.params) {
            param = new ParamSet()
            // parse data item
            bindData(param, data)
    
            // bind paramSet
            data.params?.each{ entry ->
                param.params.put(entry.key, entry.value)
            }
        } else if (data.listNames) {
            param = new EventListParam()
            bindData(param,data)
        } else if (data.instruments) {
            param = new InstrumentParam()
            bindData(param,data)
        } else {
            throw new RuntimeException("Internal error: unknown parameter added to data cart: " + data)
        }

        param.save()
        // add to data cart
        dataCart.addToCartItems(param)

        // and save
        if (!dataCart.validate()) {
            dataCart.errors.each {println it}
            throw new ValidationException ("Unable to validate dataCart", dataCart.errors)
        }
        dataCart.save(flush: true)

        render dataCart as JSON
    }

    /**
     * Update an existing item
     */
    def update = {
        User owner = User.get(springSecurityService.principal.id)
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."

        // parse data item
        def data = JSON.parse(params.data)
        
        def param = AbstractParam.get(data.id);
        assert param != null, "Cannot find param with id " + data.id;
        assert dataCart.cartItems.contains(param), "Param must be in DataCart of current user"

        // parse data item
        bindData(param, data, [exclude : ['timeRanges', 'params']]);
        if (param instanceof TimeRangeParam) {
            param.timeRanges.clear()
            data.timeRanges?.each{ timeRange->
                param.addTimeRange(DateUtil.fromIsoDate(timeRange.startTime), DateUtil.fromIsoDate(timeRange.endTime))
            }
        }
        // update params
        data.params?.each{ entry ->
            param.params.put(entry.key, entry.value)
        }

        // save param
        //param.save()
        dataCart.save(flush: true)

        render dataCart as JSON
    }

    /**
     * Remove a data item from the data cart.
     */
    def delete = {
        User owner = User.get(springSecurityService.principal.id)
        DataCart dataCart = DataCart.findByOwner(owner)
        assert dataCart != null, "Internal error: unable to find data cart for current user."
        
        // parse data item
        def data = JSON.parse(params.data)

        def param = AbstractParam.get(data.id);
        
        assert param != null, "Cannot find param with id " + data.id;
        assert dataCart.cartItems.contains(param), "Param must be in DataCart of current user"
        
        dataCart.removeFromCartItems(param)
        dataCart.save(flush : true)        
        
        println "before to json"
        render dataCart as JSON
    }
}
