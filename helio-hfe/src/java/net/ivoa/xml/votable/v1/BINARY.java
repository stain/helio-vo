
package net.ivoa.xml.votable.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}STREAM"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stream"
})
@XmlRootElement(name = "BINARY")
public class BINARY {

    @XmlElement(name = "STREAM", required = true)
    protected STREAM stream;

    /**
     * Gets the value of the stream property.
     * 
     * @return
     *     possible object is
     *     {@link STREAM }
     *     
     */
    public STREAM getSTREAM() {
        return stream;
    }

    /**
     * Sets the value of the stream property.
     * 
     * @param value
     *     allowed object is
     *     {@link STREAM }
     *     
     */
    public void setSTREAM(STREAM value) {
        this.stream = value;
    }

}
