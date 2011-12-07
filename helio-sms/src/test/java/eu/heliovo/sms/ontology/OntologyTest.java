package eu.heliovo.sms.ontology;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyIRIMapperImpl;

/*
 * Test the integrety of the ontology
 */
public class OntologyTest {
	
	private Set<OWLOntology> onts = new HashSet<OWLOntology>();
	private OWLOntology ont=null;
	private OWLReasoner reasoner;
	
	@Before
    public void loadOntology() throws OWLException, IOException {
		String dir1;
			dir1 = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
			IRI flareOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/10/helio-full.owl"); 
			IRI flareOntologyFile = IRI.create(new File(dir1+"/src/main/resources/helio-full.owl"));  
			IRI upperOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/5/HELIO_upperOntology.owl"); 
			IRI upperOntologyFile = IRI.create(new File(dir1+"/src/main/resources/HELIO_upperOntology.owl"));  
			IRI physicisOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/HELIO_physicsOntology.owl"); 
			IRI physicsOntologyFile = IRI.create(new File(dir1+"/src/main/resources/HELIO_physicsOntology.owl"));  
			IRI coordinateSystemOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/CoordinateSystems.owl"); 
			IRI coordinateSystemOntologyFile = IRI.create(new File(dir1+"/src/main/resources/CoordinateSystems.owl"));  
			IRI organisationalOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/2/HELIO_organisationalOntology.owl"); 
			IRI organisationalOntologyFile = IRI.create(new File(dir1+"/src/main/resources/HELIO_organisationalOntology.owl"));
			
			OWLOntologyIRIMapperImpl mapper = new  OWLOntologyIRIMapperImpl(); 
			mapper.addMapping(flareOntologyIRI, flareOntologyFile);
			mapper.addMapping(upperOntologyIRI, upperOntologyFile);
			mapper.addMapping(physicisOntologyIRI, physicsOntologyFile);
			mapper.addMapping(coordinateSystemOntologyIRI, coordinateSystemOntologyFile);
			mapper.addMapping(organisationalOntologyIRI, organisationalOntologyFile);
			manager.addIRIMapper(mapper);
			
			File file1= new File(dir1+"/src/main/resources/helio-full.owl");
			
			
			ont= manager.loadOntologyFromOntologyDocument(file1);
		
		
		
			onts.add(ont);
			OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory(); 
			OWLReasonerConfiguration config = new SimpleConfiguration(); 
			reasoner = reasonerFactory.createReasoner(ont, config); 
			reasoner.precomputeInferences();
	}

	
	@Test
    public void testOntologyConsitency() {
		boolean consistent = reasoner.isConsistent();
		assertTrue("ontlogy inconstistent ", consistent);
	}
	
	@Test
    public void testUnsatisfiableClasses() {
		Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses(); 
		Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom(); 
		assertTrue("unsatifyable classes ", unsatisfiable.isEmpty());
	}

}
