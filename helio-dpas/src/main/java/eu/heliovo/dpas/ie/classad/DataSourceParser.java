package eu.heliovo.dpas.ie.classad;

import condor.classad.Constant;
import condor.classad.Expr;
import condor.classad.RecordExpr;

/*
 * Data source contains the following fields:
 * 
 * InstrumentName (String) 	- The name of the instrument present in the data source
 * Priority (integer)		- The priority of the source. To be used when there are more than one source.
 * 
 */

public class DataSourceParser 
{	
	/*
	 * ClassAd Utilities
	 */
	ClassAdUtilities			cadUtils		=	new ClassAdUtilities();
	/*
	 * Creates a default, nameless data source with the following fields:
	 * 
	 * Instrument Name 	(String)		= Undefined
	 * Priority 		(Integer)		= Undefined
	 * 
	 */
	public 	RecordExpr	create()
	{
		RecordExpr	dataSource		=	new RecordExpr();

		dataSource					=	setInstrumentName(dataSource, Constant.Undef);
		dataSource					=	setPriority(dataSource, Constant.Undef);
		
		return dataSource;
	}
	/*
	 * Creates a data source given instrument name and priority with the following fields:
	 * 
	 * Instrument Name 	(String)		= Undefined
	 * Priority 		(Integer)		= Undefined
	 * 
	 */
	public 	RecordExpr	create(String instrumentName, int priority)
	{
		RecordExpr	dataSource		=	new RecordExpr();

		dataSource					=	setInstrumentName(dataSource, Constant.getInstance(instrumentName));
		dataSource					=	setPriority(dataSource, Constant.getInstance(priority));
		
		return dataSource;
	}
	/*
	 * Manipulators
	 */
	/*
	 * Instrument Name - String
	 */
	public	Expr	getInstrumentName(RecordExpr action)
	{
		return	action.lookup(DataSourceTags.InstrumentName);
	}
	public	String	getInstrumentNameAsString(RecordExpr action)
	{
		return	action.lookup(DataSourceTags.InstrumentName).stringValue();
	}
	public	RecordExpr	setInstrumentName(RecordExpr action, Expr desc)
	{
		return action.insertAttribute(DataSourceTags.InstrumentName, desc);
	}
	public	RecordExpr	setInstrumentName(RecordExpr action, String name)
	{
		return action.insertAttribute(DataSourceTags.InstrumentName, Constant.getInstance(name));
	}
	/*
	 * Instrument Name
	 */
	public	Expr	getPriority(RecordExpr action)
	{
		return	action.lookup(DataSourceTags.Priority);
	}
	public	int	getPriorityAsInt(RecordExpr action)
	{
		return	action.lookup(DataSourceTags.Priority).intValue();
	}
	public	RecordExpr	setPriority(RecordExpr action, Expr desc)
	{
		return action.insertAttribute(DataSourceTags.Priority, desc);
	}	
	public	RecordExpr	setPriority(RecordExpr action, int p)
	{
		return action.insertAttribute(DataSourceTags.Priority, Constant.getInstance(p));
	}	
}
