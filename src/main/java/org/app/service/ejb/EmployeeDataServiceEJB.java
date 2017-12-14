package org.app.service.ejb;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Employee;
import org.app.service.entities.Project;
import org.app.service.entities.Release;

@Stateless @LocalBean
public class EmployeeDataServiceEJB 
		extends EntityRepositoryBase<Employee>
		implements EmployeeDataService {
	
	private static Logger logger = Logger.getLogger(EmployeeDataServiceEJB.class.getName());
	
	@PostConstruct
	public void init() {
		logger.info("POSTCONSTRUCT-INIT injected EntityManager: " + this.em);
	}

	public String getMessage() {
		return "ProjectSprint DataService is working...";
	}

}