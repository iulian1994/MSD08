package org.app.service.ejb;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.app.service.entities.Patient;

@Stateless @LocalBean
public class Patient1DataServiceEJB implements Patient1DataService{
	private static Logger logger = Logger.getLogger(Patient1DataServiceEJB.class.getName());
	
 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public Patient1DataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Patient addPatient(Patient patientToAdd){
		em.persist(patientToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(patientToAdd);
		return patientToAdd;
	}	

	public Collection<Patient> getPatients(){
		List<Patient> patients = em.createQuery("SELECT f FROM Patient f", Patient.class)
				.getResultList();
		return patients;
	}
	// REMOVE
	@Override
	public String removePatient(Patient patientToDelete){
		patientToDelete = em.merge(patientToDelete);
		em.remove(patientToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query

	// Others
	public String getMessage() {
		return "FeatureServiceEJB is ON... ";
	}

}


