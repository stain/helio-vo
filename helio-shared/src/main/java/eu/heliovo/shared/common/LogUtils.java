package eu.heliovo.shared.common;


public class LogUtils
{

//	TimeUtilities		tUtils	=	new TimeUtilities();
//
//	public	String	createExceptionEntry(Class<?> c, String m)
//	{
//		return	" * EXCEPTION in class " + c.getName() + 
//			" at " + tUtils.getMiniStamp() + " : " + m;
//	}
//
//	public	void	printExceptionEntry(Class<?> c, String m)
//	{
//		System.out.println("[ " + tUtils.getMiniStamp() + " ] ============================= ");
//		System.out.println(" * EXCEPTION in class " + c.getName());
//		System.out.println(m);
//		System.out.println("[ " + tUtils.getMiniStamp() + " ] ============================= ");
//	}
//
//	public	String	createLogEntry(String m)
//	{
//		return " - " + tUtils.getMiniStamp() + " : " + m;
//	}
//
	static public	void	printLongLogEntry(String m)
	{
		System.out.println("[ " + TimeUtils.getMiniStamp() + " ] ------------------ ");
		System.out.println(m);
		System.out.println("[ " + TimeUtils.getMiniStamp() + " ] ------------------ ");
	}

	static public void printShortLogEntry(String arg) 
	{
		System.out.println("[ " + TimeUtils.getMiniStamp() + " ] " + arg);		
	}
	
	static public void printShortLogEntry(Class<?> c, String arg) 
	{
		System.out.println("[ " + TimeUtils.getMiniStamp() + " ][ " 
				+ c.getSimpleName() + " ] : "+ arg);		
	}

}