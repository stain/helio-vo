package eu.heliovo.dpas.ie.components;

import eu.heliovo.dpas.ie.internalData.DPASRequest;
import eu.heliovo.dpas.ie.internalData.Identity;

public class DummyIdentityEngine
{
	public	Identity	createDummyIdentity()
	{
		return new Identity();
	}
	
	public boolean checkAuthorization(DPASRequest request)
	{
		return true;
	}
}
