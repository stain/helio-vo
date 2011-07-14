package eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0_1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 15 11:23:43 BST 2010
 * Generated source version: 2.2.10
 * 
 */
 
@WebService(targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1", name = "HelioQueryService")
@XmlSeeAlso({eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.ObjectFactory.class, eu.heliovo.dpas.ie.services.hqi.service.net.ivoa.xml.votable.v1.ObjectFactory.class, eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.instruments.v0.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface HelioQueryService {

    @WebResult(name = "queryResponse", targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1", partName = "parameters")
    @WebMethod(operationName = "TimeQuery")
    public eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.QueryResponse timeQuery(
        @WebParam(partName = "parameters", name = "TimeQuery", targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1")
        eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.TimeQuery parameters
    );

    @WebResult(name = "queryResponse", targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1", partName = "parameters")
    @WebMethod(operationName = "Query")
    public eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.QueryResponse query(
        @WebParam(partName = "parameters", name = "Query", targetNamespace = "http://helio-vo.eu/xml/QueryService/v0.1")
        eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.Query parameters
    );
}
