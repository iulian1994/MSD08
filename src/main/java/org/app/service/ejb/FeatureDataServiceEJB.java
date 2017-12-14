package org.app.service.ejb;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Feature;

@Stateless @LocalBean
public class FeatureDataServiceEJB implements FeatureDataService{
	private static Logger logger = Logger.getLogger(FeatureDataServiceEJB.class.getName());
	
	/* DataService initialization */
	// Inject resource 
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	// Constructor
	public FeatureDataServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		

	/* CRUD operations implementation */
	// CREATE or UPDATE
	@Override
	public Feature addFeature(Feature featureToAdd){
		em.persist(featureToAdd);
		em.flush();
		// transactions are managed by default by container
		em.refresh(featureToAdd);
		return featureToAdd;
	}	
	// READ
	@Override
	public Feature getFeatureByID(Integer featureID) {
		return em.find(Feature.class, featureID);
	}	
	public Collection<Feature> getFeatures(){
		List<Feature> features = em.createQuery("SELECT f FROM Feature f", Feature.class)
				.getResultList();
		return features;
	}
	// REMOVE
	@Override
	public String removeFeature(Feature featureToDelete){
		featureToDelete = em.merge(featureToDelete);
		em.remove(featureToDelete);
		em.flush();
		return "True";
	}
	
	// Custom READ: custom query
	@Override
	public Feature getFeatureByName(String name) {
		return em.createQuery("SELECT f FROM Feature f WHERE f.name = :name", Feature.class)
				.setParameter("name", name)
				.getSingleResult();
	}	
	
	// Others
	public String getMessage() {
		return "FeatureServiceEJB is ON... ";
	}
}


