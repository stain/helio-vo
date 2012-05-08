package eu.heliovo.dpas.ie.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;

/**
 * Servlet implementation class DpasQueryServlet
 */
public class PatQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatQueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw =  response.getWriter(); 
		try{
		     ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();

		     ResultTO []rt = shortNameDao.getAccessTableDetails(null);
		     for(int i = 0;i < rt.length;i++) {
		    	 pw.println(rt[i].getHelioInst() + "," + rt[i].getProviderName() + "," + rt[i].getProviderSource() +","+ rt[i].getPvdrRanking());
		     }
		     
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		finally
		{
			if(pw!=null){
				pw.close();
				pw=null;
			}
		}
	}

}
