
package ch.i4ds.helio.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resultItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="measurementStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="measurementEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="urlFITS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlCorrectedRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlFrontRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlPartRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlRearRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flareNr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="measurementPeak" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="peakCS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalCounts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="energyKeVFrom" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="energyKeVTo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="xPos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="yPos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="radial" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AR" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="urlPhaseFITSGZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlIntensityFITSGZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlPreview" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlPreviewThumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultItem", propOrder = {
    "measurementStart",
    "measurementEnd",
    "urlFITS",
    "urlCorrectedRate",
    "urlFrontRate",
    "urlPartRate",
    "urlRate",
    "urlRearRate",
    "flareNr",
    "measurementPeak",
    "peakCS",
    "totalCounts",
    "energyKeVFrom",
    "energyKeVTo",
    "xPos",
    "yPos",
    "radial",
    "ar",
    "urlPhaseFITSGZ",
    "urlIntensityFITSGZ",
    "urlPreview",
    "urlPreviewThumb"
})
public class ResultItem {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar measurementStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar measurementEnd;
    protected String urlFITS;
    protected String urlCorrectedRate;
    protected String urlFrontRate;
    protected String urlPartRate;
    protected String urlRate;
    protected String urlRearRate;
    protected int flareNr;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar measurementPeak;
    protected int peakCS;
    protected int totalCounts;
    protected int energyKeVFrom;
    protected int energyKeVTo;
    protected int xPos;
    protected int yPos;
    protected int radial;
    @XmlElement(name = "AR")
    protected int ar;
    protected String urlPhaseFITSGZ;
    protected String urlIntensityFITSGZ;
    protected String urlPreview;
    protected String urlPreviewThumb;

    /**
     * Gets the value of the measurementStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMeasurementStart() {
        return measurementStart;
    }

    /**
     * Sets the value of the measurementStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMeasurementStart(XMLGregorianCalendar value) {
        this.measurementStart = value;
    }

    /**
     * Gets the value of the measurementEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMeasurementEnd() {
        return measurementEnd;
    }

    /**
     * Sets the value of the measurementEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMeasurementEnd(XMLGregorianCalendar value) {
        this.measurementEnd = value;
    }

    /**
     * Gets the value of the urlFITS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlFITS() {
        return urlFITS;
    }

    /**
     * Sets the value of the urlFITS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlFITS(String value) {
        this.urlFITS = value;
    }

    /**
     * Gets the value of the urlCorrectedRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlCorrectedRate() {
        return urlCorrectedRate;
    }

    /**
     * Sets the value of the urlCorrectedRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlCorrectedRate(String value) {
        this.urlCorrectedRate = value;
    }

    /**
     * Gets the value of the urlFrontRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlFrontRate() {
        return urlFrontRate;
    }

    /**
     * Sets the value of the urlFrontRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlFrontRate(String value) {
        this.urlFrontRate = value;
    }

    /**
     * Gets the value of the urlPartRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPartRate() {
        return urlPartRate;
    }

    /**
     * Sets the value of the urlPartRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPartRate(String value) {
        this.urlPartRate = value;
    }

    /**
     * Gets the value of the urlRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRate() {
        return urlRate;
    }

    /**
     * Sets the value of the urlRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRate(String value) {
        this.urlRate = value;
    }

    /**
     * Gets the value of the urlRearRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRearRate() {
        return urlRearRate;
    }

    /**
     * Sets the value of the urlRearRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRearRate(String value) {
        this.urlRearRate = value;
    }

    /**
     * Gets the value of the flareNr property.
     * 
     */
    public int getFlareNr() {
        return flareNr;
    }

    /**
     * Sets the value of the flareNr property.
     * 
     */
    public void setFlareNr(int value) {
        this.flareNr = value;
    }

    /**
     * Gets the value of the measurementPeak property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMeasurementPeak() {
        return measurementPeak;
    }

    /**
     * Sets the value of the measurementPeak property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMeasurementPeak(XMLGregorianCalendar value) {
        this.measurementPeak = value;
    }

    /**
     * Gets the value of the peakCS property.
     * 
     */
    public int getPeakCS() {
        return peakCS;
    }

    /**
     * Sets the value of the peakCS property.
     * 
     */
    public void setPeakCS(int value) {
        this.peakCS = value;
    }

    /**
     * Gets the value of the totalCounts property.
     * 
     */
    public int getTotalCounts() {
        return totalCounts;
    }

    /**
     * Sets the value of the totalCounts property.
     * 
     */
    public void setTotalCounts(int value) {
        this.totalCounts = value;
    }

    /**
     * Gets the value of the energyKeVFrom property.
     * 
     */
    public int getEnergyKeVFrom() {
        return energyKeVFrom;
    }

    /**
     * Sets the value of the energyKeVFrom property.
     * 
     */
    public void setEnergyKeVFrom(int value) {
        this.energyKeVFrom = value;
    }

    /**
     * Gets the value of the energyKeVTo property.
     * 
     */
    public int getEnergyKeVTo() {
        return energyKeVTo;
    }

    /**
     * Sets the value of the energyKeVTo property.
     * 
     */
    public void setEnergyKeVTo(int value) {
        this.energyKeVTo = value;
    }

    /**
     * Gets the value of the xPos property.
     * 
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Sets the value of the xPos property.
     * 
     */
    public void setXPos(int value) {
        this.xPos = value;
    }

    /**
     * Gets the value of the yPos property.
     * 
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Sets the value of the yPos property.
     * 
     */
    public void setYPos(int value) {
        this.yPos = value;
    }

    /**
     * Gets the value of the radial property.
     * 
     */
    public int getRadial() {
        return radial;
    }

    /**
     * Sets the value of the radial property.
     * 
     */
    public void setRadial(int value) {
        this.radial = value;
    }

    /**
     * Gets the value of the ar property.
     * 
     */
    public int getAR() {
        return ar;
    }

    /**
     * Sets the value of the ar property.
     * 
     */
    public void setAR(int value) {
        this.ar = value;
    }

    /**
     * Gets the value of the urlPhaseFITSGZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPhaseFITSGZ() {
        return urlPhaseFITSGZ;
    }

    /**
     * Sets the value of the urlPhaseFITSGZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPhaseFITSGZ(String value) {
        this.urlPhaseFITSGZ = value;
    }

    /**
     * Gets the value of the urlIntensityFITSGZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlIntensityFITSGZ() {
        return urlIntensityFITSGZ;
    }

    /**
     * Sets the value of the urlIntensityFITSGZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlIntensityFITSGZ(String value) {
        this.urlIntensityFITSGZ = value;
    }

    /**
     * Gets the value of the urlPreview property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPreview() {
        return urlPreview;
    }

    /**
     * Sets the value of the urlPreview property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPreview(String value) {
        this.urlPreview = value;
    }

    /**
     * Gets the value of the urlPreviewThumb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPreviewThumb() {
        return urlPreviewThumb;
    }

    /**
     * Sets the value of the urlPreviewThumb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPreviewThumb(String value) {
        this.urlPreviewThumb = value;
    }

}
