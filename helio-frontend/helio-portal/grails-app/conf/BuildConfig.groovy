grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

// Needed for http://jira.codehaus.org/browse/GRAILS-6096?page=com.atlassian.jira.plugin.system.issuetabpanels%3Aall-tabpanel
grails.project.war.osgi.headers = false

grails.project.dependency.resolution = {
    // Everything with a version that ends with -SNAPSHOT is changing
    chainResolver.changingPattern = ".*-SNAPSHOT"

    // inherit Grails' default dependencies
    inherits("global") {
        excludes "xml-apis"
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
//        // Register the new JAR
//        def classLoader = getClass().classLoader
//        classLoader.addURL(new File(baseFile, "lib/grails-snapshot-dependencies-fix-0.1.jar").toURI().toURL())
//        
//        // Get a hold of the class for the new resolver
//        def snapshotResolverClass = classLoader.loadClass("grails.resolver.SnapshotAwareM2Resolver")
//        
//        // Define a new resolver that is snapshot aware
//        resolver(snapshotResolverClass.newInstance(name: "helio-snapshots", root: "http://helio-dev.cs.technik.fhnw.ch/archiva/repository/snapshots"))

        
        mavenRepo "http://helio-dev.cs.technik.fhnw.ch/archiva/repository/snapshots"
        mavenRepo "http://helio-dev.cs.technik.fhnw.ch/archiva/repository/internal"
        mavenLocal()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        // mavenCentral()
        grailsHome()
        grailsCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"

    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		provided 'javax.servlet:servlet-api:2.5'
        compile ('eu.heliovo:helio-clientapi:5.0-SNAPSHOT', 'eu.heliovo:helio-cis-client:5.0-SNAPSHOT') {
            excludes 'slf4j-log4j12', 'slf4j-api', 'hibernate-commons-annotations'    
        }
	}
    // force grails to fetch the latest versions from the maven repo rather than from the ivy cache.
    changing = true
}

// MSo: due to a bug in grails 1.3.7 the generated war file includes an outdated slf4j-api-1.5.2.jar
// http://jira.codehaus.org/browse/GRAILS-5943
// the following hack just deletes this file
grails.war.resources = { stagingDir, args ->
    copy(file: "src/resources/buildinfo.properties", tofile: "${stagingDir}/WEB-INF/classes/buildinfo.properties", filtering: true) {
      filterset {
        filter(token:"TITLE", value:"Foo Bar")
        filter(token:"BUILD_ID", value:"BUILD_ID")
        filter(token:"JOB_NAME", value:"JOB_NAME")
        filter(token:"BUILD_TAG", value:"BUILD_TAG")
        filter(token:"EXECUTOR_NUMBER", value:"EXECUTOR_NUMBER")
        filter(token:"JAVA_HOME", value:"JAVA_HOME")
        filter(token:"WORKSPACE", value:"WORKSPACE")
        filter(token:"JENKINS_URL", value:"JENKINS_URL")
        filter(token:"SVN_REVISION", value:"SVN_REVISION")
      }
    }
	delete(file:"${stagingDir}/WEB-INF/lib/axis-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/axis-jaxrpc-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/axis-saaj-1.3.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.5.2.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.6.1.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/slf4j-log4j12-1.6.1.jar")    
	delete(file:"${stagingDir}/WEB-INF/lib/hsqldb-1.8.0.10.jar")    
}