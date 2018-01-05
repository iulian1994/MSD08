package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.MedicalService;

@Remote
public interface HospitalAdmissionDataService extends EntityRepository<HospitalAdmission> {
	// Classic CRUD methods
	// CREATE and UPDATE
	/* HospitalAdmission add(HospitalAdmission)					inherited from EntityRepository*/
	// READ
	/* HospitalAdmission getById(HospitalAdmission)				inherited from EntityRepository*/
	/* Collection<HospitalAdmission> toCollection()	inherited from EntityRepository*/
	// DELETE
	/* boolean remove(HospitalAdmission)				inherited from EntityRepository*/
	
	// Other operations
	String getMessage();
}
