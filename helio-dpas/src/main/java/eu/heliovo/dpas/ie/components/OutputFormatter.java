package eu.heliovo.dpas.ie.components;

import java.util.HashMap;

public class OutputFormatter
{
	public	String	format(Object genericResult)
	{
		/*
		 * The result is a hash map - the query was globally sorted
		 */
		if(genericResult.getClass().equals(HashMap.class))
		{
			System.out.println("Result of the right format");
			

		}
		return "Work in Progress...";
	}
}
