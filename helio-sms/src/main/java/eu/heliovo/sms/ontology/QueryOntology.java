package eu.heliovo.sms.ontology;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import eu.heliovo.sms.ontology.dlquery.DLQueryEngine;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyIRIMapperImpl;

public class QueryOntology {

	private Set<OWLOntology> onts = new HashSet<OWLOntology>();
	private OWLOntology ont;
	private OWLReasoner reasoner;
	private DLQueryEngine dlQueryEngine;
	private ShortFormProvider shortFormProvider;
	private Hashtable<String,OWLObjectProperty> objectProperty = null;
	private Hashtable<String,OWLAnnotationProperty> annotationProperty = null;
	private Hashtable<String,OWLClass> classList = null;
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * public constructor - set up ontologies and check validity
	 */
	public QueryOntology() {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
			IRI flareOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/10/helio-flare1.owl"); 
			IRI flareOntologyFile = IRI.create(this.getClass().getResource("/helio-flare1.owl"));  
			IRI upperOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/5/HELIO_upperOntology.owl"); 
			IRI upperOntologyFile = IRI.create(this.getClass().getResource("/HELIO_upperOntology.owl"));  
			IRI physicisOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/HELIO_physicsOntology.owl"); 
			IRI physicsOntologyFile = IRI.create(this.getClass().getResource("/HELIO_physicsOntology.owl"));  
			IRI coordinateSystemOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/CoordinateSystems.owl"); 
			IRI coordinateSystemOntologyFile = IRI.create(this.getClass().getResource("/CoordinateSystems.owl"));  
			IRI organisationalOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/2/HELIO_organisationalOntology.owl"); 
			IRI organisationalOntologyFile = IRI.create(this.getClass().getResource("/HELIO_organisationalOntology.owl"));
			
			OWLOntologyIRIMapperImpl mapper = new  OWLOntologyIRIMapperImpl(); 
			mapper.addMapping(flareOntologyIRI, flareOntologyFile);
			mapper.addMapping(upperOntologyIRI, upperOntologyFile);
			mapper.addMapping(physicisOntologyIRI, physicsOntologyFile);
			mapper.addMapping(coordinateSystemOntologyIRI, coordinateSystemOntologyFile);
			mapper.addMapping(organisationalOntologyIRI, organisationalOntologyFile);
			manager.addIRIMapper(mapper);

			ont = manager.loadOntology(IRI.create(this.getClass().getResource("/helio-flare1.owl")));
	
			onts.add(ont);
			//OWLReasonerFactory reasonerFactory = new FaCTPlusPlusReasonerFactory();
			OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory(); 
			//ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor(); 
			OWLReasonerConfiguration config = new SimpleConfiguration(); 
			reasoner = reasonerFactory.createReasoner(ont, config); 
			reasoner.precomputeInferences(); 
			boolean consistent = reasoner.isConsistent();
			logger.debug("Consistent: " + consistent);
			Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses(); 
			Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom(); 
			if (!unsatisfiable.isEmpty()) {
				logger.warn("The following classes are unsatisfiable: ");
				for(OWLClass cls : unsatisfiable) {
				   logger.warn(" " + cls);
				}
			}
			else {
				logger.debug("There are no unsatisfiable classes");
			} 
			shortFormProvider = new SimpleShortFormProvider();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
	}
	
	private OWLClass getClass(String classShort){
		if(classList == null){
			classList= new Hashtable<String,OWLClass>();
			addClass(ont);
		}
		return classList.get(classShort);
	}
	
	private OWLObjectProperty getObjectProperty(String propertyShort){
		if(objectProperty == null){
			objectProperty= new Hashtable<String,OWLObjectProperty>();
			addObjectProperty(ont);
		}
		return objectProperty.get(propertyShort);
	}
	
	private OWLAnnotationProperty getAnnotationProperty(String propertyShort){
		if(annotationProperty == null){
			annotationProperty= new Hashtable<String,OWLAnnotationProperty>();
			addAnnotationProperty(ont);
		}
		return annotationProperty.get(propertyShort);
	}
	
	private void addClass(OWLOntology ontology){
		Set<OWLOntology> setO = ontology.getDirectImports();
		onts.addAll(setO);
		Iterator<OWLOntology> itO = setO.iterator();
		Iterator<OWLClass> it=ontology.getClassesInSignature().iterator();
		while(it.hasNext()){
			OWLClass cl = it.next();
//				System.out.println(cl + " " + cl.getIRI().toString().substring(
//						cl.getIRI().toString().indexOf('#')+1));
			classList.put(cl.getIRI().toString().substring(
					cl.getIRI().toString().indexOf('#')+1), cl);
		}
		while(itO.hasNext()){
			OWLOntology onto = itO.next();
			addClass(onto);
		
			
		}
	}
	
	private void addObjectProperty(OWLOntology ontology){
		Set<OWLOntology> setO = ontology.getDirectImports();
		Iterator<OWLOntology> itO = setO.iterator();
		Iterator<OWLObjectProperty> it=ontology.getObjectPropertiesInSignature().iterator();
		while(it.hasNext()){
			OWLObjectProperty op = it.next();
			System.out.println(op + " " + op.getIRI().toString().substring(
					op.getIRI().toString().indexOf('#')));
			objectProperty.put(op.getIRI().toString().substring(
					op.getIRI().toString().indexOf('#')), op);
		}
		while(itO.hasNext()){
			OWLOntology onto = itO.next();
			addObjectProperty(onto);
		
			
		}
	}
	
