package eu.heliovo.dpas.ie.services.cdaweb.provider;


import java.net.URL;
import java.util.Calendar;

import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.DatasetDescription;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.ViewDescription;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import eu.heliovo.dpas.ie.services.cdaweb.utils.CdaWebUtils;
import uk.ac.starlink.table.StarTable;



public class CDAWEBProvider
{

	public	void query(CdaWebDataTO cdaWebTO) throws DataNotFoundException {
		
		StarTable[] tables=null;
		eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub binding;
        try{
        	binding = (eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub)
            new eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CDASWSLocator().getCoordinatedDataAnalysisSystemPort();
        	String []instr = new String[1];
        	instr[0]=cdaWebTO.getInstrument();
        	ViewDescription[] value = null;        
            ViewDescription[] views = binding.getAllViewDescriptions();
            //Start Date
            Calendar startTime=CdaWebUtils.getDateFormat(cdaWebTO.getDateFrom());
            //End date
            Calendar endTime=CdaWebUtils.getDateFormat(cdaWebTO.getDateTo());
            //Temp start time.
            Calendar startTemp = Calendar.getInstance();
            //Temp end time
            Calendar endTemp = Calendar.getInstance();
            
            for(int i = 0;i < views.length;i++) {
                if(views[i].isPublicAccess() && views[i].getId().equals("sp_phys")) {
                       binding = (eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub)
                                     new eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CDASWSLocator().getCoordinatedDataAnalysisSystemPort(new URL(views[i].getEndpointAddress()));
                       
                       DatasetDescription[] dsd = binding.getDatasetsByInstrument(instr, new String[0]);
                       //noDuplMap.clear();
                       for(int k = 0;k < dsd.length;k++) {
                          if(startTime.before(dsd[k].getStartTime())) {
                              System.out.println("WARNING: Your given start time is before the dataset start time; using dataset start time");
                              startTemp = dsd[k].getStartTime();
                          }else {
                              startTemp = startTime;
                          }
                          if(endTime.after(dsd[k].getEndTime())) {
                              System.out.println("WARNING: Your given end time is after the datasets end time; using the dataset end time");
                              endTemp = dsd[k].getEndTime();
                          }else {
                              endTemp = endTime;
                          }
                          FileDescription[] fds = binding.getDataFiles(dsd[k].getId(),startTemp,endTemp);
                       }//for
                }
            }
        }catch(Exception e){
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
      
}
