
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets.Extent;


/**
 * <p>Java class for QueryLogicalBlock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryLogicalBlock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="equals" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock"/>
 *           &lt;element name="different" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock"/>
 *           &lt;element name="not" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock"/>
 *           &lt;element name="or" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock"/>
 *           &lt;element name="contained" type="{http://soteriaspace.eu/schemas/datasets}Extent"/>
 *           &lt;element name="and" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock"/>
 *           &lt;element name="not-contained" type="{http://soteriaspace.eu/schemas/datasets}Extent"/>
 *           &lt;element name="bigger" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock"/>
 *           &lt;element name="lesser" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryLogicalBlock", propOrder = {
    "equalsOrDifferentOrNot"
})
public class QueryLogicalBlock {

    @XmlElementRefs({
        @XmlElementRef(name = "bigger", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "lesser", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "contained", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "and", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "not-contained", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "equals", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "different", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "not", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class),
        @XmlElementRef(name = "or", namespace = "http://soteriaspace.eu/schemas/query", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> equalsOrDifferentOrNot;

    /**
     * Gets the value of the equalsOrDifferentOrNot property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equalsOrDifferentOrNot property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEqualsOrDifferentOrNot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link Extent }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link Extent }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}
     * {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getEqualsOrDifferentOrNot() {
        if (equalsOrDifferentOrNot == null) {
            equalsOrDifferentOrNot = new ArrayList<JAXBElement<?>>();
        }
        return this.equalsOrDifferentOrNot;
    }

}
