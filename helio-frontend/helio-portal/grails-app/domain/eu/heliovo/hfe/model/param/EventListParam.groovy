package eu.heliovo.hfe.model.param

import eu.heliovo.hfe.model.security.User


class EventListParam extends AbstractParam {
    /**
    * Wire the spring security service.
    */
   transient springSecurityService
   
   transient taskDescriptorService
  
   
   static hasMany = [
       entries : EventListParamEntry
   ]
   
   /**
    * Load the task description from the config
    * @return
    */
   def findTaskDescriptor() {
       taskDescriptorService.taskDescriptor[this.taskName]
   }
   
   def String toString() {
       "EventList [entries: " + entries +"]"
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
