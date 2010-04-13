/**
 * SECwsdlPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.astro.ts.sec.dao.interfaces;

public interface SECPortType extends java.rmi.Remote {

    /**
     * sql:See SEC documentation.
     */
    public java.lang.String sql(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * describeCatalogue:See SEC documentation.
     */
    public java.lang.String describeCatalogue(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * describeTable:See SEC documentation.
     */
    public java.lang.String describeTable(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * countRows:See SEC documentation.
     */
    public java.lang.String countRows(java.lang.String name) throws java.rmi.RemoteException;
}
