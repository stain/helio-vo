
/*
 * 
 */

package eu.heliovo.dpas.ie.services.uoc.service.eu.helio_vo.xml.queryservice.v0_1;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 15 11:23:43 BST 2010
 * Generated source version: 2.2.10
 * 
 */


@WebServiceClient(name = "HelioQueryServiceService", 
                  wsdlLocation = "file:HelioService.wsdl",
                  targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1") 
public class HelioQueryServiceService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServiceService");
    public final static QName HelioQueryServicePort = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:HelioService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:HelioService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public HelioQueryServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelioQueryServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelioQueryServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns HelioQueryService
     */
    @WebEndpoint(name = "HelioQueryServicePort")
    public HelioQueryService getHelioQueryServicePort() {
        return super.getPort(HelioQueryServicePort, HelioQueryService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelioQueryService
     */
    @WebEndpoint(name = "HelioQueryServicePort")
    public HelioQueryService getHelioQueryServicePort(WebServiceFeature... features) {
        return super.getPort(HelioQueryServicePort, HelioQueryService.class, features);
    }

}