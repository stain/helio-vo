package eu.heliovo.idlclient.model;

public class IdlClass {
	
	private Class<?> mClass;
	
	public IdlClass(Class<?> mClass)
	{
		this.mClass = mClass;
	}
	
	public String getName()
	{
		return mClass.getName();
	}

}
