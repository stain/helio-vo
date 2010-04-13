/*
 * Created on Jan 7, 2005
 */
package org.egso.comms.pis.conf;

import java.net.URI;

/**
 * Interface for objects that provide configuration data.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface Configuration extends org.egso.comms.nds.conf.Configuration {

    public String getId();

    public URI getEndpoint();

    public String getName();

    public URI getNDSEndpoint();

    public long getNDSUpdateInterval();

    public int getThreadPoolSize();
    
}