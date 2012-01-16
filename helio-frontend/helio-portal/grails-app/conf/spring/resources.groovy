
// Place your Spring DSL code here
beans = {
    // a filter to create a temporary user for non-logged-in users. 
    tempUserFilter(eu.heliovo.hfe.filter.TempUserSecurityFilter) {
      // properties
    }
    
    conversionService (org.springframework.context.support.ConversionServiceFactoryBean){
    }
}