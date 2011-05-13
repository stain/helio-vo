
package eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryResponseBlockArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryResponseBlockArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="recorditem" type="{http://virtualsolar.org/VSO/VSOi}QueryResponseBlock" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResponseBlockArray", propOrder = {
    "recorditem"
})
public class QueryResponseBlockArray {

    protected List<QueryResponseBlock> recorditem;

    /**
     * Gets the value of the recorditem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recorditem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecorditem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryResponseBlock }
     * 
     * 
     */
    public List<QueryResponseBlock> getRecorditem() {
        if (recorditem == null) {
            recorditem = new ArrayList<QueryResponseBlock>();
        }
        return this.recorditem;
    }

}
