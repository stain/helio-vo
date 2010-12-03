
package eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.datasets.Extent;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.soteriaspace.schemas.query package. 
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

    private final static QName _Query_QNAME = new QName("http://soteriaspace.eu/schemas/query", "query");
    private final static QName _Records_QNAME = new QName("http://soteriaspace.eu/schemas/query", "records");
    private final static QName _QueryLogicalBlockBigger_QNAME = new QName("http://soteriaspace.eu/schemas/query", "bigger");
    private final static QName _QueryLogicalBlockEquals_QNAME = new QName("http://soteriaspace.eu/schemas/query", "equals");
    private final static QName _QueryLogicalBlockOr_QNAME = new QName("http://soteriaspace.eu/schemas/query", "or");
    private final static QName _QueryLogicalBlockDifferent_QNAME = new QName("http://soteriaspace.eu/schemas/query", "different");
    private final static QName _QueryLogicalBlockNot_QNAME = new QName("http://soteriaspace.eu/schemas/query", "not");
    private final static QName _QueryLogicalBlockNotContained_QNAME = new QName("http://soteriaspace.eu/schemas/query", "not-contained");
    private final static QName _QueryLogicalBlockLesser_QNAME = new QName("http://soteriaspace.eu/schemas/query", "lesser");
    private final static QName _QueryLogicalBlockAnd_QNAME = new QName("http://soteriaspace.eu/schemas/query", "and");
    private final static QName _QueryLogicalBlockContained_QNAME = new QName("http://soteriaspace.eu/schemas/query", "contained");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.soteriaspace.schemas.query
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryLogicalBlockItem }
     * 
     */
    public QueryLogicalBlockItem createQueryLogicalBlockItem() {
        return new QueryLogicalBlockItem();
    }

    /**
     * Create an instance of {@link RecordList }
     * 
     */
    public RecordList createRecordList() {
        return new RecordList();
    }

    /**
     * Create an instance of {@link QueryRelationBlock }
     * 
     */
    public QueryRelationBlock createQueryRelationBlock() {
        return new QueryRelationBlock();
    }

    /**
     * Create an instance of {@link MainQueryLogicalBlockItem }
     * 
     */
    public MainQueryLogicalBlockItem createMainQueryLogicalBlockItem() {
        return new MainQueryLogicalBlockItem();
    }

    /**
     * Create an instance of {@link RecordId }
     * 
     */
    public RecordId createRecordId() {
        return new RecordId();
    }

    /**
     * Create an instance of {@link QueryLogicalBlock }
     * 
     */
    public QueryLogicalBlock createQueryLogicalBlock() {
        return new QueryLogicalBlock();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MainQueryLogicalBlockItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "query")
    public JAXBElement<MainQueryLogicalBlockItem> createQuery(MainQueryLogicalBlockItem value) {
        return new JAXBElement<MainQueryLogicalBlockItem>(_Query_QNAME, MainQueryLogicalBlockItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecordList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "records")
    public JAXBElement<RecordList> createRecords(RecordList value) {
        return new JAXBElement<RecordList>(_Records_QNAME, RecordList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "bigger", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryRelationBlock> createQueryLogicalBlockBigger(QueryRelationBlock value) {
        return new JAXBElement<QueryRelationBlock>(_QueryLogicalBlockBigger_QNAME, QueryRelationBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "equals", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryRelationBlock> createQueryLogicalBlockEquals(QueryRelationBlock value) {
        return new JAXBElement<QueryRelationBlock>(_QueryLogicalBlockEquals_QNAME, QueryRelationBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "or", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryLogicalBlock> createQueryLogicalBlockOr(QueryLogicalBlock value) {
        return new JAXBElement<QueryLogicalBlock>(_QueryLogicalBlockOr_QNAME, QueryLogicalBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "different", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryRelationBlock> createQueryLogicalBlockDifferent(QueryRelationBlock value) {
        return new JAXBElement<QueryRelationBlock>(_QueryLogicalBlockDifferent_QNAME, QueryRelationBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "not", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryLogicalBlock> createQueryLogicalBlockNot(QueryLogicalBlock value) {
        return new JAXBElement<QueryLogicalBlock>(_QueryLogicalBlockNot_QNAME, QueryLogicalBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Extent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "not-contained", scope = QueryLogicalBlock.class)
    public JAXBElement<Extent> createQueryLogicalBlockNotContained(Extent value) {
        return new JAXBElement<Extent>(_QueryLogicalBlockNotContained_QNAME, Extent.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRelationBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "lesser", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryRelationBlock> createQueryLogicalBlockLesser(QueryRelationBlock value) {
        return new JAXBElement<QueryRelationBlock>(_QueryLogicalBlockLesser_QNAME, QueryRelationBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLogicalBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "and", scope = QueryLogicalBlock.class)
    public JAXBElement<QueryLogicalBlock> createQueryLogicalBlockAnd(QueryLogicalBlock value) {
        return new JAXBElement<QueryLogicalBlock>(_QueryLogicalBlockAnd_QNAME, QueryLogicalBlock.class, QueryLogicalBlock.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Extent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soteriaspace.eu/schemas/query", name = "contained", scope = QueryLogicalBlock.class)
    public JAXBElement<Extent> createQueryLogicalBlockContained(Extent value) {
        return new JAXBElement<Extent>(_QueryLogicalBlockContained_QNAME, Extent.class, QueryLogicalBlock.class, value);
    }

}
