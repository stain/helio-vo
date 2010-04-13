/**
 * SECwsdl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.astro.ts.sec.dao.interfaces;

public interface SEC extends javax.xml.rpc.Service {
    public java.lang.String getSECwsdlPortAddress();

    public SECPortType getSECwsdlPort() throws javax.xml.rpc.ServiceException;

    public SECPortType getSECwsdlPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
