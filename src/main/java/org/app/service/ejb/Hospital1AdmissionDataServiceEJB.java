package org.app.service.ejb;
import javax.ejb.Remote;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.MedicalService;
import org.app.service.entities.Patient;

@Path("hospitaladmission")
@Stateless @LocalBean
public class Hospital1AdmissionDataServiceEJB
implements Hospital1AdmissionDataService {
	private static Logger logger = Logger.getLogger(Hospital1AdmissionDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public Hospital1AdmissionDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}	

	/* CRUD operations implementation */
	// CREATE or UPDATE
		@PUT @Path("/{id}") 	/* SCRUM/data/features/{id} 	REST-resource: Feature-entity */	
		@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
		@Override
		public HospitalAdmission addHospitalAdmission(HospitalAdmission hospitaladmissionToAdd){
			em.persist(hospitaladmissionToAdd);
			em.flush();
			// transactions are managed by default by container
			em.refresh(hospitaladmissionToAdd);
			return hospitaladmissionToAdd;
		}
	
	// READ
		@GET @Path("/{id}")		/* SCRUM/data/features 		REST-resource: Feature-entity */
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
		@Override
		public HospitalAdmission getHospitalAdmissionByID(@PathParam("id")Integer addmissionNo) {
			logger.info("**** DEBUG REST getHospitalAdmissionByID(): id = " + addmissionNo);
			return em.find(HospitalAdmission.class, addmissionNo);
		}
	
		@GET 					/* SCRUM/data/features 		REST-resource: Features-collection */
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
		public Collection<HospitalAdmission> getHospitalAdmissions(){
			List<HospitalAdmission> hospitaladmission = em.createQuery("SELECT h FROM HospitalAdmission h", HospitalAdmission.class)
					.getResultList();
			logger.info("**** DEBUG REST hospitaladmission.size()= " + hospitaladmission.size());
			return hospitaladmission;
		}
		// REMOVE
		@DELETE 					/* SCRUM/data/features		REST-resource: Feature-entity */
		@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@Override
		public String removeHospitalAdmission(HospitalAdmission hospitalAdmissionToDelete){
			hospitalAdmissionToDelete = em.merge(hospitalAdmissionToDelete);
			em.remove(hospitalAdmissionToDelete);
			em.flush();
			return "True";
		}
	
		// Others
		public String getMessage() {
			return "FeatureServiceEJB is ON... ";
		}	
}