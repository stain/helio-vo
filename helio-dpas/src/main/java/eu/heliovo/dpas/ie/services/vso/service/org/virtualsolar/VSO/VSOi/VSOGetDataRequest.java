/**
 * VSOGetDataRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi;

public class VSOGetDataRequest  implements java.io.Serializable {
    private java.lang.Float version;

    private eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest request;

    public VSOGetDataRequest() {
    }

    public VSOGetDataRequest(
           java.lang.Float version,
           eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest request) {
           this.version = version;
           this.request = request;
    }


    /**
     * Gets the version value for this VSOGetDataRequest.
     * 
     * @return version
     */
    public java.lang.Float getVersion() {
        return version;
    }


    /**
     * Sets the version value for this VSOGetDataRequest.
     * 
     * @param version
     */
    public void setVersion(java.lang.Float version) {
        this.version = version;
    }


    /**
     * Gets the request value for this VSOGetDataRequest.
     * 
     * @return request
     */
    public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest getRequest() {
        return request;
    }


    /**
     * Sets the request value for this VSOGetDataRequest.
     * 
     * @param request
     */
    public void setRequest(eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest request) {
        this.request = request;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VSOGetDataRequest)) return false;
        VSOGetDataRequest other = (VSOGetDataRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              this.request.equals(other.getRequest())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VSOGetDataRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://virtualsolar.org/VSO/VSOi", "VSOGetDataRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("", "request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://virtualsolar.org/VSO/VSOi", "GetDataRequest"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
