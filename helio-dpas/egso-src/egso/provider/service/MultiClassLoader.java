package org.egso.provider.service;

import java.util.Hashtable;

import org.egso.provider.admin.ProviderMonitor;


/**
 * A simple test class loader capable of loading from multiple sources, such as
 * local files or a URL. This class is derived from an article by Chuck McManis
 * http://www.javaworld.com/javaworld/jw-10-1996/indepth.src.html with large
 * modifications. Note that this has been updated to use the non-deprecated
 * version of defineClass() -- JDM.
 *
 * @author    Jack Harich - 8/18/97
 * @author    John D. Mitchell - 99.03.04
 * @version
 */
public abstract class MultiClassLoader extends ClassLoader {

//---------- Fields --------------------------------------
	/**
	 * JAVADOC: Description of the Field
	 */
	private Hashtable<String,Class<?>> classes = new Hashtable<String,Class<?>>();
	/**
	 * JAVADOC: Description of the Field
	 */
	private char classNameReplacementChar;

	/**
	 * JAVADOC: Description of the Field
	 */
	protected boolean monitorOn = false;
	/**
	 * JAVADOC: Description of the Field
	 */
	protected boolean sourceMonitorOn = true;


//---------- Initialization ------------------------------
	/**
	 * JAVADOC: Constructor for the MultiClassLoader object
	 */
	public MultiClassLoader() {
	}

//---------- Public Methods ------------------------------

	/**
	 * This optional call allows a class name such as "COM.test.Hello" to be
	 * changed to "COM_test_Hello", which is useful for storing classes from
	 * different packages in the same retrival directory. In the above example the
	 * char would be '_'.
	 *
	 * @param replacement  JAVADOC: The new classNameReplacementChar value
	 */
	public void setClassNameReplacementChar(char replacement) {
		classNameReplacementChar = replacement;
	}

//---------- Superclass Overrides ------------------------

	/**
	 * This is a simple version for external clients since they will always want
	 * the class resolved before it is returned to them.
	 *
	 * @param className                   JAVADOC: Description of the Parameter
	 * @return                            JAVADOC: Description of the Return Value
	 * @exception ClassNotFoundException  JAVADOC: Description of the Exception
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return (loadClass(className, true));
	}

//---------- Abstract Implementation ---------------------

	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param className                   JAVADOC: Description of the Parameter
	 * @param resolveIt                   JAVADOC: Description of the Parameter
	 * @return                            JAVADOC: Description of the Return Value
	 * @exception ClassNotFoundException  JAVADOC: Description of the Exception
	 */
	public synchronized Class<?> loadClass(String className,
			boolean resolveIt) throws ClassNotFoundException {

		Class<?> result;
		byte[] classBytes;
		monitor(">> MultiClassLoader.loadClass(" + className + ", " + resolveIt + ")");

		//----- Check our local cache of classes
		result = classes.get(className);
		if (result != null) {
			monitor(">> returning cached result.");
			return result;
		}

		//----- Check with the primordial class loader
		try {
			result = super.findSystemClass(className);
			monitor(">> returning system class (in CLASSPATH).");
			return result;
		} catch (ClassNotFoundException e) {
			ProviderMonitor.getInstance().reportException(e);
			monitor(">> Not a system class.");
		}

		//----- Try to load it from preferred source
		// Note loadClassBytes() is an abstract method
		classBytes = loadClassBytes(className);
		if (classBytes == null) {
			throw new ClassNotFoundException();
		}

		//----- Define it (parse the class file)
		result = defineClass(className, classBytes, 0, classBytes.length);
		if (result == null) {
			throw new ClassFormatError();
		}

		//----- Resolve if necessary
		if (resolveIt) {
			resolveClass(result);
		}

		// Done
		classes.put(className, result);
		monitor(">> Returning newly loaded class.");
		return result;
	}

//---------- Protected Methods ---------------------------

	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param className  JAVADOC: Description of the Parameter
	 * @return           JAVADOC: Description of the Return Value
	 */
	protected abstract byte[] loadClassBytes(String className);


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param className  JAVADOC: Description of the Parameter
	 * @return           JAVADOC: Description of the Return Value
	 */
	protected String formatClassName(String className) {
		if (classNameReplacementChar == '\u0000') {
			// '/' is used to map the package to the path
			return className.replace('.', '/') + ".class";
		} else {
			// Replace '.' with custom char, such as '_'
			return className.replace('.',
					classNameReplacementChar) + ".class";
		}
	}


	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param text  JAVADOC: Description of the Parameter
	 */
	protected void monitor(String text) {
		if (monitorOn) {
			print(text);
		}
	}

//--- Std

	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param text  JAVADOC: Description of the Parameter
	 */
	protected static void print(String text) {
		System.out.println(text);
	}

}

