package eu.heliovo.cis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.heliovo.shared.common.utilities.LogUtilities;

public class CisServiceDemo 
{
	CisService				cisService		=	new CisServiceImpl();
	LogUtilities			logUtilities	=	new LogUtilities();

	public static void main(String[] args) 
	{
		CisServiceDemo		demoCIS	=	new CisServiceDemo();
		demoCIS.runUI();
	}
		
	private void runUI() 
	{
		boolean terminate	=	false;
		
		while (!terminate) 
		{
			System.out.println();
			System.out.println(" * Please Select the action you want to perform : ");
			System.out.println("----------------------------------------------------------------------------");					
			System.out.println(" * [0] - Test if the CIS is running");
			System.out.println(" * [1] - Create a new user");
//			System.out.println(" * [2] - Select a job to run ");
//			System.out.println(" * [3] - Run a test application on the grid");
//			System.out.println(" * [4] - Run a test application on the computational cache");
//			System.out.println(" * [5] - Check execution status ");
//			System.out.println(" * [6] - Retrieve output of a completed application ");
			System.out.println(" * [X] - Exit ");
			System.out.println("----------------------------------------------------------------------------");					
			System.out.print(" * Please enter the corresponding key: ");

			String key = null;
			InputStreamReader 	reader 	= new InputStreamReader(System.in);
			BufferedReader 		in 		= new BufferedReader(reader);

			try 
			{
				key = in.readLine();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			System.out.println();

			if (key.equals("0") 
//					|| key.equals("1") 
//					|| key.equals("2")
//					|| key.equals("3") 
//					|| key.equals("4") 
//					|| key.equals("5") 
//					|| key.equals("6") 
					|| key.equals("X")) 
			{
				if (key.equals("0")) 
				{
					try 
					{
						performSimpleTest();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
//				if (key.equals("1")) 
//				{
//					try 
//					{
//						createUser();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
//				else if (key.equals("2")) 
//				{
//					try 
//					{
//						selectAndSubmitApplication();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//
//				} 				
//				if (key.equals("3")) 
//				{
//					try 
//					{
//						submitTestOnGrid();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
//				if (key.equals("4")) 
//				{
//					try 
//					{
//						submitTestOnMiniGrid();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
//				if (key.equals("5")) 
//				{
//					try 
//					{
//						checkExecutionStatus();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
//				if (key.equals("6")) 
//				{
//					try 
//					{
//						retrieveExecutionOutput();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
				else if (key.equals("X")) 
				{
					logUtilities.printShortLogEntry("Quitting...");
					terminate = true;
				}
			}
			else
				logUtilities.printShortLogEntry("Your selection ["+key+"] is not valid, please selec a new one...");
				
			logUtilities.printShortLogEntry("... done");
		}
	}

	private void performSimpleTest() 
	{
		logUtilities.printShortLogEntry("Invoking test on the CIS...");
		logUtilities.printShortLogEntry(cisService.test("test-param"));
		logUtilities.printShortLogEntry("...done");		
	}	
}
