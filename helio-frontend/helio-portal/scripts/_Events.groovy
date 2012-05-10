eventCreateWarStart = {
    warName, stagingDir ->
    //if (grailsEnv == "production") {
    //}
    
    println "Copying build info file to classpath!"
    ant.copy(file: "${basedir}/jenkins/buildinfo.properties", tofile: "${classesDir}/buildinfo.properties", filtering: true, overwrite: true) {
        filterset {
          filter(token:"TITLE", value:"TITLE")
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
}
