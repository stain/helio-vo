package eu.heliovo.dpas.ie.services.cdaweb.provider;

import java.net.URL;
import java.util.Calendar;
import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.DatasetDescription;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.FileDescription;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.ViewDescription;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import eu.heliovo.dpas.ie.services.cdaweb.utils.CdaWebUtils;
import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CDASWSLocator;
import eu.heliovo.dpas.ie.services.cdaweb.utils.PointsStarTable;
import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;

public class CDAWEBProvider
{

	public	void query(CdaWebDataTO cdaWebTO) throws DataNotFoundException {
		
		StarTable[] tables=new StarTable[1];
		CoordinatedDataAnalysisSystemBindingStub binding;
		System.out.println(" Executing CDAWEB provider ");
        try{
        	binding = (CoordinatedDataAnalysisSystemBindingStub)
            new CDASWSLocator().getCoordinatedDataAnalysisSystemPort();
        	//Instrument
        	String []instr = new String[1];
        	instr[0]=cdaWebTO.getInstrument();
        	//Mission name
        	String []missionName = new String[1];
        	missionName[0]=cdaWebTO.getMissionName();
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
            //
            FileDescription[] fileDesc=new FileDescription[0] ;
            for(int i = 0;i < views.length;i++) {
                if(views[i].isPublicAccess() && views[i].getId().equals("sp_phys")) {
                       binding = (CoordinatedDataAnalysisSystemBindingStub)new CDASWSLocator().getCoordinatedDataAnalysisSystemPort(new URL(views[i].getEndpointAddress()));
                       System.out.println(" Getting DatasetDescription for CDAWEB ");
                       DatasetDescription[] dsd = binding.getDatasetsByInstrument(missionName,instr);
                       //DatasetDescription[] dsd1 = binding.getDatasets(missionName, new String[1]);
                       //noDuplMap.clear();
                       //tables=new StarTable[dsd1.length];
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
                          System.out.println(" Getting FileDescription for CDAWEB ");
                          FileDescription[] fds = binding.getDataFiles(dsd[k].getId(),startTemp,endTemp);
                          fileDesc=CdaWebUtils.addArrays(fileDesc, fds);
                          System.out.println(" Adding FileDescription ");
                          System.out.println("....DONE !!!");
                       }//for
                       tables[0]=new PointsStarTable(fileDesc,cdaWebTO.getInstrument());
                       cdaWebTO.setStarTableArray(tables);
                       cdaWebTO.setQuerystatus("OK");
                       System.out.println(" Generating Votable for CDAWEB ");
       		           //Call to CDAWEB dao
                       CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao) DAOFactory.getDAOFactory(cdaWebTO.getWhichProvider());
                       cdaWebQueryDao.generateVOTable(cdaWebTO);
                       System.out.println(" DONE !!! ");
                }
            }
        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
      
}
