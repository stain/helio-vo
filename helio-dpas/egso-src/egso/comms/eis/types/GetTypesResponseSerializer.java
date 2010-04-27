/*
 * Created on Dec 2, 2004
 */
package org.egso.comms.eis.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

/**
 * Serializer for <code>GetTypesResponse</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;GetTypesResponse&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;types&quot; type=&quot;xsd:string&quot; minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.GetTypesResponse
 */
public class GetTypesResponseSerializer {

    // Constants
    
    private static final String TYPES_LOCALNAME = "types";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public GetTypesResponseSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public void serializeGetTypesResponse(GetTypesResponse getTypesResponse, Response response, SOAPElement getTypesElement) throws SerializationException {
        try {
            if (getTypesResponse.getTypes() != null) {
                Iterator types = getTypesResponse.getTypes().iterator();
                while (types.hasNext()) {
                    Class type = (Class) types.next();
                    SOAPElement typeElement = getTypesElement.addChildElement(soapFactory.createName(TYPES_LOCALNAME));
                    typeElement.setValue(type.getName());
                }
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize get types response", e);
        }
    }

    public GetTypesResponse deserializeGetTypesResponse(Response response, SOAPElement getTypesElement) throws SerializationException {
        try {
            GetTypesResponse getTypesResponse = new GetTypesResponse();
            List typesList = new ArrayList();
            Iterator typeElements = getTypesElement.getChildElements(soapFactory.createName(TYPES_LOCALNAME));
            while (typeElements.hasNext()) {
                SOAPElement typeElement = (SOAPElement) typeElements.next();
                typesList.add(Class.forName(typeElement.getValue()));
            }
            getTypesResponse.setTypes(typesList);
            return getTypesResponse;
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Failed to deserialize get types request", e);
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize get types request", e);
        }
    }

}