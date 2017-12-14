package org.app.service.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Sprint;

@Stateless @LocalBean
public class SprintDataServiceEJB 
		extends EntityRepositoryBase<Sprint>
		implements SprintDataService {
	
	private static Logger logger = Logger.getLogger(SprintDataServiceEJB.class.getName());
	
	@PostConstruct
	public void init() {
		logger.info("POSTCONSTRUCT-INIT injected EntityManager: " + this.em);
	}

	public String getMessage() {
		return "SprintDataServiceEJB DataService is working...";
	}

}