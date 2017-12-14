package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.Employee;
import org.app.service.entities.Release;
import org.app.service.entities.Task;

@Remote
public interface EmployeeTaskDataService extends EntityRepository<Employee>{
	
	Employee createNewEmployee(Integer eid);
	
	Task getTaskById(Integer id);
	
	String getMessage();
}
