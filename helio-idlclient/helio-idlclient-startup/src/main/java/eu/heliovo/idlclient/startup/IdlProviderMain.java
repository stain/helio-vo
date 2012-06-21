package eu.heliovo.idlclient.startup;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Main class to startup the IDL servlet.
 * @author MarcoSoldati
 *
 */
public class IdlProviderMain {
    /**
     * The logger instance
     */
    private static final Logger _LOGGER = Logger.getLogger(IdlProviderMain.class);
    
    /**
     * Keep reference to the server.
     */
    private Server server;
    
    /**
     * The port of the server.  Defaults to 8085.
     */
    private int serverPort = 8085;

    /**
     * Name of the host on which the server is running. Defaults to 127.0.0.1.
     */
    private String hostName = "127.0.0.1";
    
    /**
     * The webapp root path.
     */
    private File webAppRootPath;
    
    /**
     * Directory for where to place temporary files. Defaults to helioTempDir/idl_provider.
     */
    private File tempDir = new HelioFileUtil("idl").getHelioTempDir("provider");

    /**
     * The webapp context path, Defaults to "/".
     */
    private String webAppContextPath = "/";
    
    /**
     * The port on which the server currently runs.
     */
    private int effectiveServerPort;
    
    /**
     * Name on which the server is running.
     */
    private String effectiveHostName;

    

    /**
     * Create the IdlProviderMain
     * @param args the arguments as documented in usage
     * @throws Exception in case of any error.
     */
    public IdlProviderMain(String args[]) throws Exception {
        init(args);
    }
    
    
    private void init(String[] args) throws Exception {
        // TODO: read params and assign to properties

        server = new Server();
        //Server server = new Server();
        
        SocketConnector connector = new SocketConnector();
        //Connector connector = new SelectChannelConnector();
     
        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        
        // set the host and port
        connector.setPort(this.serverPort);
        connector.setHost(this.hostName);

        // add connector
        server.addConnector(connector);
     
        // add context
        WebAppContext wac = new WebAppContext();
        wac.setContextPath (this.webAppContextPath);
        
        ProtectionDomain protectionDomain = IdlProviderMain.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        wac.setWar(location.toExternalForm());
     
        wac.setTempDirectory(this.tempDir);
        
        server.setHandler(wac);
        server.setStopAtShutdown(true);
        
        // setup and configure the web app context.
        
        if (_LOGGER.isInfoEnabled()) {
            _LOGGER.info(String.format("Starting up webserver %1$s:%2$s%3$s", this.hostName, this.serverPort, this.webAppContextPath));
        }   
        try {
            server.start();
            while (server.isStarting()) {
                Thread.sleep(50);
            }            
            this.effectiveServerPort = connector.getLocalPort();
            this.effectiveHostName = connector.getHost();
            _LOGGER.info("Server started on " + this.effectiveHostName + ":" + this.effectiveServerPort + ". Press enter to stop server") ;
            System.in.read();
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    /**
     * Start up the IDL servlet
     * @param args the arguments to use.
     * @throws Exception in case of any error.
     */
    public static void main(String[] args) throws Exception {
        new IdlProviderMain(args);
    }
    
    /**
     * Set the server port. If 0 the implementation will use a default port.
     * @param port the server port.
     */
    public void setServerPort(int port) {
        this.serverPort = port;        
    }
    
    /**
     * The server port as set by the user.
     * This may differ from the port returned by  {@link #getEffectiveServerPort()} .
     * @return the server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Set the name of the host. Set to 127.0.0.1 to disallow non-local hosts.
     * @param hostName the servers' host name.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    /**
     * The host name of the server as set by the user.
     * @return the host name of the server.
     */
    public String getHostName() {
        return hostName;
    }
    
    /**
     * The server port on which the current server is running.
     * @return the server port on which the server currently runs.
     */
    public int getEffectiveServerPort() {
        return effectiveServerPort;
    }
    
    /**
     * The effective host name of the server.
     * This may differ from the host name returned by {@link #getHostName()}
     * @return the host name.
     */
    public String getEffectiveHostName() {
        return effectiveHostName;
    }
    
    /**
     * Get the root path of the web app.
     * @return the webAppRootPath the root path of the webapp.
     */
    public File getWebAppRootPath() {
        return webAppRootPath;
    }

    /**
     * Get the context path of the webapp.
     * @return the webAppContextPath
     */
    public String getWebAppContextPath() {
        return webAppContextPath;
    }

    /**
     * Set the context path of the webapp.
     * @param webAppContextPath the webAppContextPath to set
     */
    public void setWebAppContextPath(String webAppContextPath) {
        this.webAppContextPath = webAppContextPath;
    }
    

    /**
     * The directory where to extract the web app. 
     * @return the web app temp dir.
     */
    public File getTempDir() {
        return tempDir;
    }

    /**
     * The directory where to extract the web app. 
     * @param tempDir the tempDir to set
     */
    public void setTempDir(File tempDir) {
        this.tempDir = tempDir;
    }
}
