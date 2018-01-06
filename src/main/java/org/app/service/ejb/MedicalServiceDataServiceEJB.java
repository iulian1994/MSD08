package org.app.service.ejb;

import java.util.logging.Logger;

import java.io.Serializable;
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
import org.app.service.entities.Employee;
import org.app.service.entities.MedicalActivity;
import org.app.service.entities.MedicalService;
import org.app.service.entities.Patient;
import org.app.service.entities.Task;


@Path("medicalservices")
@Stateless @LocalBean
public class MedicalServiceDataServiceEJB 
		extends EntityRepositoryBase<MedicalService>
		implements MedicalServiceDataService {
	
	private static Logger logger = Logger.getLogger(MedicalServiceDataServiceEJB.class.getName());
	
	private EntityRepository<MedicalActivity> activityRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		activityRepository = new EntityRepositoryBase<MedicalActivity>(this.em, MedicalActivity.class);
		logger.info("POSTCONSTRUCT-INIT activityRepository: " + this.activityRepository);
	}

	/******** REST MAPPING IMPLEMENTATION ************************************************/
	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<MedicalService> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public MedicalService getById(@PathParam("id") Integer id){ 
		MedicalService medicalservice = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + medicalservice);
		return medicalservice;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<MedicalService> addIntoCollection(MedicalService medicalservice) {
		// save aggregate
		super.add(medicalservice);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public MedicalService add(MedicalService medicalservice) {
		// restore aggregation-relation
		for (MedicalActivity r: medicalservice.getMedicalactivity())
			r.setRel_service(medicalservice);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		medicalservice = super.add(medicalservice);
		// return updated collection	
		return medicalservice;
	}
	//@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<MedicalService> removeFromCollection(MedicalService medicalservice) {
		logger.info("DEBUG: called REMOVE - project: " + medicalservice);
		super.remove(medicalservice);
		return super.toCollection();
	}
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		MedicalService medicalservice = super.getById(id);
		super.remove(medicalservice);
	}
	// GET method on second repository for Release-type entities
	@GET @Path("/{serviceId}/activities/{activityID}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MedicalActivity getMedicalMedicalActivityById(@PathParam("activityID") Integer activityID){
		logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
		MedicalActivity medicalactivity = activityRepository.getById(activityID);
		return medicalactivity;
	}
	
	/* Other test-proposal methods ************************************************************/
	@POST @Path("/new/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	// Aggregate factory method
	public MedicalService createNewMedicalService(@PathParam("id")Integer id){
		logger.info("**** DEBUG REST createNewProject POST");
		// create project aggregate
		MedicalService medicalservice = new MedicalService(id, "NEW MedSrvRESTTT" + "." + id);
		List<MedicalActivity> activityService = new ArrayList<>();
		
		Integer activityCount = 3;
		for (int i=0; i<=activityCount-1; i++){
			activityService.add(new MedicalActivity(i,"rest-med_activity_nr"+i,"Blood tests...", medicalservice));
		}
		medicalservice.setMedicalactivity(activityService);		//posibil error!!!!!!!
		// save project aggregate
		this.add(medicalservice);
		// return project aggregate to service client
		return medicalservice;
	}

	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String getMessage(){
		return "Medical Service DataService is working...";
	}
	@GET @Path("/medicalservicesdata")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProjectData() throws Exception{
		MedicalService dto = new MedicalService(1111, "Pro 1111REST");
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