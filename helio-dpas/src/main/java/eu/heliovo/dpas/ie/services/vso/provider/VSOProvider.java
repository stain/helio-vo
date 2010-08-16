package eu.heliovo.dpas.ie.services.vso.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import eu.heliovo.dpas.ie.providers.DPASDataProviderException;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.DataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderGetDataResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequestBlock;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.Time;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOGetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiBindingStub;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiServiceLocator;

import eu.heliovo.dpas.ie.common.DebugUtilities;
import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

public class VSOProvider implements DPASDataProvider
{

	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
