package eu.heliovo.registryclient;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;
import static uk.ac.starlink.registry.RegistryRequestFactory.keywordSearch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.starlink.registry.AbstractRegistryClient;
import uk.ac.starlink.registry.BasicCapability;
import uk.ac.starlink.registry.BasicRegistryClient;
import uk.ac.starlink.registry.BasicResource;
import uk.ac.starlink.registry.SoapClient;

@SuppressWarnings("unused")
public class RegistryClient {
	Logger log = getLogger(RegistryClient.class.getName());

	/** The default address of the registry. */
	public static final String REGISTRY_AT_MSSL = "http://msslxw.mssl.ucl.ac.uk:8080/helio_registry/services/RegistryQueryv1_0";
	// http://msslxw.mssl.ucl.ac.uk:8080/helio_registry/services/RegistryQueryv1_0?wsdl

	/**
	 * Description of the kind of result that uses the HQI.
	 * 
	 * @see #getResource(String,String)
	 */
	public static final String HELIO_QUERY_INTERFACE = "ivo://helio-vo.eu/std/FullQuery/v0.2";

	private AbstractRegistryClient<BasicResource> registry;

	private static final HashMap<String, String> urnTailRemap = new HashMap<String, String>();
	static {
		urnTailRemap.put("hqi:0.2", HELIO_QUERY_INTERFACE);
		urnTailRemap.put("cea:1.0", "ivo://org.astrogrid/std/CEA/v1.0");
		urnTailRemap.put("registry", "ivo://ivoa.net/std/Registry");
	}

	public RegistryClient() {
		try {
			setRegistryURL(new URL(REGISTRY_AT_MSSL));
		} catch (MalformedURLException e) {
			log.log(SEVERE,
					"failed to create and install initial registry URL", e);
		}
	}

	public void setRegistryURL(URL url) throws MalformedURLException {
		registry = new BasicRegistryClient(new SoapClient(url));
	}

	private HashMap<String, URI> buildIdToUrlMap(BasicResource r) {
		HashMap<String, URI> id2url = new HashMap<String, URI>();
		log.info("found match: " + r.getIdentifier() + " (" + r.getTitle()
				+ ")");
		boolean isHQI = false;
		for (BasicCapability c : r.getCapabilities()) {
			if (isHQI) {
				log.info("processing HQI as having interface "
						+ c.getAccessUrl());
				id2url.put(HELIO_QUERY_INTERFACE, URI.create(c.getAccessUrl()));
				isHQI = false;
				continue;
			}
			log.info("url " + c.getAccessUrl() + " is " + c.getStandardId()
					+ " of type " + c.getXsiType());
			isHQI = HELIO_QUERY_INTERFACE.equals(c.getStandardId());
			try {
				id2url.put(c.getStandardId(), new URI(c.getAccessUrl()));
			} catch (URISyntaxException e) {
				log.log(WARNING, "failed to convert access URL to a URI", e);
			}
		}
		return id2url;
	}

	/**
	 * Get the URI for some HELIO service resource that is characterised by a
	 * particular keyword. Which service URI to return is determined by an
	 * educated guess.
	 * 
	 * @param keyword
	 *            The characteristic name of the resource. An abbreviation from
	 *            the HELIO Architecture.
	 * @return The URI of the resource, or <tt>null</tt> if there is no such
	 *         resource.
	 * @throws LookupFailedException
	 *             If we just couldn't successfully look anything up.
	 */
	public URI getResource(String keyword) throws LookupFailedException {
		List<BasicResource> result;
		try {
			result = registry.getResourceList(keywordSearch(
					new String[] { keyword }, false));
		} catch (IOException e) {
			throw new LookupFailedException("failed to access registry", e);
		}

		/*
		 * First scan through the results to see if we can handle them as
		 * instances of the HQI; that's special.
		 */
		for (BasicResource r : result) {
			HashMap<String, URI> id2url = buildIdToUrlMap(r);
			if (id2url.containsKey(HELIO_QUERY_INTERFACE)) {
				URI uri = id2url.get(HELIO_QUERY_INTERFACE);
				log.info("can map \"" + keyword + "\" to " + uri);
				return uri;
			}
		}

		for (BasicResource r : result) {
			// log.info("found match: " + r.getIdentifier() + " (" +
			// r.getTitle()
			// + ")");
			// for (BasicCapability c : r.getCapabilities())
			// log.info("url " + c.getAccessUrl() + " is " + c.getStandardId());
			for (BasicCapability c : r.getCapabilities()) {
				String url = c.getAccessUrl().trim();
				log.info("trying to return " + url + " of type "
						+ c.getStandardId() + " (xsi:type = " + c.getXsiType()
						+ ")");
				try {
					return new URI(url);
				} catch (URISyntaxException e) {
					continue;
				}
			}
		}
		throw new LookupFailedException("no valid results from registry");
	}

