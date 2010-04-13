/*
 * Created on Jul 6, 2004
 */
package org.egso.comms.eis.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import javax.activation.DataHandler;

import org.apache.commons.id.uuid.VersionFourGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;

/**
 * Class for managing an HTTP accessible filespace.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class FilespaceManager {

    // Logging

    private Logger logger = LogManager.getLogger(FilespaceManager.class);

    // Constants

    public static final String FILE_PREFIX = "File-";

    public static final String PIPE_PREFIX = "Pipe-";

    // Instance variables

    private File filespaceBaseDirectory = null;

    private URI filespaceBaseURI = null;
    
    private VersionFourGenerator uuidGenerator = new VersionFourGenerator();

    private boolean configured = false;

    private boolean init = false;

    // Configurable implemenation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, filespace base directory: " + configuration.getFilespaceBaseDirectory() + ", filespace base URI: " + configuration.getFilespaceBaseURI());
        filespaceBaseDirectory = configuration.getFilespaceBaseDirectory();
        filespaceBaseURI = configuration.getFilespaceBaseURI();
        configured = true;
    }

    // Lifecycle implementation

    public synchronized void init() throws StorageException {
        if (!init) {
            try {
                logger.info("Initializing");
                if (!configured) {
                    configure(ConfigurationFactory.createConfiguration());
                }
                filespaceBaseDirectory.mkdirs();
                init = true;
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new StorageException("Failed to initialize", e);
            }
        }
    }

    public synchronized void destroy() {
        init = false;
        logger.info("Destroyed");
    }

    public boolean isInit() {
        return init;
    }

    public URI storeData(DataHandler dataHandler, boolean pipe) throws StorageException {
        try {
            String fileName = null;
            if (pipe) {
                fileName = PIPE_PREFIX + uuidGenerator.nextIdentifier().toString();;
            } else {
                fileName = FILE_PREFIX + uuidGenerator.nextIdentifier().toString();;
            }
            File file = new File(filespaceBaseDirectory, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            dataHandler.writeTo(fileOutputStream);
            logger.info("Storing data, " + fileName + " bytes: " + file.length());
            return filespaceBaseURI.resolve(fileName);
        } catch (IOException e) {
            throw new StorageException("Failed to store data", e);
        }
    }

}