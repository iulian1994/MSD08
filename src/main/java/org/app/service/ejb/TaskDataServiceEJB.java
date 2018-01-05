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
import org.app.service.entities.Employee;
import org.app.service.entities.Task;

@Path("tasks")
@Stateless @LocalBean
public class TaskDataServiceEJB implements TaskDataService {
	private static Logger logger = Logger.getLogger(TaskDataServiceEJB.class.getName());

	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public TaskDataServiceEJB() {
	}
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
		public Task addTask(Task taskToAdd){
			em.persist(taskToAdd);
			em.flush();
			// transactions are managed by default by container
			em.refresh(taskToAdd);
			return taskToAdd;
		}
		// READ
		@GET @Path("/{id}")		/* SCRUM/data/features 		REST-resource: Feature-entity */
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
		@Override
		public Task getTaskByID(@PathParam("id")Integer taskID) {
			logger.info("**** DEBUG REST getHospitalAdmissionByID(): id = " + taskID);
			return em.find(Task.class, taskID);
		}
		@GET 					/* SCRUM/data/features 		REST-resource: Features-collection */
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
		public Collection<Task> getTasks(){
			List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class)
					.getResultList();
			logger.info("**** DEBUG REST hospitaladmission.size()= " + tasks.size());
			return tasks;
		}
		// REMOVE
		@DELETE 					/* SCRUM/data/features		REST-resource: Feature-entity */
		@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@Override
		public String removeTask(Task taskToRemove){
			taskToRemove = em.merge(taskToRemove);
			em.remove(taskToRemove);
			em.flush();
			return "True";
		}
	
		// Others
		public String getMessage() {
			return "FeatureServiceEJB is ON... ";
		}
}