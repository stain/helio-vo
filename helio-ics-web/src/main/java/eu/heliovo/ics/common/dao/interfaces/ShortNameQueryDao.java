/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.interfaces;

import java.util.HashMap;

import eu.heliovo.ics.common.dao.exception.DataNotFoundException;
import eu.heliovo.ics.common.dao.exception.ShortNameQueryException;
import eu.heliovo.ics.common.transfer.CommonResultTO;



public interface ShortNameQueryDao {
	//public String getShortNameQuery(String sKey)throws  DataNotFoundException;
	//public CommonResultTO getSNQueryResult(String sSql, HashMap<String,String> hmArgs) throws ShortNameQueryException;
	public CommonResultTO getSNQueryResult(String sSql,HashMap<String,String> hmArgs,int startRow,int noOfRecords) throws ShortNameQueryException;
}
