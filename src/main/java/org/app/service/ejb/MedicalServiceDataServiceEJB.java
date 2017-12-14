package org.app.service.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.MedicalService;

@Stateless @LocalBean
public class MedicalServiceDataServiceEJB 
		extends EntityRepositoryBase<MedicalService>
		implements MedicalServiceDataService {
	
	private static Logger logger = Logger.getLogger(MedicalServiceDataServiceEJB.class.getName());
	
	@PostConstruct
	public void init() {
		logger.info("POSTCONSTRUCT-INIT injected EntityManager: " + this.em);
	}

	public String getMessage() {
		return "===============================>>> MedicalService is working...";
	}

}