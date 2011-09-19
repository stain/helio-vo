package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IdlClass;
import model.IdlFieldType;
import model.IdlHelioCatalog;
import model.IdlHelioField;
import eu.heliovo.clientapi.model.catalog.HelioCatalog;
import eu.heliovo.clientapi.model.catalog.HelioCatalogDao;
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory;
import eu.heliovo.clientapi.model.field.DomainValueDescriptor;
import eu.heliovo.clientapi.model.field.FieldType;
import eu.heliovo.clientapi.model.field.HelioField;
import eu.heliovo.idlclient.provider.serialize.*;
import eu.heliovo.registryclient.HelioServiceName;

/**
 * AsyncQueryServiceServlet for IDL Clients.
 * Accept a query from IDL and pass it to the HELIO query.
 * Result is serialized for IDL and passed to IDL client.
 */
public class StaticCatalogRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticCatalogRegistryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IdlObjConverter idl = IdlObjConverter.getInstance();
		idl.registerSerialisationHandler(HelioCatalog.class, IdlHelioCatalog.class);
		idl.registerSerialisationHandler(HelioField.class, IdlHelioField.class);
		idl.registerSerialisationHandler(FieldType.class, IdlFieldType.class);
		idl.registerSerialisationHandler(Class.class, IdlClass.class);
		
		try	{
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();

			String catalog = request.getParameter("catalog");
			String service = request.getParameter("service");
			
			if(service == null) throw new RuntimeException("service must not be empty");
			
			//HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.HEC.getName());
			HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.valueOf(service.toUpperCase()));
			
			if(hecDao == null)
			{
				//throw new RuntimeException("Unable to find service with name " + service.toUpperCase());
			}
			
			ArrayList<HelioCatalog> catList = new ArrayList<HelioCatalog>();
			for (DomainValueDescriptor<String> c : hecDao.getCatalogField().getValueDomain()) {
				HelioCatalog hc = hecDao.getCatalogById(c.getValue());
				catList.add(hc);
				if(catalog != null)
				{
					if(hc.getCatalogName().compareToIgnoreCase(catalog) == 0)
					{
						writer.println(idl.idlserialize(hc));
						return;
					}
					
				}
	        }
			writer.println((idl.idlserialize(catList)));
		} catch(Exception e) {
			String out = idl.idlserialize(e);
            PrintWriter writer = response.getWriter();
	        response.setContentType("text/plain");
	        response.setContentLength(out.length());
			writer.append(out);
		}
	
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
