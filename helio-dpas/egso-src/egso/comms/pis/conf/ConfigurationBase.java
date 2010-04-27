/*
 * Created on Jan 13, 2005
 */
package org.egso.comms.pis.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Base <code>Configuration</code> class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class ConfigurationBase implements Configuration {

    private String id = null;

    private String name = null;

    private URI endpoint = null;

    private URI ndsEndpoint = null;

    private long ndsUpdateInterval = 600000l;

    private int threadPoolSize = 25;

    private URI hibernateConnectionURL = null;

    private String hibernateConnectionUsername = "egso_writer";

    private String hibernateConnectionPassword = "egso_team";
    
    private URL hibernateConfigurationURL = ConfigurationBase.class.getResource("hibernate.cfg.xml");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public void parseEndpoint(String endpoint) throws URISyntaxException {
        setEndpoint(new URI(endpoint));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getNDSEndpoint() {
        return ndsEndpoint;
    }

    public void setNDSEndpoint(URI ndsEndpoint) {
        this.ndsEndpoint = ndsEndpoint;
    }

    public URI getPrimaryNDSEndpoint() {
        return ndsEndpoint;
    }

    public long getNDSUpdateInterval() {
        return ndsUpdateInterval;
    }

    public long getPrimaryNDSUpdateInterval() {
        return ndsUpdateInterval;
    }

    public void setNDSUpdateInterval(long ndsUpdateInterval) {
        this.ndsUpdateInterval = ndsUpdateInterval;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public URI getHibernateConnectionURL() {
        return hibernateConnectionURL;
    }

    public void setHibernateConnectionURL(URI hibernateConnectionURL) {
        this.hibernateConnectionURL = hibernateConnectionURL;
    }

    public String getHibernateConnectionUsername() {
        return hibernateConnectionUsername;
    }

    public void setHibernateConnectionUsername(String hibernateConnectionUsername) {
        this.hibernateConnectionUsername = hibernateConnectionUsername;
    }

    public String getHibernateConnectionPassword() {
        return hibernateConnectionPassword;
    }

    public void setHibernateConnectionPassword(String hibernateConnectionPassword) {
        this.hibernateConnectionPassword = hibernateConnectionPassword;
    }

    public URL getHibernateConfigurationURL() {
        return hibernateConfigurationURL;
    }
    
    public void setHibernateConfigurationURL(URL hibernateConfigurationURL) {
        this.hibernateConfigurationURL = hibernateConfigurationURL;
    }

}