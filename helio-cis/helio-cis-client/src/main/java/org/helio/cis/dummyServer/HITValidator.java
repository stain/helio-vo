package org.helio.cis.dummyServer;

import org.helio.cis.hit.HelioIdentityToken;

public interface HITValidator 
{
	/**
	 * Checks if is valid.
	 *
	 * @param hit the hit
	 * @return true, if is valid
	 */
	boolean	isValid(HelioIdentityToken hit);
	
	/**
	 * Checks if is valid.
	 *
	 * @param hit the hit
	 * @param strongValidation the strong validation
	 * @return true, if is valid
	 */
	boolean isValid(HelioIdentityToken hit, boolean strongValidation);
}
