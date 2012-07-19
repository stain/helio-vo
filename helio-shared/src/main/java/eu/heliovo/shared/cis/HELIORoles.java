package eu.heliovo.shared.cis;

import java.util.HashSet;

public class HELIORoles 
{
	/*
	 * The 3 main roles of HELIO
	 */
	public 	static 	final	String simpleUser		=	"simpleUser";
	public 	static 	final	String gridUser			=	"gridUser";
	public 	static 	final	String administrator	=	"administrator";

	private	static	HashSet<String>	roles			=	new HashSet<String>()
	{
		private static final long serialVersionUID = 1L;
		{
			add(simpleUser);
			add(administrator);
			add(gridUser);
		}
	};
	
	public static	boolean	isValidRole(String role)
	{
		return 	roles.contains(role);
	}
}
