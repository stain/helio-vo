package eu.heliovo.clientapi.query.paramquery.impl;

import eu.heliovo.clientapi.model.catalog.HelioCatalogDao;
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory;
import eu.heliovo.clientapi.model.field.HelioField;
import eu.heliovo.clientapi.query.longrunningquery.impl.LongRunningQueryServiceFactory;
/**
 * Default implementation of the param query for the HEC service. 
 * @author marco soldati at fhnw ch 
 */
public class HecParamQueryServiceImpl extends AbstractParamQueryServiceImpl {

	/**
	 * Keep a reference to the hecDao
	 */
	private HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao("hec");;

	/**
	 * Reference to the query service factory.
	 */
	private static LongRunningQueryServiceFactory queryServiceFactory = LongRunningQueryServiceFactory.getInstance();
	
	/**
	 * Create a mock implementation of the DPAS.
	 */
	public HecParamQueryServiceImpl() {
		super(null);	
	}

	@Override
	public String getName() {
		return queryService.getName();
	}
	
	@Override
	public String getDescription() {
		return "Helio Event Catalogue";
	}
	
	@Override
	public HelioField<?>[] getFieldDescriptions(String catalog) throws IllegalArgumentException {
		if (catalog == null) {
			return new HelioField[] {hecDao.getCatalogField()};
		}
		
		HelioField<?>[] helioFields = hecDao.getFields(catalog);
		return helioFields;
	}		
}
