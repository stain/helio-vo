package ch.i4ds.helio.frontend;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

public class BuildInfoTest {
	
    @Test public void getProperties() {
		BuildInfo instance = BuildInfo.getInstance();
		assertNotNull(instance.getBuildID());
		assertNotNull(instance.getBuildNumber());
		assertNotNull(instance.getBuildTag());
		assertNotNull(instance.getExecutorNumber());
		assertNotNull(instance.getJavaHome());
		assertNotNull(instance.getJenkinsUrl());
		assertNotNull(instance.getJobName());
		assertNotNull(instance.getSvnRevision());
		assertNotNull(instance.getWorkspacePath());
	}

}
