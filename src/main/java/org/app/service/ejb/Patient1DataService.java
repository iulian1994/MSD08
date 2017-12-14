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
import org.app.service.entities.Patient;

// Implement simple CRUD Operations
@Remote
public interface Patient1DataService{
	// CREATE or UPDATE
	Patient addPatient(Patient patientToAdd);

	// DELETE
	String removePatient(Patient patientToDelete);

	// READ
	Collection<Patient> getPatients();

	// Others
	String getMessage();
}

