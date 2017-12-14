package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.Patient;


@Remote
public interface PatientDataService 
	extends EntityRepository<Patient>{

	String getMessage();
}