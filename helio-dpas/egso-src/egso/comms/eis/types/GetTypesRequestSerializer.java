/*
 * Created on Dec 6, 2004
 */
package org.egso.comms.eis.types;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

/**
 * Serializer for <code>GetTypesRequest</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;GetTypesRequest&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;notifierType&quot; type=&quot;xsd:string&quot; minOccurs=&quot;0&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.GetTypesRequest
 */
public class GetTypesRequestSerializer {

    // Constants
    
    private static final String NOTIFIER_TYPE_LOCALNAME = "notifierType";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public GetTypesRequestSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public void serializeGetTypesRequest(GetTypesRequest getTypesRequest, Request request, SOAPElement getTypesElement) throws SerializationException {
        try {
            if (getTypesRequest.getNotifierType() != null) {
                SOAPElement notifierTypeElement = getTypesElement.addChildElement(soapFactory.createName(NOTIFIER_TYPE_LOCALNAME));
                notifierTypeElement.setValue(getTypesRequest.getNotifierType().getName());
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize get types request", e);
        }
    }

    public GetTypesRequest deserializeGetTypesRequest(Request request, SOAPElement getTypesElement) throws SerializationException {
        try {
            GetTypesRequest getTypesRequest = new GetTypesRequest();
            if (getTypesElement.getValue() != null) {
                getTypesRequest.setNotifierType(Class.forName(getTypesElement.getValue()));
            }
            return getTypesRequest;
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Failed to deserialize get types request", e);
        }
    }

}