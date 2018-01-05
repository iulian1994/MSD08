package org.app.service.ejb.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.app.service.ejb.MedicalTestDataService;
import org.app.service.ejb.MedicalTestDataServiceEJB;
import org.app.service.entities.MedicalTest;
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
public class TestMedicalTestDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestMedicalTestDataServiceEJBArq.class.getName());
	@EJB // EJB DataService Ref
	private static MedicalTestDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(MedicalTest.class.getPackage())
	                .addClass(MedicalTestDataService.class)
	                .addClass(MedicalTestDataServiceEJB.class)
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
	public void test4_GetMedicalTest() {
		logger.info("DEBUG: Junit TESTING: testGetMedTest ...");
		
		Collection<MedicalTest> medicaltests = service.getMedicalTest();
		assertTrue("Fail to read features!", medicaltests.size() > 0);
	}
	
	@Test
	public void test3_AddMedicalTest() {
		logger.info("DEBUG: Junit TESTING: testAddMedTest ...");
		
		Integer medicaltestToAdd = 3;
		for (int i=1; i <= medicaltestToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addMedicalTest(new MedicalTest(null, "MedicalTest_" + (100 + i)));
		}
		Collection<MedicalTest> medicaltests = service.getMedicalTest();
		assertTrue("Fail to add features!", medicaltests.size() == medicaltestToAdd);
	}
	
	@Test
	public void test2_DeleteMedicalTest() {
		logger.info("DEBUG: Junit TESTING: testDeleteMedicalTest ...");
		
		Collection<MedicalTest> medicaltests = service.getMedicalTest();
		for (MedicalTest m: medicaltests)
			service.removeMedicalTest(m);
		Collection<MedicalTest> medicaltestsAfterDelete = service.getMedicalTest();
		assertTrue("Fail to read features!", medicaltestsAfterDelete.size() == 0);
	}	
}