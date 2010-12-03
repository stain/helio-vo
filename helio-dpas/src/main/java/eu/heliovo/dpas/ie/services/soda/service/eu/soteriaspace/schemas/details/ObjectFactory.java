
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.details;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.soteriaspace.schemas.details package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RecordDetail_QNAME = new QName("http://soteriaspace.eu/schemas/details", "recordDetail");
    private final static QName _RecordDetailAccessFtp_QNAME = new QName("", "ftp");
    private final static QName _RecordDetailAccessRest_QNAME = new QName("", "rest");
    private final static QName _RecordDetailAccessUri_QNAME = new QName("", "uri");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.soteriaspace.schemas.details
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InlineAccess }
     * 
     */
    public InlineAccess createInlineAccess() {
        return new InlineAccess();
    }

    /**
     * Create an instance of {@link RecordDetail }
     * 
     */
    public RecordDetail createRecordDetail() {
        return new RecordDetail();
    }

    /**
     * Create an instance of {@link RecordDetail.Access }
     * 
     */
    public RecordDetail.Access createRecordDetailAccess() {
        return new RecordDetail.Access();
    }

    /**
     * Create an instance of {@link InlineAccess.Property }
     * 
     */
    public InlineAccess.Property createInlineAccessProperty() {
        return new InlineAccess.Property();
    }

    /**
     * Create an instance of {@link Ftp }
     * 
     */
    public Ftp createFtp() {
        return new Ftp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecordDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/details", name = "recordDetail")
    public JAXBElement<RecordDetail> createRecordDetail(RecordDetail value) {
        return new JAXBElement<RecordDetail>(_RecordDetail_QNAME, RecordDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ftp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ftp", scope = RecordDetail.Access.class)
    public JAXBElement<Ftp> createRecordDetailAccessFtp(Ftp value) {
        return new JAXBElement<Ftp>(_RecordDetailAccessFtp_QNAME, Ftp.class, RecordDetail.Access.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "rest", scope = RecordDetail.Access.class)
    public JAXBElement<String> createRecordDetailAccessRest(String value) {
        return new JAXBElement<String>(_RecordDetailAccessRest_QNAME, String.class, RecordDetail.Access.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "uri", scope = RecordDetail.Access.class)
    public JAXBElement<String> createRecordDetailAccessUri(String value) {
        return new JAXBElement<String>(_RecordDetailAccessUri_QNAME, String.class, RecordDetail.Access.class, value);
    }

}