	private void addAnnotationProperty(OWLOntology ontology){
		Set<OWLOntology> setO = ontology.getDirectImports();
		Iterator<OWLOntology> itO = setO.iterator();
		Iterator<OWLAnnotationProperty> it=ontology.getAnnotationPropertiesInSignature().iterator();
		while(it.hasNext()){
			OWLAnnotationProperty op = it.next();
			System.out.println("op " + op);
			if(op.getIRI().toString().contains("#")){
				annotationProperty.put(op.getIRI().toString().substring(
						op.getIRI().toString().indexOf('#')+1), op);
			} else {
				annotationProperty.put(op.getIRI().toString().substring(
						op.getIRI().toString().lastIndexOf('/')+1), op);
			}
		}
		while(itO.hasNext()){
			OWLOntology onto = itO.next();
			addAnnotationProperty(onto);
			
			
		}
	}

	/**
	 * 
	 * @param owlTerm Name of Concept used in ontology
	 * @return List of SubClasses of input concept
	 */
	public List<String> getOwlClass(String owlTerm){
		logger.info("getOwlClass entered");
		OWLClass term = getClass(owlTerm);
		Iterator<OWLClass> iterator = reasoner.getSubClasses(term, true).getFlattened().iterator();
		ArrayList<String> list = new ArrayList<String>();
		while(iterator.hasNext()){
			String exp = iterator.next().toString();
			list.add(exp.substring(exp.lastIndexOf('#')+1,exp.lastIndexOf('>')));
		}
		return list;
	}

	/**
	 * 
	 * @param phenomenon Name of Concept used in ontology
	 * @return List of Equivalent Classes of input concept
	 */
	public List<String> getEquivalents(String phenomenon) {
		logger.info("getOwlClass entered");
		OWLClass term = getClass(phenomenon);
		Iterator<OWLClass>  iterator=reasoner.getEquivalentClasses(term).iterator();
		ArrayList<String> list = new ArrayList<String>();
		while(iterator.hasNext()){
			String exp = iterator.next().toString();
			list.add(exp.substring(exp.lastIndexOf('#')+1,exp.lastIndexOf('>')));
		}
		return list;
	}

	/**
	 * 
	 * @param phenomenon Name of Concept used in ontology
	 * @return List of EquivalentClassed, sub classes and Super classes of input concept
	 */
	public List<String> getRelated(String phenomenon) {
		logger.info("getRelated entered");
		OWLClass term = getClass(phenomenon);
		Iterator<OWLClass>  iterator=reasoner.getEquivalentClasses(term).iterator();
		ArrayList<String> list = new ArrayList<String>();
		while(iterator.hasNext()){
			String exp = iterator.next().toString();
			list.add(exp.substring(exp.lastIndexOf('#')+1,exp.lastIndexOf('>')));
		}
		iterator=reasoner.getSubClasses(term, true).getFlattened().iterator();
		while(iterator.hasNext()){
			String exp = iterator.next().toString();
			list.add(exp.substring(exp.lastIndexOf('#')+1,exp.lastIndexOf('>')));
		}
		iterator=reasoner.getSuperClasses(term, true).getFlattened().iterator();
		while(iterator.hasNext()){
			String exp = iterator.next().toString();
			list.add(exp.substring(exp.lastIndexOf('#')+1,exp.lastIndexOf('>')));
		}
		return list;
	}

	/**
	 * 
	 * @param phenomenon Name of Concept used in ontology
	 * @return list of annotations with Property "HELIOIdentifier" if "HELIOService" annotation contains "hec" 
	 * @throws ParserException
	 */
	public List<String> getEventCatalogues(String phenomenon) throws ParserException {
		ArrayList<String> list= new ArrayList<String>(); 
		logger.info("getEventCatalogues entered");
		String classExpression = new String("Catalog and (containsDataAboutPhenomenon some "+phenomenon+")");
		dlQueryEngine = new DLQueryEngine(reasoner, shortFormProvider);
        Set<OWLClass> subClasses = dlQueryEngine.getSubClasses(classExpression, true);
        for(OWLClass cl : subClasses){
        	logger.debug("result classes " + cl.toString());
        	OWLAnnotationProperty service = getAnnotationProperty("HELIOService");
        	OWLAnnotationProperty identifier = getAnnotationProperty("HELIOIdentifier");
        	
        	Set<OWLAnnotation> servSet = cl.getAnnotations(ont, service);
        	Set<OWLAnnotation> identSet = cl.getAnnotations(ont, identifier);
        	for(OWLAnnotation an : servSet){
        		if(an.getValue().toString().contains("hec")){
        			for(OWLAnnotation an2 : identSet){
        				String content= an2.getValue().toString();
        				list.add(content.substring(content.indexOf("\"")+1, content.lastIndexOf("\"")));
        			}
        			break;
        		}
        	}
        }
		return list;
	}	

}
