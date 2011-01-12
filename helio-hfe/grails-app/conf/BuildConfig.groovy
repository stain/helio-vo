grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

// Needed for http://jira.codehaus.org/browse/GRAILS-6096?page=com.atlassian.jira.plugin.system.issuetabpanels%3Aall-tabpanel
grails.project.war.osgi.headers = false

grails.project.dependency.resolution = {
    inherits("global") {
		excludes "servlet-api"
	}
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
		mavenLocal()
		mavenRepo "http://helio-dev.i4ds.ch:8123/archiva/repository/internal"
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
        // runtime 'com.mysql:mysql-connector-java:5.1.5'
        
        // Prevent inclusion of servlet-api in the WAR file
		provided 'javax.servlet:servlet-api:2.3'
    }
    
    // Needed to automatically include the Maven POM dependencies
	pom true
}
