package eu.heliovo.shared.common.utilities;

import java.util.Vector;


public class LogUtilities
{
	TimeUtilities		tUtils	=	new TimeUtilities();

	public	String	createExceptionEntry(Class c, String m)
	{
		return	" * EXCEPTION in class " + c.getName() + 
			" at " + tUtils.getMiniStamp() + " : " + m;
	}

	public	void	printExceptionEntry(Class c, String m)
	{
		System.out.println("[ " + tUtils.getMiniStamp() + " ] ============================= ");
		System.out.println(" * EXCEPTION in class " + c.getName());
		System.out.println(m);
		System.out.println("[ " + tUtils.getMiniStamp() + " ] ============================= ");
	}

	public	String	createLogEntry(String m)
	{
		return " - " + tUtils.getMiniStamp() + " : " + m;
	}

	public	void	printLongLogEntry(String m)
	{
		System.out.println("[ " + tUtils.getMiniStamp() + " ] ------------------ ");
		System.out.println(m);
		System.out.println("[ " + tUtils.getMiniStamp() + " ] ------------------ ");
	}

	public void printShortLogEntry(String arg) 
	{
		System.out.println("[ " + tUtils.getMiniStamp() + " ] " + arg);		
	}
}