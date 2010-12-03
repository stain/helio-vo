
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryLogicalBlockItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryLogicalBlockItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="or" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock" minOccurs="0"/>
 *         &lt;element name="and" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock" minOccurs="0"/>
 *         &lt;element name="not" type="{http://soteriaspace.eu/schemas/query}QueryLogicalBlock" minOccurs="0"/>
 *         &lt;element name="lesser" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock" minOccurs="0"/>
 *         &lt;element name="bigger" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock" minOccurs="0"/>
 *         &lt;element name="equals" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock" minOccurs="0"/>
 *         &lt;element name="different" type="{http://soteriaspace.eu/schemas/query}QueryRelationBlock" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryLogicalBlockItem", propOrder = {
    "or",
    "and",
    "not",
    "lesser",
    "bigger",
    "equals",
    "different"
})
public class QueryLogicalBlockItem {

    protected QueryLogicalBlock or;
    protected QueryLogicalBlock and;
    protected QueryLogicalBlock not;
    protected QueryRelationBlock lesser;
    protected QueryRelationBlock bigger;
    protected QueryRelationBlock equals;
    protected QueryRelationBlock different;

    /**
     * Gets the value of the or property.
     * 
     * @return
     *     possible object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public QueryLogicalBlock getOr() {
        return or;
    }

    /**
     * Sets the value of the or property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public void setOr(QueryLogicalBlock value) {
        this.or = value;
    }

    /**
     * Gets the value of the and property.
     * 
     * @return
     *     possible object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public QueryLogicalBlock getAnd() {
        return and;
    }

    /**
     * Sets the value of the and property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public void setAnd(QueryLogicalBlock value) {
        this.and = value;
    }

    /**
     * Gets the value of the not property.
     * 
     * @return
     *     possible object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public QueryLogicalBlock getNot() {
        return not;
    }

    /**
     * Sets the value of the not property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryLogicalBlock }
     *     
     */
    public void setNot(QueryLogicalBlock value) {
        this.not = value;
    }

    /**
     * Gets the value of the lesser property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRelationBlock }
     *     
     */
    public QueryRelationBlock getLesser() {
        return lesser;
    }

    /**
     * Sets the value of the lesser property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRelationBlock }
     *     
     */
    public void setLesser(QueryRelationBlock value) {
        this.lesser = value;
    }

    /**
     * Gets the value of the bigger property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRelationBlock }
     *     
     */
    public QueryRelationBlock getBigger() {
        return bigger;
    }

    /**
     * Sets the value of the bigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRelationBlock }
     *     
     */
    public void setBigger(QueryRelationBlock value) {
        this.bigger = value;
    }

    /**
     * Gets the value of the equals property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRelationBlock }
     *     
     */
    public QueryRelationBlock getEquals() {
        return equals;
    }

    /**
     * Sets the value of the equals property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRelationBlock }
     *     
     */
    public void setEquals(QueryRelationBlock value) {
        this.equals = value;
    }

    /**
     * Gets the value of the different property.
     * 
     * @return
     *     possible object is
     *     {@link QueryRelationBlock }
     *     
     */
    public QueryRelationBlock getDifferent() {
        return different;
    }

    /**
     * Sets the value of the different property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryRelationBlock }
     *     
     */
    public void setDifferent(QueryRelationBlock value) {
        this.different = value;
    }

}
