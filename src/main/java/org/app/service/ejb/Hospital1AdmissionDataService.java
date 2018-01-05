package org.app.service.ejb;

import javax.ejb.Remote;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.service.entities.EntityBase;
import org.app.patterns.EntityRepository;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.Patient;

@Remote
public interface Hospital1AdmissionDataService {
	// CREATE or UPDATE
	HospitalAdmission addHospitalAdmission(HospitalAdmission hospitaladmissionToAdd);

	// DELETE
	String removeHospitalAdmission(HospitalAdmission hospitalAdmissionToDelete);

	// READ
	HospitalAdmission getHospitalAdmissionByID(Integer addmissionNo);
	Collection<HospitalAdmission> getHospitalAdmissions();
	
	// Others
	String getMessage();
}
