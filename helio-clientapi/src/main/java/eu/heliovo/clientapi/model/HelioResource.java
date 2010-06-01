package eu.heliovo.clientapi.model;


/**
 * Describe any Helio object as a resrouce. All objects are meant to implement this interface. 
 * @author marco soldati at fhnw ch
 *
 */
public interface HelioResource {
	/**
	 * Get a qualified identifier for the resource. 
	 * This identifier is unique within the whole Helio system.
	 * @return the resource id.
	 */
	public String getResourceId();
}
