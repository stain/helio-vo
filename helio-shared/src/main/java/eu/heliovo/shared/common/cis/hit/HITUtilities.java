package eu.heliovo.shared.common.cis.hit;

import eu.heliovo.shared.common.cis.hit.info.HITInfo;
import eu.heliovo.shared.common.cis.hit.info.HITInfoException;

public class HITUtilities 
{
	
	public	HIT			buildHITFromPayload(HITPayload hitPayload) throws HITUtilitiesException
	{
		HIT 	result	=	new HIT();
		HITInfo	hitInfo	=	new HITInfo();
		try 
		{
			hitInfo.buildInfoFromString(hitPayload.information);
			result.setHitInfo(hitInfo);
		} 
		catch (HITInfoException e) 
		{
			e.printStackTrace();
			throw new HITUtilitiesException();
		}
		
		return result;
	}
	public	HITPayload	buildPayloadFromHIt(HIT hit)
	{
		return new HITPayload();
	}
}
