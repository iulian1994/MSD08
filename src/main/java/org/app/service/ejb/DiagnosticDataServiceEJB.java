package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Diagnostic;
import org.app.service.entities.Patient;

@Stateless @LocalBean
public class DiagnosticDataServiceEJB implements DiagnosticDataService {

	private static Logger logger = Logger.getLogger(DiagnosticDataServiceEJB.class.getName());
	
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
	public Collection<Diagnostic> getDiagnostics() {
		
			List<Diagnostic> diagnostics = em.createQuery("SELECT d FROM Diagnostic d", Diagnostic.class)
					.getResultList();
			return diagnostics;
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
	

	@Override
	public Diagnostic CreateDiagnostic(int a, String b) {
		Diagnostic diag = new Diagnostic(a, b);
	    diag.setDiagnosticId(a);
	    diag.setComments(b);
	    em.persist(diag);
	    return null;
	}

}
