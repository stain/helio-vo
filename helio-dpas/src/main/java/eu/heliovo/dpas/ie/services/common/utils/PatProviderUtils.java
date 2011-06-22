package eu.heliovo.dpas.ie.services.common.utils;


import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.PatDetailsFoundException;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;


public class PatProviderUtils
{

	public static void query(CommonTO commonTO) throws PatDetailsFoundException {
		
		StarTable[] tables=new StarTable[1];
        try{
           ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
           ResultTO[] resTO=shortNameDao.getAccessTableDetails(QueryWhereClauseParser.generateWhereClause(commonTO));
           tables[0]=new PointsStarTable(resTO);
           tables[0].setName(commonTO.getHelioInstrument());
           commonTO.setStarTableArray(tables);
           commonTO.setQuerystatus("OK");
           System.out.println(" Generating Votable for PAT ");
	       shortNameDao.generatePatVOTable(commonTO);
           System.out.println(" DONE !!! ");
       }catch(Exception e){
        	e.printStackTrace();
        	throw new PatDetailsFoundException(e.getMessage());
        }

	}
      
}
