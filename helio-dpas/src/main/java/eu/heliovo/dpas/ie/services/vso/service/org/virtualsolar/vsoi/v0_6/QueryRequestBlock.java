
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryRequestBlock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryRequestBlock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="time" type="{http://virtualsolar.org/VSO/VSOi}Time"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instrument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="physobs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wave" type="{http://virtualsolar.org/VSO/VSOi}Wave" minOccurs="0"/>
 *         &lt;element name="extent" type="{http://virtualsolar.org/VSO/VSOi}Extent" minOccurs="0"/>
 *         &lt;element name="field" type="{http://virtualsolar.org/VSO/VSOi}Field" minOccurs="0"/>
 *         &lt;element name="pixels" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resolution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sample" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quicklook" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRequestBlock", propOrder = {

})
public class QueryRequestBlock {

    @XmlElement(required = true)
    protected Time time;
    protected String provider;
    protected String source;
    protected String instrument;
    protected String physobs;
    protected Wave wave;
    protected Extent extent;
    protected Field field;
    protected String pixels;
    protected String level;
    protected String resolution;
    protected String detector;
    protected String filter;
    protected String sample;
    protected String quicklook;
    protected String pscale;

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setTime(Time value) {
        this.time = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvider(String value) {
        this.provider = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the instrument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrument() {
        return instrument;
    }

    /**
     * Sets the value of the instrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstrument(String value) {
        this.instrument = value;
    }

    /**
     * Gets the value of the physobs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysobs() {
        return physobs;
    }

    /**
     * Sets the value of the physobs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysobs(String value) {
        this.physobs = value;
    }

    /**
     * Gets the value of the wave property.
     * 
     * @return
     *     possible object is
     *     {@link Wave }
     *     
     */
    public Wave getWave() {
        return wave;
    }

    /**
     * Sets the value of the wave property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wave }
     *     
     */
    public void setWave(Wave value) {
        this.wave = value;
    }

    /**
     * Gets the value of the extent property.
     * 
     * @return
     *     possible object is
     *     {@link Extent }
     *     
     */
    public Extent getExtent() {
        return extent;
    }

    /**
     * Sets the value of the extent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extent }
     *     
     */
    public void setExtent(Extent value) {
        this.extent = value;
    }

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link Field }
     *     
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link Field }
     *     
     */
    public void setField(Field value) {
        this.field = value;
    }

    /**
     * Gets the value of the pixels property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPixels() {
        return pixels;
    }

    /**
     * Sets the value of the pixels property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPixels(String value) {
        this.pixels = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResolution(String value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the detector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetector() {
        return detector;
    }

    /**
     * Sets the value of the detector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetector(String value) {
        this.detector = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilter(String value) {
        this.filter = value;
    }

    /**
     * Gets the value of the sample property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSample() {
        return sample;
    }

    /**
     * Sets the value of the sample property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSample(String value) {
        this.sample = value;
    }

    /**
     * Gets the value of the quicklook property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuicklook() {
        return quicklook;
    }

    /**
     * Sets the value of the quicklook property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuicklook(String value) {
        this.quicklook = value;
    }

    /**
     * Gets the value of the pscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPscale() {
        return pscale;
    }

    /**
     * Sets the value of the pscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPscale(String value) {
        this.pscale = value;
    }

}
