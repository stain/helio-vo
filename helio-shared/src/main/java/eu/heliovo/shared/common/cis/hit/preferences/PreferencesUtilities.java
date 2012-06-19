package eu.heliovo.shared.common.cis.hit.preferences;

import condor.classad.RecordExpr;
import eu.heliovo.shared.common.utilities.ClassAdUtilities;
import eu.heliovo.shared.common.utilities.ClassAdUtilitiesException;

public class PreferencesUtilities 
{
	ClassAdUtilities	cadUtilities	=	new ClassAdUtilities();

	public	String	getElementFor(RecordExpr cadPreferences,
			String service, 
			String element)
	{
		/*
		 * TODO : Add checks that the service and element do exist
		 */
		/*
		 * Get the defined service
		 */
		RecordExpr	cadService	=	null;
		try 
		{
			cadService	=	(RecordExpr) cadUtilities.evaluate(service, cadPreferences);
		} 
		catch (ClassAdUtilitiesException e) 
		{
			System.out.println("Problem in extracting " + service + " from :");
			System.out.println(cadUtilities.exprToReadeableString(cadPreferences));			
			e.printStackTrace();
		}
		/*
		 * Return the defined element
		 */
		try 
		{
			return  cadUtilities.evaluate(element, cadService).toString();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			System.out.println("Problem in extracting " + element + " from :");
			System.out.println(cadUtilities.exprToReadeableString(cadService));			
			e.printStackTrace();
			return "Error";
		}
	}
}
