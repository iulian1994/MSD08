package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import org.app.service.ejb.Patient1DataService;
import org.app.service.ejb.Patient1DataServiceEJB;
import org.app.service.entities.Patient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class) 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPatient1DataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestPatient1DataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static Patient1DataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Patient.class.getPackage())
	                .addClass(Patient1DataService.class)
	                .addClass(Patient1DataServiceEJB.class)
	                .addAsResource("META-INF/persistence.xml")
	                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: getMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}

	@Test
	public void test2_DeletePatient() {
		logger.info("DEBUG: Junit TESTING: testDeletePatient ...");
		
		Collection<Patient> patients = service.getPatients();
		for (Patient f: patients)
			service.removePatient(f);
		Collection<Patient> patientsAfterDelete = service.getPatients();
		assertTrue("Fail to read features!", patientsAfterDelete.size() == 0);
	}	

	@Test
	public void test3_AddPatient() {
		logger.info("DEBUG: Junit TESTING: testAddPatient ...");
		
		Integer patientsToAdd = 9;
		for (int i=1; i <= patientsToAdd; i++){
			service.addPatient(new Patient(1141 + i, "Feature-curs_" + (100 + i)));
		}
		Collection<Patient> patients = service.getPatients();
		assertTrue("Fail to add features!", patients.size() == patientsToAdd);
	}
	
	@Test
	public void test4_GetFeatures() {
		logger.info("DEBUG: Junit TESTING: testGetFeatures ...");
		
		Collection<Patient> patients = service.getPatients();
		assertTrue("Fail to read features!", patients.size() > 0);
	}

}
/* http://localhost:8080/SCRUM-S2/data/features */