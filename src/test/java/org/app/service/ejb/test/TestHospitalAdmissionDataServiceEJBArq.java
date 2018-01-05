package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.HospitalAdmissionDataService;
import org.app.service.ejb.HospitalAdmissionDataServiceEJB;
import org.app.service.entities.HospitalAdmission;
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
public class TestHospitalAdmissionDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestHospitalAdmissionDataServiceEJBArq.class.getName());
	
	@EJB
	private static HospitalAdmissionDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(HospitalAdmission.class.getPackage())
	                .addClass(HospitalAdmissionDataService.class)
	                .addClass(HospitalAdmissionDataServiceEJB.class)
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
	public void test4_GetAdmissions() {
		logger.info("DEBUG: Junit TESTING: testGetProjects ...");
		Collection<HospitalAdmission> HospitalAdmissions = service.toCollection();
		assertTrue("Fail to read Addmissions!", HospitalAdmissions.size() > 0);
	}
	
	@Test
	public void test3_AddAdmissions() {
		logger.info("DEBUG: Junit TESTING: testAddAdmissions ...");
		
		Integer admissionsToAdd = 13;
		for (int i=1; i <= admissionsToAdd; i++){
			service.add(new HospitalAdmission(i));
		}
		Collection<HospitalAdmission> admissions = service.toCollection();
		assertTrue("Fail to add Addmissions!", admissions.size() == admissionsToAdd);
	}
	
	@Test
	public void test2_DeleteAdmissions() {
		logger.info("DEBUG: Junit TESTING: testDeleteAdmissions ...");
		
		Collection<HospitalAdmission> admissions = service.toCollection();
		for (HospitalAdmission a: admissions)
			service.remove(a);
		Collection<HospitalAdmission> AddmissionsAfterDelete = service.toCollection();
		assertTrue("Fail to read Addmissions!", AddmissionsAfterDelete.size() == 0);
	}	
}