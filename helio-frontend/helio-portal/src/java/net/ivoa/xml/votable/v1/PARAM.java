
package net.ivoa.xml.votable.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}DESCRIPTION" minOccurs="0"/>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}VALUES" minOccurs="0"/>
 *         &lt;element ref="{http://www.ivoa.net/xml/VOTable/v1.1}LINK" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="datatype" use="required" type="{http://www.ivoa.net/xml/VOTable/v1.1}dataType" />
 *       &lt;attribute name="precision" type="{http://www.ivoa.net/xml/VOTable/v1.1}precType" />
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="ucd" type="{http://www.ivoa.net/xml/VOTable/v1.1}ucdType" />
 *       &lt;attribute name="utype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="valueparam" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="arraysize" type="{http://www.ivoa.net/xml/VOTable/v1.1}arrayDEF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description",
    "values",
    "link"
})
@XmlRootElement(name = "PARAM")
public class PARAM {

    @XmlElement(name = "DESCRIPTION")
    protected AnyTEXT description;
    @XmlElement(name = "VALUES")
    protected VALUES values;
    @XmlElement(name = "LINK")
    protected List<LINK> link;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unit;
    @XmlAttribute(required = true)
    protected DataType datatype;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String precision;
    @XmlAttribute
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger width;
    @XmlAttribute
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object ref;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ucd;
    @XmlAttribute
    protected String utype;
    @XmlAttribute(required = true)
    protected String valueparam;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String arraysize;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link AnyTEXT }
     *     
     */
    public AnyTEXT getDESCRIPTION() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyTEXT }
     *     
     */
    public void setDESCRIPTION(AnyTEXT value) {
        this.description = value;
    }

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link VALUES }
     *     
     */
    public VALUES getVALUES() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link VALUES }
     *     
     */
    public void setVALUES(VALUES value) {
        this.values = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLINK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LINK }
     * 
     * 
     */
    public List<LINK> getLINK() {
        if (link == null) {
            link = new ArrayList<LINK>();
        }
        return this.link;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the datatype property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getDatatype() {
        return datatype;
    }

    /**
     * Sets the value of the datatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setDatatype(DataType value) {
        this.datatype = value;
    }

    /**
     * Gets the value of the precision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecision() {
        return precision;
    }

    /**
     * Sets the value of the precision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecision(String value) {
        this.precision = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWidth(BigInteger value) {
        this.width = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setRef(Object value) {
        this.ref = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the ucd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUcd() {
        return ucd;
    }

    /**
     * Sets the value of the ucd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUcd(String value) {
        this.ucd = value;
    }

    /**
     * Gets the value of the utype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtype() {
        return utype;
    }

    /**
     * Sets the value of the utype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtype(String value) {
        this.utype = value;
    }

    /**
     * Gets the value of the valueparam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueparam() {
        return valueparam;
    }

    /**
     * Sets the value of the valueparam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueparam(String value) {
        this.valueparam = value;
    }

    /**
     * Gets the value of the arraysize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArraysize() {
        return arraysize;
    }

    /**
     * Sets the value of the arraysize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArraysize(String value) {
        this.arraysize = value;
    }

}
