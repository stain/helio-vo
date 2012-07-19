package eu.heliovo.shared.common;

import java.util.Random;

public class RandomUtils 
{
	static Random	rnd	=	new Random();
	
	public static	int	getRandomBetween(int min, int max)
	{
		return min + rnd.nextInt(max-min);
	}
}
