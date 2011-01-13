
package ch.i4ds.helio.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for run_initial_workflow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="run_initial_workflow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date_from" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_to" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goes_min" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goes_max" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "run_initial_workflow", propOrder = {
    "dateFrom",
    "dateTo",
    "goesMin",
    "goesMax"
})
public class RunInitialWorkflow {

    @XmlElement(name = "date_from")
    protected String dateFrom;
    @XmlElement(name = "date_to")
    protected String dateTo;
    @XmlElement(name = "goes_min")
    protected String goesMin;
    @XmlElement(name = "goes_max")
    protected String goesMax;

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFrom(String value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTo(String value) {
        this.dateTo = value;
    }

    /**
     * Gets the value of the goesMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoesMin() {
        return goesMin;
    }

    /**
     * Sets the value of the goesMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoesMin(String value) {
        this.goesMin = value;
    }

    /**
     * Gets the value of the goesMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoesMax() {
        return goesMax;
    }

    /**
     * Sets the value of the goesMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoesMax(String value) {
        this.goesMax = value;
    }

}
