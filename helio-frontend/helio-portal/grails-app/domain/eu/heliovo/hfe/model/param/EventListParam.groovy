package eu.heliovo.hfe.model.param


import eu.heliovo.hfe.model.security.User

class EventListParam extends AbstractParam {
    /**
    * Wire the spring security service.
    */
   transient springSecurityService
   
   transient taskDescriptorService
  
   /**
    * Hold the params
    */
   List<String> listNames
   
   /**
    * The query terms are mapped to the list names
    */
   Map<String, QueryParamSet> queryTerms
   
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
       taskDescriptorService.taskDescriptor[this.taskName]
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
