package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Employee;
import org.app.service.entities.Task;

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
	
	
	public Employee createNewEmployee(Integer id){
		
		Employee employee = new Employee(null, "NEW employee" + "." + id);
		List<Task> taskEmployee = new ArrayList<>();

		Integer TaskCount = 3;
		for (int i=0; i<=TaskCount-1; i++){
			taskEmployee.add(new Task(null, "R: " + employee.geteID() + "." + i));
		}
		employee.setTasks(taskEmployee); 		
		
		employee = this.add(employee);
		
		return employee;
	}
	
	public Task getTaskById(Integer releaseid) {
		return taskRepository.getById(releaseid);
	}

	public String getMessage() {
		return "ETDataService is working...";
	}
}
