package eu.heliovo.hfe.controller

import eu.heliovo.shared.util.DateUtil;
import eu.heliovo.clientapi.model.field.Operator;
import eu.heliovo.hfe.model.cart.DataCart
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.ParamSetEntry
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

    transient jsonToGormBindingService
    
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
        def newParam;
        if (data.type =='TimeRange') {
            newParam = new TimeRangeParam()
            // parse data item
            bindData(newParam, data)
    
            // bind time ranges
            data.timeRanges?.each{ timeRange->
                newParam.addTimeRange(DateUtil.fromIsoDate(timeRange.startTime), DateUtil.fromIsoDate(timeRange.endTime))
            }
        } else if (data.type == 'ParamSet') {
            newParam = new ParamSet()
            // parse data item
            bindData(newParam, data, [exclude:['entries']])
            
            // bind paramSet
            data.entries?.each{
                newParam.addToEntries(
                    new ParamSetEntry(paramName : it.paramName, operator : Operator.EQUALS, paramValue : it.paramValue)
                )
            }
        } else if (data.type == 'EventList') {
            newParam = jsonToGormBindingService.bindEventList(data, null)
        } else if (data.type == 'Instrument') {
            newParam = jsonToGormBindingService.bindInstrument(data, null)
        } else {
            throw new RuntimeException("Internal error: unknown parameter added to data cart: " + data)
        }

        newParam.save()
        // add to data cart
        dataCart.addToCartItems(newParam)

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

        def param = AbstractParam.get(data.id)
        assertParamInDataCart(param, dataCart, data.id)
		
        // parse data item
        bindData(param, data, [exclude : ['timeRanges', 'entries']]);
        
        if (data.type =='TimeRange') {
            // parse data item
            param.timeRanges.clear()
            param.save()  // save the entries to get rid of orphans
            
            bindData(param, data, [exclude : ['timeRanges']])
            
            // bind time ranges
            data.timeRanges?.each{ timeRange->
                param.addTimeRange(DateUtil.fromIsoDate(timeRange.startTime), DateUtil.fromIsoDate(timeRange.endTime))
            }
        } else if (data.type == 'ParamSet') {
            param.entries.clear()
            param.save()  // save the entries to get rid of orphans
            
            // parse data item
            bindData(param, data, [exclude:['entries']])
            
            // bind paramSet
            data.entries?.each{
                param.addToEntries(
                    new ParamSetEntry(paramName : it.paramName, operator : Operator.EQUALS, paramValue : it.paramValue)
                )
            }
        } else if (data.type == 'EventList') {
            param = jsonToGormBindingService.bindEventList(data, param)
        } else if (data.type == 'Instrument') {
            param = jsonToGormBindingService.bindInstrument(data, param)
        } else {
            throw new RuntimeException("Internal error: unknown parameter added to data cart: " + data)
        }

        // save param
        param.save()
        dataCart.save(flush: true)

        render dataCart as JSON
    }

	private void assertParamInDataCart(AbstractParam param, DataCart dataCart, int paramId) {
		assert param != null, "Cannot find param with id " + paramId;
		assert dataCart.cartItems.contains(param), "Param must be in DataCart of current user"
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
         
        render dataCart as JSON
    }
}
