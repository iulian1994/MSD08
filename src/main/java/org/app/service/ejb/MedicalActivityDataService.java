package org.app.service.ejb;
import javax.ejb.Remote;
import java.util.Collection;
import org.app.service.entities.MedicalActivity;

@Remote
public interface MedicalActivityDataService {
	// CREATE or UPDATE
	MedicalActivity addMedicalActivity(MedicalActivity activityToAdd);

	// DELETE
	String removeMedicalActivity(MedicalActivity activityToRemove);

	// READ
	MedicalActivity getMedicalActivityByID(Integer activityID);
	Collection<MedicalActivity> getMedicalActivities();
	
	// Others
	String getMessage();
}
