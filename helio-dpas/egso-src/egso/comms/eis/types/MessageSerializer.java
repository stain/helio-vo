/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import org.egso.comms.pis.types.Message;

/**
 * Serializer for <code>Message</code> objects.
 * 
 * <pre>
 * &lt;xsd:element name=&quot;receiveMessage&quot; type=&quot;tns:Message&quot;/&gt;
 *   
 * &lt;xsd:element name=&quot;returnMessage&quot; type=&quot;tns:Message&quot;/&gt;
 *          
 * &lt;xsd:complexType name=&quot;Message&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;header&quot; type=&quot;tns:Header&quot;/&gt;
 *     &lt;xsd:element name=&quot;body&quot; type=&quot;xsd:anyType&quot; minOccurs=&quot;0&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.pis.types.Message
 */
public class MessageSerializer {

    // Constants
    
    public static final String PIS_NAMESPACE_URI = "urn:org.egso.comms/pis/types/";

    public static final String RECEIVE_MESSAGE_LOCALNAME = "receiveMessage";

    public static final String RETURN_MESSAGE_LOCALNAME = "returnMessage";

    public static final String BODY_LOCALNAME = "body";
    
    public static final String HEADER_LOCALNAME = "header";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    private HeaderSerializer headerSerializer = null;

    // Constuctors
    
    public MessageSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
        headerSerializer = new HeaderSerializer(soapFactory);
    }

    // Public interface
    
    public Message deserializeMessage(SOAPMessage soapMessage) throws SerializationException {
        try {
            return deserializeMessage((SOAPElement)soapMessage.getSOAPBody().getChildElements().next());
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize returned message", e);
        }
    }

    public Message deserializeMessage(SOAPElement messageElement) throws SerializationException {
        try {
            Message message = null;
            if(messageElement.getElementName().getLocalName().equals(RECEIVE_MESSAGE_LOCALNAME)) {
                message = new ReceiveMessage();
            } else if(messageElement.getElementName().getLocalName().equals(RETURN_MESSAGE_LOCALNAME)) {
                message = new ReturnMessage();
            } else {
                message = new Message();
            }
            Iterator headerElements = messageElement.getChildElements(soapFactory.createName(HEADER_LOCALNAME));
            SOAPElement headerElement = (SOAPElement) headerElements.next();
            message.setHeader(headerSerializer.deserializeHeader(headerElement));
            Iterator bodyElements = messageElement.getChildElements(soapFactory.createName(BODY_LOCALNAME));
            if (bodyElements.hasNext()) {
                SOAPElement bodyElement = (SOAPElement) bodyElements.next();
                message.setBody(bodyElement);
            }
            return message;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize message", e);
        }
    }

}