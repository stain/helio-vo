
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.details;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets.KeywordType;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets.Time;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets.Wave;


/**
 * <p>Java class for recordDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="instrument" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="time" type="{http://soteriaspace.eu/schemas/datasets}Time"/>
 *         &lt;element name="wavelengths" type="{http://soteriaspace.eu/schemas/datasets}Wave"/>
 *         &lt;element name="keyword" type="{http://soteriaspace.eu/schemas/datasets}keywordType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="access">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="ftp" type="{http://soteriaspace.eu/schemas/details}ftp"/>
 *                     &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="rest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *                 &lt;attribute name="thumbnail" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordDetail", propOrder = {
    "provider",
    "instrument",
    "type",
    "time",
    "wavelengths",
    "keyword",
    "access"
})
public class RecordDetail {

    @XmlElement(required = true)
    protected String provider;
    @XmlElement(required = true)
    protected String instrument;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected Time time;
    @XmlElement(required = true)
    protected Wave wavelengths;
    @XmlElement(nillable = true)
    protected List<KeywordType> keyword;
    @XmlElement(required = true)
    protected RecordDetail.Access access;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

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
     * Gets the value of the wavelengths property.
     * 
     * @return
     *     possible object is
     *     {@link Wave }
     *     
     */
    public Wave getWavelengths() {
        return wavelengths;
    }

    /**
     * Sets the value of the wavelengths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wave }
     *     
     */
    public void setWavelengths(Wave value) {
        this.wavelengths = value;
    }

    /**
     * Gets the value of the keyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyword().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeywordType }
     * 
     * 
     */
    public List<KeywordType> getKeyword() {
        if (keyword == null) {
            keyword = new ArrayList<KeywordType>();
        }
        return this.keyword;
    }

    /**
     * Gets the value of the access property.
     * 
     * @return
     *     possible object is
     *     {@link RecordDetail.Access }
     *     
     */
    public RecordDetail.Access getAccess() {
        return access;
    }

    /**
     * Sets the value of the access property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordDetail.Access }
     *     
     */
    public void setAccess(RecordDetail.Access value) {
        this.access = value;
    }


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
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="ftp" type="{http://soteriaspace.eu/schemas/details}ftp"/>
     *           &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *           &lt;element name="rest" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *       &lt;attribute name="thumbnail" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id",
        "ftpOrUriOrRest"
    })
    public static class Access {

        @XmlElement(required = true)
        protected String id;
        @XmlElementRefs({
            @XmlElementRef(name = "ftp", type = JAXBElement.class),
            @XmlElementRef(name = "uri", type = JAXBElement.class),
            @XmlElementRef(name = "rest", type = JAXBElement.class)
        })
        protected List<JAXBElement<?>> ftpOrUriOrRest;
        @XmlAttribute
        protected Boolean thumbnail;

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the ftpOrUriOrRest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ftpOrUriOrRest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFtpOrUriOrRest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link String }{@code >}
         * {@link JAXBElement }{@code <}{@link String }{@code >}
         * {@link JAXBElement }{@code <}{@link Ftp }{@code >}
         * 
         * 
         */
        public List<JAXBElement<?>> getFtpOrUriOrRest() {
            if (ftpOrUriOrRest == null) {
                ftpOrUriOrRest = new ArrayList<JAXBElement<?>>();
            }
            return this.ftpOrUriOrRest;
        }

        /**
         * Gets the value of the thumbnail property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isThumbnail() {
            return thumbnail;
        }

        /**
         * Sets the value of the thumbnail property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setThumbnail(Boolean value) {
            this.thumbnail = value;
        }

    }

}
