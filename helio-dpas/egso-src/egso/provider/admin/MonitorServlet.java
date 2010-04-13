
package org.egso.provider.admin;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


@SuppressWarnings("serial")
public class MonitorServlet extends HttpServlet {

	private static final String VERSION = "0.1 - Release 4a";
	private static final String DATE = "4 June 2004";
//	private static final String CONFIG_FILENAME = "../webapps/provider/WEB-INF/classes/conf/config.xml";
	private static final String IMAGES_DIRECTORY = "images/";
	private static final String[] ICONS_DESCRIPTIONS = {"New feature", "Feature removed", "Feature updated", "Bug fixed", "Documentation"};
	private static final String[] ICONS_NAMES = {"new.png", "remove.png", "update.png", "fix.png", "doc.png"};


	public MonitorServlet() {
	}


	public void service(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>EGSO Provider Monitoring and Managing Tool</title></head><body style=\"background-color: rgb(240, 240, 255)\">");
		displayMenu(out);
		String tmp = request.getParameter("section");
		if ((tmp == null) || (tmp.toLowerCase().equals("home"))) {
			displayHome(out);
		} else {
			ProviderMonitor monitor = null;
			try {
				monitor = ProviderMonitor.getInstance();
				tmp = tmp.toLowerCase();
				if (tmp.equals("sysinfo")) {
					displayInfo(out, monitor);
				} else {
					if (tmp.equals("archive")) {
						displayArchiveMonitor(out, monitor);
					} else {
						if (tmp.equals("service")) {
							displayServiceMonitor(out, monitor);
						} else {
							if (tmp.equals("admin")) {
								displayAdmin(out, monitor);
							} else {
								if (tmp.equals("logs")) {
									displayLogs(out, monitor);
								} else {
									if (tmp.equals("history")) {
										displayHistory(out, monitor);
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				out.println("Error while loading the page:<br/>" + e.toString());
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				out.println(sw.getBuffer());
			}
		}
		out.println("<br/><br/><hr><p align=\"right\"><font size=\"-1\">EGSO Provider Monitor Servlet - " + VERSION + "<br/>" + "Romain Linsolas (linsolas@gmail.com) - " + DATE + "</font></p>");
		out.println("</body></html>");
	}

	private void displayMenu(PrintWriter out) {
		String url = "?section=";
		String[] menu = {"HOME", "SYSTEM", "ARCHIVES", "SERVICES", "ADMIN", "LOGS", "HISTORY"};
		String[] urls = {"home", "sysinfo", "archive", "service", "admin", "logs", "history"};
		String img_prefix = "menu_";
		String[] imgs = {"home", "system", "archives", "services", "admin", "logs", "history"};
		out.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: rgb(153, 153, 204); width: 1px; margin-left: auto; margin-right: auto;\"><tbody><tr>");
		for (int i = 0 ; i < menu.length ; i++) {
			out.println("<td><a href=\"" + url + urls[i] + "\"><img src=\"" + IMAGES_DIRECTORY + img_prefix + imgs[i] + ".png\"></a></td>");
		}
		out.println("</tr></tbody></table><br/><hr><br/>");
	}

	private void displayHome(PrintWriter out) {
		
	}

	private void displayInfo(PrintWriter out, ProviderMonitor monitor) {
		SystemInformation info = monitor.getSystemInformation();
		String key = null;
		out.println("<b>Current date</b>: " + ProviderUtils.getDate() + "<br/>");
		info.addStatistic("Provider uptime", ProviderUtils.uptime(ProviderUtils.getCalendar((String) info.getStatistic("Provider startup date")), Calendar.getInstance()));
		for (Enumeration<String> e = info.getKeys() ; e.hasMoreElements() ; ) {
			key = (String) e.nextElement();
			out.println("<b>" + key + "</b>: " + info.getStatistic(key) + "<br/>");
		}
	}

	private void displayAdmin(PrintWriter out, ProviderMonitor monitor) {
		String key = null;
		out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"1\"><tbody><tr>");
		Object obj = null;
		ProviderConfiguration conf = ProviderConfiguration.getInstance();
		for (Enumeration<String> e = conf.getProperties() ; e.hasMoreElements() ; ) {
			key = (String) e.nextElement();
			out.println("<tr><td style=\"vertical-align: top; font-weight: bold; text-align: right; width: 1px; white-space: nowrap; background-color: rgb(153, 153, 255);\">");
			out.println(key + "</td><td style=\"vertical-align: top; white-space: nowrap; width: 100%;\">");
			obj = conf.getProperty(key);
			if (obj == null) {
				out.println("<i>Not available</i>");
			} else {
				if ((obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Integer)) {
					out.println(obj.toString());
				} else {
					if (obj instanceof String[]) {
						String[] tmp = (String[]) obj;
						out.print("[" + tmp[0]);
						for (int i = 1 ; i < tmp.length ; i++) {
							out.print(", " + tmp[i]);
						}
						out.println("]");
					} else {
						out.println("Value of class <i>" + obj.getClass().getName() + "</i> and " + obj.toString());
					}
				}
			}
			out.println("</td></tr>");
		}
		out.println("</tbody></table><br/><br/><br/>The XML file:<br/>");
		try {
			InputSource in = new InputSource(new FileInputStream((String) ProviderConfiguration.getInstance().getProperty("core.config")));
			DOMParser parser = new DOMParser();
			parser.parse(in);
			out.println(XMLUtils.XMLToHTML(parser.getDocument().getDocumentElement()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayServiceMonitor(PrintWriter out, ProviderMonitor monitor) {
		ServiceMonitor ser = null;
		String key = null;
		for (Enumeration<ServiceMonitor> e = monitor.getAllServices() ; e.hasMoreElements() ; ) {
			ser = (ServiceMonitor) e.nextElement();
			ser.refreshInfo();
			out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"1\"><tbody><tr>");
			out.println("<td style=\"text-align: center; vertical-align: middle; width: 1px; background-color: rgb(102, 0, 204);\" rowspan=\"" + ser.numberOfStatistics() + "\" colspan=\"1\">");
			out.println("<img src=\"" + IMAGES_DIRECTORY + "service.png\"></td>");
			String x = "";
			for (Enumeration<String> e2 = ser.getKeys() ; e2.hasMoreElements() ; ) {
				key = (String) e2.nextElement();
				out.println(x);
				out.println("<td style=\"vertical-align: top; font-weight: bold; text-align: right; width: 1px; white-space: nowrap; background-color: rgb(153, 153, 255);\">");
				out.println(key + "</td><td style=\"vertical-align: top; white-space: nowrap; width: 100%;\">");
				x = (String) ser.getStatistic(key);
				if ((x == null) || (x.trim().equals(""))) {
					x = "<i>Not available</i>";
				}
				out.println(x + "</td></tr>");
				x = "<tr>";
			}
			out.println("</tbody></table><br/><br/>");
		}
	}

	private void displayArchiveMonitor(PrintWriter out, ProviderMonitor monitor) {
		ArchiveMonitor arc = null;
		String key = null;
		for (Enumeration<ArchiveMonitor> e = monitor.getAllArchives() ; e.hasMoreElements() ; ) {
			arc = (ArchiveMonitor) e.nextElement();
			arc.refreshInfo();
			out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"1\"><tbody><tr>");
			out.println("<td style=\"text-align: center; vertical-align: middle; width: 1px; background-color: rgb(102, 0, 204);\" rowspan=\"" + arc.numberOfStatistics() + "\" colspan=\"1\">");
			out.println("<img src=\"" + IMAGES_DIRECTORY + ((String) arc.getStatistic("Type")).toLowerCase() + ".png\"></td>");
			String x = "";
			for (Enumeration<String> e2 = arc.getKeys() ; e2.hasMoreElements() ; ) {
				key = (String) e2.nextElement();
				out.println(x);
				out.println("<td style=\"vertical-align: top; font-weight: bold; text-align: right; width: 1px; white-space: nowrap; background-color: rgb(153, 153, 255);\">");
				out.println(key + "</td><td style=\"vertical-align: top; white-space: nowrap; width: 100%;\">");
				x = (String) arc.getStatistic(key);
				if ((x == null) || (x.trim().equals(""))) {
					x = "<i>Not available</i>";
				}
				out.println(x + "</td></tr>");
				x = "<tr>";
			}
			out.println("</tbody></table><br/><br/>");
		}
	}

	private void displayLogs(PrintWriter out, ProviderMonitor monitor) {
		out.println("<table cellpadding=\"2\" cellspacing=\"0\" border=\"1\" style=\"text-align: left; width: 100%;\"><tbody>");
		out.println("<tr><td style=\"background-color: rgb(102, 0, 204); text-align: center;\"><span style=\"font-weight: bold; color: rgb(255, 255, 255);\">Information about the last query processed</span></td></tr>");
		out.println("<tr><td style=\"background-color: rgb(153, 153, 255);\">QUERY [not validated]</td></tr>");
		out.println("<tr><td>" + monitor.getLastQuery(false) + "</td></tr>");
		out.println("<tr><td style=\"background-color: rgb(153, 153, 255);\">QUERY [validated]</td></tr>");
		out.println("<tr><td>" + monitor.getLastQuery(true) + "</td></tr>");
		out.println("<tr><td style=\"background-color: rgb(153, 153, 255);\">RESULTS</td></tr>");
		if (monitor.getLastQueryResult() != null) {
			try {
				DocumentBuilderFactory facto = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = facto.newDocumentBuilder();
				InputSource is = new InputSource(new ByteArrayInputStream(monitor.getLastQueryResult().getBytes()));
				out.println("<tr><td>" + XMLUtils.XMLToHTML(builder.parse(is)) + "</td></tr>");
			} catch (Throwable t) {
				out.println("ERROR while loading the VOTable:<br/>" + t.getMessage());
			}
		} else {
			out.println("<tr><td>No results found yet.</tr></td>");
		}
		out.println("</tbody></table><br/><br/><b>EXCEPTIONS</b><br/>");
		ExceptionsMonitor exc = monitor.getExceptionsMonitor();
		if (exc.getNumberOfExceptions() == 0) {
			out.println("No exception has been thrown for the moment :)<br/>");
			return;
		}
		Iterator<String> dates = exc.getDates();
		int x = 0;
		StringBuffer sb = null;
		for (Iterator<String> exceptions = exc.getExceptions() ; exceptions.hasNext() ; ) {
			sb = new StringBuffer((String) exceptions.next());
			while ((x = sb.indexOf("\n")) != -1) {
				sb = sb.deleteCharAt(x).insert(x, "<br/>");
			}
			out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"1\" style=\"text-align: left; width: 100%;\"><tbody><tr>");
			out.println("<td style=\"vertical-align: top; width: 1px; background-color: rgb(204, 204, 255);\">");
			out.println("<img src=\"" + IMAGES_DIRECTORY + "exception.png\"></td>");
			out.println("<td style=\"vertical-align: top; font-weight: bold; background-color: rgb(204, 204, 255);\">" + (String) dates.next() + "</td></tr>");
			out.println("<tr><td rowspan=\"1\" colspan=\"2\">" + sb.toString() + "</td></tr></tbody></table><br/><br/>");
		}
		out.println("<font size=\"-1\"><u>Note:</u> only the last " + exc.getMaximumExceptions() + " exceptions are displayed.</font><br/>");
	}

	private void displayHistory(PrintWriter out, ProviderMonitor monitor) {
		try {
			InputSource in = new InputSource(new FileInputStream((String) ProviderConfiguration.getInstance().getProperty("core.history")));
			DOMParser parser = new DOMParser();
			parser.parse(in);
			Node root = parser.getDocument().getDocumentElement();
			int index = Integer.parseInt(root.getAttributes().getNamedItem("last-index").getNodeValue());
			Node release = null;
			Node feat = null;
			Node n = null;
			NodeList features = null;
			NodeList nl = null;
			String desc = null;
			while (index >= 0) {
				try {
					release = XMLTools.getInstance().selectSingleNode(root, "//release[@index='" + index + "']");
					out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"0\" style=\"text-align: left; width: 100%;\">");
					out.println("<tbody><tr><td style=\"vertical-align: top; width: 100%; background-color: rgb(204, 204, 255);\"><b>Version</b>: " + release.getAttributes().getNamedItem("version").getNodeValue());
					out.println("  -  <b>Date</b>: " + release.getAttributes().getNamedItem("date").getNodeValue());
					out.println("</td></tr></tbody></table>");
					out.println("<table cellpadding=\"1\" cellspacing=\"0\" border=\"0\" style=\"text-align: left; width: 100%;\"><tbody>");
					features = release.getChildNodes();
					for (int i = 0 ; i < features.getLength() ; i++) {
						feat = features.item(i);
						if ((feat.getNodeType() == Node.ELEMENT_NODE) && (feat.getLocalName().equals("feature"))) {
							desc = "";
							nl = feat.getChildNodes();
							for (int j = 0 ; j < nl.getLength() ; j++) {
								n = nl.item(j);
								if (n.getNodeType() == Node.TEXT_NODE) {
									desc += n.getNodeValue().trim();
								}
							}
							desc = desc.replace('[', '<').replace(']', '>');
							out.println("<tr><td style=\"vertical-align: top; width: 1px; white-space: nowrap; background-color: rgb(204, 204, 255);\">");
							out.println("<img src=\"" + IMAGES_DIRECTORY + feat.getAttributes().getNamedItem("type").getNodeValue().toLowerCase() + ".png\"></td><td>" + desc + "</td></tr>");
						}
					}
					out.println("</tbody></table><br/><br/>");
				} catch (Exception e) {
					out.println("Error, while getting the " + index + "th release information: " + e.getMessage() + "<br/>");
				} finally {
					index--;
				}
			}
			out.println("<hr>");
			for (int i = 0 ; i < ICONS_NAMES.length ; i++) {
				out.println("<img src=\"" + IMAGES_DIRECTORY + ICONS_NAMES[i] + "\">&nbsp;" + ICONS_DESCRIPTIONS[i] + "&nbsp;&nbsp;");
			}
		} catch (Exception exc) {
			out.println("Error while reading the XML file for the Provider History: " + exc.getMessage());
		}
	}


}
