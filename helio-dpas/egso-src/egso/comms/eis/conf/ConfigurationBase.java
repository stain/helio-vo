/*
 * Created on Jan 14, 2005
 */
package org.egso.comms.eis.conf;

import java.io.File;
import java.net.URI;

/**
 * Base <code>Configuration</code> class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class ConfigurationBase implements Configuration {

    // Instance variables
    
    private String id = null;

    private String name = null;

    private URI type = null;

    private URI endpoint = null;

    private String parentPISId = null;
    
    private URI parentPISEndpoint = null;

    private long timeoutPeriod = 60000l;

    private URI filespaceBaseURI = null;

    private File filespaceBaseDirectory = null;

    private int threadPoolSize = 25;
    
    private int listeners = 1;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getType() {
        return type;
    }
    
    public void setType(URI type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public URI getJMSEndpoint() {
        return endpoint;
    }

    public void setJMSEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getParentPISId() {
        return parentPISId;
    }

    public void setParentPISId(String parentPISId) {
        this.parentPISId = parentPISId;
    }

    public URI getParentPISEndpoint() {
        return parentPISEndpoint;
    }

    public void setParentPISEndpoint(URI parentPISEndpoint) {
        this.parentPISEndpoint = parentPISEndpoint;
    }

    public long getTimeoutPeriod() {
        return timeoutPeriod;
    }

    public void setTimeoutPeriod(long timeoutPeriod) {
        this.timeoutPeriod = timeoutPeriod;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public File getFilespaceBaseDirectory() {
        return filespaceBaseDirectory;
    }

    public void setFilespaceBaseDirectory(File filespaceBaseDirectory) {
        this.filespaceBaseDirectory = filespaceBaseDirectory;
    }

    public URI getFilespaceBaseURI() {
        return filespaceBaseURI;
    }

    public void setFilespaceBaseURI(URI filespaceBaseURI) {
        if(!filespaceBaseURI.toString().endsWith("/")) {
            this.filespaceBaseURI = URI.create(filespaceBaseURI.toString() + "/");
        } else {
            this.filespaceBaseURI = filespaceBaseURI;
        }
   }
    
    public int getListeners() {
        return listeners;
    }
    
    public void setListeners(int listeners) {
        this.listeners = listeners;
    }
    
}
