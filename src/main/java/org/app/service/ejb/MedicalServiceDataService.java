package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.MedicalService;
import org.app.service.entities.MedicalActivity;


@Remote
public interface MedicalServiceDataService 
	extends EntityRepository<MedicalService>{
	
	MedicalService createNewMedicalService(Integer serviceId);
	
	MedicalActivity getMedicalMedicalActivityById(Integer activityID);
	
	String getMessage();
	}