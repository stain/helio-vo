package org.helio.cis.dummyServer;

import org.helio.cis.hit.HelioIdentityToken;

public interface HITGenerator 
{
	HelioIdentityToken	generate(boolean strongSecurity) throws Exception;
	HelioIdentityToken	generate(String userName, boolean strongSecurity) throws Exception;	
}