package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.MedicalService;

@Remote
public interface HospitalAdmissionMedicalServiceDataService extends EntityRepository<HospitalAdmission> {
		// create aggregate entity: hospital admission root with medicalservice as components
		HospitalAdmission createNewHospitalAdmission(Integer id);
		// Query method on release components
		MedicalService getMedicalServiceById(Integer serviceId);
		// Other
		String getMessage();
}
