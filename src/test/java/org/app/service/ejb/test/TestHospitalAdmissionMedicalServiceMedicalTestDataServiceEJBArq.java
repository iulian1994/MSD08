package org.app.service.ejb.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.app.patterns.EntityRepository;
import org.app.service.ejb.MedicalTestDataService;
import org.app.service.ejb.MedicalTestDataServiceEJB;
import org.app.service.ejb.HospitalAdmissionMedicalServiceDataService;
import org.app.service.ejb.HospitalAdmissionMedicalServiceDataServiceEJB;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.MedicalService;
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
public class TestHospitalAdmissionMedicalServiceMedicalTestDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestHospitalAdmissionMedicalServiceMedicalTestDataServiceEJBArq.class.getName());
	// Arquilian infrastructure
	@Deployment
	public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "msd-test.war")
                .addPackage(EntityRepository.class.getPackage()).addPackage(HospitalAdmission.class.getPackage())
                .addClass(MedicalTestDataService.class).addClass(MedicalTestDataServiceEJB.class)
                .addClass(HospitalAdmissionMedicalServiceDataService.class).addClass(HospitalAdmissionMedicalServiceDataServiceEJB.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	@EJB // Test EJB Data Service Reference is injected
	private static HospitalAdmissionMedicalServiceDataService service;	
	// JUnit test methods
	@Test
	public void test4_GetAdmission() {
		logger.info("DEBUG: Junit TESTING: testGetAdmission 7002 ...");
		HospitalAdmission hospitaladmission = service.getById(7002);
		assertNotNull("Fail to Get Admission 7002!", hospitaladmission);
	}
	/* CREATE Test 2: create aggregate*/
	@Test
	public void test3_CreateNewAddmission(){
		HospitalAdmission hospitaladmission = service.createNewHospitalAdmission(7002);
		assertNotNull("Fail to create new admission in repository!", hospitaladmission);
		// update project
				
		List<MedicalService> medicalservices = hospitaladmission.getMedicalservice();
		for(MedicalService m: medicalservices)
			m.setCodeName(m.getCodeName() + " - changed by test client Marius.agg");
		hospitaladmission = service.add(hospitaladmission);
		assertNotNull("Fail to save new admission in repository!", hospitaladmission);
		logger.info("DEBUG createNewadmission: admission changed: " + hospitaladmission);
		// check read
		hospitaladmission = service.getById(7002);  // !!!
		assertNotNull("Fail to find changed admission in repository!", hospitaladmission);
		logger.info("DEBUG createNewadmission: queried admission" + hospitaladmission);
	}
	@Test
	public void test2_DeleteAdmissions() {
		logger.info("DEBUG: Junit TESTING: testDeleteAdmissions 7002...");
		HospitalAdmission hospitaladmission = service.getById(7002);  // !!!
		if (hospitaladmission != null)
			service.remove(hospitaladmission);
		hospitaladmission = service.getById(7002);  // !!!
		assertNull("Fail to delete Admission 7002!", hospitaladmission);
	}
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}	
}