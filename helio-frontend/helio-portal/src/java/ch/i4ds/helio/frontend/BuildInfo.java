package ch.i4ds.helio.frontend;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Some static methods to read the version properties from the manifest file.
 * @author MarcoSoldati
 *
 */
public class BuildInfo {
	/**
	 * Happy, little logger
	 */
	private static final Logger _LOGGER = Logger.getLogger(BuildInfo.class);
	
	/**
	 * Path of the classpath resource to load.
	 */
	private static final String BUILD_INFO = "/buildinfo.properties";
	
	/**
	 * Keep the singleton instance
	 */
	private static final BuildInfo instance = new BuildInfo();

	/**
	 * Get the singleton instance of the BuildInfo
	 * @return the build info instance
	 */
	public static BuildInfo getInstance() {
		return instance;
	}

	/**
	 * Keep a reference to the properties.
	 */
	private final Properties props = new Properties();
	
	/**
	 * Hide the default constructor
	 */
	private BuildInfo() {
		InputStream inputStream = BuildInfo.class.getResourceAsStream(BUILD_INFO);
		if (inputStream == null) {
			_LOGGER.warn("Cannot find resource '" + BUILD_INFO + "' in classpath");
		}
		try {
			props.load(inputStream);
		} catch (IOException e) {
			_LOGGER.warn("Unable to load properties from '" + BUILD_INFO + "': " + e.getMessage(), e);
		}
	}
	
	/**
	 * Get the current build number, such as "153"
	 */
	public String getBuildNumber() {
		return getProperty("hfe.buildnumber", "n/a");
	}

	/**
	 * Get he current build id, such as "2005-08-22_23-59-59" (YYYY-MM-DD_hh-mm-ss)
	 */
	public String getBuildID() {		
		return getProperty("hfe.buildid", "n/a");
	}

	/**
	 * Get the name of the project of this build, such as "foo"
	 */
	public String getJobName() {
		return getProperty("hfe.jobname", "n/a");
	}

	/**
	 * Get the build tag such as "jenkins-${JOBNAME}-${BUILD_NUMBER}". Convenient to put into a resource file, a jar file, etc for easier identification. 
	 * @return
	 */
	public String getBuildTag() { 
		return getProperty("hfe.buildtag", "n/a");
	}
	
	/**
	 * Get the unique number that identifies the current executor (among executors of the same machine) that's carrying out this build. This is the number you see in the "build executor status", except that the number starts from 0, not 1.	 
	 * @return
	 */
	public String getExecutorNumber() {
		return getProperty("hfe.executornumber", "n/a");		
	}

	/**
	 * Get location of Java. If your job is configured to use a specific JDK, this variable is set to the JAVA_HOME of the specified JDK. When this variable is set, PATH is also updated to have $JAVA_HOME/bin.
	 */
	public String getJavaHome() {
		return getProperty("hfe.javahome", "n/a");
	}

	/**
	 * WORKSPACE: The absolute path of the workspace.
	 */
	public String getWorkspacePath() {
		return getProperty("hfe.workspace", "n/a");
	}
	
	/**
	 * Full URL of Jenkins, like http://server:port/jenkins/
	 */
	public String getJenkinsUrl() {
		return getProperty("hfe.jenkinsurl", "n/a");
	}
	
	/**
	 *  Get the subversion revision number of the module.
	 */
	public String getSvnRevision() {
		return getProperty("hfe.svnrevision", "n/a");
	}
	
	private String getProperty(String propertyName, String defaultValue) {
		String value = props.getProperty(propertyName, defaultValue);
		if (value.startsWith("${")) {
			value = defaultValue;
		}
		return value;
	}
}
