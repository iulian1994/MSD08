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
public class PatientDataServiceEJB implements PatientDataService{
		private static Logger logger = Logger.getLogger(PatientDataServiceEJB.class.getName());
		
		/* DataService initialization */
		// Inject resource 
		@PersistenceContext(unitName="MSD")
		private EntityManager em;
		// Constructor
		public PatientDataServiceEJB() {
		}
		// Init after constructor
		@PostConstruct
		public void init(){
			logger.info("POSTCONSTRUCT-INIT : " + this.em);
		}		

		
		@Override
		public Patient addPatient(Patient patientToAdd){
			em.persist(patientToAdd);
			em.flush();
			// transactions are managed by default by container
			em.refresh(patientToAdd);
			return patientToAdd;
		}	
		
		@Override
		public Patient getPatientByID(int patientID) {
			return em.find(Patient.class, patientID);
		}	
		public Collection<Patient> getPatients(){
			List<Patient> patients = em.createQuery("SELECT p FROM Patient p", Patient.class)
					.getResultList();
			return patients;
		}
		
		// REMOVE
		@Override
		public String dischargePatient(Patient patientToDischage){
			patientToDischage = em.merge(patientToDischage);
			em.remove(patientToDischage);
			em.flush();
			return "True";
		}
		
		// Custom READ: custom query//      Patient getPatientByName(String patientName);	
		@Override
		public Patient getPatientByName(String name) {
			return em.createQuery("SELECT p FROM Pacient p WHERE p.name = :name", Patient.class)
					.setParameter("name", name)
					.getSingleResult();
		}
		
		// Others
		public String getMessage() {
			return "PATIENTDataServiceEJB functioneaza!!!!!!... ";
		}
	}