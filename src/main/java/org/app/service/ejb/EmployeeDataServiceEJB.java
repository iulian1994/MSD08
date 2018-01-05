package org.app.service.ejb;

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
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.Patient;
import org.app.service.entities.Task;

@Path("employees")
@Stateless @LocalBean
public class EmployeeDataServiceEJB 
		extends EntityRepositoryBase<Employee>
		implements EmployeeDataService {
	
	private static Logger logger = Logger.getLogger(EmployeeDataServiceEJB.class.getName());
	
	private EntityRepository<Task> taskRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		taskRepository = new EntityRepositoryBase<Task>(this.em, Task.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.taskRepository);
	}

	/******** REST MAPPING IMPLEMENTATION ************************************************/

	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Employee> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Employee getById(@PathParam("id") Integer id){ 
		Employee employee = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + employee);
		return employee;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Employee> addIntoCollection(Employee employee) {
		// save aggregate
		super.add(employee);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Employee add(Employee employee) {
		// restore aggregation-relation
		for (Task r: employee.getTasks())
			r.setResponsible(employee);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		employee = super.add(employee);
		// return updated collection	
		return employee;
	}
	//@Override
		@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
		@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
		public Collection<Employee> removeFromCollection(Employee employee) {
			logger.info("DEBUG: called REMOVE - project: " + employee);
			super.remove(employee);
			return super.toCollection();
		}
		@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
		public void remove(@PathParam("id")Integer id) {
			logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
			Employee employee = super.getById(id);
			super.remove(employee);
		}
		// GET method on second repository for Release-type entities
				@GET @Path("/{employeeID}/tasks/{taskID}")
				@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
				@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
				public Task getTaskById(@PathParam("taskID") Integer taskID){
					logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
					Task task = taskRepository.getById(taskID);
					return task;
				}
	
				/* Other test-proposal methods ************************************************************/
				@POST @Path("/new/{id}")
				@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
				@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
				// Aggregate factory method
				public Employee createNewEmployee(@PathParam("id")Integer id){
					logger.info("**** DEBUG REST createNewProject POST");
					// create project aggregate
					Employee employee = new Employee(id, "NEW EmployeeRESTTT" + "." + id);
					List<Task> tasksEmployee = new ArrayList<>();
					
					Integer taskCount = 3;
					for (int i=0; i<=taskCount-1; i++){
						tasksEmployee.add(new Task(i,"rest-task_nr"+i, employee));
					}
					employee.setTasks(tasksEmployee);		//posibil error!!!!!!!
					// save project aggregate
					this.add(employee);
					// return project aggregate to service client
					return employee;
				}
				
				@GET @Path("/test") // Check if resource is up ...
				@Produces({ MediaType.TEXT_PLAIN})
				public String getMessage(){
					return "Employee DataService is working...";
				}
				@GET @Path("/patientdata")
				@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
				public Response getProjectData() throws Exception{
					Employee dto = new Employee(1111, "Pro 1111REST");
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