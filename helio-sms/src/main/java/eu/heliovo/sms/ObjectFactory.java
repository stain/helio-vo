
package eu.heliovo.sms;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.heliovo.sms package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SmsFault_QNAME = new QName("http://sms.heliovo.eu/", "smsFault");
    private final static QName _GetRelatedResponse_QNAME = new QName("http://sms.heliovo.eu/", "getRelatedResponse");
    private final static QName _GetHECListNamesResponse_QNAME = new QName("http://sms.heliovo.eu/", "getHECListNamesResponse");
    private final static QName _GetRelated_QNAME = new QName("http://sms.heliovo.eu/", "getRelated");
    private final static QName _GetHECListNames_QNAME = new QName("http://sms.heliovo.eu/", "getHECListNames");
    private final static QName _GetEquivalents_QNAME = new QName("http://sms.heliovo.eu/", "getEquivalents");
    private final static QName _GetEquivalentsResponse_QNAME = new QName("http://sms.heliovo.eu/", "getEquivalentsResponse");
    private final static QName _GetKnownPhenomenaResponse_QNAME = new QName("http://sms.heliovo.eu/", "getKnownPhenomenaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.heliovo.sms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetHECListNamesResponse }
     * 
     */
    public GetHECListNamesResponse createGetHECListNamesResponse() {
        return new GetHECListNamesResponse();
    }

    /**
     * Create an instance of {@link GetEquivalentsResponse }
     * 
     */
    public GetEquivalentsResponse createGetEquivalentsResponse() {
        return new GetEquivalentsResponse();
    }

    /**
     * Create an instance of {@link GetEquivalents }
     * 
     */
    public GetEquivalents createGetEquivalents() {
        return new GetEquivalents();
    }

    /**
     * Create an instance of {@link GetHECListNames }
     * 
     */
    public GetHECListNames createGetHECListNames() {
        return new GetHECListNames();
    }

    /**
     * Create an instance of {@link GetRelated }
     * 
     */
    public GetRelated createGetRelated() {
        return new GetRelated();
    }

    /**
     * Create an instance of {@link GetKnownPhenomenaResponse }
     * 
     */
    public GetKnownPhenomenaResponse createGetKnownPhenomenaResponse() {
        return new GetKnownPhenomenaResponse();
    }

    /**
     * Create an instance of {@link GetKnownPhenomena }
     * 
     */
    public GetKnownPhenomena createGetKnownPhenomena() {
        return new GetKnownPhenomena();
    }

    /**
     * Create an instance of {@link GetRelatedResponse }
     * 
     */
    public GetRelatedResponse createGetRelatedResponse() {
        return new GetRelatedResponse();
    }

    /**
     * Create an instance of {@link SmsFault }
     * 
     */
    public SmsFault createSmsFault() {
        return new SmsFault();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SmsFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "smsFault")
    public JAXBElement<SmsFault> createSmsFault(SmsFault value) {
        return new JAXBElement<SmsFault>(_SmsFault_QNAME, SmsFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRelatedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getRelatedResponse")
    public JAXBElement<GetRelatedResponse> createGetRelatedResponse(GetRelatedResponse value) {
        return new JAXBElement<GetRelatedResponse>(_GetRelatedResponse_QNAME, GetRelatedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHECListNamesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getHECListNamesResponse")
    public JAXBElement<GetHECListNamesResponse> createGetHECListNamesResponse(GetHECListNamesResponse value) {
        return new JAXBElement<GetHECListNamesResponse>(_GetHECListNamesResponse_QNAME, GetHECListNamesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRelated }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getRelated")
    public JAXBElement<GetRelated> createGetRelated(GetRelated value) {
        return new JAXBElement<GetRelated>(_GetRelated_QNAME, GetRelated.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHECListNames }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getHECListNames")
    public JAXBElement<GetHECListNames> createGetHECListNames(GetHECListNames value) {
        return new JAXBElement<GetHECListNames>(_GetHECListNames_QNAME, GetHECListNames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEquivalents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getEquivalents")
    public JAXBElement<GetEquivalents> createGetEquivalents(GetEquivalents value) {
        return new JAXBElement<GetEquivalents>(_GetEquivalents_QNAME, GetEquivalents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEquivalentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getEquivalentsResponse")
    public JAXBElement<GetEquivalentsResponse> createGetEquivalentsResponse(GetEquivalentsResponse value) {
        return new JAXBElement<GetEquivalentsResponse>(_GetEquivalentsResponse_QNAME, GetEquivalentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetKnownPhenomenaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sms.heliovo.eu/", name = "getKnownPhenomenaResponse")
    public JAXBElement<GetKnownPhenomenaResponse> createGetKnownPhenomenaResponse(GetKnownPhenomenaResponse value) {
        return new JAXBElement<GetKnownPhenomenaResponse>(_GetKnownPhenomenaResponse_QNAME, GetKnownPhenomenaResponse.class, null, value);
    }

}
