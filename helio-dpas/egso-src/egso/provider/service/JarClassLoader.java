package org.egso.provider.service;


/**
 * JarClassLoader provides a minimalistic ClassLoader which shows how to
 * instantiate a class which resides in a .jar file. <br>
 * <br>
 *
 *
 * @author    John D. Mitchell, Non, Inc., Mar 3, 1999
 * @version   0.5
 */

public class JarClassLoader extends MultiClassLoader {
	/**
	 * JAVADOC: Description of the Field
	 */
	private JarResources jarResources;


	/**
	 * JAVADOC: Constructor for the JarClassLoader object
	 *
	 * @param jarName  JAVADOC: Description of the Parameter
	 */
	public JarClassLoader(String jarName) {
		// Create the JarResource and suck in the .jar file.
		jarResources = new JarResources(jarName);
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param className  JAVADOC: Description of the Parameter
	 * @return           JAVADOC: Description of the Return Value
	 */
	protected byte[] loadClassBytes(String className) {
		// Support the MultiClassLoader's class name munging facility.
		className = formatClassName(className);
		// Attempt to get the class data from the JarResource.
		return (jarResources.getResource(className));
	}
}

