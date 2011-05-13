
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataRequestItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataRequestItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileiditem" type="{http://virtualsolar.org/VSO/VSOi}FileidItem"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataRequestItem", propOrder = {

})
public class DataRequestItem {

    @XmlElement(required = true)
    protected String provider;
    @XmlElement(required = true)
    protected FileidItem fileiditem;

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
     * Gets the value of the fileiditem property.
     * 
     * @return
     *     possible object is
     *     {@link FileidItem }
     *     
     */
    public FileidItem getFileiditem() {
        return fileiditem;
    }

    /**
     * Sets the value of the fileiditem property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileidItem }
     *     
     */
    public void setFileiditem(FileidItem value) {
        this.fileiditem = value;
    }

}
