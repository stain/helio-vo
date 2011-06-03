package eu.heliovo.registryclient.impl;

import java.net.URL;

import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.shared.util.AssertUtil;

/**
 * Default implementation of the access interface
 * @author MarcoSoldati
 *
 */
public class AccessInterfaceImpl implements AccessInterface {

    /**
     * the interface type
     */
    private final AccessInterfaceType type;
    
    /**
     * the endpoint of the interface
     */
    private final URL url;

    /**
     * The capability provided by this interface
     */
    private final ServiceCapability serviceCapability;

    /**
     * The type of the interface
     * @param type the interface type
     * @param url the URL of the endpoint
     */
    public AccessInterfaceImpl(AccessInterfaceType type, ServiceCapability serviceCapability, URL url) {
        AssertUtil.assertArgumentNotNull(type, "type");
        AssertUtil.assertArgumentNotNull(serviceCapability, "serviceCapability");
        AssertUtil.assertArgumentNotNull(url, "url");
        this.type = type;
        this.serviceCapability = serviceCapability;
        this.url = url;
    }
    
    @Override
    public URL getUrl() {
        return url;
    }
    
    @Override
    public AccessInterfaceType getInterfaceType() {
        return type;
    }
    
    @Override
    public ServiceCapability getCapability() {
        return serviceCapability;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessInterfaceImpl [");
        sb.append("capability:").append(serviceCapability.getName());
        sb.append(", type:").append(type);
        sb.append(", url:").append(url);
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccessInterface) {
            AccessInterface other = (AccessInterface) obj;
            return (other.getInterfaceType().equals(this.getInterfaceType()) 
                &&  other.getCapability().equals(this.getCapability())
                &&  other.getUrl().equals(this.getUrl()));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.type.hashCode() * 13 + this.url.hashCode() * 37 + this.serviceCapability.hashCode() * 31;
    }
}
