package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.Employee;
import org.app.service.entities.Task;

@Remote
public interface EmployeeDataService 
	extends EntityRepository<Employee>{
	// Classic CRUD methods
			// CREATE and UPDATE
			/* Project add(Project)					inherited from EntityRepository*/
			// READ
			/* Project getById(Project)				inherited from EntityRepository*/
			/* Collection<Project> toCollection()	inherited from EntityRepository*/
			// DELETE
			/* boolean remove(Project)				inherited from EntityRepository*/
			
			// create aggregate entity: project root with releases as components
			Employee createNewEmployee(Integer employeeID);
			// Query method on release components
			Task getTaskById(Integer taskID);
			// Other
			String getMessage();
	}