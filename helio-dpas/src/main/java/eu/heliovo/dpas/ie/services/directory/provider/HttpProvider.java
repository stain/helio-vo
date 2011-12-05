package eu.heliovo.dpas.ie.services.directory.provider;

import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.archives.HttpArchiveExplorer;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.transfer.HttpDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;

public class HttpProvider implements DirQueryDao
{
	
	HttpArchiveExplorer	explorer	=	null;

	public HttpProvider(HttpDataTO httpTO)
	{
		super();
		initialize(httpTO);
	}

	private void initialize(HttpDataTO httpTO)
	{
		explorer	=	new HttpArchiveExplorer(httpTO);	
	}

	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom, Calendar dateTo,int maxResults) throws Exception
	{
		/*
		 * KMB-CHECK
		 * IF(instrument.equals("LYRA")) {
		 *   dateFrom.setHour(0);
		 * }
		 */
		return explorer.query(dateFrom.getTime(), dateTo.getTime());
	}

	@Override
	public void generateVOTable(DirDataTO dirWebTO)
			throws DataNotFoundException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
