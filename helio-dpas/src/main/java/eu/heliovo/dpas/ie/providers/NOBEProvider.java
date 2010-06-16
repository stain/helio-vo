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

public class NOBEProvider implements DPASDataProvider
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
	public List<DPASResultItem> query(Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception
	{
		return explorer.query(dateFrom.getTime(), dateTo.getTime());
	}
}
