package eu.heliovo.dpas.ie.classad;

import condor.classad.RecordExpr;

/*
 * Data source contains the following fields:
 * 
 * InstrumentName (String) 	- The name of the instrument present in the data source
 * Priority (integer)		- The priority of the source. To be used when there are more than one source.
 * 
 */

public class DataSourceKeyParser 
{	
	/*
	 * ClassAd Utilities
	 */
	ClassAdUtilities			cadUtils		=	new ClassAdUtilities();
	/*
	 * Creates a key given the instrument name and priority with the following fields:
	 * 
	 * Instrument Name 	(String)		= Undefined
	 * Priority 		(Integer)		= Undefined
	 * 
	 */
	public 	RecordExpr	create(String instrumentName) throws ClassAdUtilitiesException
	{
		String	sKey	=	"[" +
				"Requirements = " +
				"other." +
				DataSourceTags.InstrumentName + 
				" == " + 
				"\"" + instrumentName + "\";" +
				"Rank = " +
				"other." +
				DataSourceTags.Priority + ";" +
				"];";
		
		return cadUtils.string2CompleteRecordExpr(sKey);
	}
}
