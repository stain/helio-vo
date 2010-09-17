/**
 * CDASWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package  eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw;

public interface CDASWS extends javax.xml.rpc.Service {
    public java.lang.String getCoordinatedDataAnalysisSystemPortAddress();

    public eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystem getCoordinatedDataAnalysisSystemPort() throws javax.xml.rpc.ServiceException;

    public eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystem getCoordinatedDataAnalysisSystemPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
