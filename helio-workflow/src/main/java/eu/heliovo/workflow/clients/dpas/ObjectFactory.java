
package eu.heliovo.workflow.clients.dpas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.heliovo.workflow.clients.dpas package. 
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

    private final static QName _TimeQuery_QNAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "TimeQuery");
    private final static QName _QueryResponse_QNAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "queryResponse");
    private final static QName _Query_QNAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "Query");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.heliovo.workflow.clients.dpas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link TimeQuery }
     * 
     */
    public TimeQuery createTimeQuery() {
        return new TimeQuery();
    }

    /**
     * Create an instance of {@link Query }
     * 
     */
    public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://helio-vo.eu/xml/QueryService/v0.1", name = "TimeQuery")
    public JAXBElement<TimeQuery> createTimeQuery(TimeQuery value) {
        return new JAXBElement<TimeQuery>(_TimeQuery_QNAME, TimeQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://helio-vo.eu/xml/QueryService/v0.1", name = "queryResponse")
    public JAXBElement<QueryResponse> createQueryResponse(QueryResponse value) {
        return new JAXBElement<QueryResponse>(_QueryResponse_QNAME, QueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Query }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://helio-vo.eu/xml/QueryService/v0.1", name = "Query")
    public JAXBElement<Query> createQuery(Query value) {
        return new JAXBElement<Query>(_Query_QNAME, Query.class, null, value);
    }

}
