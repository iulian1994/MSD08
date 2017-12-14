package org.app.service.ejb;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Diagnostic;
import org.app.service.entities.Patient;

@Stateless @LocalBean
public class DiagnosticDataServiceEJB implements DiagnosticDataService {

	private static Logger logger = Logger.getLogger(PatientDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public DiagnosticDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* IMPLEMENTARE CRUD */
	// CREATE or UPDATE ----- MERGE
	
	@Override
	public Diagnostic addDiagnostic(Diagnostic dianosticToAdd) {
		em.persist(dianosticToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(dianosticToAdd);
		return dianosticToAdd;
	}

	@Override
	public String removeDiagnostic(Diagnostic diagnosticToDischage) {
		diagnosticToDischage = em.merge(diagnosticToDischage);
		em.remove(diagnosticToDischage);
		em.flush();
		return "True";
	}

	@Override
	public Diagnostic getDiagnosticByID(int diagnosticID) {
		return em.find(Diagnostic.class, diagnosticID);
	}

	@Override
	public Collection<Diagnostic> getPatients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diagnostic getDiagnosticByName(String diagnosticName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		return "DiagnosticDataServiceEJB functioneaza!!!!!!... ";
	}

}
