package eu.heliovo.cis.service;

public class SimpleLoginData 
{
	String	name	=	null;
	String	pwd		=	null;
	
	public SimpleLoginData(String name, String pwd) 
	{
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}