	/**
	 * Get the URI for some HELIO service resource that is characterised by a
	 * particular keyword. The returned URI is characterised by the given result
	 * identifier, which indicates the interface being sought.
	 * 
	 * @param keyword
	 *            The characteristic name of the resource. An abbreviation from
	 *            the HELIO Architecture.
	 * @param resultID
	 *            The identifier (normally a URI) of the URI.
	 * @return The URI of the resource, or <tt>null</tt> if there is no such
	 *         resource.
	 * @throws LookupFailedException
	 *             If we just couldn't successfully look anything up.
	 */
	public URI getResource(String keyword, String resultID)
			throws LookupFailedException {
		List<BasicResource> result;
		try {
			result = registry.getResourceList(keywordSearch(
					new String[] { keyword }, false));
		} catch (IOException e) {
			throw new LookupFailedException("failed to access registry", e);
		}

		HashMap<String, URI> id2url = null;
		for (BasicResource r : result) {
			id2url = buildIdToUrlMap(r);
			if (id2url.containsKey(resultID)) {
				break;
			}
		}
		if (id2url != null && id2url.containsKey(resultID)) {
			log.info("trying to return " + id2url.get(resultID) + " of type "
					+ resultID);
			return id2url.get(resultID);
		}
		throw new LookupFailedException("no valid results from registry");
	}
	/**
	 * Map from a URI in the "helio" scheme to the address of the SOAP service
	 * that the registry best believes will implement that service.
	 * 
	 * @param helioURI
	 *            The URI to map, which will really be a URN.
	 * @return The URL of the SOAP service.
	 * @throws URISyntaxException
	 *             If the result in the registry is bad.
	 * @throws LookupFailedException
	 *             If we couldn't do the lookup at all.
	 */
	public URI getMappingOfHelioURI(URI helioURI) throws URISyntaxException,
			LookupFailedException {
		if (!helioURI.getScheme().equals("helio"))
			throw new URISyntaxException(helioURI.toString(),
					"HELIO URIs must use the \"helio\" scheme");
		String[] bits = helioURI.getSchemeSpecificPart().split(":", 2);
		if (bits == null || bits.length > 2 || bits.length < 1)
			throw new URISyntaxException(helioURI.toString(),
					"doesn't match \"helio:*\" or \"helio:*:*\"");
		if (bits.length == 1)
			return getResource(bits[0]);
		if (urnTailRemap.containsKey(bits[1]))
			bits[1] = urnTailRemap.get(bits[1]);
		return getResource(bits[0], bits[1]);
	}

	/**
	 * Map from a URI in the "helio" scheme to the address of the SOAP service
	 * that the registry best believes will implement that service.
	 * 
	 * @param helioURI
	 *            The URI to map, which will really be a URN.
	 * @return The URL of the SOAP service.
	 * @throws URISyntaxException
	 *             If the result in the registry is bad.
	 * @throws LookupFailedException
	 *             If we couldn't do the lookup at all.
	 * @deprecated Use the {@linkplain #getMappingOfHelioURI(URI) fully
	 *             URI-based API} instead.
	 */
	public URI getMappingOfHelioURI(String helioURI) throws URISyntaxException,
			LookupFailedException {
		String[] bits = helioURI.split(":", 3);
		if (bits == null || bits.length > 3 || bits.length < 2
				|| !bits[0].equals("helio"))
			throw new URISyntaxException(helioURI,
					"doesn't match \"helio:*\" or \"helio:*:*\"");
		if (bits.length == 2)
			return getResource(bits[1]);
		if (urnTailRemap.containsKey(bits[2]))
			bits[2] = urnTailRemap.get(bits[2]);
		return getResource(bits[1], bits[2]);
	}
}
