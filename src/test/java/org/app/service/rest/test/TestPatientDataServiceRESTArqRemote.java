package org.app.service.rest.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.Patient1DataService;
import org.app.service.entities.Patient;
import org.app.service.rest.ApplicationConfig;
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
public class TestPatientDataServiceRESTArqRemote {
	private static Logger logger = Logger.getLogger(TestPatientDataServiceRESTArqRemote.class.getName());

//	 server_wildfly_web_url/deployment_archive_name/ApplicationConfig_@ApplicationPath/EJB_@Path
	private static String serviceURL = "http://localhost:8080/MSD-S3/data/patients";	
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-s4-test.war")
	                .addPackage(Patient.class.getPackage()); // all mode by default
	}	
	@Test
	public void test1_GetMessage() {
		String resourceURL = serviceURL + "/test";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		String response = ClientBuilder.newClient().target(resourceURL)
				.request().get()
				.readEntity(String.class);
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}

	@Test
	public void test4_GetProjects() {
		logger.info("DEBUG: Junit TESTING: test4_GetProjects ...");
		Collection<Patient> patients = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Patient>>(){});
		assertTrue("Fail to read Patients!", patients.size() > 0);
		patients.stream().forEach(System.out::println);
	}

	@Test
	public void test3_AddProject() {
		// addIntoCollection
		logger.info("DEBUG: Junit TESTING: test3_AddProject ...");
		Client client = ClientBuilder.newClient();
		Collection<Patient> patients;
		Integer patientsToAdd = 3;
		Patient patient;
		for (int i=1; i <= patientsToAdd; i++){
			patient = new Patient(i, "Patient_remote_" + (100 + i));
			patients = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(patient, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Patient>>(){});
			assertTrue("Fail to read Projects!", patients.size() > 0);
		}
		patients = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Patient>>(){});
		assertTrue("Fail to add Projects!", patients.size() >= patientsToAdd);
		patients.stream().forEach(System.out::println);
	}
	
	@Test
	public void test3_CreateProject() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: Junit TESTING: test3_CreatePatient ...");
		Client client = ClientBuilder.newClient();
		
		Integer patientsToAdd = 3;
		Patient patient;
		for (int i=1; i <= patientsToAdd; i++){
			patient = ClientBuilder.newClient().target(resourceURL + i)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Patient.class);
			System.out.println(">>> Created/posted Project: " + patient);
		}

		Collection<Patient> patients = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Patient>>(){});
		
		assertTrue("Fail to add Patient!", patients.size() >= patientsToAdd);
	}	
	
	@Test
	public void test2_DeleteProject() {
		String resourceURL = serviceURL + "/";
		logger.info("DEBUG: Junit TESTING: test2_DeleteProject ...");
		Client client = ClientBuilder.newClient();
		Collection<Patient> patients = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Patient>>(){});		
		
		for (Patient p: patients) {
			client.target(resourceURL + p.getPatientid()).request().delete();
		}
		
		Collection<Patient> patientsAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Patient>>(){});	
		assertTrue("Fail to read Projects!", patientsAfterDelete.size() == 0);
	}
//	@Test
	public void test1_GetByID() {
		String resourceURL = serviceURL + "/1";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		
		Patient patient = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Patient.class);
		
		assertNotNull("REST Data Service failed!", patient);
		logger.info(">>>>>> DEBUG: REST Response ..." + patient);
	}	
	@Test
	public void test5_UpdateProject() {
		String resourceURL = serviceURL + "/1"; //createNewProject
		logger.info("************* DEBUG: Junit TESTING: test5_UpdateProject ... :" + resourceURL);
		Client client = ClientBuilder.newClient();
		// Get project
		Patient patient = client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Patient.class);
		
		assertNotNull("REST Data Service failed!", patient);
		logger.info(">>> Initial Project: " + patient);
		
		// update and save project
		patient.setName(patient.getName() + "_UPD_JSON_REMOTE");
		patient = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(patient, MediaType.APPLICATION_JSON))
				.readEntity(Patient.class);
		
		logger.info(">>> Updated Project: " + patient);
		
		assertNotNull("REST Data Service failed!", patient);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
