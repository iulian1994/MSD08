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
import javax.persistence.Query;

import org.app.service.entities.Diagnostic;

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

		List<Diagnostic> Diagnostic = em.createQuery("SELECT d FROM Diagnostic d", Diagnostic.class)
				.getResultList();
		return Diagnostic;
	}

	@Override
	public Collection<Diagnostic> getDiagnosticsByDescription(String description)
	{
		Query query = em.createQuery("SELECT d FROM Diagnostic d where d.Description LIKE :searchString", Diagnostic.class);
		query.setParameter("searchString","%" + description + "%");
		List<Diagnostic> Diagnostic = query.getResultList();
		return Diagnostic;
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
