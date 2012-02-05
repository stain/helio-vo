package eu.heliovo.hfe.model.param

import java.util.Map;

import eu.heliovo.hfe.model.security.User
import eu.heliovo.hfe.utils.TaskDescriptor

class EventListParam extends AbstractParam {
    /**
    * Wire the spring security service.
    */
   transient springSecurityService
  
   /**
    * Hold the params
    */
   List<String> listNames 
   
   /**
    * The filter is stored in a JSON string as it will be required on the client side only. 
    */
   String filter
   
   static hasMany = [
       listNames : String
   ]
      
   /**
    * Load the task description from the config
    * @return
    */
   def findTaskDescriptor() {
       TaskDescriptor.taskDescriptor[this.taskName]
   }
   
   def String toString() {
       "EventList [listNames: " + listNames +"]"
   }
   
   /**
    * Assign user if required.
    */
   def beforeValidate() {
       if (!owner) {
           owner = User.get(springSecurityService.principal.id)
       }
   }

    static constraints = {
        filter nullable : true
    }
}
