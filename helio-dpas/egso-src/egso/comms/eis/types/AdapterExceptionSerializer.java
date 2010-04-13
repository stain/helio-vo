/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.MissingHandlerException;
import org.egso.comms.eis.adapter.RequestHandlerException;
import org.egso.comms.pis.types.Message;

/**
 * Serializer for <code>AdapterException</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;AdapterException&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:choice&gt;
 *       &lt;xsd:element name=&quot;missingHandler&quot; type=&quot;tns:string&quot;/&gt;
 *       &lt;xsd:element name=&quot;handlerException&quot; type=&quot;tns:string&quot;/&gt;
 *     &lt;/xsd:choice&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see org.egso.comms.eis.adapter.AdapterException
 */
public class AdapterExceptionSerializer {

    // Constants
    
    private static final String MISSING_HANDLER_LOCALNAME = "missingHandler";

    private static final String HANDLER_EXCEPTION_LOCALNAME = "handlerException";

    // Instance variables
    
    private SOAPFactory soapFactory = null;

    // Constructors
    
    public AdapterExceptionSerializer(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    // Public interface
    
    public void serializeException(Exception exception, Message message, SOAPElement exceptionElement) throws SerializationException {
        try {
            if (exception instanceof MissingHandlerException) {
            } else if (exception instanceof RequestHandlerException) {
                SOAPElement handlerExceptionElement = exceptionElement.addChildElement(soapFactory.createName(HANDLER_EXCEPTION_LOCALNAME));
                RequestHandlerException requestHandlerException = (RequestHandlerException) exception;
                handlerExceptionElement.setValue(requestHandlerException.getMessage());
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize exception", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Exception deserializeException(Message message, SOAPElement exceptionElement) {
        AdapterException exception = null;
        Iterator<SOAPElement> choiceElements = exceptionElement.getChildElements();
        SOAPElement choiceElement = choiceElements.next();
        if (choiceElement.getElementName().getLocalName().equals(MISSING_HANDLER_LOCALNAME)) {
            exception = new MissingHandlerException();
        } else if (choiceElement.getElementName().getLocalName().equals(HANDLER_EXCEPTION_LOCALNAME)) {
            exception = new RequestHandlerException(choiceElement.getValue());
        }
        return exception;
    }

}