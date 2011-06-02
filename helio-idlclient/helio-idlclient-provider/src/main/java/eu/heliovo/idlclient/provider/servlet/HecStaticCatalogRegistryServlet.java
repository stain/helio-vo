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
import eu.heliovo.clientapi.model.service.HelioServiceName;
import eu.heliovo.idlclient.provider.serialize.IdlConverter;

/**
 * AsyncQueryServiceServlet for IDL Clients.
 * Accept a query from IDL and pass it to the HELIO query.
 * Result is serialized for IDL and passed to IDL client.
 */
public class HecStaticCatalogRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HecStaticCatalogRegistryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IdlConverter idl = IdlConverter.getInstance();
		idl.registerSerialisationHandler(HelioCatalog.class, IdlHelioCatalog.class);
		idl.registerSerialisationHandler(HelioField.class, IdlHelioField.class);
		idl.registerSerialisationHandler(FieldType.class, IdlFieldType.class);
		idl.registerSerialisationHandler(Class.class, IdlClass.class);
		
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		
		HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.HEC.getName());
		
		ArrayList<HelioCatalog> catList = new ArrayList<HelioCatalog>();
		for (DomainValueDescriptor<String> c : hecDao.getCatalogField().getValueDomain()) {
			HelioCatalog hc = hecDao.getCatalogById(c.getValue());
			catList.add(hc);
        }
		writer.println((idl.idlserialize(catList)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
