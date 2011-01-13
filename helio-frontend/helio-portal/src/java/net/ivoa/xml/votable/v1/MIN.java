
package net.ivoa.xml.votable.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="valuemin" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inclusive" type="{http://www.ivoa.net/xml/VOTable/v1.1}yesno" default="yes" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MIN")
public class MIN {

    @XmlAttribute(required = true)
    protected String valuemin;
    @XmlAttribute
    protected Yesno inclusive;

    /**
     * Gets the value of the valuemin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuemin() {
        return valuemin;
    }

    /**
     * Sets the value of the valuemin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuemin(String value) {
        this.valuemin = value;
    }

    /**
     * Gets the value of the inclusive property.
     * 
     * @return
     *     possible object is
     *     {@link Yesno }
     *     
     */
    public Yesno getInclusive() {
        if (inclusive == null) {
            return Yesno.YES;
        } else {
            return inclusive;
        }
    }

    /**
     * Sets the value of the inclusive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Yesno }
     *     
     */
    public void setInclusive(Yesno value) {
        this.inclusive = value;
    }

}
