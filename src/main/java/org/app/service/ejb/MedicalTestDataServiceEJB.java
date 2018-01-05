package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.app.service.entities.MedicalTest;

@Stateless @LocalBean
public class MedicalTestDataServiceEJB implements MedicalTestDataService{
	private static Logger logger = Logger.getLogger(MedicalTestDataServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public MedicalTestDataServiceEJB() {
	}
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}	
	
	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public MedicalTest addMedicalTest(MedicalTest medicaltestToAdd){
		em.persist(medicaltestToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(medicaltestToAdd);
		return medicaltestToAdd;
	}	
	// READ
	@Override
	public MedicalTest getMedicalTestByID(Integer testID) {
		return em.find(MedicalTest.class, testID);
	}	
	public Collection<MedicalTest> getMedicalTest(){
		List<MedicalTest> medicaltests = em.createQuery("SELECT m FROM MedicalTest m", MedicalTest.class)
				.getResultList();
		return medicaltests;
	}
	
	// REMOVE
		@Override
		public String removeMedicalTest(MedicalTest medicaltestToDelete){
			medicaltestToDelete = em.merge(medicaltestToDelete);
			em.remove(medicaltestToDelete);
			em.flush();
			return "True";
		}
		// Custom READ: custom query
		@Override
		public MedicalTest getMedicalTestByName(String medicaltestName) {
			return em.createQuery("SELECT m FROM MedicalTest m WHERE m.name = :name", MedicalTest.class)
					.setParameter("name", medicaltestName)
					.getSingleResult();
		}	
		
		// Others
		public String getMessage() {
			return "MedTestServiceEJB is ON... ";
		}	
}