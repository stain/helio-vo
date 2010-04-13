/*
 * Created on Jan 13, 2005
 */
package org.egso.comms.log.conf;

import java.net.URI;
import java.net.URL;

/**
 * Base <code>Configuration</code> class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class ConfigurationBase implements Configuration {

    // Instance variables
    
    private URI hibernateConnectionURL = URI.create("jdbc:mysql://localhost:3306/egso_comms_log");

    private String hibernateConnectionUsername = "egso_writer";

    private String hibernateConnectionPassword = "egso_team";
    
    private URL hibernateConfigurationURL = ConfigurationBase.class.getResource("hibernate.cfg.xml");
    
    public URI getHibernateConnectionURL() {
        return hibernateConnectionURL;
    }

    public void setHibernateConnectionURL(URI hibernateDatabaseURL) {
        this.hibernateConnectionURL = hibernateDatabaseURL;
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

    public void setHibernatConnectionPassword(String hibernateConnectionPassword) {
        this.hibernateConnectionPassword = hibernateConnectionPassword;
    }

    public URL getHibernateConfigurationURL() {
        return hibernateConfigurationURL;
    }
    
    public void setHibernateConfigurationURL(URL hibernateConfigurationURL) {
        this.hibernateConfigurationURL = hibernateConfigurationURL;
    }

}
