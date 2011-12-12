package eu.heliovo.hfe.controller

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.springframework.web.multipart.MultipartHttpServletRequest

import eu.heliovo.hfe.model.result.HelioResult
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
     * Upload a VOTable. The table will be parsed and then returned to be diplayed.
     */
    def asyncUpload ={
 //       log.info("votable upload =>" +params);

        try{
            // some sanity checks
            if (!(request instanceof MultipartHttpServletRequest)) {
                throw new RuntimeException("Internal error: this is not a proper upload request. Are you trying to hack me?");
            }

            def file = request?.getFile("fileInput")
            if (file == null || file.getOriginalFilename()=="") {
                throw new RuntimeException("Please select a VOTable file to upload.");
            }

            if (!file.getOriginalFilename().endsWith(".xml")) {
                throw new RuntimeException("Not a valid xml file. The name should end with .xml");
            }

            def votableModel = voTableService.parseAndSaveVoTable(file);

            def votableResult = [result:votableModel];
            render template:'/output/votableResult', bean:votableResult, var:'votableResult'
        } catch(Exception e) {
            def message = e.getMessage() ? e.getMessage(): "Internal erro: a " + e.getClass() + " occurred while loading your VOTable.";
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw))
            def stackTrace = sw.toString();
            sw.close();
            println "upload error " + message;
            def responseObject = [message : message, stackTrace : stackTrace];
            render template:'/output/votableResultError', bean:responseObject, var:'responseObject'
        }
    }
    
    /**
    * Download result as a VOTable, i.e. retrieve the original content from the database 
    */
   def download = {
       log.info("VoTableController.downloadVOTable =>" + params);
       HelioResult result = HelioResult.get(params.resultId);
       if(result !=null){
           def name = voTableService.getFilename(result);
           
           response.setContentType("application/xml")
           response.setHeader("Content-disposition", "attachment;filename=" + name);
           response.outputStream << voTableService.getContent(result);
       }
       else {
           render(status: 503, text: 'Failed to retrieve votable wit id ${params.resultId}')
       }
   }
}
