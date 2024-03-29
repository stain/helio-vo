// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

// fetch buildinfo properteis if possible
def buildinfo = getClass().getResource("buildinfo.properties")
if (buildinfo)
    grails.config.locations = [ "classpath:buildinfo.properties" ]

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"
grails.views.javascript.library="jquery"
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// enable GSP preprocessing: replace head -> g:captureHead, title -> g:captureTitle, meta -> g:captureMeta, body -> g:captureBody
grails.views.gsp.sitemesh.preprocess = true

grails.converters.json.default.deep = true


// set per-environment serverURL stem for creating absolute links
environments {
    production {
        helio.appId = 'helio'
        grails.serverURL = "https://helio.i4ds.technik.fhnw.ch/${appName}"
    }
    development {
        helio.appId = 'helio-dev'
        grails.serverURL = "https://helio.i4ds.technik.fhnw.ch/${appName}"
        grails.converters.json.pretty.print = true
    }
    test {
        helio.appId = 'helio-test'
        grails.serverURL = "http://localhost:8080/${appName}"
    }
}

// log4j configuration
def logbaseDir = "${userHome}"

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    
    appenders {
        rollingFile name: "stacktrace", maxFileSize: 1024,
            file: logbaseDir + "/.helio/logs/stacktrace.log" 
    }
    
    debug 'grails.app.tagLib',
          'grails.app.controller',
          'grails.app.service'
              

     info 'grails.app.controller',
          'grails.app.service',
          'eu.heliovo'
           

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'

    warn   'org.mortbay.log'

    root {
        additivity = true;
    }
}    
 
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'eu.heliovo.hfe.model.security.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'eu.heliovo.hfe.model.security.UserRole'
grails.plugins.springsecurity.authority.className = 'eu.heliovo.hfe.model.security.Role'

grails.plugins.springsecurity.providerNames = [
    'daoAuthenticationProvider',
    //'cisAuthenticationProvider',
    'rememberMeAuthenticationProvider',
    'anonymousAuthenticationProvider']

// some remember me settings
rememberMe.cookieName = 'helio-frontend'
rememberMe.key = 'helioHFE'
rememberMe.tokenValiditySeconds = 5184000  // 60days

// anon settings (important for TemUserSecurityFilter)
anon.key = 'helioHFE';

// make inner classes validating.
grails.validateable.packages = ['eu.heliovo.hfe.model']
