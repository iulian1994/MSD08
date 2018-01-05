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
import org.app.service.entities.MedicalTest;

@Remote
public interface MedicalTestDataService {
	// CREATE or UPDATE
		MedicalTest addMedicalTest(MedicalTest medicaltestToAdd);

		// DELETE
		String removeMedicalTest(MedicalTest medicaltestToDelete);

		// READ
		MedicalTest getMedicalTestByID(Integer testID);
		Collection<MedicalTest> getMedicalTest();
		
		// Custom READ: custom query
		MedicalTest getMedicalTestByName(String medicaltestName);
		
		// Others
		String getMessage();
}
