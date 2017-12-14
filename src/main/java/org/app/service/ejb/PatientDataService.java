package org.app.service.ejb;

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
import org.app.service.entities.Feature;
import org.app.service.entities.Patient;

@Remote
public interface PatientDataService{
	// CREATE or UPDATE
	Patient addPatient(Patient patientToAdd);

	// DELETE
	String dischargePatient(Patient patientToDischage);

	// READ
	Patient getPatientByID(int patientID);
	Collection<Patient> getPatients();
	
	// Custom READ: custom query
	Patient getPatientByName(String patientName);
	
	// Others
	String getMessage();
}
