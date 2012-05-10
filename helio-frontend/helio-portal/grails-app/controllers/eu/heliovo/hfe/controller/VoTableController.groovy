package eu.heliovo.hfe.controller

import org.springframework.web.multipart.MultipartHttpServletRequest

import eu.heliovo.hfe.model.result.HelioResult
import eu.heliovo.hfe.service.VoTableService
import eu.heliovo.hfe.utils.TaskDescriptor
import grails.converters.JSON

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
        try{
            // some sanity checks
            if (!(request instanceof MultipartHttpServletRequest)) {
                throw new RuntimeException("Internal error: this is not a proper upload request. Are you trying to hack me?");
            }

            def file = request?.getFile("fileInput")
            if (file == null || file.getOriginalFilename()=="") {
                throw new RuntimeException("Please select a VOTable file to upload.");
            }

//            if (!file.getOriginalFilename().endsWith(".xml")) {
//                throw new RuntimeException("Not a valid xml file. The name should end with .xml");
//            }

            def taskDescriptor = TaskDescriptor.findTaskDescriptor("votableupload")
            
            def votableModel = voTableService.parseAndSaveVoTable(file);

            render template:'/output/votableResult', model:[result:votableModel, taskDescriptor : taskDescriptor]
        } catch (Exception e) {
            def message = e.getMessage() ? e.getMessage(): "Internal error: " + e.getClass() + " occurred while processing your VOTable.";
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw))
            def stackTrace = sw.toString();
            sw.close();
            //println "upload error " + message;
            def responseObject = [message : message, stackTrace : stackTrace];
            render template:'/output/votableResultError', bean:responseObject, var:'responseObject'
        }
    }
    
    /**
    * Download result as a VOTable, i.e. retrieve the original content from the database 
    */
   def download = {
       //log.info("VoTableController.downloadVOTable =>" + params);
       HelioResult result = HelioResult.get(params.resultId);
       if(result !=null){
           def name = voTableService.getFilename(result);
           
           response.setContentType("application/xml")
           response.setHeader("Content-disposition", "attachment;filename=" + name);
           response.outputStream << voTableService.getContent(result);
       }
       else {
           render(status: 503, text: 'Failed to retrieve votable with id ${params.resultId}')
       }
   }
   
   /**
    * get the data of a given VoTable Result in Ajax format.
    */
   def data = {
       def votableModel
       try {
           def result = HelioResult.get(params.resultId);
           votableModel = voTableService.createVOTableModel(result);
           def table = votableModel.tables[params.tableIndex.toInteger()];
           def tableModel = 
           [
               "aoColumns" : [],
               "aaData": []
           ]
           
           // render header rows
           for(def field : table.fields) {
               def headerCell = [:]
               headerCell["sTitle"] = field.name
               
               // HELIO specific properties, ignored by data tables
               headerCell["sDescription"] = field.description
               headerCell["sNullValue"] = '-'
               headerCell["ucd"] = field.ucd
               headerCell["unit"] = field.unit
               headerCell["utype"] = field.utype
               headerCell["xtype"] = field.xtype
               tableModel.aoColumns.add(headerCell)           
           }
           
           // render the data
           for (int i = 0; i < table.data.rowCount; i++) {
               def currentRow = table.data.getRow(i)
               def row = []
               
               for (def action : table.rowactions) {
                   row += '<div class="' + action + '"></div'
               }

               for (int j = 0; j < currentRow.size(); j++) {
                   def cell = currentRow[j]
                   def value;
                   if (cell == table.fields[j].nullValue) {
                       value = '-'
                   } else if (cell == Float.NaN) {
                       value = ''
                   } else if (table.fields[j].rendering_hint=='url' && cell != null) {
                       value = '<a target="_blank" href="' + cell + '">'+ cell.substring(cell.lastIndexOf('/')+1,cell.length()) + '</a';
                   } else if (table.fields[j].rendering_hint.startsWith('url=') && cell != null) {
                       value = '<a target="_blank" href="' + String.format(table.fields[j].rendering_hint.substring(4), cell) + '" >' + cell +'</a>' 
                   } else {
                       value = cell
                   }
                   row += value
               }
               tableModel.aaData.add(row)
           } 
           
          // println "table: " + table.data.getRow(4)
          // println "table: " + tableModel.aaData[4] 
           render tableModel as JSON
           //render template:'/output/votabledata', model : [table : table]
           
       } catch (Exception e) {
           def status = "Error while processing result votable (see logs for more information)"
           response.setHeader("status", status)
           votableModel = null
           throw e
       }
   }
}
