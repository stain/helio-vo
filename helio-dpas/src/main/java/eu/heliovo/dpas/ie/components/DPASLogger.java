package eu.heliovo.dpas.ie.components;

import java.util.Date;
import java.util.HashMap;

import eu.heliovo.dpas.ie.internalData.DPASRequest;

public class DPASLogger
{
	HashMap<Date, DPASRequest>	log	=	new HashMap<Date, DPASRequest>();

	public	void	addRequest(DPASRequest	r)
	{
		log.put(new Date(), r);
	}
}
