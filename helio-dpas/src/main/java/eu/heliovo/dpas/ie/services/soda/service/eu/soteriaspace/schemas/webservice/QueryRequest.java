
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.webservice;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query.MainQueryLogicalBlockItem;


/**
 * <p>Java class for queryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="query" type="{http://soteriaspace.eu/schemas/query}MainQueryLogicalBlockItem"/>
 *         &lt;element name="force_update" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *       &lt;attribute name="request_id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryRequest", propOrder = {
    "query",
    "forceUpdate",
    "offset"
})
public class QueryRequest {

    @XmlElement(required = true)
    protected MainQueryLogicalBlockItem query;
    @XmlElement(name = "force_update")
    protected boolean forceUpdate;
    protected long offset;
    @XmlAttribute(name = "request_id")
    protected BigInteger requestId;

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link MainQueryLogicalBlockItem }
     *     
     */
    public MainQueryLogicalBlockItem getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link MainQueryLogicalBlockItem }
     *     
     */
    public void setQuery(MainQueryLogicalBlockItem value) {
        this.query = value;
    }

    /**
     * Gets the value of the forceUpdate property.
     * 
     */
    public boolean isForceUpdate() {
        return forceUpdate;
    }

    /**
     * Sets the value of the forceUpdate property.
     * 
     */
    public void setForceUpdate(boolean value) {
        this.forceUpdate = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     */
    public long getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     */
    public void setOffset(long value) {
        this.offset = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequestId(BigInteger value) {
        this.requestId = value;
    }

}
