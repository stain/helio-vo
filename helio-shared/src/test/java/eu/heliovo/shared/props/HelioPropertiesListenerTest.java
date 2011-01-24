package eu.heliovo.shared.props;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.servlet.ServletContext;

import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.heliovo.shared.props.HelioPropertiesListener;
import eu.heliovo.shared.util.FileUtil;

/**
 * Unit test for the config listener
 * @author marco soldati at fhnw ch
 *
 */
public class HelioPropertiesListenerTest {

	/**
	 * Temporary path for test files
	 */
	private File tempDir;
	
	@Before public void setUp() {
		tempDir = new File(System.getProperty("java.io.tmpdir"), "helio-shared");
		assertNotNull(tempDir);
		if (!tempDir.exists()) {
			assertTrue(tempDir.mkdirs());
		}
	}
	
	@After public void tearDown() {
		FileUtil.removeDir(tempDir);
	}
	
	/**
	 * Test the configuration listener
	 */
	@Test public void testConfigurationListener() {
		File contextPath = new File(tempDir, "contextDir");
		assertTrue(contextPath.mkdirs());
		
		HelioPropertiesListener configListener = new HelioPropertiesListener();
		ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
		ServletContext context = mock(ServletContext.class);
		when(servletContextEvent.getServletContext()).thenReturn(context);
		
		when(context.getRealPath("/")).thenReturn(contextPath.getAbsolutePath());
		
		configListener.contextInitialized(servletContextEvent);
		
		assertEquals(contextPath.getAbsolutePath(), System.getProperty("webapp-root"));
		
	}
}
