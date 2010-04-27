package eu.heliovo.dpas.ie.common;

import java.util.Random;

public class RandomUtilities 
{
	public static long createRandomLong(long start, long end) 
	{
		Random randomGenerator = new Random();
		long range = end - start + 1;
		long fraction = (long) (range * randomGenerator.nextDouble());
		return fraction + start;
	}

	public static int createRandomInt(int start, int end) 
	{
		Random randomGenerator = new Random();
		int range = end - start + 1;
		int fraction = (int)(range * randomGenerator.nextDouble());
		return fraction + start;
	}
}
