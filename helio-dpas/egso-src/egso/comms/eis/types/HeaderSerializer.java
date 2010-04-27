/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.pis.types.Header;

/**
 * Serializer for <code>Header</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;Header&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;deliveryId&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;deliveryNodes&quot; type=&quot;xsd:string&quot; minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 *     &lt;xsd:element name=&quot;statusCode&quot; type=&quot;xsd:int&quot;/&gt; &lt;xsd:element name=&quot;senderId&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;recipientId&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;metadata&quot; type=&quot;xsd:anyType&quot; minOccurs=&quot;0&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.pis.types.Header
 */
public class HeaderSerializer {

    // Constants
    
    private static final String DELIVERY_ID_LOCALNAME = "deliveryId";

    private static final String DELIVERY_NODES_LOCALNAME = "deliveryNodes";

    private static final String STATUS_CODE_LOCALNAME = "statusCode";

    private static final String SENDER_ID_LOCALNAME = "senderId";

    private static final String RECIPIENT_ID_LOCALNAME = "recipientId";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public HeaderSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public Header deserializeHeader(SOAPElement headerElement) throws SerializationException {
        try {
            Header header = new Header();
            Iterator deliveryIdElements = headerElement.getChildElements(soapFactory.createName(DELIVERY_ID_LOCALNAME));
            SOAPElement deliveryIdElement = (SOAPElement) deliveryIdElements.next();
            header.setDeliveryId(deliveryIdElement.getValue());
            List deliveryNodesList = new ArrayList();
            Iterator deliveryNodesElements = headerElement.getChildElements(soapFactory.createName(DELIVERY_NODES_LOCALNAME));
            while (deliveryNodesElements.hasNext()) {
                SOAPElement deliveryNodesElement = (SOAPElement) deliveryNodesElements.next();
                deliveryNodesList.add(deliveryNodesElement.getValue());
            }
            header.setDeliveryNodes((String[]) deliveryNodesList.toArray(new String[] {}));
            Iterator statusCodeElements = headerElement.getChildElements(soapFactory.createName(STATUS_CODE_LOCALNAME));
            SOAPElement statusCodeElement = (SOAPElement) statusCodeElements.next();
            header.setStatusCode(Integer.parseInt(statusCodeElement.getValue()));
            Iterator senderIdElements = headerElement.getChildElements(soapFactory.createName(SENDER_ID_LOCALNAME));
            SOAPElement senderIdElement = (SOAPElement) senderIdElements.next();
            header.setSenderId(senderIdElement.getValue());
            Iterator recipientIdElements = headerElement.getChildElements(soapFactory.createName(RECIPIENT_ID_LOCALNAME));
            SOAPElement recipientIdElement = (SOAPElement) recipientIdElements.next();
            header.setRecipientId(recipientIdElement.getValue());
            return header;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize header", e);
        }
    }

}