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
 * Serializer for <code>ResponseSubtype</code> objects.
 * 
 * <pre>
 *  &lt;xsd:complexType name=&quot;ResponseSubtype&quot;&gt;
 *    &lt;xsd:sequence&gt;
 *      &lt;xsd:choice&gt;
 *        &lt;xsd:element name=&quot;createSession&quot; type=&quot;xsd:anyType&quot;/&gt;
 *        &lt;xsd:element name=&quot;alive&quot; type=&quot;xsd:anyType&quot;/&gt;
 *        &lt;xsd:element name=&quot;validateSession&quot; type=&quot;xsd:boolean&quot;/&gt;
 *        &lt;xsd:element name=&quot;getTypes&quot; type=&quot;tns:GetTypesResponse&quot;/&gt;
 *        &lt;xsd:element name=&quot;getObject&quot; type=&quot;xsd:string&quot;/&gt;
 *        &lt;xsd:element name=&quot;invokeObject&quot; type=&quot;tns:InvokeObjectResponse&quot;/&gt;
 *      &lt;/xsd:choice&gt;
 *    &lt;/xsd:sequence&gt;
 *  &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.ResponseSubtype
 */
public class ResponseSubtypeSerializer {

    // Constants
    
    private static final String CREATE_SESSION_LOCALNAME = "createSession";

    private static final String ALIVE_LOCALNAME = "alive";

    private static final String VALIDATE_SESSION_LOCALNAME = "validateSession";

    private static final String GET_TYPES_LOCALNAME = "getTypes";

    private static final String GET_OBJECT_LOCALNAME = "getObject";

    private static final String INVOKE_OBJECT_LOCALNAME = "invokeObject";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    private GetTypesResponseSerializer getTypesResponseSerializer = null;

    private InvokeObjectResponseSerializer invokeObjectResponseSerializer = null;

    // Constructors
    
    public ResponseSubtypeSerializer(SOAPFactory soapFactory, FilespaceManager filespaceManager) {
        this.soapFactory = soapFactory;
        invokeObjectResponseSerializer = new InvokeObjectResponseSerializer(soapFactory, filespaceManager);
        getTypesResponseSerializer = new GetTypesResponseSerializer(soapFactory);
    }

    // Public interface
    
    public void serializeResponseSubtype(ResponseSubtype responseSubtype, Response response) throws SerializationException {
        try {
            if (responseSubtype instanceof CreateSessionResponse) {
                SOAPElement createSessionElement = response.getSubtype().addChildElement(soapFactory.createName(CREATE_SESSION_LOCALNAME));
                CreateSessionResponse createSessionResponse = (CreateSessionResponse) responseSubtype;
                createSessionElement.setValue(createSessionResponse.getSessionId());
            } else if (responseSubtype instanceof AliveResponse) {
                SOAPElement aliveElement = response.getSubtype().addChildElement(soapFactory.createName(ALIVE_LOCALNAME));
                AliveResponse aliveResponse = (AliveResponse) responseSubtype;
                aliveElement.setValue(Boolean.toString(aliveResponse.isAlive()));
            } else if (responseSubtype instanceof ValidateSessionResponse) {
                SOAPElement validateSessionElement = response.getSubtype().addChildElement(soapFactory.createName(VALIDATE_SESSION_LOCALNAME));
                ValidateSessionResponse validateSessionResponse = (ValidateSessionResponse) responseSubtype;
                validateSessionElement.setValue(Boolean.toString(validateSessionResponse.isValid()));
            } else if (responseSubtype instanceof GetTypesResponse) {
                SOAPElement getTypesElement = response.getSubtype().addChildElement(soapFactory.createName(GET_TYPES_LOCALNAME));
                getTypesResponseSerializer.serializeGetTypesResponse((GetTypesResponse) responseSubtype, response, getTypesElement);
            } else if (responseSubtype instanceof GetObjectResponse) {
                SOAPElement getObjectElement = response.getSubtype().addChildElement(soapFactory.createName(GET_OBJECT_LOCALNAME));
                GetObjectResponse getObjectResponse = (GetObjectResponse) responseSubtype;
                getObjectElement.setValue(getObjectResponse.getObjectId());
            } else if (responseSubtype instanceof InvokeObjectResponse) {
                SOAPElement invokeObjectElement = response.getSubtype().addChildElement(soapFactory.createName(INVOKE_OBJECT_LOCALNAME));
                invokeObjectResponseSerializer.serializeInvokeObjectResponse((InvokeObjectResponse) responseSubtype, response, invokeObjectElement);
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize response", e);
        }
    }

    public ResponseSubtype deserializeResponseSubtype(Response response) throws SerializationException {
        ResponseSubtype responseSubtype = null;
        Iterator choiceElements = response.getSubtype().getChildElements();
        SOAPElement choiceElement = (SOAPElement) choiceElements.next();
        if (choiceElement.getElementName().getLocalName().equals(CREATE_SESSION_LOCALNAME)) {
            responseSubtype = new CreateSessionResponse(choiceElement.getValue());
        } else if (choiceElement.getElementName().getLocalName().equals(ALIVE_LOCALNAME)) {
            responseSubtype = new AliveResponse(Boolean.valueOf(choiceElement.getValue()).booleanValue());
        } else if (choiceElement.getElementName().getLocalName().equals(VALIDATE_SESSION_LOCALNAME)) {
            responseSubtype = new ValidateSessionResponse(Boolean.valueOf(choiceElement.getValue()).booleanValue());
        } else if (choiceElement.getElementName().getLocalName().equals(GET_TYPES_LOCALNAME)) {
            responseSubtype = getTypesResponseSerializer.deserializeGetTypesResponse(response, choiceElement);
        } else if (choiceElement.getElementName().getLocalName().equals(GET_OBJECT_LOCALNAME)) {
            responseSubtype = new GetObjectResponse(choiceElement.getValue());
        } else if (choiceElement.getElementName().getLocalName().equals(INVOKE_OBJECT_LOCALNAME)) {
            responseSubtype = invokeObjectResponseSerializer.deserializeInvokeObjectResponse(response, choiceElement);
        }
        return responseSubtype;
    }

}