package org.app.service.ejb;

import javax.ejb.Remote;
import org.app.patterns.EntityRepository;
import org.app.service.entities.Patient;
import org.app.service.entities.HospitalAdmission;

@Remote
public interface Patient1DataService extends EntityRepository<Patient> {
	// Classic CRUD methods
		// CREATE and UPDATE
		/* Project add(Project)					inherited from EntityRepository*/
		// READ
		/* Project getById(Project)				inherited from EntityRepository*/
		/* Collection<Project> toCollection()	inherited from EntityRepository*/
		// DELETE
		/* boolean remove(Project)				inherited from EntityRepository*/
		
		// create aggregate entity: project root with releases as components
		Patient createNewPatient(Integer patientid);
		// Query method on release components
		HospitalAdmission getHospitalAdmissionById(Integer admissionNo);
		// Other
		String getMessage();
}