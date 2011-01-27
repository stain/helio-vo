package eu.heliovo.registryclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class CachedRegistryClient extends RegistryClient {
	private static final String KEYWORD_HFC = "helio_hfc";
	private static final String KEYWORD_HEC = "hec";
	private static final String KEYWORD_DPAS = "dpas";
	private static final String KEYWORD_ILS = "ils";
	private static final String KEYWORD_ICS = "ics";
	private static final String KEYWORD_CEA = "cea";

	// Caches
	private HashMap<String, URI> cache = new HashMap<String, URI>();

	private URI lookupOrCache(String keyword) throws LookupFailedException {
		try {
			log.info("doing direct lookup of \"" + keyword + "\"");
			URI uri = getResource(keyword);
			cache.put(keyword, uri);
			return uri;
		} catch (LookupFailedException e) {
			log.fine("direct lookup failed; trying cached lookup of \"" + keyword + "\"");
			if (cache.containsKey(keyword))
				return cache.get(keyword);
			throw e;
		}
	}

	/**
	 * Get the "best" URI for the HFC.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getHFC() throws LookupFailedException {
		return lookupOrCache(KEYWORD_HFC);
	}

	/**
	 * Get the "best" URI for the HEC.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getHEC() throws LookupFailedException {
		return lookupOrCache(KEYWORD_HEC);
	}

	/**
	 * Get the "best" URI for the DPAS.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getDPAS() throws LookupFailedException {
		return lookupOrCache(KEYWORD_DPAS);
	}

	/**
	 * Get the "best" URI for the ICS.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getICS() throws LookupFailedException {
		return lookupOrCache(KEYWORD_ICS);
	}

	/**
	 * Get the "best" URI for the ILS.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getILS() throws LookupFailedException {
		return lookupOrCache(KEYWORD_ILS);
	}

	/**
	 * Get the "best" URI for the CEA.
	 * 
	 * @throws LookupFailedException
	 */
	public URI getCEA() throws LookupFailedException {
		return lookupOrCache(KEYWORD_CEA);
	}

	public static void main(String[] args) throws LookupFailedException,
			URISyntaxException {
		CachedRegistryClient registry = new CachedRegistryClient();
		System.out.println("HEC = " + registry.getHEC() + "\nHFC = "
				+ registry.getHFC() + "\nDPAS = " + registry.getDPAS());
		URI term = URI.create("helio:cea:cea:1.0");
		System.out.println("I looked for \"" + term + "\" and have found \""
				+ registry.getMappingOfHelioURI(term) + "\"");
	}
}
