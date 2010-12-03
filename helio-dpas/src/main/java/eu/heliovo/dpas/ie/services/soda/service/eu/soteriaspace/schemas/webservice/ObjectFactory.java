
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.soteriaspace.schemas.webservice package. 
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

    private final static QName _QueryResponse_QNAME = new QName("http://soteriaspace.eu/schemas/webservice", "queryResponse");
    private final static QName _QueryRequest_QNAME = new QName("http://soteriaspace.eu/schemas/webservice", "queryRequest");
    private final static QName _Error_QNAME = new QName("http://soteriaspace.eu/schemas/webservice", "error");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.soteriaspace.schemas.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryRequest }
     * 
     */
    public QueryRequest createQueryRequest() {
        return new QueryRequest();
    }

    /**
     * Create an instance of {@link GetRecordDetailsRequest }
     * 
     */
    public GetRecordDetailsRequest createGetRecordDetailsRequest() {
        return new GetRecordDetailsRequest();
    }

    /**
     * Create an instance of {@link QueryError }
     * 
     */
    public QueryError createQueryError() {
        return new QueryError();
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/webservice", name = "queryResponse")
    public JAXBElement<QueryResponse> createQueryResponse(QueryResponse value) {
        return new JAXBElement<QueryResponse>(_QueryResponse_QNAME, QueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/webservice", name = "queryRequest")
    public JAXBElement<QueryRequest> createQueryRequest(QueryRequest value) {
        return new JAXBElement<QueryRequest>(_QueryRequest_QNAME, QueryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/webservice", name = "error")
    public JAXBElement<QueryError> createError(QueryError value) {
        return new JAXBElement<QueryError>(_Error_QNAME, QueryError.class, null, value);
    }

}
