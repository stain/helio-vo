package org.egso.provider.service;

import java.io.File;
import java.util.Hashtable;
import java.util.jar.JarFile;

import org.apache.xerces.parsers.DOMParser;
import org.egso.provider.ProviderConfiguration;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.admin.ServiceMonitor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class ServiceLoader {

	private Hashtable<String,EGSOService> servicesPool = null;


	public ServiceLoader() {
		ProviderConfiguration conf = ProviderConfiguration.getInstance();
		boolean launch = ((String) conf.getProperty("service.load-on-startup")).equals("true");
		String[] dirs = (String[]) conf.getProperty("services.directories");
		init(launch, dirs);
	}
	
	private void init(boolean loadOnStartup, String[] dirs) {
		servicesPool = new Hashtable<String,EGSOService>();
		if (loadOnStartup) {
			for (int i = 0 ; i < dirs.length ; i++) {
				loadServices(dirs[i]);
			}
		}
	}

	public boolean hasService(String id) {
		return (servicesPool.containsKey(id));
	}

	public EGSOService getService(String id) {
		return ((EGSOService) servicesPool.get(id));
	}

	public void stopService(String id) {
		EGSOService service = (EGSOService) servicesPool.get(id);
		if (service != null) {
			
		}
	}

	public void restartService(String id) {
		EGSOService service = (EGSOService) servicesPool.get(id);
		if (service != null) {
			
		}
	}

	public void loadServices(String directory) {
		System.out.println("[SERVICELOADER] Loading EGSO Services in the directory '" + directory + "'.");
		File f = new File(directory);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0 ; i < files.length ; i++) {
				System.out.println("Loading EGSO Service contained in the file " + files[i].getName());
				if (files[i].getName().endsWith(".jar")) {
					loadService(directory + "/" + files[i].getName());
				}
			}
		} else {
			//System.out.println("[WARNING - SERVICELOADER]: '" + directory + "' NOT A DIRECTORY.");
		}
	}


	public void loadService(String jarFile) {
		try {
			JarFile jar = new JarFile(jarFile);
			InputSource in = new InputSource(jar.getInputStream(jar.getJarEntry("conf/service.xml")));
			DOMParser domParser = new DOMParser();
			domParser.parse(in);
			Hashtable<String,String>[] hash = readConfFile(domParser.getDocument().getDocumentElement());
			ServiceDescriptor sd = new ServiceDescriptor(hash[0], hash[1]);
			String className = sd.getMainClass();
			System.out.println("Loading " + className + " class...");
			JarClassLoader jarLoader = new JarClassLoader(jarFile);
			Class<?> c = jarLoader.loadClass(className, true);
			System.out.println("CLASS NAME: " + c.getName());
			Object o = c.newInstance();
			if (o instanceof EGSOService) {
				EGSOService egso = (EGSOService) o;
				egso.setDescriptor(sd);
				ProviderMonitor monitor = ProviderMonitor.getInstance();
				monitor.addService(sd.getID(), new ServiceMonitor(egso));
				egso.start();
			} else {
				System.out.println("Not a service");
			}
		} catch (Exception e) {
			ProviderMonitor.getInstance().reportException(e);
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
  private Hashtable<String,String>[] readConfFile(Node conf) {
		Hashtable<String,String> properties = new Hashtable<String,String>();
		Hashtable<String,String> options = new Hashtable<String,String>();
		NodeList nl = conf.getChildNodes();
		NodeList nl2 = null;
		NodeList nl3 = null;
		Node n = null;
		Node n2 = null;
		Node n3 = null;
		String value = null;
		for (int i = 0 ; i < nl.getLength() ; i++) {
			n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				nl2 = n.getChildNodes();
				if (n.getNodeName().equals("description")) {
					// Get the Service description.
					for (int j = 0 ; j < nl2.getLength() ; j++) {
						n2 = nl2.item(j);
						if (n2.getNodeType() == Node.ELEMENT_NODE) {
							nl3 = n2.getChildNodes();
							for (int k = 0 ; k < nl3.getLength() ; k++) {
								n3 = nl3.item(k);
								if (n3.getNodeType() == Node.TEXT_NODE) {
									value = n3.getNodeValue().trim();
								}
							}
							properties.put(n2.getNodeName(), value);
						}
					}
				} else {
					if (n.getNodeName().equals("context")) {
						// Get information to run the Service.
						for (int j = 0 ; j < nl2.getLength() ; j++) {
							n2 = nl2.item(j);
							if (n2.getNodeType() == Node.ELEMENT_NODE) {
								if (n2.getNodeName().equals("main-class")) {
									properties.put("main-class", n2.getAttributes().getNamedItem("name").getNodeValue());
								} else {
									if (n2.getNodeName().equals("options")) {
										// Manage options.
										nl3 = n2.getChildNodes();
										for (int k = 0 ; k < nl3.getLength() ; k++) {
											n3 = nl3.item(k);
											if ((n3.getNodeType() == Node.ELEMENT_NODE) && (n3.getNodeName().equals("option"))) {
												options.put(n3.getAttributes().getNamedItem("name").getNodeValue(), n3.getAttributes().getNamedItem("value").getNodeValue());
											}
										}
									} else {
										System.out.println("SERVICELOADER - Don't know what to do with the node service/java/" + n2.getNodeValue());
									}
								}
							}
						}
					} else {
						System.out.println("SERVICELOADER - Don't know what to do with the node service/" + n.getNodeValue());
					}
				}
			}
		}
		return (new Hashtable[]{properties, options});
	}
	
}
