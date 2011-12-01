package eu.heliovo.hfe.controller

import javax.xml.bind.JAXBContext

import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

import net.ivoa.xml.votable.v1.VOTABLE
import eu.heliovo.clientapi.frontend.ResultVT
import eu.heliovo.clientapi.utils.STILUtils
import eu.heliovo.hfe.service.ResultVTManagerService
import eu.heliovo.hfe.service.VoTableService

/**
 * Controller to handle voTable uploads, downloads and displaying. 
 * @author MarcoSoldati
 */
class VoTableController {
    /**
     * point to the voTableUplaodService
     */
    VoTableService voTableService;

    def index = { }
    
    /**
    * Upload a VoTable. The table will be parsed and then returned to be diplayed.
    */
   def asyncUpload ={
       log.info("asyncUpload =>" +params);

       try{
           // some sanity checks
           def file = request.getFile("fileInput") 
           if (file.getOriginalFilename()=="") {
               throw new RuntimeException("A valid xml VO-table file must be selected to continue.");
           }
           
           if (!file.getOriginalFilename().endsWith(".xml")) {
               throw new RuntimeException("Not a valid xml file. The name should end with .xml");
           }
           
           def helioResult = voTableService.parseVoTable(file);
           
           def serviceName = 'upload';
           //ResultVT result = new ResultVT(votable);
           int resultId= ResultVTManagerService.addResult(helioResult,serviceName);
           def uploadId =request.getFile("fileInput").getOriginalFilename();
           
           def responseObject = [result:helioResult];
           render template:'templates/stilResponse', bean:responseObject, var:'responseObject'
       } catch(Exception e) {
           //e.printStackTrace();
           def responseObject = [error:e.getMessage() ];
           render template:'templates/response', bean:responseObject, var:'responseObject'
       }
   }

}
