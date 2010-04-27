package org.egso.common.configuration;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalXMLConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;


/**
 * <p>The NamedConfigurationFactory creates and stores named configurations 
 * for any software module. Each module can define as many named configurations as needed.
 * It should use the optional submodules argument to distinct them.</p>
 * <p>Each named configuration contains a set of xml or properties configuration files that 
 * match a specific file name structure: prefix, module name, optional submodule name
 * and postfix (see below). For each named configuration a default configuration file with the 
 * prefix <code>default-</code>) should exist. To override the default properties additional 
 * files with localized values and the prefix <code>local-</code> or <code>test-</code> 
 * may be provided in the same path context as the <code>default-</code> configuration.</p>
 * <p>As name of the NamedConfiguration any String is accepted. However it is adviced to use the 
 * full name of the modules package (e.g. "foo.bar.mymodule").</p>
 * 
 * <p>A sample xml file looks like this<br/>
 * <pre>
 *   <b>default-simple-test.xml</b>
 *   &lt;configuration&gt;
 *     &lt;parent1&gt;
 *       &lt;child1 attr="attrvalue1"&gt;
 *         value1
 *       &lt;/child1&gt;
 *     &lt;/parent1&gt;
 *     &lt;parent2&gt;
 *       &lt;child2 attr="attrvalue2"&gt;
 *         value2
 *       &lt;/child2&gt;
 *     &lt;/parent2&gt;
 *   &lt;/configuration&gt;
 * 
 *   <b>local-simple-test.xml</b> (overwrite parent1)
 *   &lt;configuration&gt;
 *     &lt;parent1&gt;
 *       &lt;child1 attr="attrvalue1"&gt;
 *         value1
 *       &lt;/child1&gt;
 *     &lt;/parent1&gt;
 *   &lt;/configuration&gt;
 * </pre>
 * </p>
 * 
 * <p>At module startup the configuration is intialized by calling the static 
 * {@link #addNamedConfiguration(String, String, String) addNamedConfiguration(name, path, module)} or
 * {@link #addNamedConfiguration(String, String, String, String) 
 * addNamedConfiguration(name, path, module, submodule)}.<br/>
 * This can be done in a static section in the module's main class or during intialisation of the module. e.g:<br/>
 * <pre>
 * static
 * {
 *     try {
 *         NamedConfigurationFactory.addNamedConfiguration("foo.bar", "/data/conf", "mymodule", "mysubmodule");
 *     } catch (Exception ex) {
 *         ex.printStacktrace();
 *     }
 * }
 * </pre>
 * </p>
 * <p>To get a reference to a configuration object use:<br/>
 * <pre>
 *     Configuration configuration = NamedConfigurationFactory.getConfigurationByName("foo.bar");
 * </pre>
 * </p>
 * <p>To access a property use the notation as explained in
 * {@link org.apache.commons.configuration.Configuration org.apache.commons.configuration.Configuration}
 * <pre>
 *    configuration.getString("parent1.child1");
 *    configuration.getString("parent1.child1[@attr]");
 * </pre>
 * </p>
 * <p>The NamedConfigurationFactory tries to locate the configuration files<br/>
 * <ol>
 *   <li>in the classpath through {@link java.lang.Class#getResource(java.lang.String) java.lang.Class.getResource(String)}.
 *       The path argument of the named configuration must be relative to the classpath. 
 *       Hence a default-file can be bundled in a jar while a local- configuration can be 
 *       in any other place in the classpath that points to the same relative path </li>
 *   <li>in the current working directory if the specified path is relative, 
 *       or from the absolute location if the specified path is absolute.</li>
 * </ol>
 * </p>
 * <p>
 * The file names must match this schema:<br/>
 * <pre>
 *   filename  ::= prefix<b>-</b>module(<b>-</b>submodule)?<b>.</b>(postfix)<br>
 *   prefix    ::= <b>default</b> | <b>local</b> | <b>test</b>
 *   module    ::= <b>eis</b> | <b>consumer</b> | <b>broker</b> | <b>...</b>  
 *   submodule ::= <b>ssr</b> | <b>core</b> | ...
 *   postfix   ::= <b>xml</b> | <b>properties</b>
 * </pre>
 * </p>
 * <p>
 * Currently the following type of property files are supported.
 * <ul>
 *   <li><code>*.xml</code>: XML files</li>
 *   <li><code>*.properties</code>: Java Property files</li>
 * </ul>
 * </p>
 * <p>
 * By default the configuration files are loaded in the following sequence: <br/>
 * <ol>
 *   <li>Load all files starting with <code>test-*</code>:
 *       Configuration files that are used for testing.
 *   </li>
 *   <li>Load all files starting with <code>local-*</code>:
 *       Localized configuration for an installation of a module.
 *   </li>
 *   <li>Load all files starting with <code>default-*</code>: 
 *       These files contain default properties that are common to all installations of a module.
 *       They must not be modified.</li>
 * </ol>
 * </p>
 * 
 * @author Marco Soldati
 * @version 18.07.2004
 */
