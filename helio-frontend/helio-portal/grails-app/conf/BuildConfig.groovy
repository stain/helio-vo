grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

// Needed for http://jira.codehaus.org/browse/GRAILS-6096?page=com.atlassian.jira.plugin.system.issuetabpanels%3Aall-tabpanel
grails.project.war.osgi.headers = false

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
   		excludes "xml-apis"
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
		mavenLocal()
		mavenRepo "http://helio-dev.cs.technik.fhnw.ch/archiva/repository/snapshots"
		mavenRepo "http://helio-dev.cs.technik.fhnw.ch/archiva/repository/internal"
        grailsHome()
		grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		provided 'javax.servlet:servlet-api:2.5'
        compile ('eu.heliovo:helio-clientapi:5.0-SNAPSHOT', 'eu.heliovo:helio-cis-client:5.0-SNAPSHOT') {
            excludes 'slf4j-log4j12', 'slf4j-api'    
        }
	}
}

// MSo: due to a bug in grails 1.3.7 the generated war file includes an outdated slf4j-api-1.5.2.jar
// http://jira.codehaus.org/browse/GRAILS-5943
// the following hack just deletes this file
grails.war.resources = { stagingDir ->
	delete(file:"${stagingDir}/WEB-INF/lib/axis-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/axis-jaxrpc-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/axis-saaj-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.5.2.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.6.1.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-log4j12-1.6.1.jar")    
}