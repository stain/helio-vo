
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
 *       &lt;choice>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}TABLEDATA"/>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}BINARY"/>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}FITS"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tabledata",
    "binary",
    "fits"
})
@XmlRootElement(name = "DATA")
public class DATA {

    @XmlElement(name = "TABLEDATA")
    protected TABLEDATA tabledata;
    @XmlElement(name = "BINARY")
    protected BINARY binary;
    @XmlElement(name = "FITS")
    protected FITS fits;

    /**
     * Gets the value of the tabledata property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEDATA }
     *     
     */
    public TABLEDATA getTABLEDATA() {
        return tabledata;
    }

    /**
     * Sets the value of the tabledata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEDATA }
     *     
     */
    public void setTABLEDATA(TABLEDATA value) {
        this.tabledata = value;
    }

    /**
     * Gets the value of the binary property.
     * 
     * @return
     *     possible object is
     *     {@link BINARY }
     *     
     */
    public BINARY getBINARY() {
        return binary;
    }

    /**
     * Sets the value of the binary property.
     * 
     * @param value
     *     allowed object is
     *     {@link BINARY }
     *     
     */
    public void setBINARY(BINARY value) {
        this.binary = value;
    }

    /**
     * Gets the value of the fits property.
     * 
     * @return
     *     possible object is
     *     {@link FITS }
     *     
     */
    public FITS getFITS() {
        return fits;
    }

    /**
     * Sets the value of the fits property.
     * 
     * @param value
     *     allowed object is
     *     {@link FITS }
     *     
     */
    public void setFITS(FITS value) {
        this.fits = value;
    }

}
