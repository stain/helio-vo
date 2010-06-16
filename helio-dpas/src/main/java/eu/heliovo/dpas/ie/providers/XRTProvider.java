package eu.heliovo.dpas.ie.providers;

import java.util.Calendar;
import java.util.List;

import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;
import eu.heliovo.dpas.ie.sensors.archives.ArchiveExplorer;
import eu.heliovo.dpas.ie.sensors.archives.GenericNewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.NewPath;
import eu.heliovo.dpas.ie.sensors.archives.NewPathFragment;
import eu.heliovo.dpas.ie.sensors.archives.XRTFileFragment;

public class XRTProvider implements DPASDataProvider
{
	/*
	 * Path Example:
	 * 
	 * http://www.sdc.uio.no/vol/fits/xrt/level0/2006/10/24/H1100/XRT20061024_112530.2.fits.gz
	 */
	ArchiveExplorer	explorer	=	null;

	public XRTProvider()
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
		
		String			rootString		=	"http://www.sdc.uio.no/vol/fits/xrt/level0/";
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
		
		NewPathFragment	timeFragment	=	new XRTTimeFragment();
		NewPathFragment	fileFragment	=	new XRTFileFragment();

		path.add(rootFragment);
		path.add(yearFragment);
		path.add(monthFragment);
		path.add(dayFragment);
		path.add(timeFragment);	
		path.add(fileFragment);	
				
		explorer	=	new ArchiveExplorer(path);	
	}

	@Override
	public List<DPASResultItem> query(Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception
	{
		return explorer.query(dateFrom.getTime(), dateTo.getTime());
	}
}
