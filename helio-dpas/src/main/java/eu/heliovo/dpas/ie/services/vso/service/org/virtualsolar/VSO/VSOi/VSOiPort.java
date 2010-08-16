/**
 * VSOiPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi;

public interface VSOiPort extends java.rmi.Remote {
    public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse[] query(eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequest body) throws java.rmi.RemoteException;
    public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderGetDataResponse[] getData(eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOGetDataRequest body) throws java.rmi.RemoteException;
}
