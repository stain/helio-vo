
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Thumbnail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Thumbnail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="hires" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lowres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Thumbnail", propOrder = {

})
public class Thumbnail {

    protected String hires;
    protected String lowres;

    /**
     * Gets the value of the hires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHires() {
        return hires;
    }

    /**
     * Sets the value of the hires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHires(String value) {
        this.hires = value;
    }

    /**
     * Gets the value of the lowres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLowres() {
        return lowres;
    }

    /**
     * Sets the value of the lowres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLowres(String value) {
        this.lowres = value;
    }

}
