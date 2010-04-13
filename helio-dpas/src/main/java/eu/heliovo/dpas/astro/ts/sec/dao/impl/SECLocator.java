/**
 * SECwsdlLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.astro.ts.sec.dao.impl;

import eu.heliovo.dpas.astro.ts.sec.dao.interfaces.SEC;
import eu.heliovo.dpas.astro.ts.sec.dao.interfaces.SECPortType;

public class SECLocator extends org.apache.axis.client.Service implements SEC {

    public SECLocator() {
    }


    public SECLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SECLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SECwsdlPort
    private java.lang.String SECwsdlPort_address = "http://sec.ts.astro.it/sec_server2.php";

    public java.lang.String getSECwsdlPortAddress() {
        return SECwsdlPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SECwsdlPortWSDDServiceName = "SECwsdlPort";

    public java.lang.String getSECwsdlPortWSDDServiceName() {
        return SECwsdlPortWSDDServiceName;
    }

    public void setSECwsdlPortWSDDServiceName(java.lang.String name) {
        SECwsdlPortWSDDServiceName = name;
    }

    public SECPortType getSECwsdlPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SECwsdlPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSECwsdlPort(endpoint);
    }

    public SECPortType getSECwsdlPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SECBindingStub _stub = new SECBindingStub(portAddress, this);
            _stub.setPortName(getSECwsdlPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSECwsdlPortEndpointAddress(java.lang.String address) {
        SECwsdlPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SECPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SECBindingStub _stub = new SECBindingStub(new java.net.URL(SECwsdlPort_address), this);
                _stub.setPortName(getSECwsdlPortWSDDServiceName());
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
        if ("SECwsdlPort".equals(inputPortName)) {
            return getSECwsdlPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sec.ts.astro.it/sec_server.php", "SECwsdl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sec.ts.astro.it/sec_server.php", "SECwsdlPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SECwsdlPort".equals(portName)) {
            setSECwsdlPortEndpointAddress(address);
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
