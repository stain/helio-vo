package eu.heliovo.dpas.ie.providers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import condor.classad.RecordExpr;

import eu.heliovo.dpas.ie.classad.ClassAdMapper;
import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilities;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;
import eu.heliovo.dpas.ie.classad.DataSourceKeyParser;
import eu.heliovo.dpas.ie.classad.DataSourceParser;
import eu.heliovo.dpas.ie.dataProviders.EIS;
import eu.heliovo.dpas.ie.dataProviders.NewHessi;
import eu.heliovo.dpas.ie.dataProviders.NewPhoenix2;

/**
 * The Class DataProviderManager.
 * 
 * @author Gabriele Pierantoni - pierang@cs.tcd.ie
 */
public class DataProviderManager
{
	ClassAdMapper		map			=	new ClassAdMapper();
	DataSourceParser	dsParser	=	new DataSourceParser();
	DataSourceKeyParser	dskParser	=	new DataSourceKeyParser();
	ClassAdUtilities	cadUtils	=	new ClassAdUtilities();
	
	public DataProviderManager() throws ArithmeticException, ClassAdMapperException, ClassAdUtilitiesException 
	{
		initialize();
	}

	private void initialize() throws ArithmeticException, ClassAdMapperException, ClassAdUtilitiesException 
	{
		addProvider("PHOENIX__2", 1, new Phoenix2Provider());
		addProvider("RHESSI__HESSI_GMR", 1, new RhessiProvider());
		addProvider("RHESSI__HESSI_HXR", 1, new RhessiProvider());
		addProvider("HINODE__EIS", 1, new EIS());
	}

	public	void	addProvider(String instrumentName, int priority, Object provider) throws ArithmeticException, ClassAdMapperException, ClassAdUtilitiesException
	{
		/*
		 * First create the recorExpr for the name and priority
		 */
		RecordExpr	key	=	dsParser.create(instrumentName, priority);
//		System.out.println(cadUtils.exprToReadeableString(key));
		/*
		 * Add the provider to the ClassAd map with the created key
		 */
		map.add(cadUtils.expr2String(key), provider);
//		System.out.println(map.toString());
	}
	
	public Object getBest(String instrumentName) throws DataProviderManagerException
	{
		/*
		 * Create the request key
		 */
		RecordExpr	key	=	null;
		try 
		{
			key	=	dskParser.create(instrumentName);
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
		try 
		{
			return map.getBest(cadUtils.expr2String(key));
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
		catch (ClassAdMapperException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
	}

	/*
	 * TODO : Return all data providers
	 */
	public List getAll(String instrumentName) throws DataProviderManagerException
	{
		HashMap			allSources	=	null;
		List			sources		=	new LinkedList();
		/*
		 * Create the request key
		 */
		RecordExpr	key	=	null;
		try 
		{
			key	=	dskParser.create(instrumentName);
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
		try 
		{
			allSources	=	map.getAllSorted(cadUtils.expr2String(key));
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
		catch (ClassAdMapperException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			throw new DataProviderManagerException();
		}
		/*
		 * Now create the list with all the sources
		 */
//		System.out.println("All Sources : " + allSources);
		/*
		 * Sort out from highest to lowest priority
		 */
		Set		keys	=	allSources.keySet();
//		System.out.println("All Keys : " + keys);
		List	sortedKeys	=	new LinkedList();
		sortedKeys.addAll(keys);
//		System.out.println("Key List : " + sortedKeys);
		
		Collections.sort(sortedKeys);
//		System.out.println("Sorted Key List : " + sortedKeys);
		Collections.reverse(sortedKeys);
//		System.out.println("Sorted Key List : " + sortedKeys);
		/*
		 * Now create the list
		 */
		Iterator	i	=	sortedKeys.iterator();
		while(i.hasNext())
		{
			sources.add(allSources.get(i.next()));
		}
//		System.out.println("Sorted Source List : " + sources);
		return sources;
	}

	public boolean isPresent(String currentInstrument) 
	{
		try 
		{
			return	(getAll(currentInstrument).size() != 0);
		} 
		catch (DataProviderManagerException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
