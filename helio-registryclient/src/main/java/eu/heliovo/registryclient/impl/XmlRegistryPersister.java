package eu.heliovo.registryclient.impl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import uk.ac.starlink.registry.BasicResource;
import eu.heliovo.shared.props.HelioFileUtil;

public class XmlRegistryPersister implements RegistryPersister {
    
    /**
     * The file util is required to access the helio temp dir.
     */
    private HelioFileUtil helioFileUtil;
    
    @Override
    public void persistRegistry(List<BasicResource> allServices) throws IOException {
        File file = getRegistryFile();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        XMLEncoder xmlEncoder = new XMLEncoder(out);
        xmlEncoder.writeObject(allServices);
        xmlEncoder.close();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<BasicResource> loadRegistry() throws IOException {
        File file = getRegistryFile();
        InputStream in = new FileInputStream(file );
        XMLDecoder xmlDecoder = new XMLDecoder(in);
        return (List<BasicResource>)xmlDecoder.readObject();
    }

    /**
     * Get the local registry file
     * @return the registry file
     */
    private File getRegistryFile() {
        return new File(helioFileUtil.getHelioTempDir("persisted_registry"), "registry.xml");
    }


    /**
     * @return the helioFileUtil
     */
    public HelioFileUtil getHelioFileUtil() {
        return helioFileUtil;
    }


    /**
     * @param helioFileUtil the helioFileUtil to set
     */
    public void setHelioFileUtil(HelioFileUtil helioFileUtil) {
        this.helioFileUtil = helioFileUtil;
    }
    
    
}
