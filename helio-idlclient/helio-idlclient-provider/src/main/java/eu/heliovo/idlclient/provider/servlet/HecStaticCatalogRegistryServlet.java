package eu.heliovo.idlclient.provider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IdlHelioCatalog;
import model.IdlHelioField;

import eu.heliovo.clientapi.model.catalog.HelioCatalog;
import eu.heliovo.clientapi.model.catalog.HelioCatalogDao;
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory;
import eu.heliovo.clientapi.model.field.DomainValueDescriptor;
import eu.heliovo.clientapi.model.field.HelioField;
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
		
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		
		String catalog = request.getParameter("catalog");
		HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao("hec");
		
		if(catalog != null && catalog != "")
		{
			HelioCatalog hc = hecDao.getCatalogById(catalog);
			writer.println((idl.idlserialize(hc)));
		}
		else
		{
			ArrayList<String> catList = new ArrayList<String>();
			for (DomainValueDescriptor<String> c : hecDao.getCatalogField().getValueDomain()) {
				HelioCatalog hc = hecDao.getCatalogById(c.getValue());
				catList.add(hc.getCatalogName());
	        }
			writer.println((idl.idlserialize(catList)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
