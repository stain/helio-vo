/**
 * CDASWSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw;

public class CDASWSLocator extends org.apache.axis.client.Service implements eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CDASWS {

    public CDASWSLocator() {
    }


    public CDASWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CDASWSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CoordinatedDataAnalysisSystemPort
    private java.lang.String CoordinatedDataAnalysisSystemPort_address = "http://cdaweb.gsfc.nasa.gov:80/WS/jaxrpc";

    public java.lang.String getCoordinatedDataAnalysisSystemPortAddress() {
        return CoordinatedDataAnalysisSystemPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CoordinatedDataAnalysisSystemPortWSDDServiceName = "CoordinatedDataAnalysisSystemPort";

    public java.lang.String getCoordinatedDataAnalysisSystemPortWSDDServiceName() {
        return CoordinatedDataAnalysisSystemPortWSDDServiceName;
    }

    public void setCoordinatedDataAnalysisSystemPortWSDDServiceName(java.lang.String name) {
        CoordinatedDataAnalysisSystemPortWSDDServiceName = name;
    }

    public eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystem getCoordinatedDataAnalysisSystemPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CoordinatedDataAnalysisSystemPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCoordinatedDataAnalysisSystemPort(endpoint);
    }

    public eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystem getCoordinatedDataAnalysisSystemPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub _stub = new eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub(portAddress, this);
            _stub.setPortName(getCoordinatedDataAnalysisSystemPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCoordinatedDataAnalysisSystemPortEndpointAddress(java.lang.String address) {
        CoordinatedDataAnalysisSystemPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystem.class.isAssignableFrom(serviceEndpointInterface)) {
                eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub _stub = new eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub(new java.net.URL(CoordinatedDataAnalysisSystemPort_address), this);
                _stub.setPortName(getCoordinatedDataAnalysisSystemPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CoordinatedDataAnalysisSystemPort".equals(inputPortName)) {
            return getCoordinatedDataAnalysisSystemPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://cdaweb.gsfc.nasa.gov/WS/CDASWS", "CDASWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://cdaweb.gsfc.nasa.gov/WS/CDASWS", "CoordinatedDataAnalysisSystemPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CoordinatedDataAnalysisSystemPort".equals(portName)) {
            setCoordinatedDataAnalysisSystemPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
