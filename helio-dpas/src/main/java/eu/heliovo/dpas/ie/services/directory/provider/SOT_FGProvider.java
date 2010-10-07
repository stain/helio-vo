package eu.heliovo.dpas.ie.services.directory.provider;

import java.util.Calendar;
import java.util.List;

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.archives.ArchiveExplorer;
import eu.heliovo.dpas.ie.services.directory.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.services.directory.archives.NewPath;
import eu.heliovo.dpas.ie.services.directory.archives.NewPathFragment;
import eu.heliovo.dpas.ie.services.directory.archives.SOT_SPFileFragment;
import eu.heliovo.dpas.ie.services.directory.archives.XRTTimeFragment;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;

public class SOT_FGProvider implements DirQueryDao
{
	/*
	 * Path Example:
	 * 
	 * http://www.sdc.uio.no/vol/fits/sot/level0/2010/06/09/FGIV/H0700/FGIV20100609_075702.0.fits.gz
	 * 
	 */
	ArchiveExplorer	explorer	=	null;

	public SOT_FGProvider()
	{
		super();
		initialize();
	}

	private void initialize()
	{
		/*
		 * Initialiazing the path for the archive explorer
		 */
		NewPath	path	=	new NewPath();
		
		String			rootString		=	"http://www.sdc.uio.no/vol/fits/sot/level0/";
		String			yearPattern		=	"yyyy";
		String			monthPattern	=	"MM";
		String			dayPattern		=	"dd";

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

		NewPathFragment	dayFragment	=	new GenericNewPathFragment(
				dayPattern,
				false,
				Calendar.DATE,
				dayPattern);

		NewPathFragment	instNameFragment	=	new GenericNewPathFragment(
				"FGIV",
				true,
				null,
				null);

		NewPathFragment	timeFragment	=	new XRTTimeFragment();
		NewPathFragment	fileFragment	=	new SOT_SPFileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(instNameFragment);
		path.add(timeFragment);	
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
