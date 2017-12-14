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

// Implement simple CRUD Operations
@Remote
public interface FeatureDataService{
	// CREATE or UPDATE
	Feature addFeature(Feature featureToAdd);

	// DELETE
	String removeFeature(Feature featureToDelete);

	// READ
	Feature getFeatureByID(Integer featureID);
	Collection<Feature> getFeatures();
	
	// Custom READ: custom query
	Feature getFeatureByName(String featureName);
	
	// Others
	String getMessage();

}