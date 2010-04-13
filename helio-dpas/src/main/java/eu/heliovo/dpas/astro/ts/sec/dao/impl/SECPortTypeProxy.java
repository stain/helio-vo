package eu.heliovo.dpas.astro.ts.sec.dao.impl;

import eu.heliovo.dpas.astro.ts.sec.dao.interfaces.SECPortType;

public class SECPortTypeProxy implements SECPortType {
  private String _endpoint = null;
  private SECPortType sECwsdlPortType = null;
  
  public SECPortTypeProxy() {
    _initSECwsdlPortTypeProxy();
  }
  
  public SECPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSECwsdlPortTypeProxy();
  }
  
  private void _initSECwsdlPortTypeProxy() {
    try {
      sECwsdlPortType = (new SECLocator()).getSECwsdlPort();
      if (sECwsdlPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sECwsdlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sECwsdlPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sECwsdlPortType != null)
      ((javax.xml.rpc.Stub)sECwsdlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public SECPortType getSECwsdlPortType() {
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType;
  }
  
  public java.lang.String sql(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.sql(name);
  }
  
  public java.lang.String describeCatalogue(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.describeCatalogue(name);
  }
  
  public java.lang.String describeTable(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.describeTable(name);
  }
  
  public java.lang.String countRows(java.lang.String name) throws java.rmi.RemoteException{
    if (sECwsdlPortType == null)
      _initSECwsdlPortTypeProxy();
    return sECwsdlPortType.countRows(name);
  }
  
  
}