package eu.heliovo.clientapi;

import eu.heliovo.clientapi.core.HelioCore;
import eu.heliovo.clientapi.query.HelioParamQuery;
import eu.heliovo.clientapi.query.HelioSqlQuery;

/**
 * This is just a convenience interface to collect all modules of the API in one place.
 * @author marco soldati at fhnw ch
 *
 */
public interface HelioApi extends HelioCore, HelioParamQuery, HelioSqlQuery {

}
