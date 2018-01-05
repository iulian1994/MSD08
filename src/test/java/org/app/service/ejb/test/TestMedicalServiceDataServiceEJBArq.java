package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.MedicalServiceDataService;
import org.app.service.ejb.MedicalServiceDataServiceEJB;
import org.app.service.entities.Employee;
import org.app.service.entities.MedicalService;
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
public class TestMedicalServiceDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestMedicalServiceDataServiceEJBArq.class.getName());
	
	@EJB
	private static MedicalServiceDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Employee.class.getPackage())
	                .addClass(MedicalServiceDataService.class)
	                .addClass(MedicalServiceDataServiceEJB.class)
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
	public void test4_GetMedicalService() {
		logger.info("DEBUG: Junit TESTING: testGetMedicalService =========>>>> ...");
		Collection<MedicalService> MedicalService = service.toCollection();
		assertTrue("Fail to read MedService!", MedicalService.size() > 0);
	}

	@Test
	public void test3_AddMedicalService() {
		logger.info("DEBUG: Junit TESTING: testAddMedicalService ...");
		
		Integer medservToAdd = 23;
		for (int i=1; i <= medservToAdd; i++){
			//service.add(new MedicalService(i, "MedicalServ_nr_" + (100 + i)));
			service.add(new MedicalService(i, "Allalala"));
		}
		Collection<MedicalService> medservices = service.toCollection();
		assertTrue("Fail to add MedicalServices!", medservices.size() == medservToAdd);
	}

	@Test
	public void test2_DeleteMedicalService() {
		logger.info("DEBUG: Junit TESTING: testDeleteMedicalServ ...");
		
		Collection<MedicalService> medservices = service.toCollection();
		for (MedicalService m: medservices)
			service.remove(m);
		Collection<MedicalService> MedAfterDelete = service.toCollection();
		assertTrue("Fail to read MedicalService!", MedAfterDelete.size() == 0);
	}	
}
