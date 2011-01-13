package ch.i4ds.helio
import ch.ResultVT
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;
import ch.PostXML
import ch.EditFile


class SingleServiceController {

    def TableInfoService;
    def DataQueryService;
    
    def index = {
      redirect(action: "querySubmit")



    }

    def querySubmit ={
        println params;

        //@todo add this to the bootstrap as a resource
        Hashtable hecHash = TableInfoService.serviceMethod("files/tableshec.xml");
        Hashtable icsHash = TableInfoService.serviceMethod("files/tablesics.xml");
        Hashtable ilsHash = TableInfoService.serviceMethod("files/tablesils.xml");
        Hashtable dpasHash = TableInfoService.getDpasHash();

        if(params.maxDate == null){
        [hecHash:hecHash,icsHash:icsHash,ilsHash:ilsHash,dpasHash:dpasHash]
        }
         else if(params.serviceGroup== null){
            flash.message = "Please select a service before proceding"
            [hecHash:hecHash,icsHash:icsHash,ilsHash:ilsHash,dpasHash:dpasHash]
            
        }else  if(params.tableSelection== null){
            flash.message = "Please select a table before proceding"
            [hecHash:hecHash,icsHash:icsHash,ilsHash:ilsHash,dpasHash:dpasHash]
            
        }else{

        Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
        Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
        String extra = params.tableSelection;
        String where = params.where;



        LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
        LinkedList<String> minDateList= new LinkedList<String>();
        LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp



    if(params.startDate != null&&params.endDate != null){
    
            
            
             if(params.startDate instanceof String){

                 minDateList.add(params.startDate);
             }
             else{
               params.startDate.each{minDateList.add(it)};
             }

            if(params.endDate instanceof String){
                maxDateList.add(params.endDate);
            }
            else{
                params.endDate.each{maxDateList.add(it)}
            }
            
                
            
    }else{
        maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
    }


    if(params.tableSelection != null){


             if(params.tableSelection instanceof String)extraList.add(params.tableSelection);
             else params.tableSelection.each{extraList.add(it)};




    }else{
        extraList.add(extra);
    }
        
        

        // Gets the address string for the selected service, however doing it dinamically might have some performance impact.
        String addressPort = PortDirectory.class.getField(params.serviceGroup).get(String);

        println addressPort
        println maxDate.format("yyyy-MM-dd'T'HH:mm:ss")
        println minDate.format("yyyy-MM-dd'T'HH:mm:ss")


        ResultVT result = DataQueryService.queryService2(minDateList,maxDateList,extraList,addressPort,where);
        println result;

    

        //flash.message = "Search Successful with parameters "+query.getMinDate()+" and "+query.getMaxDate()+" for "+result.getStack().size()+" results.";



        
        //flash.message = "Search Successful with parameters "+query.getMinDate()+" and "+query.getMaxDate()+" for "+result.getStack().size()+" results.";

      
        
        
        [result:result,hecHash:hecHash,icsHash:icsHash,ilsHash:ilsHash,dpasHash:dpasHash,minDate:params.minDate,maxDate:params.maxDate]
        }
    }

}
