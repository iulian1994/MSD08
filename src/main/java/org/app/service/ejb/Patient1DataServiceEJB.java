package org.app.service.ejb;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Patient;
import org.app.service.entities.HospitalAdmission;

@Path("patients")
@Stateless @LocalBean
public class Patient1DataServiceEJB extends EntityRepositoryBase<Patient>
implements Patient1DataService{
	private static Logger logger = Logger.getLogger(Patient1DataServiceEJB.class.getName());
	
	
	// Local component-entity-repository
	private EntityRepository<HospitalAdmission> HospitalAdmissionRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		HospitalAdmissionRepository = new EntityRepositoryBase<HospitalAdmission>(this.em, HospitalAdmission.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.HospitalAdmissionRepository);
	}
	
	/******** REST MAPPING IMPLEMENTATION ************************************************/

	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Patient> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}	
	
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Patient getById(@PathParam("id") Integer id){ 
		Patient patient = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + patient);
		return patient;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Patient> addIntoCollection(Patient patient) {
		// save aggregate
		super.add(patient);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	// 
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Patient add(Patient patient) {
		// restore aggregation-relation
		for (HospitalAdmission r: patient.getHospitaladmission())
			r.setPatient(patient);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		patient = super.add(patient);
		// return updated collection	
		return patient;
	}
	
	//@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Patient> removeFromCollection(Patient patient) {
		logger.info("DEBUG: called REMOVE - project: " + patient);
		super.remove(patient);
		return super.toCollection();
	}
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		Patient patient = super.getById(id);
		super.remove(patient);
	}
	
	// GET method on second repository for Release-type entities
		@GET @Path("/{patientid}/hospitaladmission/{addmissionNo}")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public HospitalAdmission getHospitalAdmissionById(@PathParam("addmissionNo") Integer admissionNo){
			logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
			HospitalAdmission hospitaladmission = HospitalAdmissionRepository.getById(admissionNo);
			return hospitaladmission;
		}
		
		/* Other test-proposal methods ************************************************************/
		@POST @Path("/new/{id}")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
		// Aggregate factory method
		public Patient createNewPatient(@PathParam("id")Integer id){
			logger.info("**** DEBUG REST createNewProject POST");
			// create project aggregate
			Patient patient = new Patient(id, "NEW PatientRESTTT" + "." + id);
			List<HospitalAdmission> admissionsPatient = new ArrayList<>();
			
			Integer admissionsCount = 3;
			for (int i=0; i<=admissionsCount-1; i++){
				admissionsPatient.add(new HospitalAdmission(i, patient));
			}
			patient.setHospitaladmission(admissionsPatient);		
			// save project aggregate
			this.add(patient);
			// return project aggregate to service client
			return patient;
		}
		
		@GET @Path("/test") // Check if resource is up ...
		@Produces({ MediaType.TEXT_PLAIN})
		public String getMessage(){
			return "Patient DataService is working...";
		}	
		
		@GET @Path("/patientdata")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public Response getProjectData() throws Exception{
			Patient dto = new Patient(1111, "Pro 1111REST");
			JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(dto, os);
			String aString = new String(os.toByteArray(),"UTF-8");
			
			Response response = Response
					.status(Status.OK)
					.type(MediaType.TEXT_PLAIN)
					.entity(aString)
					.build();
			
			return response;
		}
	
	
	
	
}