@SuppressWarnings("deprecation")
public class NamedConfigurationFactory
{        
    /**
     * Keep a reference to the singleton configurationFactory. 
     */
    private static Map<String,NamedConfiguration> configMap = new HashMap<String,NamedConfiguration>(); /*Configuration*/
    
    
    /**
     * Create and return a new Configuration object for the submitted settings. 
     * The Configurations is stored and can be reloaded by getConfigurationByName   
     * @param configName name of the configuration
     * @param path       path of the configuration files. 
     * @param module     name of the module
     * @param submodule  name of the submodule or null if no submodule.
     * @return a org.apache.commons.configuration.Configuration or null if the configuration does not exist. 
     */
    public static Configuration addNamedConfiguration(String configName, String path, String module, String submodule)    
    {
        if (getConfigurationByName(configName) != null)
            throw new IllegalArgumentException("Configuration with name " + configName + " already exists.");
        
        NamedConfiguration namedConfig = new NamedConfiguration(configName, path, module, submodule);
        
        configMap.put(configName, namedConfig);
        return namedConfig.getConfiguration();
    }
    
    /**
     * Create and return a new Configuration object for the submitted settings.
     * Overloads {@link #addNamedConfiguration(String configName, String path, String module, String submodule)
     * addNamedConfiguration(String configName, String path, String module, String submodule)}
     * and sets submodule to null
     * @param configName name of the configuration
     * @param path       path of the configuration files. 
     * @param module     name of the module
     * @return a org.apache.commons.configuration.Configuration or null if the configuration does not exist. 
     */
    public static Configuration addNamedConfiguration(String configName, String path, String module)
    {
        return addNamedConfiguration(configName, path, module, null);
    }
    
    /**
     * This method removes the named configuration and releases any resources. 
     * @param configName
     */
    public static void removeNamedConfiguration(String configName)
    {
        synchronized(configMap)
        {
            NamedConfiguration config = configMap.get(configName);
            if (config != null)
            {
                config.release();
                configMap.remove(config);
            }
        }    
    }
    
    /**
     * Replace an existing configuration by a new one or just add it if there 
     * is no configuration with the same name 
     * @param configName name of the configuration
     * @param path       path of the configuration files. 
     * @param module     name of the module
     * @param submodule  name of the submodule or null if no submodule.
     * @return a org.apache.commons.configuration.Configuration or null if the configuration does not exist. 
     */
    public Configuration replaceConfiguration(String configName, String path, String module, String submodule)
    {
        removeNamedConfiguration(configName);
        return addNamedConfiguration(configName, path, module, submodule);
    }
    
    /**
     * Replace an existing configuration by a new one or just add it if there 
     * is no configuration with the same name 
     * @param configName name of the configuration
     * @param path       path of the configuration files. 
     * @param module     name of the module
     * @return a org.apache.commons.configuration.Configuration or null if the configuration does not exist. 
     */
    public Configuration replaceConfiguration(String configName, String path, String module)
    {
        removeNamedConfiguration(configName);
        return addNamedConfiguration(configName, path, module);
    }
    
    /**
     * Get a configuration by its name or null if the configuration does not exist.
     * @param configName name of the configuration object.
     * @return a org.apache.commons.configuration.Configuration or null if the configuration does not exist. 
     */
    public static Configuration getConfigurationByName(String configName)
    {
        NamedConfiguration config = configMap.get(configName);
        if (config != null)
            return config.getConfiguration();
        return null;
    }
    
    /**
     * Hide the constructor for public access.
     */
    private NamedConfigurationFactory()
    {
    }
}

/**
 * Simple data bean that holds a named configuration.
 * @author Marco Soldati
 * @created 20.08.2004
 */
class NamedConfiguration
{
    /**
     * the default prefix sequence for the configuration files.
     */
    private static final String[] DEFAULT_PREFIX_SEQUENCE = {"test", "local", "default"}; 
        
