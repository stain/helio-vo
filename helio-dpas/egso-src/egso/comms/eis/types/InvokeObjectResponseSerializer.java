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
 * Serializer for <code>InvokeObjectResponse</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;InvokeObjectResponse&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;outputParameter&quot; type=&quot;xsd:string&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.InvokeObjectResponse
 */
public class InvokeObjectResponseSerializer {

    // Constants
    
    private static final String OUT_PARAM_LOCALNAME = "outputParameter";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    private ParameterSerializer parameterSerializer = null;

    // Constructors
    
    public InvokeObjectResponseSerializer(SOAPFactory soapFactory, FilespaceManager filespaceManager) {
        this.soapFactory = soapFactory;
        parameterSerializer = new ParameterSerializer(soapFactory, filespaceManager);
    }

    // Public interface
    
    public void serializeInvokeObjectResponse(InvokeObjectResponse invokeObjectResponse, Response response, SOAPElement invokeObjectElement) throws SerializationException {
        try {
            SOAPElement outputParamElement = invokeObjectElement.addChildElement(soapFactory.createName(OUT_PARAM_LOCALNAME));
            parameterSerializer.serializeParameter(invokeObjectResponse.getOutputParam(), outputParamElement);
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize invoke object response", e);
        }
    }

    public InvokeObjectResponse deserializeInvokeObjectResponse(Response response, SOAPElement invokeObjectElement) throws SerializationException {
        try {
            InvokeObjectResponse invokeObjectResponse = new InvokeObjectResponse();
            Iterator outputParamElements = invokeObjectElement.getChildElements(soapFactory.createName(OUT_PARAM_LOCALNAME));
            SOAPElement outputParamElement = (SOAPElement) outputParamElements.next();
            invokeObjectResponse.setOutputParam(parameterSerializer.deserializeParameter(outputParamElement));
            return invokeObjectResponse;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize invoke object response", e);
        }
    }

}