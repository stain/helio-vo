package eu.heliovo.dpas.ie.services.directory.provider;

import java.util.Calendar;
import java.util.List;

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.archives.ArchiveExplorer;
import eu.heliovo.dpas.ie.services.directory.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.services.directory.archives.NewPath;
import eu.heliovo.dpas.ie.services.directory.archives.NewPathFragment;
import eu.heliovo.dpas.ie.services.directory.archives.NOBEFileFragment;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;

public class NOBEProvider implements DirQueryDao
{
	/*
	 * Path Example:
	 * 
	 * ftp://solar.nro.nao.ac.jp/pub/norh/fits/daily/1996/05/ifa960501_024500
	 */
	ArchiveExplorer	explorer	=	null;

	public NOBEProvider()
	{
		super();
		initialize();
	}

	private void initialize()
	{
		/*
		 * Initializing the path for the archive explorer
		 */
		NewPath	path	=	new NewPath();
		
		String			rootString		=	"ftp://solar.nro.nao.ac.jp/pub/norh/fits/daily/";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";

		NewPathFragment	rootFragment	=	new GenericNewPathFragment(
				rootString,
				true,
				null,
				null);

		NewPathFragment	yearFragment	=	new GenericNewPathFragment(
				yearPattern,
				false,
				Calendar.YEAR,
				yearPattern);

		NewPathFragment	monthFragment	=	new GenericNewPathFragment(
				monthPattern,
				false,
				Calendar.MONTH,
				monthPattern);


		NewPathFragment	fileFragment	=	new NOBEFileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(fileFragment);	
				
		explorer	=	new ArchiveExplorer(path);	
	}

	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception
	{
		return explorer.query(dateFrom.getTime(), dateTo.getTime());
	}

	@Override
	public void generateVOTable(DirDataTO cdaWebTO)
			throws DataNotFoundException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
