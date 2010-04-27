package org.egso.provider.service;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;

import org.apache.log4j.Logger;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.admin.ServiceMonitor;
import org.egso.provider.utils.Conversion;
import org.egso.provider.utils.XMLTools;
import org.xml.sax.InputSource;


/**
 * JAVADOC: Description of the Class
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.1 - 26/10/2004
 */
/*
1.1 - 26/10/2004:
	Use of SAXParser instead of XercesParser.
*/
public class CoSECService extends EGSOService {

	/**
	 * JAVADOC: Description of the Field
	 */
	private final static String URL = "http://www.lmsal.com/cgi-diapason/";
	/**
	 * JAVADOC: Description of the Field
	 */
	private final static String FORMAT_IN = "YYYY-MM-DD";
	/**
	 * JAVADOC: Description of the Field
	 */
	private final static String FORMAT_OUT = "DD-mmm-YYYY";
	/**
	 * JAVADOC: Description of the Field
	 */
	private String urlToNAImage = "";
	/**
	 * JAVADOC: Description of the Field
	 */
	private Logger logger = null;


	/**
	 * JAVADOC: Constructor for the CoSECService object
	 */
	public CoSECService() {
		init();
	}


	/**
	 * JAVADOC: Description of the Method
	 */
	public void start() {
	}


	/**
	 * JAVADOC: Description of the Method
	 */
	public void stop() {
	}


	/**
	 * JAVADOC: Description of the Method
	 */
	private void init() {
		logger = Logger.getLogger(this.getClass().getName());
		logger.info("CoSEC Service Initialization");
		Hashtable<String,String> desc = new Hashtable<String,String>();
		desc.put("name", "CoSEC Services Accessor");
		desc.put("id", "COSEC");
		desc.put("description", "Provide the access to CoSEC services");
		desc.put("version", "1.0");
		desc.put("date", "26 may 2004");
		desc.put("author", "Romain Linsolas (linsolas@gmail.com)");
		desc.put("main-class", "CoSECService");
		desc.put("jar", "cosec.jar");
		setDescriptor(new ServiceDescriptor(desc, new Hashtable<String,String>()));
		ProviderMonitor monitor = ProviderMonitor.getInstance();
		monitor.addService(getDescriptor().getID(), new ServiceMonitor(this));
		urlToNAImage = (String) ProviderConfiguration.getInstance().getProperty("service.cosec-na-image");
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param startDate  JAVADOC: Description of the Parameter
	 * @param endDate    JAVADOC: Description of the Parameter
	 * @return           JAVADOC: Description of the Return Value
	 */
	public String cosecPlotGoesProtons(String startDate, String endDate) {
		logger.info("CoSEC 'Plot GOES Prontons' Service: " + startDate + " - " + endDate);
		StringBuffer query = new StringBuffer();
		query.append(URL);
		query.append("ssw_service_plot_goes_protons.sh?param1=");
		query.append(convertDate(startDate));
		query.append("&param2=");
		query.append(convertDate(endDate));
		query.append("&cosec_mode=2");
		query.append("&extra=/log");
		return (invokeCosec(query.toString()));
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param startDate  JAVADOC: Description of the Parameter
	 * @param endDate    JAVADOC: Description of the Parameter
	 * @return           JAVADOC: Description of the Return Value
	 */
	public String cosecPlotGoesXrays(String startDate, String endDate) {
		logger.info("CoSEC 'Plot GOES Xrays' Service: " + startDate + " - " + endDate);
		StringBuffer query = new StringBuffer();
		query.append(URL);
		query.append("ssw_service_plot_goes_xrays.sh?param1=");
		query.append(convertDate(startDate));
		query.append("&param2=");
		query.append(convertDate(endDate));
		query.append("&cosec_mode=2");
		return (invokeCosec(query.toString()));
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param date  JAVADOC: Description of the Parameter
	 * @return      JAVADOC: Description of the Return Value
	 */
	private String convertDate(String date) {
		return (Conversion.convertDate(FORMAT_IN, FORMAT_OUT, date) + "+" + date.substring(10).trim());
	}


	/*
	public String cosecLesUrl(String date, String extra) {
		StringBuffer query = new StringBuffer();
		query.append(URL);
		query.append("ssw_service_les_url.sh?param1=");
		query.append(Conversion.convertDate(FORMAT_IN, FORMAT_OUT, date));
		query.append("&extra=");
		query.append(convertExtra(extra));
		query.append("&cosec_mode=2");
		return(invokeCosec(query.toString()));
	}
	public String cosecPlotAce(String startDate, String endDate, String extra) {
		StringBuffer query = new StringBuffer();
		query.append(URL);
		query.append("ssw_service_plot_ace.sh?param1=");
		query.append(Conversion.convertDate(FORMAT_IN, FORMAT_OUT, startDate));
		query.append("&param2=");
		query.append(Conversion.convertDate(FORMAT_IN, FORMAT_OUT, endDate));
		query.append("&extra=");
		query.append(convertExtra(extra));
		query.append("&cosec_mode=2");
		return(invokeCosec(query.toString()));
	}
*/


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param http  JAVADOC: Description of the Parameter
	 * @return      JAVADOC: Description of the Return Value
	 */
	private String invokeCosec(String http) {
		CoSECParser parser = null;
		getDescriptor().access();
		try {
			System.out.print("CoSEC URL> " + http + " ...");
			long x = System.currentTimeMillis();
			URL url = new URL(http + "\n");
			URLConnection connection = url.openConnection();
			connection.setUseCaches(true);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestProperty("Content-length", "0");
			// get the outputStream to write to
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			// and write the data
			osw.flush();
			osw.close();
			connection.connect();
			System.out.println(" CoSEC contacted, waiting for result...");
/*
			InputStream is = connection.getInputStream();
			int a = 0;
			while ((a = is.read()) != -1) {
				System.out.print((char) a);
			}
			System.out.println("\n -- FINISHED --");
*/
			InputSource is = new InputSource(connection.getInputStream());
			is.setEncoding("ISO-8859-1");
			SAXParser saxParser = XMLTools.getSAXParser();
			parser = new CoSECParser();
			saxParser.parse(is, parser);
			System.out.println("TIME NEEDED BY COSEC = " + (System.currentTimeMillis() - x) + " ms");
//			System.out.println("RESULT FOUND: " + parser.getResult());
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
			return (urlToNAImage);
		}
		String result = parser.getResult();
		logger.info("CoSEC response: " + result);
		return (result);
	}

}


