package org.egso.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.InteractionMediator;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.admin.SystemInformation;
import org.egso.provider.communication.ProviderSessionFactoryImpl;
import org.egso.provider.query.QueryEngine;
import org.egso.provider.utils.ProviderUtils;

/**
 * Main class of the provider system.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 2.0 - 21/10/2004 [01/10/2003]
 */
public class Provider {

    /**
     * true if the provider was initialized
     */
    private boolean isInitialized = false;

    /**
     * Singelton Instance of this Object
     */
    private static Provider provider = null;

    /**
     * JAVADOC: Description of the Field
     */
    private InteractionMediator mediator = null;

    /**
     * JAVADOC: Description of the Field
     */
    private QueryEngine queryEngine = null;

    private Logger logger = null;

    private String providerIP = "127.0.0.1";

    /**
     * Use getInstance() to get the ProviderContainerManager. <br>
     * The constructor takes a user parameter object
     * 
     * @param parameters User parameters for the provider
     * @exception Exception if an error occurs
     */
    private Provider() throws Exception {
        logger = Logger.getLogger(this.getClass().getName());
        logger.info("PROVIDER Initialization. Started at " + ProviderUtils.getDate());
        initialize();
    }

    public static Provider getInstance() throws Exception {
        if (provider == null) {
            provider = new Provider();
        }
        return (provider);
    }

    /**
     * Initializes the provider system.
     * 
     * @exception Exception Thrown if anything goes wrong during
     * initialisation
     */
    public void initialize() throws Exception {
        if (isInitialized) {
            throw new RuntimeException("The provider component is already initialized");
        }
        startMonitoring();
        queryEngine = new QueryEngine();
        mediator = new InteractionMediator();
        try {
            mediator.init();
        } catch (AdapterException ae) {
            ae.printStackTrace();
        }
        mediator.setSessionFactory(new ProviderSessionFactoryImpl(queryEngine));
        // mark the component as initalized
        isInitialized = true;
    }

    private void startMonitoring() {
        ProviderMonitor providerMonitor = ProviderMonitor.getInstance();
        SystemInformation sysInfo = new SystemInformation();
        String startup = ProviderUtils.getDate();
        sysInfo.addStatistic("Provider startup date", startup);
        try {
            providerIP = InetAddress.getLocalHost().toString();
            sysInfo.addStatistic("IP address", providerIP);
        } catch (UnknownHostException uhe) {
            ProviderMonitor.getInstance().reportException(uhe);
            uhe.printStackTrace();
            sysInfo.addStatistic("IP address", "IP not found");
        }
        Properties properties = System.getProperties();
        sysInfo.addStatistic("Java name", properties.getProperty("java.runtime.name"));
        sysInfo.addStatistic("Java version", properties.getProperty("java.vm.version"));
        sysInfo.addStatistic("Operating System", properties.getProperty("os.name"));
        sysInfo.addStatistic("Operating System version", properties.getProperty("os.version"));
        providerMonitor.setSystemInformation(sysInfo);
    }

    public String getProviderIP() {
        return (providerIP);
    }

    public void dispose() {
        mediator.destroy();
    }
}

