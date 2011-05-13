
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VSOGetDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VSOGetDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getdataresponseitem" type="{http://virtualsolar.org/VSO/VSOi}GetDataResponseItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VSOGetDataResponse", propOrder = {
    "getdataresponseitem"
})
public class VSOGetDataResponse {

    protected List<GetDataResponseItem> getdataresponseitem;

    /**
     * Gets the value of the getdataresponseitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getdataresponseitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetdataresponseitem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetDataResponseItem }
     * 
     * 
     */
    public List<GetDataResponseItem> getGetdataresponseitem() {
        if (getdataresponseitem == null) {
            getdataresponseitem = new ArrayList<GetDataResponseItem>();
        }
        return this.getdataresponseitem;
    }

}
