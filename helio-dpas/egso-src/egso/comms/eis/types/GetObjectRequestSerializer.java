/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

/**
 * Serializer for <code>GetObjectRequest</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;GetObjectRequest&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;type&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;notifierId&quot; type=&quot;xsd:string&quot; minOccurs=&quot;0&quot;/&gt;
 *     &lt;xsd:element name=&quot;notifierType&quot; type=&quot;xsd:string&quot; minOccurs=&quot;0&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;&amp;&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.GetObjectRequest
 */
public class GetObjectRequestSerializer {

    // Constants
    
    private static final String TYPE_LOCALNAME = "type";

    private static final String NOTIFIER_ID_LOCALNAME = "notifierId";

    private static final String NOTIFIER_TYPE_LOCALNAME = "notifierType";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public GetObjectRequestSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public void serializeGetObjectRequest(GetObjectRequest getObjectRequest, Request request, SOAPElement getObjectElement) throws SerializationException {
        try {
            SOAPElement typeElement = getObjectElement.addChildElement(soapFactory.createName(TYPE_LOCALNAME));
            typeElement.setValue(getObjectRequest.getType().getName());
            String notifierId = getObjectRequest.getNotifierId();
            if (notifierId != null) {
                SOAPElement notifierIdElement = getObjectElement.addChildElement(soapFactory.createName(NOTIFIER_ID_LOCALNAME));
                notifierIdElement.setValue(notifierId);
            }
            Class notifierType = getObjectRequest.getNotifierType();
            if (notifierType != null) {
                SOAPElement notifierIdElement = getObjectElement.addChildElement(soapFactory.createName(NOTIFIER_TYPE_LOCALNAME));
                notifierIdElement.setValue(notifierType.getName());
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize get object request", e);
        }
    }

    public GetObjectRequest deserializeGetObjectRequest(Request request, SOAPElement getObjectElement) throws SerializationException {
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest();
            Iterator typeElements = getObjectElement.getChildElements(soapFactory.createName(TYPE_LOCALNAME));
            SOAPElement typeElement = (SOAPElement) typeElements.next();
            getObjectRequest.setType(Class.forName(typeElement.getValue()));
            Iterator notifierIdElements = getObjectElement.getChildElements(soapFactory.createName(NOTIFIER_ID_LOCALNAME));
            if (notifierIdElements.hasNext()) {
                SOAPElement notifierIdElement = (SOAPElement) notifierIdElements.next();
                getObjectRequest.setNotifierId(notifierIdElement.getValue());
            }
            Iterator notifierTypeElements = getObjectElement.getChildElements(soapFactory.createName(NOTIFIER_TYPE_LOCALNAME));
            if (notifierTypeElements.hasNext()) {
                SOAPElement notifierTypeElement = (SOAPElement) notifierTypeElements.next();
                getObjectRequest.setNotifierType(Class.forName(notifierTypeElement.getValue()));
            }
            return getObjectRequest;
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Failed to deserialize get object request", e);
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize get object request", e);
        }
    }

}