package eu.heliovo.idlclient.provider.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.support.GenericXmlApplicationContext;

import eu.heliovo.clientapi.HelioClient;

/**
 * Setup the HELIO client instance and store it into the application context.
 * @author MarcoSoldati
 *
 */
public class IdlClientStartupListener implements ServletContextListener {

    /**
     * Id of the servlet context attribute that stores the helioClient instance
     */
    public static final String HELIO_CLIENT = null;
    
    /**
     * Store the context for proper shutdown.
     */
    private GenericXmlApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = new GenericXmlApplicationContext("classpath:spring/clientapi-main.xml");
        final HelioClient helioClient = context.getBean(HelioClient.class);
        sce.getServletContext().setAttribute(HELIO_CLIENT, helioClient);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        context.destroy();
    }
}
