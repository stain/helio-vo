package eu.heliovo.shared.cis;

import java.util.HashSet;

public class HELIORoles 
{
	public 	static 	final	String simpleUser		=	"simpleUser";
	public 	static 	final	String administrator	=	"administrator";

	private	static	HashSet<String>	roles			=	new HashSet<String>()
			{{
				add(simpleUser);
				add(administrator);
			}};
	
	public static	boolean	isValidRole(String role)
	{
		return 	roles.contains(role);
	}
}
