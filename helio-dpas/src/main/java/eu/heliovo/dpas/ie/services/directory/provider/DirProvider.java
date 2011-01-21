package eu.heliovo.dpas.ie.services.directory.provider;

import java.util.List;
import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.service.org.ws.cdaw.CoordinatedDataAnalysisSystemBindingStub;
import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DirInsAnlyFactory;
import eu.heliovo.dpas.ie.services.directory.utils.DpasUtilities;
import eu.heliovo.dpas.ie.services.directory.utils.PointsStarTable;


public class DirProvider
{
	private DpasUtilities dpasUtils = new DpasUtilities();

	public	void query(DirDataTO dirTO) throws DataNotFoundException {
		System.out.println(" : Directory structure : ");
		CoordinatedDataAnalysisSystemBindingStub binding;
		List<DPASResultItem> 		results 		= null;
		StarTable[] tables=null;
		int count=0;
        try{
        	DirQueryDao dpasDataProvider=(DirQueryDao) DirInsAnlyFactory.getDirProvider(dirTO);
        	results=dpasDataProvider.query(dirTO.getInstrument(), dpasUtils.HELIOTimeToCalendar(dirTO.getDateFrom()), dpasUtils.HELIOTimeToCalendar(dirTO.getDateTo()), 2);
        	//
        	if(results!=null && results.size()>0){
        		tables=new StarTable[1];
        		tables[count]=new PointsStarTable(results,dirTO.getInstrument(),dirTO.getDateTo());
        		tables[count].setName(dirTO.getInstrument());
        	}
        	
        	dirTO.setStarTableArray(tables);
        	dirTO.setQuerystatus("OK");
        	DirQueryDao dirQueryDao=(DirQueryDao)DAOFactory.getDAOFactory(dirTO.getWhichProvider());
        	dirQueryDao.generateVOTable(dirTO); 
        	System.out.println(" Size of the result : "+results.size());
        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
      
}
