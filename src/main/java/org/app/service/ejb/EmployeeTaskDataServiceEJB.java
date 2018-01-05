package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Employee;
import org.app.service.entities.Task;

 /* http://localhost:8080/SCRUM/data/features */
@Stateless @LocalBean
public class EmployeeTaskDataServiceEJB extends EntityRepositoryBase<Employee>
		implements EmployeeTaskDataService, Serializable {
	private static Logger logger = Logger.getLogger(EmployeeTaskDataServiceEJB.class.getName());
	
	
	private EntityRepository<Task> taskRepository;
	@PostConstruct
	public void init() {
		
		taskRepository = new EntityRepositoryBase<Task>(this.em,Task.class);
		logger.info("POSTCONSTRUCT-INIT taskRepository: " + this.taskRepository);
	}
	
	@POST @Path("/new/{employeeID}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Employee createNewEmployee(@PathParam("employeeID") Integer id){
		
		Employee employee = new Employee(id, "NEW employee" + "." + id);
		List<Task> taskEmployee = new ArrayList<>();

		Integer TaskCount = 4;
		for (int i=0; i<=TaskCount-1; i++){
			taskEmployee.add(new Task(null, "Test-REST-EJB"+ i,employee));
		}
		employee.setTasks(taskEmployee); 		
		
		employee = this.add(employee);
		
		return employee;
	}
	@GET @Path("/{employeeID}/tasks/{taskID}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Task getTaskById(@PathParam("taskID")Integer taskID) {
		return taskRepository.getById(taskID);
	}
	
	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String getMessage() {
		return "ETDataService is working...";
	}
}
