
// Place your Spring DSL code here
beans = {
    importBeans "classpath:spring/clientapi-main.xml"
    
    // a filter to create a temporary user for non-logged-in users. 
    tempUserFilter(eu.heliovo.hfe.filter.TempUserSecurityFilter) {
      // properties
    }
    
    conversionService (org.springframework.context.support.ConversionServiceFactoryBean){
    }
	
	springConfig.addAlias 'hibernateDatastoreclientapiSessionFactory', 'hibernateDatastore'	
}