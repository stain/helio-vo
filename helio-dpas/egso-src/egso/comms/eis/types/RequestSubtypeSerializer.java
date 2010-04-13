/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.eis.storage.FilespaceManager;

/**
 * Serializer for <code>RequestSubtype</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;RequestSubtype&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:choice&gt;
 *       &lt;xsd:element name=&quot;createSession&quot; type=&quot;xsd:anyType&quot;/&gt;
 *       &lt;xsd:element name=&quot;alive&quot; type=&quot;xsd:anyType&quot;/&gt;
 *       &lt;xsd:element name=&quot;validateSession&quot; type=&quot;xsd:string&quot;/&gt;
 *       &lt;xsd:element name=&quot;closeSession&quot; type=&quot;xsd:anyType&quot;/&gt;
 *       &lt;xsd:element name=&quot;getTypes&quot; type=&quot;tns:GetTypesRequest&quot;/&gt;
 *       &lt;xsd:element name=&quot;getObject&quot; type=&quot;tns:GetObjectRequest&quot;/&gt;
 *       &lt;xsd:element name=&quot;invokeObject&quot; type=&quot;tns:InvokeObjectRequest&quot;/&gt;
 *       &lt;xsd:element name=&quot;releaseObject&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;/xsd:choice&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.RequestSubtype
 */
public class RequestSubtypeSerializer {

    // Constants
    
    private static final String CREATE_SESSION_LOCALNAME = "createSession";

    private static final String ALIVE_LOCALNAME = "alive";

    private static final String VALIDATE_SESSION_LOCALNAME = "validateSession";

    private static final String CLOSE_SESSION_LOCALNAME = "closeSession";

    private static final String GET_TYPES_LOCALNAME = "getTypes";

    private static final String GET_OBJECT_LOCALNAME = "getObject";

    private static final String INVOKE_OBJECT_LOCALNAME = "invokeObject";

    private static final String RELEASE_OBJECT_LOCALNAME = "releaseObject";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    private GetTypesRequestSerializer getTypesRequestSerializer = null;

    private GetObjectRequestSerializer getObjectRequestSerializer = null;

    private InvokeObjectRequestSerializer invokeObjectRequestSerializer = null;

    // Constructors
    
    public RequestSubtypeSerializer(SOAPFactory soapFactory, FilespaceManager filespaceManager) {
        this.soapFactory = soapFactory;
        getObjectRequestSerializer = new GetObjectRequestSerializer(soapFactory);
        getTypesRequestSerializer = new GetTypesRequestSerializer(soapFactory);
        invokeObjectRequestSerializer = new InvokeObjectRequestSerializer(soapFactory, filespaceManager);
    }

    // Public interface
    
    public void serializeRequestSubtype(RequestSubtype requestSubtype, Request request) throws SerializationException {
        try {
            if (requestSubtype instanceof CreateSessionRequest) {
                SOAPElement createSessionElement = request.getSubtype().addChildElement(soapFactory.createName(CREATE_SESSION_LOCALNAME));
                CreateSessionRequest createSessionRequest = (CreateSessionRequest) requestSubtype;
                request.setClientId(createSessionRequest.getClientId());
            } else if (requestSubtype instanceof AliveRequest) {
                SOAPElement aliveElement = request.getSubtype().addChildElement(soapFactory.createName(ALIVE_LOCALNAME));
                AliveRequest aliveRequest = (AliveRequest) requestSubtype;
                request.setClientId(aliveRequest.getClientId());
            } else if (requestSubtype instanceof ValidateSessionRequest) {
                SOAPElement validateSessionElement = request.getSubtype().addChildElement(soapFactory.createName(VALIDATE_SESSION_LOCALNAME));
            } else if (requestSubtype instanceof CloseSessionRequest) {
                SOAPElement closeSessionElement = request.getSubtype().addChildElement(soapFactory.createName(CLOSE_SESSION_LOCALNAME));
            } else if (requestSubtype instanceof GetTypesRequest) {
                SOAPElement getTypesElement = request.getSubtype().addChildElement(soapFactory.createName(GET_TYPES_LOCALNAME));
                getTypesRequestSerializer.serializeGetTypesRequest((GetTypesRequest) requestSubtype, request, getTypesElement);
            } else if (requestSubtype instanceof GetObjectRequest) {
                SOAPElement getObjectElement = request.getSubtype().addChildElement(soapFactory.createName(GET_OBJECT_LOCALNAME));
                getObjectRequestSerializer.serializeGetObjectRequest((GetObjectRequest) requestSubtype, request, getObjectElement);
            } else if (requestSubtype instanceof InvokeObjectRequest) {
                SOAPElement invokeObjectElement = request.getSubtype().addChildElement(soapFactory.createName(INVOKE_OBJECT_LOCALNAME));
                invokeObjectRequestSerializer.serializeInvokeObjectRequest((InvokeObjectRequest) requestSubtype, invokeObjectElement);
            } else if (requestSubtype instanceof ReleaseObjectRequest) {
                SOAPElement releaseObjectElement = request.getSubtype().addChildElement(soapFactory.createName(RELEASE_OBJECT_LOCALNAME));
                ReleaseObjectRequest releaseObjectRequest = (ReleaseObjectRequest) requestSubtype;
                releaseObjectElement.setValue(releaseObjectRequest.getObjectId());
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize request subtype", e);
        }
    }

    public RequestSubtype deserializeRequestSubtype(Request request) throws SerializationException {
        RequestSubtype requestSubtype = null;
        Iterator choiceElements = request.getSubtype().getChildElements();
        SOAPElement choiceElement = (SOAPElement) choiceElements.next();
        if (choiceElement.getElementName().getLocalName().equals(CREATE_SESSION_LOCALNAME)) {
            requestSubtype = new CreateSessionRequest(request.getClientId());
        } else if (choiceElement.getElementName().getLocalName().equals(ALIVE_LOCALNAME)) {
            requestSubtype = new AliveRequest(request.getClientId());
        } else if (choiceElement.getElementName().getLocalName().equals(VALIDATE_SESSION_LOCALNAME)) {
            requestSubtype = new ValidateSessionRequest();
        } else if (choiceElement.getElementName().getLocalName().equals(CLOSE_SESSION_LOCALNAME)) {
            requestSubtype = new CloseSessionRequest();
        } else if (choiceElement.getElementName().getLocalName().equals(GET_TYPES_LOCALNAME)) {
            requestSubtype = getTypesRequestSerializer.deserializeGetTypesRequest(request, choiceElement);
        } else if (choiceElement.getElementName().getLocalName().equals(GET_OBJECT_LOCALNAME)) {
            requestSubtype = getObjectRequestSerializer.deserializeGetObjectRequest(request, choiceElement);
        } else if (choiceElement.getElementName().getLocalName().equals(INVOKE_OBJECT_LOCALNAME)) {
            requestSubtype = invokeObjectRequestSerializer.deserializeInvokeObjectRequest(choiceElement);
        } else if (choiceElement.getElementName().getLocalName().equals(RELEASE_OBJECT_LOCALNAME)) {
            requestSubtype = new ReleaseObjectRequest(choiceElement.getValue());
        }
        return requestSubtype;
    }

}