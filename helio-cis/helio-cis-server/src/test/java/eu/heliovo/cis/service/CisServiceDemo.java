package eu.heliovo.cis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.heliovo.shared.common.utilities.LogUtilities;

public class CisServiceDemo 
{
	CisService			cisService		=	new CisServiceImpl();
	LogUtilities		logUtilities	=	new LogUtilities();

	public static void main(String[] args) 
	{
		CisServiceDemo		demo	=	new CisServiceDemo();
		demo.runUI();
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
			System.out.println(" * [2] - Remove and existing user");
			System.out.println(" * [3] - Validate a user");
			System.out.println(" * [4] - Authenticate a user - NOT ACTIVE NO SERVER SIDE ");
			System.out.println(" * [5] - Change the password for a user");
			System.out.println(" * [6] - Get preferences for a user");
			System.out.println(" * [7] - Set preferences for a user");
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
					|| key.equals("1") 
					|| key.equals("2")
					|| key.equals("3") 
					|| key.equals("5") 
					|| key.equals("6") 
					|| key.equals("7") 
					|| key.equals("X")) 
			{
				if (key.equals("0")) 
				{
					try 
					{
						simpleTest();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
				if (key.equals("1")) 
				{
					try 
					{
						createUser();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
				else if (key.equals("2")) 
				{
					try 
					{
						removeUser();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					

				} 				
				if (key.equals("3")) 
				{
					try 
					{
						validateUser();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
//				if (key.equals("4")) 
//				{
//					try 
//					{
//						authenticateUser();
//					} 
//					catch (Exception e) 
//					{
//						e.printStackTrace();
//					}					
//				} 
				if (key.equals("5")) 
				{
					try 
					{
						changePassword();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
				if (key.equals("6")) 
				{
					try 
					{
						getPreferences();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
				if (key.equals("7")) 
				{
					try 
					{
						setPreferences();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}					
				} 
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

	private void changePassword() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to change the password of : ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(!cisService.isUserPresent(userName))
			{
				System.out.println(" * " + userName + " is not present, Please select another one");
				System.out.println();
			}
			else
			{
				System.out.println();
				System.out.print(" * Please enter the password for " + userName + " : ");
				String userPwd = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);

				try 
				{
					userPwd = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				if(cisService.validateUser(userName, userPwd))
				{
					System.out.println();
					System.out.print(" * Please enter the new password for " + userName + " : ");
					String newUserPwd = null;
					reader 	= 	new InputStreamReader(System.in);
					in 		= 	new BufferedReader(reader);

					try 
					{
						newUserPwd = in.readLine();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					cisService.changePwdForUser(userName, userPwd, newUserPwd);
				}
				else
				{
					System.out.println(" * password is not valid ! ");
				}
				System.out.println();
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}		
	}

//	private void authenticateUser() 
//	{
//		System.out.println();
//		System.out.print(" * Please enter the name of the user you want to authenticate : ");
//		InputStreamReader 	reader 	= new InputStreamReader(System.in);
//		BufferedReader 		in 		= new BufferedReader(reader);
//		String userName	=	null;
//		
//		try 
//		{
//			userName = in.readLine();
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
//		System.out.println();
//
//		try 
//		{
//			if(!cisApi.isUserPresent(userName))
//			{
//				System.out.println(" * " + userName + " is not present, Please select another one");
//				System.out.println();
//			}
//			else
//			{
//				System.out.println();
//				System.out.print(" * Please enter the password for " + userName + " : ");
//				String userPwd = null;
//				reader 	= 	new InputStreamReader(System.in);
//				in 		= 	new BufferedReader(reader);
//
//				try 
//				{
//					userPwd = in.readLine();
//				} 
//				catch (IOException e) 
//				{
//					e.printStackTrace();
//				}
//				System.out.println();
//				/*
//				 * Add here double check
//				 */
//				OldCisAuthenticationToken	authToken	=	cisApi.authenticateUser(userName, userPwd);
//				System.out.println(" * " + userName + " is authenticated by CIS");
////				if(authToken.isAuthenticated())
////					System.out.println(" * " + userName + " is authenticated by CIS");
////				else
////					System.out.println(" * " + userName + " is NOT authenticated by CIS");
//			}
//		} 
//		catch (CisApiException e) 
//		{
////			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
//			System.out.println(" * " + userName + " is NOT authenticated by CIS");
//		}			
//	}

	private void simpleTest() 
	{
		if(cisService.test("test") != null)
			logUtilities.printShortLogEntry("CIS Service is running...");
		else
			logUtilities.printShortLogEntry("CIS Service is NOT running...");
	}

	private void createUser() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to create : ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(cisService.isUserPresent(userName))
			{
				System.out.println(" * " + userName + " is already present, Please select another name");
				System.out.println();
			}
			else
			{
				System.out.println();
				System.out.print(" * Please enter the password for user : ");
				String userPwd = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);

				try 
				{
					userPwd = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				/*
				 * Add here double check
				 */
				cisService.addUser(userName, userPwd);
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}		
	}

	private void removeUser() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to remove: ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(!cisService.isUserPresent(userName))
				System.out.print(" * " + userName + " is NOT present, Please select another name");
			else
			{
				System.out.println();
				System.out.print(" * Please enter the password for user : ");
				String userPwd = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);

				try 
				{
					userPwd = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				/*
				 * Add here double check
				 */
				cisService.removeUser(userName, userPwd);
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}				
	}

	private void validateUser() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to validate : ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(!cisService.isUserPresent(userName))
			{
				System.out.println(" * " + userName + " is not present, Please select another one");
				System.out.println();
			}
			else
			{
				System.out.println();
				System.out.print(" * Please enter the password for " + userName + " : ");
				String userPwd = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);

				try 
				{
					userPwd = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				if(cisService.validateUser(userName, userPwd))
					System.out.println(" * " + userName + " is a valid user in the CIS");
				else
					System.out.println(" * " + userName + " is NOT a valid user in the CIS");
				System.out.println();
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}		
	}

	private void getPreferences() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to see the preferences of : ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(!cisService.isUserPresent(userName))
				System.out.print(" * " + userName + " is NOT present, Please select another user");
			else
			{
				System.out.println();
				System.out.print(" * Please enter the service : ");
				String service = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);
				try 
				{
					service = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				System.out.println();
				System.out.print(" * Please enter the field : ");
				String field = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);
				try 
				{
					field = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				String	value	=	cisService.getPreferenceForUser(userName, service, field);
				logUtilities.printShortLogEntry(" * Value for " + userName + " of " + service + " of " + field + " is " + value);		
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}					
	}

	private void setPreferences() 
	{
		System.out.println();
		System.out.print(" * Please enter the name of the user you want to set the preferences of : ");
		InputStreamReader 	reader 	= new InputStreamReader(System.in);
		BufferedReader 		in 		= new BufferedReader(reader);
		String userName	=	null;
		
		try 
		{
			userName = in.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println();

		try 
		{
			if(!cisService.isUserPresent(userName))
				System.out.print(" * " + userName + " is NOT present, Please select another user");
			else
			{
				System.out.println();
				System.out.print(" * Please enter the password for user : ");
				String userPwd = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);

				try 
				{
					userPwd = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}								
				System.out.println();
				System.out.print(" * Please enter the service : ");
				String service = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);
				try 
				{
					service = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				System.out.println();
				System.out.print(" * Please enter the field : ");
				String field = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);
				try 
				{
					field = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				System.out.println();
				System.out.print(" * Please enter the value : ");
				String value = null;
				reader 	= 	new InputStreamReader(System.in);
				in 		= 	new BufferedReader(reader);
				try 
				{
					value = in.readLine();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				System.out.println();
				cisService.setPreferenceForUser(userName, userPwd, service, field, value);
			}
		} 
		catch (CisServiceException e) 
		{
			System.out.print(" * CANNOT check if " + userName + " is already present, Please contact the CIS administrator");
		}							
	}		
}
