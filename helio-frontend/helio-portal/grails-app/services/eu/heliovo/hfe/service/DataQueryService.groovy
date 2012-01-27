package eu.heliovo.hfe.service;
import eu.heliovo.clientapi.HelioClient
import eu.heliovo.clientapi.frontend.ResultVT
import eu.heliovo.clientapi.linkprovider.*
import eu.heliovo.clientapi.query.*
import eu.heliovo.clientapi.query.asyncquery.*
import eu.heliovo.clientapi.utils.STILUtils
import eu.heliovo.registryclient.*
import eu.heliovo.shared.util.*

import uk.ac.starlink.table.StarTable;


/**
 * Service methods to access a catalog query.
 * TODO: rename to CatalogQueryService
 * @author MarcoSoldati
 *
 */
class DataQueryService {

    boolean transactional = true

    def serviceMethod() {

    }

    /**
     * Main query method for dpas,hec,ics,ils, this connects to the HelioAPI.
     * TODO: Rename to catalogQuery, use Stil rather than ResultVT.
     * @params are pretty self explanatory.
     *
     */
    def queryService(String serviceName, List<String> minDate, List<String> maxDate, List<String> from, String where) {
    	log.info("queryService  ::" + serviceName + ", " + minDate+", "+maxDate+", "+from+", " + where);
    	
    	int maxrecords = 0;
    	int startindex = 0;
    	
    	if(minDate.size()==0){
            log.info("queryService :: cant complete, date fields empty" );
            return;
    	}
    	if(maxDate.size()==0){
            log.info("queryService :: cant complete, date fields empty" );
            return;
    	}
        def helioClient = new HelioClient();
        HelioQueryService service;
        HelioQueryResult result;
        
        switch (serviceName){
            case "ICS":
                service = helioClient.getServiceInstance(HelioServiceName.ICS, ServiceCapability.ASYNC_QUERY_SERVICE, "ivo://helio-vo.eu/ics/ics_pat");
            break;
            case "DES":
                service = helioClient.getServiceInstance(HelioServiceName.DES, ServiceCapability.ASYNC_QUERY_SERVICE, null);
            break;
            default:
            HelioServiceName helioServiceName = HelioServiceName.valueOf(serviceName.toUpperCase());
            service = helioClient.getServiceInstance(helioServiceName, ServiceCapability.SYNC_QUERY_SERVICE, null);
            break;

        }
        //lists need to come in as a 1 to 1 relation between date and from, permuteLists makes sure this relation is kept by padding lists with the required elements.
        List<String>[] permuted = DateUtil.permuteLists(minDate,from)
        minDate = permuted[0];
        permuted = DateUtil.permuteLists(maxDate,from)
        maxDate = permuted[0];
        from  = permuted[1];

        result = service.query(minDate, maxDate, from, where, 0, 0, null);
        //StarTable starTable = STILUtils.read(result.asURL());
        ResultVT resvt = new ResultVT(result.asVOTable(),result.getUserLogs());
            	
    	log.info("queryService :Got Result.");
    	
    	return resvt;
    }
}