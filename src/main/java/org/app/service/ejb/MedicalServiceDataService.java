package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.MedicalService;

@Remote
public interface MedicalServiceDataService 
	extends EntityRepository<MedicalService>{
		String getMessage();
	}