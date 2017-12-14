package org.app.service.ejb.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.service.ejb.FeatureDataService;
import org.app.service.ejb.FeatureDataServiceEJB;
import org.app.service.ejb.PatientDataService;
import org.app.service.ejb.PatientDataServiceEJB;
import org.app.service.entities.Feature;
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
public class TestPatientDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestPatientDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static PatientDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Patient.class.getPackage())
	                .addClass(PatientDataService.class)
	                .addClass(PatientDataServiceEJB.class)
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
	public void test4_GetPatients() {
		logger.info("DEBUG: Junit TESTING: testGetPATIENTS ...");
		
		Collection<Patient> patients = service.getPatients();
		assertTrue("Fail to read PATIENTS!!!!! dmzzz", patients.size() > 0);
	}
	
	@Test
	public void test3_AddPatients() {
		logger.info("DEBUG: Junit TESTING: testAddPATIENT ...");
		
		Integer patientsToAdd = 3;
		for (int i=1111; i <= patientsToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addPatient(new Patient(1001 + i, "Patient_" + (1010 + i)));
			service.addPatient(new Patient(i,"marius"));
		}
		Collection<Patient> patients = service.getPatients();
		assertTrue("Fail to add patients!!! dmzzz  nr=" + patients.size(), patients.size() == patientsToAdd);
	}
	
	@Test
	public void test2_DeletePatients() {
		logger.info("DEBUG: Junit TESTING: testDeletePatients ...");
		
		Collection<Patient> patients = service.getPatients();
		for (Patient p: patients)
			service.dischargePatient(p);
		Collection<Patient> patientsAfterDischarge = service.getPatients();
		assertTrue("Fail to read patients!", patientsAfterDischarge.size() == 0);
	}	
}