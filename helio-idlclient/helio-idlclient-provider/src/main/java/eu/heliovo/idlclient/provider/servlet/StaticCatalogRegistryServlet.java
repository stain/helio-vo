package eu.heliovo.idlclient.provider.servlet;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.config.ConfigurablePropertyDescriptor;
import eu.heliovo.clientapi.model.catalog.HelioCatalog;
import eu.heliovo.clientapi.model.field.DomainValueDescriptor;
import eu.heliovo.clientapi.model.field.FieldType;
import eu.heliovo.clientapi.model.field.HelioField;
import eu.heliovo.clientapi.model.service.HelioService;
import eu.heliovo.clientapi.query.AnnotatedBean;
import eu.heliovo.idlclient.model.IdlClass;
import eu.heliovo.idlclient.model.IdlFieldType;
import eu.heliovo.idlclient.model.IdlHelioCatalog;
import eu.heliovo.idlclient.model.IdlHelioField;
import eu.heliovo.idlclient.provider.serialize.IdlObjConverter;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;

/**
 * AsyncQueryServiceServlet for IDL Clients.
 * Accept a query from IDL and pass it to the HELIO query.
 * Result is serialized for IDL and passed to IDL client.
 */
public class StaticCatalogRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the helio client
	 */
	private HelioClient helioClient;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    helioClient = (HelioClient)config.getServletContext().getAttribute(IdlClientStartupListener.HELIO_CLIENT);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticCatalogRegistryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IdlObjConverter idlConverter = IdlObjConverter.getInstance();
		idlConverter.registerSerialisationHandler(HelioCatalog.class, IdlHelioCatalog.class);
		idlConverter.registerSerialisationHandler(HelioField.class, IdlHelioField.class);
		idlConverter.registerSerialisationHandler(FieldType.class, IdlFieldType.class);
		idlConverter.registerSerialisationHandler(Class.class, IdlClass.class);
		
		try	{
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();

			String catalogParam = request.getParameter("catalog");
			String serviceParam = request.getParameter("service");
			
			if(serviceParam == null || serviceParam.isEmpty()) {
			    throw new IllegalArgumentException("Parameter 'service' must not be null or empty.");
			}
			
			HelioServiceName helioServiceName = findServiceName(serviceParam);
			HelioService syncService = findSyncService(helioServiceName);

			
			ConfigurablePropertyDescriptor<String> fromPropertyDescriptor = findFromPropertyDescriptor(syncService);
			if (fromPropertyDescriptor != null) {
    			Collection<DomainValueDescriptor<String>> valueDomain = fromPropertyDescriptor.getValueDomain();
    			
    			ArrayList<HelioCatalog> catList = new ArrayList<HelioCatalog>();
    			for (DomainValueDescriptor<String> currentDomainValue : valueDomain) {
    				if(catalogParam != null) {
    					if(currentDomainValue.getValue().equalsIgnoreCase(catalogParam)) {
    						writer.println(idlConverter.idlSerialize(currentDomainValue));
    						return;
    					}
    				}
    	        }
    			writer.println((idlConverter.idlSerialize(catList)));
			} else {
			    throw new IllegalArgumentException("Unable to find property descriptor for the 'from' query ");
			}
		} catch(Exception e) {
			String out = idlConverter.idlSerialize(e);
            PrintWriter writer = response.getWriter();
	        response.setContentType("text/plain");
	        response.setContentLength(out.length());
			writer.append(out);
		}
	}

    private HelioService findSyncService(HelioServiceName helioServiceName) {
        HelioService syncService = helioClient.getServiceInstance(helioServiceName, null, ServiceCapability.SYNC_QUERY_SERVICE);
        
        if(syncService == null)	{
        	throw new IllegalArgumentException("Unable to find sync service for name: " + helioServiceName);
        }
        return syncService;
    }

    private HelioServiceName findServiceName(String serviceParam) {
        HelioServiceName helioServiceName = HelioServiceName.valueOf(serviceParam.toUpperCase());
        if (helioServiceName == null) {
            throw new IllegalArgumentException("Unable to find 'service' with name '" + serviceParam + "'. Allowed values are " + HelioServiceName.values().toString());			    
        }
        return helioServiceName;
    }

	/**
	 * Find the property descriptor for property "from"
	 * @param syncService the sync service to look for the from property.
	 * @return the PropertyDescriptor or null if not found.
	 */
    @SuppressWarnings("unchecked")
    private ConfigurablePropertyDescriptor<String> findFromPropertyDescriptor(HelioService syncService) {
        BeanInfo beanInfo = null;
        if (syncService instanceof AnnotatedBean) {
            beanInfo = ((AnnotatedBean)syncService).getBeanInfo();
        }
        if (beanInfo == null) {
            return null;
        }
        
        PropertyDescriptor fromPropertyDescriptor = null;
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if ("from".equals(propertyDescriptor.getName())) {
                fromPropertyDescriptor = propertyDescriptor;
            }
        }
        
        if (fromPropertyDescriptor == null) {
            return null;
        }
        
        if (fromPropertyDescriptor instanceof ConfigurablePropertyDescriptor<?>) {
            return (ConfigurablePropertyDescriptor<String>)fromPropertyDescriptor;
        } else {
            throw new RuntimeException("Unexpected type for 'from' property descriptor " + fromPropertyDescriptor);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
