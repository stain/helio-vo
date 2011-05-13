
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Wave complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Wave">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="wavemin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wavemax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="waveunit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wavetype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Wave", propOrder = {

})
public class Wave {

    @XmlElement(required = true)
    protected String wavemin;
    @XmlElement(required = true)
    protected String wavemax;
    @XmlElement(required = true)
    protected String waveunit;
    protected String wavetype;

    /**
     * Gets the value of the wavemin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWavemin() {
        return wavemin;
    }

    /**
     * Sets the value of the wavemin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWavemin(String value) {
        this.wavemin = value;
    }

    /**
     * Gets the value of the wavemax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWavemax() {
        return wavemax;
    }

    /**
     * Sets the value of the wavemax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWavemax(String value) {
        this.wavemax = value;
    }

    /**
     * Gets the value of the waveunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaveunit() {
        return waveunit;
    }

    /**
     * Sets the value of the waveunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaveunit(String value) {
        this.waveunit = value;
    }

    /**
     * Gets the value of the wavetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWavetype() {
        return wavetype;
    }

    /**
     * Sets the value of the wavetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWavetype(String value) {
        this.wavetype = value;
    }

}
