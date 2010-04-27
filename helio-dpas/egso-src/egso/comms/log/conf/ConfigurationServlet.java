/*
 * Created on Jan 18, 2005
 */
package org.egso.comms.log.conf;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet class for webapp configuration.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("serial")
public class ConfigurationServlet extends HttpServlet {
    
    // Constants
    
    private static final String LOG4J_CONFIGURATION = "log4j.xml";
    
    public void init(ServletConfig config) throws ServletException {
        DOMConfigurator.configure(ConfigurationFactory.class.getResource(LOG4J_CONFIGURATION));
    }
    
}
