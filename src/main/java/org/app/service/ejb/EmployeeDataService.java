package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.Employee;

@Remote
public interface EmployeeDataService 
	extends EntityRepository<Employee>{
		String getMessage();
	}