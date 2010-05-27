
package eu.heliovo.workflow.clients.dpas;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "HelioQueryServiceServiceTwo", targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1", wsdlLocation = "http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioTavernaService?wsdl")
public class HelioQueryServiceServiceTwo
    extends Service
{

    private final static URL HELIOQUERYSERVICESERVICETWO_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(eu.heliovo.workflow.clients.dpas.HelioQueryServiceServiceTwo.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = eu.heliovo.workflow.clients.dpas.HelioQueryServiceServiceTwo.class.getResource(".");
            url = new URL(baseUrl, "http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioTavernaService?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioTavernaService?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        HELIOQUERYSERVICESERVICETWO_WSDL_LOCATION = url;
    }

    public HelioQueryServiceServiceTwo(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelioQueryServiceServiceTwo() {
        super(HELIOQUERYSERVICESERVICETWO_WSDL_LOCATION, new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServiceServiceTwo"));
    }

    /**
     * 
     * @return
     *     returns HelioQueryService
     */
    @WebEndpoint(name = "HelioQueryServicePortTwo")
    public HelioQueryService getHelioQueryServicePortTwo() {
        return super.getPort(new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServicePortTwo"), HelioQueryService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelioQueryService
     */
    @WebEndpoint(name = "HelioQueryServicePortTwo")
    public HelioQueryService getHelioQueryServicePortTwo(WebServiceFeature... features) {
        return super.getPort(new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServicePortTwo"), HelioQueryService.class, features);
    }

}