
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="method" type="{http://virtualsolar.org/VSO/VSOi}MethodItem" minOccurs="0"/>
 *         &lt;element name="info" type="{http://virtualsolar.org/VSO/VSOi}Info" minOccurs="0"/>
 *         &lt;element name="datacontainer" type="{http://virtualsolar.org/VSO/VSOi}DataContainer" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDataRequest", propOrder = {

})
public class GetDataRequest {

    protected MethodItem method;
    protected Info info;
    protected DataContainer datacontainer;

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link MethodItem }
     *     
     */
    public MethodItem getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodItem }
     *     
     */
    public void setMethod(MethodItem value) {
        this.method = value;
    }

    /**
     * Gets the value of the info property.
     * 
     * @return
     *     possible object is
     *     {@link Info }
     *     
     */
    public Info getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     * 
     * @param value
     *     allowed object is
     *     {@link Info }
     *     
     */
    public void setInfo(Info value) {
        this.info = value;
    }

    /**
     * Gets the value of the datacontainer property.
     * 
     * @return
     *     possible object is
     *     {@link DataContainer }
     *     
     */
    public DataContainer getDatacontainer() {
        return datacontainer;
    }

    /**
     * Sets the value of the datacontainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataContainer }
     *     
     */
    public void setDatacontainer(DataContainer value) {
        this.datacontainer = value;
    }

}
