/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.pis.types.Message;

/**
 * Serializer for <code>Response</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;Response&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;exception&quot; type=&quot;tns:AdapterException&quot; minOccurs=&quot;0&quot;/&gt;
 *     &lt;xsd:element name=&quot;content&quot; type=&quot;xsd:anyType&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.Request
 */
public class ResponseSerializer {

    // Constants
    
    public static final String HANDLER_ID_LOCALNAME = "handlerId";

    public static final String EXCEPTION_LOCALNAME = "exception";

    public static final String SUBTYPE_LOCALNAME = "subtype";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    private AdapterExceptionSerializer exceptionSerializer = null;

    // Constructors
    
    public ResponseSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
        exceptionSerializer = new AdapterExceptionSerializer(soapFactory);
    }

    // Public interface
    
    public void serializeResponse(Response response, Message message, SOAPElement responseElement) throws SerializationException {
        try {
            message.getHeader().setDeliveryId(response.getRequestId());
            message.getHeader().setRecipientId(response.getClientId());
            message.getHeader().setSenderId(response.getServerId());
            if (response.getException() != null) {
                SOAPElement exceptionElement = responseElement.addChildElement(soapFactory.createName(EXCEPTION_LOCALNAME));
                exceptionSerializer.serializeException(response.getException(), message, exceptionElement);
            }
            SOAPElement contentElement = response.getSubtype();
            responseElement.addChildElement(contentElement);
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize request", e);
        }
    }

    public Response deserializeResponse(Message message, SOAPElement responseElement) throws SerializationException {
        try {
            Response response = new Response();
            response.setRequestId(message.getHeader().getDeliveryId());
            response.setClientId(message.getHeader().getRecipientId());
            response.setServerId(message.getHeader().getSenderId());
            Iterator exceptionElements = responseElement.getChildElements(soapFactory.createName(EXCEPTION_LOCALNAME));
            if (exceptionElements.hasNext()) {
                SOAPElement exceptionElement = (SOAPElement) exceptionElements.next();
                response.setException((AdapterException) exceptionSerializer.deserializeException(message, exceptionElement));
            }
            Iterator subtypeElements = responseElement.getChildElements(soapFactory.createName(SUBTYPE_LOCALNAME));
            SOAPElement subtypeElement = (SOAPElement) subtypeElements.next();
            response.setSubtype(subtypeElement);
            return response;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize response", e);
        }
    }

}