/**
 * VSOiPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.virtualsolar.VSO.VSOi;

public interface VSOiPort extends java.rmi.Remote {
    public org.virtualsolar.VSO.VSOi.ProviderQueryResponse[] query(org.virtualsolar.VSO.VSOi.QueryRequest body) throws java.rmi.RemoteException;
    public org.virtualsolar.VSO.VSOi.ProviderGetDataResponse[] getData(org.virtualsolar.VSO.VSOi.VSOGetDataRequest body) throws java.rmi.RemoteException;
}
