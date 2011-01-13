
package net.ivoa.xml.votable.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}COOSYS"/>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}PARAM"/>
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
    "coosysOrPARAM"
})
@XmlRootElement(name = "DEFINITIONS")
public class DEFINITIONS {

    @XmlElements({
        @XmlElement(name = "COOSYS", type = COOSYS.class),
        @XmlElement(name = "PARAM", type = PARAM.class)
    })
    protected List<Object> coosysOrPARAM;

    /**
     * Gets the value of the coosysOrPARAM property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coosysOrPARAM property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCOOSYSOrPARAM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COOSYS }
     * {@link PARAM }
     * 
     * 
     */
    public List<Object> getCOOSYSOrPARAM() {
        if (coosysOrPARAM == null) {
            coosysOrPARAM = new ArrayList<Object>();
        }
        return this.coosysOrPARAM;
    }

}
