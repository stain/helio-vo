package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi;

public class VSOiPortProxy implements eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiPort {
  private String _endpoint = null;
  private eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiPort vSOiPort = null;
  
  public VSOiPortProxy() {
    _initVSOiPortProxy();
  }
  
  public VSOiPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initVSOiPortProxy();
  }
  
  private void _initVSOiPortProxy() {
    try {
      vSOiPort = (new eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiServiceLocator()).getsdacVSOi();
      if (vSOiPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)vSOiPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)vSOiPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (vSOiPort != null)
      ((javax.xml.rpc.Stub)vSOiPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiPort getVSOiPort() {
    if (vSOiPort == null)
      _initVSOiPortProxy();
    return vSOiPort;
  }
  
  public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse[] query(eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequest body) throws java.rmi.RemoteException{
    if (vSOiPort == null)
      _initVSOiPortProxy();
    return vSOiPort.query(body);
  }
  
  public eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderGetDataResponse[] getData(eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOGetDataRequest body) throws java.rmi.RemoteException{
    if (vSOiPort == null)
      _initVSOiPortProxy();
    return vSOiPort.getData(body);
  }
  
  
}