    /**
     * the default delimiter in the file names.
     */
    private static final String DEFAULT_DELIMITER = "-";
        
    /**
     * the delimiter for the file names
     */
    private String delimiter = DEFAULT_DELIMITER;
    
    /**
     * holds the prefix sequence
     */
    private String[] prefixSequence = DEFAULT_PREFIX_SEQUENCE; 
    
        
    private String name = null;
    private String path = null;
    private String module = null;
    private String submodule = null;        
    
    private Configuration configuration = null;

    /**
     * Initialize the NamedConfiguration and load the configuration object.
     * @param name
     * @param path
     * @param module
     * @param submodule
     */
    protected NamedConfiguration(String name, String path, String module, String submodule)
    {
        if (name == null)
            throw new IllegalArgumentException("'name' of configuration must not be null.");
        if (path == null)
            throw new IllegalArgumentException("'path' of configuration must not be null.");
        if (module == null)
            throw new IllegalArgumentException("'module' of configuration must not be null.");

        this.name = name;
        
        // make sure the path is in a proper format
        if (!(path.endsWith("/") || path.endsWith("\\")))
            this.path = path + "/";
        else
            this.path = path;
        
        this.module = module;
        this.submodule = submodule;
        
        initialize();
    }

    
    @SuppressWarnings("deprecation")
    private synchronized void initialize()
    {
        CompositeConfiguration config = null;
        
        // create a new composite Configuration
        config = new CompositeConfiguration();
        
        // loop through the config sequence and load files
        for (int i = 0; i < prefixSequence.length; i++)
        {
            boolean found = false;
            StringBuffer sb = new StringBuffer();
            sb.append(path)
            .append(prefixSequence[i])
            .append(delimiter)
            .append(module);
            if (submodule != null)
                sb.append(delimiter).append(submodule);
            
            String fileNamePart1 = sb.toString();            
            // try to find the xml
            URL configFile = findConfigFile(fileNamePart1 + ".xml");
                        
            if (configFile != null)
            {
                found = true;
                try
                {
                    HierarchicalXMLConfiguration xmlConfig = new HierarchicalXMLConfiguration(); 
                    xmlConfig.load(configFile);
                    config.addConfiguration(xmlConfig);
                }
                catch (Exception e)
                {
                    Logger.getLogger(NamedConfigurationFactory.class)
                    .warn(e.getClass().getName() + " while loading configuration '" + configFile.getFile() 
                            + "' :" + e.getMessage(), e);
                }
            }

            // try to find properties
            configFile = findConfigFile(fileNamePart1 + ".properties");
            if (configFile != null)
            {
                found = true;
                try
                {
                    config.addConfiguration(new PropertiesConfiguration(configFile.getFile()));
                }
                catch (Exception e)
                {
                    Logger.getLogger(NamedConfigurationFactory.class)
                    .warn(e.getClass().getName() + " while loading configuration '" + configFile.getFile() 
                            + "' :" + e.getMessage(), e);
                }
            }
            
            if (i == prefixSequence.length - 1 && found == false)
            {
                Logger.getLogger(NamedConfigurationFactory.class)
                    .warn("no default configuration with prefix '" + prefixSequence[i] + delimiter + "' found!");
            }
        }
        
        this.configuration = config;
    }
    

    /**
     * Cleanup everything.
     *
     */
    public void release()
    {
        name = null;
        module = null;
        submodule = null;
        path = null;
        configuration = null;
    }
    
    /**
     * Tries to find the file in the resources and then in current path. 
     * @param fileName
     * @return
     */
    @SuppressWarnings("deprecation")
    private URL findConfigFile(String fileName)
    {
        //System.out.println("looking for file " + fileName);
        
        URL fileUrl = null; 
        
        // adust the fileName if needed
        fileUrl = getClass().getResource(
                (fileName.startsWith("/") || fileName.startsWith("\\")) ? fileName : "/" + fileName);
        
        if (fileUrl != null)
            return fileUrl;
       

        File file = new File(fileName);
        
        try {
            if (file != null && file.exists())
                return file.toURL();
        } 
        catch (java.net.MalformedURLException e) 
        {
            // ignore
        }
        
        return null;
    }
    
    
    /**
     * @return Returns the configuration.
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }        
    
    /**
     * @return Returns the module.
     */
    public String getModule()
    {
        return module;
    }
    
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return Returns the path.
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @return Returns the submodule.
     */
    public String getSubmodule()
    {
        return submodule;
    }        
}