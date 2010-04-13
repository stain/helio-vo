/*
 * Created on Jul 13, 2004
 */
package org.egso.comms.eis.types;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.egso.comms.eis.storage.FilespaceManager;
import org.egso.comms.eis.storage.StorageException;
import org.w3c.dom.NodeList;

/**
 * Serializer for <code>Parameter</code> objects.
 * 
 * <pre>
 * &lt;xsd:complexType name=&quot;Parameter&quot;&gt;
 *   &lt;xsd:sequence&gt;
 *     &lt;xsd:element name=&quot;type&quot; type=&quot;xsd:string&quot;/&gt;
 *     &lt;xsd:element name=&quot;value&quot; type=&quot;xsd:any&quot;/&gt;
 *   &lt;/xsd:sequence&gt;
 * &lt;/xsd:complexType&gt;
 * </pre>
 * 
 * <code>Parameter.types</code> supported:
 * 
 * <ul>
 * <li><code>Boolean.TYPE</code> and <code>Boolean.class</code>
 * </li>
 * <li><code>Byte.TYPE</code> and <code>Byte.class</code></li>
 * <li><code>Charcter.TYPE</code> and <code>Character.class</code>
 * </li>
 * <li><code>Double.TYPE</code> and <code>Double.class</code>
 * </li>
 * <li><code>Float.TYPE</code> and <code>Float.class</code></li>
 * <li><code>Long.TYPE</code> and <code>Long.class</code></li>
 * <li><code>Integer.TYPE</code> and <code>Integer.class</code>
 * </li>
 * <li><code>Short.TYPE</code> and <code>Short.class</code></li>
 * <li><code>Void.TYPE</code> and <code>Void.class</code></li>
 * <li><code>String.class</code></li>
 * <li><code>BigDecimal.class</code></li>
 * <li><code>BigInteger.class</code></li>
 * <li><code>Date.class</code></li>
 * <li><code>Calendar.class</code></li>
 * <li><code>URI.class</code></li>
 * <li><code>Class.class</code> where <code>Parameter.value</code>
 * must also be present remotely</li>
 * <li><code>DataHandler.class</code> where
 * <code>Parameter.value</code> is serialized separately</li>
 * </ul>
 * 
 * In addition to:
 * 
 * <ul>
 * <li><code>List.class</code> where <code>Parameter.value</code>
 * is deserialized as an <code>ArrayList</code></li>
 * <li><code>Map.class</code> where <code>Parameter.value</code>
 * is deserialized as a <code>HashMap</code></li>
 * <li><code>Set.class</code> where <code>Parameter.value</code>
 * is deserialized as a <code>HashSet</code></li>
 * <li>Multi-dimensional arrays</li>
 * </ul>
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see Parameter
 */
public class ParameterSerializer {

    // Constants
    
    private static final String TYPE_LOCALNAME = "type";

    private static final String VALUE_LOCALNAME = "value";

    private static final String ELEMENT_LOCALNAME = "element";

    private static final String MAPPING_LOCALNAME = "mapping";

    private static final String KEY_LOCALNAME = "key";

    private static final String NAME_LOCALNAME = "name";

    private static final String URI_LOCALNAME = "uri";

    private static final String CONTENT_TYPE_LOCALNAME = "content-type";

    // Instance variables
    
    private FilespaceManager filespaceManager = new FilespaceManager();

    private SOAPFactory soapFactory = null;

    // Constructors
    
    public ParameterSerializer(SOAPFactory soapFactory, FilespaceManager filespaceManager) {
        this.soapFactory = soapFactory;
        this.filespaceManager = filespaceManager;
    }

    // Public interface
    
