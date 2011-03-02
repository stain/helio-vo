package eu.heliovo.dpas.ie.services.directory.provider;

import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.archives.FtpArchiveExplorer;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;

public class FtpProvider implements DirQueryDao
{
	/*
	 * Path Example:
	 * 
	 * ftp://solar.nro.nao.ac.jp/pub/norh/fits/daily/1996/05/ifa960501_024500
	 */
	FtpArchiveExplorer	explorer	=	null;

	public FtpProvider(FtpDataTO ftpTO)
	{
		super();
		initialize(ftpTO);
	}

	private void initialize(FtpDataTO ftpTO)
	{
		explorer	=	new FtpArchiveExplorer(ftpTO);	
	}

	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom, Calendar dateTo,int maxResults) throws Exception
	{
		return explorer.query(dateFrom.getTime(), dateTo.getTime());
	}

	@Override
	public void generateVOTable(DirDataTO cdaWebTO) throws DataNotFoundException, Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		// TODO Auto-generated method stub
	}
}
