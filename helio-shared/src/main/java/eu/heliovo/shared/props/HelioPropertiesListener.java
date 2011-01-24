package eu.heliovo.shared.props;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A servlet context listener that initializes the Helio environment on startup.
 * This includes
 * <ul>
 *   <li>Set up some path properties to point to default path locations</li>
 * </ul>
 * @author marco soldati at fhnw ch
 *
 */
public class HelioPropertiesListener implements ServletContextListener {

	/**
	 * Abuse to enum construct to initialize the properties.
	 * @author marco soldati at fhnw ch
	 *
	 */
	private enum HelioProperty {
		WEB_APP_ROOT("webapp-root") {
			@Override
			String getPropertyValue(ServletContextEvent event) {
		        ServletContext context = event.getServletContext();
		        String path = context.getRealPath("/");
		        if (path == null) {
		        	throw new RuntimeException("Property " + getPropertyName() + " cannot be set. War is probably not extracted.");
		        }
				return path;
			}
		},
		;
		
		/**
		 * The name of the property to set
		 */
		private final String propertyName;

		private HelioProperty(String propertyName) {
			this.propertyName = propertyName;
		}
		
		/**
		 * Get the name of the property.
		 * @return the name, must not be null.
		 */
		public String getPropertyName() {
			return propertyName;
		}
		
		/**
		 * Get the value of a property.
		 * All enum params must return a non null value.
		 * @param servletContextEvent the current context event
		 * @return the computed value of the property. must not be null.
		 */
		abstract String getPropertyValue(ServletContextEvent servletContextEvent); 
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		for (HelioProperty prop : HelioProperty.values()) {
			System.setProperty(prop.getPropertyName(), prop.getPropertyValue(sce));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		for (HelioProperty prop : HelioProperty.values()) {
			System.clearProperty(prop.getPropertyName());
		}
	}

}
