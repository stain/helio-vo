/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.pis.types.Message;

/**
 * Serializer for <code>FormalMessage</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;FormalMessage&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:choice&gt;
 *       &lt;xsd:element name=&quot;request&quot; type=&quot;tns:Request&quot;/&gt;
 *       &lt;xsd:element name=&quot;request&quot; type=&quot;tns:Response&quot;/&gt;
 *     &lt;/xsd:choice&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.FormalMessage
 */
public class FormalMessageSerializer {

    // Constants
    
    private static final String REQUEST_LOCALNAME = "request";

    private static final String RESPONSE_LOCALNAME = "response";

    // Instance variables
    
    private RequestSerializer requestSerializer = null;

    private ResponseSerializer responseSerializer = null;

    private SOAPFactory soapFactory = null;

    // Constructors
    
    public FormalMessageSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
        requestSerializer = new RequestSerializer(soapFactory);
        responseSerializer = new ResponseSerializer(soapFactory);
    }

    // Public interface
    
    public void serializeFormalMessage(FormalMessage formalMessage, Message message) throws SerializationException {
        try {
            if (formalMessage instanceof Request) {
                SOAPElement requestElement = message.getBody().addChildElement(soapFactory.createName(REQUEST_LOCALNAME));
                requestSerializer.serializeRequest((Request) formalMessage, message, requestElement);
            } else if (formalMessage instanceof Response) {
                SOAPElement responseElement = message.getBody().addChildElement(soapFactory.createName(RESPONSE_LOCALNAME));
                responseSerializer.serializeResponse((Response) formalMessage, message, responseElement);
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize formal message", e);
        }
    }

    @SuppressWarnings("unchecked")
    public FormalMessage deserializeFormalMessage(Message message) throws SerializationException {
        FormalMessage formalMessage = null;
        Iterator<SOAPElement> choiceElements = message.getBody().getChildElements();
        SOAPElement choiceElement = choiceElements.next();
        if (choiceElement.getElementName().getLocalName().equals(REQUEST_LOCALNAME)) {
            formalMessage = requestSerializer.deserializeRequest(message, choiceElement);
        } else if (choiceElement.getElementName().getLocalName().equals(RESPONSE_LOCALNAME)) {
            formalMessage = responseSerializer.deserializeResponse(message, choiceElement);
        }
        return formalMessage;
    }

}