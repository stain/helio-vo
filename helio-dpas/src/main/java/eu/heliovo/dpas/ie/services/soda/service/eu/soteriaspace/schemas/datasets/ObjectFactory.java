
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.soteriaspace.schemas.datasets package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.soteriaspace.schemas.datasets
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KeywordType }
     * 
     */
    public KeywordType createKeywordType() {
        return new KeywordType();
    }

    /**
     * Create an instance of {@link Extent }
     * 
     */
    public Extent createExtent() {
        return new Extent();
    }

    /**
     * Create an instance of {@link Wave }
     * 
     */
    public Wave createWave() {
        return new Wave();
    }

    /**
     * Create an instance of {@link Time }
     * 
     */
    public Time createTime() {
        return new Time();
    }

}
