package org.egso.provider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.egso.provider.admin.ProviderMonitor;

/**
 * Servlet to power up a Provider. This is just a workaround and
 * should be replaced by an own service.
 * 
 * @author Marco Soldati
 * @version 2.0 - //2004 [27/11/2003]
 */
@SuppressWarnings("serial")
public class ProviderServlet extends HttpServlet {

    /**
     * JAVADOC: Description of the Field
     */
    Provider provider = null;

    /**
     * Init the service.
     * 
     * @exception ServletException If anything goes wrong during
     * initialisation.
     */
    public void init() throws ServletException {
        try {
            provider = Provider.getInstance();
        } catch (Exception e) {
            ProviderMonitor.getInstance().reportException(e);
            System.out.println("Exception while initialising the provider: " + e.getMessage());
            throw new ServletException("Exception while initialising the provider: " + e.getMessage(), e);
        }
    }

    /**
     * Dispose the provider
     */
    public void destroy() {
        if (provider != null) {
            provider.dispose();
        }
        provider = null;
    }

}

