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
 * Serializer for <code>Request</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;Request&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;handlerId&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;oneWay&quot; type=&quot;xsd:boolean&quot;/&gt;
 *     &lt;xsd:element name=&quot;content&quot; type=&quot;xsd:anyType&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.Request
 */
public class RequestSerializer {

    // Constants
    
    public static final String HANDLER_ID_LOCALNAME = "handlerId";

    public static final String ONEWAY_LOCALNAME = "oneWay";

    public static final String SUBTYPE_LOCALNAME = "subtype";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public RequestSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public void serializeRequest(Request request, Message message, SOAPElement requestElement) throws SerializationException {
        try {
            message.getHeader().setDeliveryId(request.getId());
            message.getHeader().setSenderId(request.getClientId());
            message.getHeader().setRecipientId(request.getServerId());
            SOAPElement handlerIdElement = requestElement.addChildElement(soapFactory.createName(HANDLER_ID_LOCALNAME));
            handlerIdElement.setValue(request.getHandlerId());
            SOAPElement oneWayElement = requestElement.addChildElement(soapFactory.createName(ONEWAY_LOCALNAME));
            oneWayElement.setValue(Boolean.toString(request.isOneWay()));
            SOAPElement subtypeElement = request.getSubtype();
            requestElement.addChildElement(subtypeElement);
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize request", e);
        }
    }

    public Request deserializeRequest(Message message, SOAPElement requestElement) throws SerializationException {
        try {
            Request request = new Request();
            request.setId(message.getHeader().getDeliveryId());
            request.setClientId(message.getHeader().getSenderId());
            request.setServerId(message.getHeader().getRecipientId());
            Iterator handlerIdElements = requestElement.getChildElements(soapFactory.createName(HANDLER_ID_LOCALNAME));
            SOAPElement handlerIdElement = (SOAPElement) handlerIdElements.next();
            request.setHandlerId(handlerIdElement.getValue());
            Iterator oneWayElements = requestElement.getChildElements(soapFactory.createName(ONEWAY_LOCALNAME));
            SOAPElement oneWayElement = (SOAPElement) oneWayElements.next();
            request.setOneWay(Boolean.valueOf(oneWayElement.getValue()).booleanValue());
            Iterator subtypeElements = requestElement.getChildElements(soapFactory.createName(SUBTYPE_LOCALNAME));
            SOAPElement subtypeElement = (SOAPElement) subtypeElements.next();
            request.setSubtype(subtypeElement);
            return request;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize request", e);
        }
    }

}