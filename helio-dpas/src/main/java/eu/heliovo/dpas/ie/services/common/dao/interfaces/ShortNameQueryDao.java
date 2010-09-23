/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.interfaces;

import java.sql.Connection;
import java.util.HashMap;

import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;


public interface ShortNameQueryDao {

	public void loadProviderAccessTable(String fileName) throws DetailsNotFoundException;
}
