
package ch.i4ds.helio.core;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.i4ds.helio.core package. 
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

    private final static QName _UnknownHostException_QNAME = new QName("http://core.helio.i4ds.ch/", "UnknownHostException");
    private final static QName _Exception_QNAME = new QName("http://core.helio.i4ds.ch/", "Exception");
    private final static QName _RunInitialWorkflowResponse_QNAME = new QName("http://core.helio.i4ds.ch/", "run_initial_workflowResponse");
    private final static QName _GetHostNameResponse_QNAME = new QName("http://core.helio.i4ds.ch/", "get_host_nameResponse");
    private final static QName _GetInstrumentsV1Response_QNAME = new QName("http://core.helio.i4ds.ch/", "get_instruments_v1Response");
    private final static QName _GetVersion_QNAME = new QName("http://core.helio.i4ds.ch/", "get_version");
    private final static QName _GetHostName_QNAME = new QName("http://core.helio.i4ds.ch/", "get_host_name");
    private final static QName _GetInstrumentsV1_QNAME = new QName("http://core.helio.i4ds.ch/", "get_instruments_v1");
    private final static QName _QueryV1_QNAME = new QName("http://core.helio.i4ds.ch/", "query_v1");
    private final static QName _RunInitialWorkflow_QNAME = new QName("http://core.helio.i4ds.ch/", "run_initial_workflow");
    private final static QName _QueryV1Response_QNAME = new QName("http://core.helio.i4ds.ch/", "query_v1Response");
    private final static QName _GetVersionResponse_QNAME = new QName("http://core.helio.i4ds.ch/", "get_versionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.i4ds.helio.core
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RunInitialWorkflowResponse }
     * 
     */
    public RunInitialWorkflowResponse createRunInitialWorkflowResponse() {
        return new RunInitialWorkflowResponse();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link ResultItem }
     * 
     */
    public ResultItem createResultItem() {
        return new ResultItem();
    }

    /**
     * Create an instance of {@link GetInstrumentsV1Response }
     * 
     */
    public GetInstrumentsV1Response createGetInstrumentsV1Response() {
        return new GetInstrumentsV1Response();
    }

    /**
     * Create an instance of {@link GetHostNameResponse }
     * 
     */
    public GetHostNameResponse createGetHostNameResponse() {
        return new GetHostNameResponse();
    }

    /**
     * Create an instance of {@link QueryV1 }
     * 
     */
    public QueryV1 createQueryV1() {
        return new QueryV1();
    }

    /**
     * Create an instance of {@link GetInstrumentsV1 }
     * 
     */
    public GetInstrumentsV1 createGetInstrumentsV1() {
        return new GetInstrumentsV1();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link QueryV1Response }
     * 
     */
    public QueryV1Response createQueryV1Response() {
        return new QueryV1Response();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link UnknownHostException }
     * 
     */
    public UnknownHostException createUnknownHostException() {
        return new UnknownHostException();
    }

    /**
     * Create an instance of {@link GetHostName }
     * 
     */
    public GetHostName createGetHostName() {
        return new GetHostName();
    }

    /**
     * Create an instance of {@link RunInitialWorkflow }
     * 
     */
    public RunInitialWorkflow createRunInitialWorkflow() {
        return new RunInitialWorkflow();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnknownHostException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "UnknownHostException")
    public JAXBElement<UnknownHostException> createUnknownHostException(UnknownHostException value) {
        return new JAXBElement<UnknownHostException>(_UnknownHostException_QNAME, UnknownHostException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunInitialWorkflowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "run_initial_workflowResponse")
    public JAXBElement<RunInitialWorkflowResponse> createRunInitialWorkflowResponse(RunInitialWorkflowResponse value) {
        return new JAXBElement<RunInitialWorkflowResponse>(_RunInitialWorkflowResponse_QNAME, RunInitialWorkflowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHostNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_host_nameResponse")
    public JAXBElement<GetHostNameResponse> createGetHostNameResponse(GetHostNameResponse value) {
        return new JAXBElement<GetHostNameResponse>(_GetHostNameResponse_QNAME, GetHostNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInstrumentsV1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_instruments_v1Response")
    public JAXBElement<GetInstrumentsV1Response> createGetInstrumentsV1Response(GetInstrumentsV1Response value) {
        return new JAXBElement<GetInstrumentsV1Response>(_GetInstrumentsV1Response_QNAME, GetInstrumentsV1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_version")
    public JAXBElement<GetVersion> createGetVersion(GetVersion value) {
        return new JAXBElement<GetVersion>(_GetVersion_QNAME, GetVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHostName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_host_name")
    public JAXBElement<GetHostName> createGetHostName(GetHostName value) {
        return new JAXBElement<GetHostName>(_GetHostName_QNAME, GetHostName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInstrumentsV1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_instruments_v1")
    public JAXBElement<GetInstrumentsV1> createGetInstrumentsV1(GetInstrumentsV1 value) {
        return new JAXBElement<GetInstrumentsV1>(_GetInstrumentsV1_QNAME, GetInstrumentsV1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryV1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "query_v1")
    public JAXBElement<QueryV1> createQueryV1(QueryV1 value) {
        return new JAXBElement<QueryV1>(_QueryV1_QNAME, QueryV1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunInitialWorkflow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "run_initial_workflow")
    public JAXBElement<RunInitialWorkflow> createRunInitialWorkflow(RunInitialWorkflow value) {
        return new JAXBElement<RunInitialWorkflow>(_RunInitialWorkflow_QNAME, RunInitialWorkflow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryV1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "query_v1Response")
    public JAXBElement<QueryV1Response> createQueryV1Response(QueryV1Response value) {
        return new JAXBElement<QueryV1Response>(_QueryV1Response_QNAME, QueryV1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://core.helio.i4ds.ch/", name = "get_versionResponse")
    public JAXBElement<GetVersionResponse> createGetVersionResponse(GetVersionResponse value) {
        return new JAXBElement<GetVersionResponse>(_GetVersionResponse_QNAME, GetVersionResponse.class, null, value);
    }

}
