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

import org.egso.comms.eis.storage.FilespaceManager;

/**
 * Serializer for <code>InvokeObjectRequest</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;InvokeObjectRequest&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;objectId&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;method&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;inputParams&quot; type=&quot;tns:Parameter&quot; minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.types.InvokeObjectRequest
 */
public class InvokeObjectRequestSerializer {

    // Constants
    
    private static final String OBJECT_ID_LOCALNAME = "objectId";

    private static final String METHOD_LOCALNAME = "method";

    private static final String IN_PARAMS_LOCALNAME = "inputParams";

    // Instance variables
    
    private ParameterSerializer parameterSerializer = null;

    private SOAPFactory soapFactory = null;

    // Constructors
    
    public InvokeObjectRequestSerializer(SOAPFactory soapFactory, FilespaceManager filespaceManager) {
        this.soapFactory = soapFactory;
        parameterSerializer = new ParameterSerializer(soapFactory, filespaceManager);
    }

    // Public interface
    
    public void serializeInvokeObjectRequest(InvokeObjectRequest invokeObjectRequest, SOAPElement invokeObjectElement) throws SerializationException {
        try {
            SOAPElement objectIdElement = invokeObjectElement.addChildElement(soapFactory.createName(OBJECT_ID_LOCALNAME));
            objectIdElement.setValue(invokeObjectRequest.getObjectId());
            SOAPElement methodElement = invokeObjectElement.addChildElement(soapFactory.createName(METHOD_LOCALNAME));
            methodElement.setValue(invokeObjectRequest.getMethodName());
            if (invokeObjectRequest.getInputParams() != null) {
                Iterator inputParams = invokeObjectRequest.getInputParams().iterator();
                while (inputParams.hasNext()) {
                    SOAPElement inputParamElement = invokeObjectElement.addChildElement(soapFactory.createName(IN_PARAMS_LOCALNAME));
                    parameterSerializer.serializeParameter((Parameter) inputParams.next(), inputParamElement);
                }
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize invoke object request", e);
        }
    }

    public InvokeObjectRequest deserializeInvokeObjectRequest(SOAPElement invokeObjectElement) throws SerializationException {
        try {
            InvokeObjectRequest invokeObjectRequest = new InvokeObjectRequest();
            Iterator objectIdElements = invokeObjectElement.getChildElements(soapFactory.createName(OBJECT_ID_LOCALNAME));
            SOAPElement objectIdElement = (SOAPElement) objectIdElements.next();
            invokeObjectRequest.setObjectId(objectIdElement.getValue());
            Iterator methodElements = invokeObjectElement.getChildElements(soapFactory.createName(METHOD_LOCALNAME));
            SOAPElement methodElement = (SOAPElement) methodElements.next();
            invokeObjectRequest.setMethodName(methodElement.getValue());
            List<Parameter> inputParamsList = new ArrayList<Parameter>();
            Iterator inputParamElements = invokeObjectElement.getChildElements(soapFactory.createName(IN_PARAMS_LOCALNAME));
            while (inputParamElements.hasNext()) {
                SOAPElement inputParamElement = (SOAPElement) inputParamElements.next();
                inputParamsList.add(parameterSerializer.deserializeParameter(inputParamElement));
            }
            invokeObjectRequest.setInputParams(inputParamsList);
            return invokeObjectRequest;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to deserialize invoke object request", e);
        }
    }

}