    public void serializeParameter(Parameter parameter, SOAPElement parameterElement) throws SerializationException {
        try {
            Class type = parameter.getType();
            SOAPElement typeElement = parameterElement.addChildElement(soapFactory.createName(TYPE_LOCALNAME));
            typeElement.setValue(type.getName());
            Object value = parameter.getValue();
            SOAPElement valueElement = parameterElement.addChildElement(soapFactory.createName(VALUE_LOCALNAME));
            // Be careful with this. The test order is significant.
            if (value == null) {
            } else if (type.isArray()) {
                for (int i = 0; i < Array.getLength(value); i++) {
                    Object element = Array.get(value, i);
                    SOAPElement elementElement = valueElement.addChildElement(soapFactory.createName(ELEMENT_LOCALNAME + i));
                    if (element != null) {
                        serializeParameter(new Parameter(element.getClass(), element), elementElement);
                    }
                }
            } else if (List.class.isAssignableFrom(type)) {
                List list = (List) value;
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    SOAPElement elementElement = valueElement.addChildElement(soapFactory.createName(ELEMENT_LOCALNAME + i));
                    if (element != null) {
                        serializeParameter(new Parameter(element.getClass(), element), elementElement);
                    }
                }
            } else if (Map.class.isAssignableFrom(type)) {
                Map map = (Map) value;
                for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
                    Object mappingKey = iter.next();
                    Object mappingValue = map.get(mappingKey);
                    SOAPElement mappingElement = valueElement.addChildElement(soapFactory.createName(MAPPING_LOCALNAME));
                    SOAPElement mappingKeyElement = mappingElement.addChildElement(soapFactory.createName(KEY_LOCALNAME));
                    SOAPElement mappingValueElement = mappingElement.addChildElement(soapFactory.createName(VALUE_LOCALNAME));
                    if (mappingKey != null) {
                        serializeParameter(new Parameter(mappingKey.getClass(), mappingKey), mappingKeyElement);
                    }
                    if (mappingValue != null) {
                        serializeParameter(new Parameter(mappingValue.getClass(), mappingValue), mappingValueElement);
                    }
                }
            } else if (Set.class.isAssignableFrom(type)) {
                Set set = (Set) value;
                for (Iterator iter = set.iterator(); iter.hasNext();) {
                    Object element = iter.next();
                    SOAPElement elementElement = valueElement.addChildElement(soapFactory.createName(ELEMENT_LOCALNAME));
                    if (element != null) {
                        serializeParameter(new Parameter(element.getClass(), element), elementElement);
                    }
                }
            } else if (type == Boolean.TYPE || type == Boolean.class) {
                Boolean bool = (Boolean) value;
                valueElement.setValue(bool.toString());
            } else if (type == Byte.TYPE || type == Byte.class) {
                Byte byt = (Byte) value;
                valueElement.setValue(byt.toString());
            } else if (type == Character.TYPE || type == Character.class) {
                Character cha = (Character) value;
                valueElement.setValue(cha.toString());
            } else if (type == Double.TYPE || type == Double.class) {
                Double doub = (Double) value;
                valueElement.setValue(doub.toString());
            } else if (type == Float.TYPE || type == Float.class) {
                Float flo = (Float) value;
                valueElement.setValue(flo.toString());
            } else if (type == Long.TYPE || type == Long.class) {
                Long lon = (Long) value;
                valueElement.setValue(lon.toString());
            } else if (type == Integer.TYPE || type == Integer.class) {
                Integer in = (Integer) value;
                valueElement.setValue(in.toString());
            } else if (type == Short.TYPE || type == Short.class) {
                Short shor = (Short) value;
                valueElement.setValue(shor.toString());
            } else if (type == Void.TYPE || type == Void.class) {
            } else if (type == String.class) {
                String string = (String) value;
                valueElement.setValue(string);
            } else if (type == BigDecimal.class) {
                BigDecimal bigDecimal = (BigDecimal) value;
                valueElement.setValue(bigDecimal.toString());
            } else if (type == BigInteger.class) {
                BigInteger bigInteger = (BigInteger) value;
                valueElement.setValue(bigInteger.toString());
            } else if (type == Date.class) {
                Date date = (Date) value;
                valueElement.setValue(Long.toString(date.getTime()));
            } else if (type == Calendar.class) {
                Calendar calendar = (Calendar) value;
                valueElement.setValue(Long.toString(calendar.getTimeInMillis()));
            } else if (type == URI.class) {
                URI uri = (URI) value;
                valueElement.setValue(uri.toString());
            } else if (type == Class.class) {
                Class cla = (Class) value;
                valueElement.setValue(cla.getName());
            } else if (type == DataHandler.class) {
                DataHandler dataHandler = (DataHandler) value;
                URI fileURI = filespaceManager.storeData(dataHandler, true);
                SOAPElement uriElement = valueElement.addChildElement(soapFactory.createName(URI_LOCALNAME));
                uriElement.setValue(fileURI.toString());
                if (dataHandler.getName() != null) {
                    SOAPElement nameElement = valueElement.addChildElement(soapFactory.createName(NAME_LOCALNAME));
                    nameElement.setValue(dataHandler.getName());
                }
                if (dataHandler.getContentType() != null) {
                    SOAPElement contentTypeElement = valueElement.addChildElement(soapFactory.createName(CONTENT_TYPE_LOCALNAME));
                    contentTypeElement.setValue(dataHandler.getContentType());
                }
            } else {
                throw new SerializationException("Failed to serialize parameter, type not supported, type: " + value.getClass().getName());
            }
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize parameter", e);
        } catch (StorageException e) {
            throw new SerializationException("Failed to serialize parameter", e);
        }
    }

    public Parameter deserializeParameter(SOAPElement parameterElement) throws SerializationException {
        try {
            Parameter parameter = new Parameter();
            Iterator typeElements = parameterElement.getChildElements(soapFactory.createName(TYPE_LOCALNAME));
            SOAPElement typeElement = (SOAPElement) typeElements.next();
            Class type = deserializeType(typeElement);
            parameter.setType(type);
            Iterator valueElements = parameterElement.getChildElements(soapFactory.createName(VALUE_LOCALNAME));
            SOAPElement valueElement = (SOAPElement) valueElements.next();
            // Be careful with this. The test order is significant.
            if (type.isArray()) {
                NodeList elementElements = valueElement.getChildNodes();
                Object array = Array.newInstance(type.getComponentType(), elementElements.getLength());
                for (int i = 0; i < elementElements.getLength(); i++) {
                    SOAPElement elementElement = (SOAPElement) elementElements.item(i);
                    Parameter element = deserializeParameter(elementElement);
                    Array.set(array, i, element.getValue());
                }
                parameter.setValue(array);
            } else if (List.class.isAssignableFrom(type)) {
                NodeList elementElements = valueElement.getChildNodes();
                List list = new ArrayList();
                for (int i = 0; i < elementElements.getLength(); i++) {
                    SOAPElement elementElement = (SOAPElement) elementElements.item(i);
                    Parameter element = deserializeParameter(elementElement);
                    list.add(i, element.getValue());
                }
                parameter.setValue(list);
            } else if (Map.class.isAssignableFrom(type)) {
                Iterator mappingElements = valueElement.getChildElements(soapFactory.createName(MAPPING_LOCALNAME));
                Map map = new HashMap();
                while (mappingElements.hasNext()) {
                    SOAPElement mappingElement = (SOAPElement) mappingElements.next();
                    Iterator mappingKeyElements = mappingElement.getChildElements(soapFactory.createName(KEY_LOCALNAME));
                    Parameter mappingKey = deserializeParameter((SOAPElement) mappingKeyElements.next());
                    Iterator mappingValueElements = mappingElement.getChildElements(soapFactory.createName(VALUE_LOCALNAME));
                    Parameter mappingValue = deserializeParameter((SOAPElement) mappingValueElements.next());
                    map.put(mappingKey.getValue(), mappingValue.getValue());
                }
                parameter.setValue(map);
            } else if (Set.class.isAssignableFrom(type)) {
                Iterator elementElements = valueElement.getChildElements(soapFactory.createName(ELEMENT_LOCALNAME));
                Set set = new HashSet();
                while (elementElements.hasNext()) {
                    SOAPElement elementElement = (SOAPElement) elementElements.next();
                    Parameter element = deserializeParameter(elementElement);
                    set.add(element.getValue());
                }
                parameter.setValue(set);
            } else if (type == DataHandler.class) {
                DataSource dataSource = new DataSource();
                Iterator uriElements = valueElement.getChildElements(soapFactory.createName(URI_LOCALNAME));
                if (uriElements.hasNext()) {
                    SOAPElement uriElement = (SOAPElement) uriElements.next();
                    dataSource.setSource(URI.create(uriElement.getValue()));
                }
                Iterator nameElements = valueElement.getChildElements(soapFactory.createName(NAME_LOCALNAME));
                if (nameElements.hasNext()) {
                    SOAPElement nameElement = (SOAPElement) nameElements.next();
                    dataSource.setName(nameElement.getValue());
                }
                Iterator contentTypeElements = valueElement.getChildElements(soapFactory.createName(CONTENT_TYPE_LOCALNAME));
                if (contentTypeElements.hasNext()) {
                    SOAPElement contentTypeElement = (SOAPElement) contentTypeElements.next();
                    dataSource.setContentType(contentTypeElement.getValue());
                }
                parameter.setValue(new DataHandler(dataSource));
            } else if (valueElement.getValue() == null) {
            } else if (type == Boolean.TYPE || type == Boolean.class) {
                parameter.setValue(Boolean.valueOf(valueElement.getValue()));
            } else if (type == Byte.TYPE || type == Byte.class) {
                parameter.setValue(Byte.valueOf(valueElement.getValue()));
            } else if (type == Character.TYPE || type == Character.class) {
                parameter.setValue(new Character(valueElement.getValue().charAt(0)));
            } else if (type == Double.TYPE || type == Double.class) {
                parameter.setValue(Double.valueOf(valueElement.getValue()));
            } else if (type == Float.TYPE || type == Float.class) {
                parameter.setValue(Float.valueOf(valueElement.getValue()));
            } else if (type == Integer.TYPE || type == Integer.class) {
                parameter.setValue(Integer.valueOf(valueElement.getValue()));
            } else if (type == Long.TYPE || type == Long.class) {
                parameter.setValue(Long.valueOf(valueElement.getValue()));
            } else if (type == Short.TYPE || type == Short.class) {
                parameter.setValue(Short.valueOf(valueElement.getValue()));
            } else if (type == Void.TYPE || type == Void.class) {
            } else if (type == String.class) {
                parameter.setValue(valueElement.getValue());
            } else if (type == BigDecimal.class) {
                parameter.setValue(new BigDecimal(valueElement.getValue()));
            } else if (type == BigInteger.class) {
                parameter.setValue(new BigInteger(valueElement.getValue()));
            } else if (type == Date.class) {
                parameter.setValue(new Date(Long.parseLong(valueElement.getValue())));
            } else if (type == Calendar.class) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(Long.parseLong(valueElement.getValue())));
                parameter.setValue(calendar);
            } else if (type == URI.class) {
                parameter.setValue(new URI(valueElement.getValue()));
            } else if (type == Class.class) {
                parameter.setValue(deserializeType(valueElement));
            } else {
                throw new SerializationException("Failed to deserialize parameter, type not supported, type: " + type);
            }
            return parameter;
        } catch (SOAPException e) {
            throw new SerializationException("Failed to serialize parameter", e);
        } catch (URISyntaxException e) {
            throw new SerializationException("Failed to serialize parameter", e);
        }
    }
    
    // Private interface

    private Class deserializeType(SOAPElement typeElement) throws SerializationException {
        try {
            Class type = null;
            if (typeElement.getValue().equals(Boolean.TYPE.getName())) {
                type = Boolean.TYPE;
            } else if (typeElement.getValue().equals(Byte.TYPE.getName())) {
                type = Byte.TYPE;
            } else if (typeElement.getValue().equals(Character.TYPE.getName())) {
                type = Character.TYPE;
            } else if (typeElement.getValue().equals(Double.TYPE.getName())) {
                type = Double.TYPE;
            } else if (typeElement.getValue().equals(Float.TYPE.getName())) {
                type = Float.TYPE;
            } else if (typeElement.getValue().equals(Long.TYPE.getName())) {
                type = Long.TYPE;
            } else if (typeElement.getValue().equals(Integer.TYPE.getName())) {
                type = Integer.TYPE;
            } else if (typeElement.getValue().equals(Short.TYPE.getName())) {
                type = Short.TYPE;
            } else if (typeElement.getValue().equals(Void.TYPE.getName())) {
                type = Void.TYPE;
            } else {
                type = Class.forName(typeElement.getValue());
            }
            return type;
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Failed to deserialize type, check classpath", e);
        }
    }

    protected class DataSource implements javax.activation.DataSource {

        private URI source = null;

        private String name = null;

        private String contentType = null;

        public DataSource(URI source, String name, String contentType) {
            this.source = source;
            this.name = name;
            this.contentType = contentType;
        }

        public DataSource() {
        }

        public URI getSource() {
            return source;
        }

        public void setSource(URI source) {
            this.source = source;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public InputStream getInputStream() throws IOException {
            return new BufferedInputStream(source.toURL().openStream());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Data source does not support output");
        }

    }

}