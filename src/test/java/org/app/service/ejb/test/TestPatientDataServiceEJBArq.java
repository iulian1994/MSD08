package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.PatientDataService;
import org.app.service.ejb.PatientDataServiceEJB;
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
	private static Logger logger = Logger.getLogger(TestPatientDataServiceEJBArq.class.getName());
	
	@EJB
	private static PatientDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Patient.class.getPackage())
	                .addClass(PatientDataService.class)
	                .addClass(PatientDataServiceEJB.class)
	                .addClass(EntityRepository.class)
	                .addClass(EntityRepositoryBase.class)
	                .addAsResource("META-INF/persistence.xml")
	                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}	
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}

	@Test
	public void test4_GetPatients() {
		logger.info("DEBUG: Junit TESTING: testGetProjects ...");
		Collection<Patient> Patients = service.toCollection();
		assertTrue("Fail to read Patients!", Patients.size() > 0);
	}

	@Test
	public void test3_AddPatient() {
		logger.info("DEBUG: Junit TESTING: testAddProject ...");
		
		Integer patientsToAdd = 3;
		for (int i=1; i <= patientsToAdd; i++){
			service.add(new Patient(i, "Project_" + (100 + i)));
		}
		Collection<Patient> projects = service.toCollection();
		assertTrue("Fail to add Projects!", projects.size() == patientsToAdd);
	}

	@Test
	public void test2_DeletePatientt() {
		logger.info("DEBUG: Junit TESTING: testDeleteProject ...");
		
		Collection<Patient> patients = service.toCollection();
		for (Patient p: patients)
			service.remove(p);
		Collection<Patient> ProjectsAfterDelete = service.toCollection();
		assertTrue("Fail to read Projects!", ProjectsAfterDelete.size() == 0);
	}	
}
