/*
 * Created on Jan 7, 2005
 */
package org.egso.comms.nds.conf;

import java.net.URI;
import java.net.URL;

/**
 * Interface for objects that provide configuration data.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface Configuration {

    public URI getPrimaryNDSEndpoint();

    public long getPrimaryNDSUpdateInterval();

    public URI getHibernateConnectionURL();

    public String getHibernateConnectionUsername();

    public String getHibernateConnectionPassword();

    public URL getHibernateConfigurationURL();
    
}