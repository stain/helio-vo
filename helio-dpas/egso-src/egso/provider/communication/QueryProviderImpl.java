package org.egso.provider.communication;

import org.egso.common.services.network.Ping;
import org.egso.common.services.provider.CoSECProvider;
import org.egso.common.services.provider.QueryProvider;
import org.egso.common.services.provider.ResponseFileQueryProvider;
import org.egso.common.services.provider.ResponseQueryProvider;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.query.QueryEngine;


public class QueryProviderImpl implements QueryProvider, CoSECProvider, Ping {

	private QueryEngine queryEngine = null;
	private ResponseQueryProvider queryNotifier = null;
	private ResponseFileQueryProvider fileNotifier = null;
	private final static int TYPE_UNKNOWN = 0;
	private final static int TYPE_QUERY = 1;
	private final static int TYPE_FILES = 2;
	private final static int TYPE_SERVICE = 3;
	private final static int TYPE_NETWORK = 3;
	private int queryType = TYPE_UNKNOWN;


	public QueryProviderImpl() {
		System.out.println("\t-> new QueryProviderImpl [Synchronous Mode - BASIC COMMANDS ONLY].");
		queryType = TYPE_NETWORK;
	}

	public QueryProviderImpl(QueryEngine qe) {
		System.out.println("\t-> new QueryProviderImpl [Synchronous Mode]. CoSEC Service");
		queryType = TYPE_SERVICE;
		queryEngine = qe;
	}


	public QueryProviderImpl(QueryEngine qe, ResponseQueryProvider notifierClass) {
		System.out.println("\t-> new QueryProviderImpl [QUERY - Asynchronous Mode].");
		queryType = TYPE_QUERY;
		queryEngine = qe;
		queryNotifier = notifierClass;
	}


	public QueryProviderImpl(QueryEngine qe, ResponseFileQueryProvider notifierClass) {
		System.out.println("\t-> new QueryProviderImpl [FILES - Asynchronous Mode].");
		queryType = TYPE_FILES;
		queryEngine = qe;
		fileNotifier = notifierClass;
	}


	public String query(String context, String query)
		throws Exception {
		System.out.println("\t-> Getting a new QUERY.");
		if (queryType != TYPE_QUERY) {
			throw (new Exception ("The method query() is not available in Sync mode."));
		}
		return (queryEngine.query(context, query, queryNotifier));
	}


	public String fetchFiles(String context, String query)
		throws Exception {
		if (queryType != TYPE_FILES) {
			throw (new Exception("The method fetchFiles() is not available in Sync mode."));
		}
		System.out.println("\t-> Getting a new FILE QUERY.");
		return (queryEngine.fetchFiles(context, query, fileNotifier));
	}


	public String ping(String sender) {
		System.out.println("[ECI] PING Command from " + sender);
		return ((String) ProviderConfiguration.getInstance().getProperty("core.rolename"));
	}

	public String cosecPlotGoesXrays(String startDate, String endDate)
		throws Exception {
		System.out.println("\t-> Getting a new COSECPLOTGOESXRAYS.");
		if (queryType != TYPE_SERVICE) {
			throw (new Exception("The method cosecPlotGoesXrays() is not available in Async mode."));
		}
		return (queryEngine.cosecPlotGoesXrays(startDate, endDate));
	}

	public String cosecPlotGoesProtons(String startDate, String endDate)
		throws Exception {
		System.out.println("\t-> Getting a new COSECPLOTGOESPROTONS.");
		if (queryType != TYPE_SERVICE) {
			throw (new Exception("The method cosecPlotGoesProtons() is not available in Async mode."));
		}
		return (queryEngine.cosecPlotGoesProtons(startDate, endDate));
	}



}
