/*
 * Created on Jan 7, 2005
 */
package org.egso.comms.eis.conf;

import java.io.File;
import java.net.URI;

/**
 * Interface for objects that provide configuration data.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface Configuration {

    public String getId();

    public String getName();

    public URI getType();

    public URI getEndpoint();

    public URI getJMSEndpoint();

    public String getParentPISId();

    public URI getParentPISEndpoint();

    public long getTimeoutPeriod();
    
    public int getThreadPoolSize();

    public File getFilespaceBaseDirectory();

    public URI getFilespaceBaseURI();

    public int getListeners();

}