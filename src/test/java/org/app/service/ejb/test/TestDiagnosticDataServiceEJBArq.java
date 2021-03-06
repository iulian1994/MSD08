package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.app.service.ejb.DiagnosticDataService;
import org.app.service.ejb.DiagnosticDataServiceEJB;
import org.app.service.entities.Diagnostic;
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
public class TestDiagnosticDataServiceEJBArq {
	private static Logger logger = 
			Logger.getLogger(TestDiagnosticDataServiceEJBArq.class.getName());
	
	@EJB // EJB DataService Ref
	private static DiagnosticDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Diagnostic.class.getPackage())
	                .addClass(DiagnosticDataService.class)
	                .addClass(DiagnosticDataServiceEJB.class)
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
	public void test4_GetDiagnostic() {
		logger.info("DEBUG: Junit TESTING: testGetDIAG ...");
		
		Collection<Diagnostic> diagnostics = service.getDiagnostics();
		assertTrue("Fail to read PATIENTS!!!!!", diagnostics.size() > 0);
	}
	
	@Test
	public void test3_AddDiagnostic() {
		logger.info("DEBUG: Junit TESTING: testAddDIAG ...");
		
		Integer diagnosticsToAdd = 3;
		for (int i=1111; i <= diagnosticsToAdd; i++){
			//service.addFeature(new Feature(100 + i, "Feature_" + (100 + i)));
			service.addDiagnostic(new Diagnostic(1001 + i, "Patient_" + (1010 + i)));
			service.CreateDiagnostic(10 + i, "AAAA");
		}
		
		Collection<Diagnostic> diagnostics = service.getDiagnostics();
		
		assertTrue("Fail to add diagnostics!!!  nr=" + diagnostics.size(), diagnostics.size() == diagnosticsToAdd);
	}
	
	@Test
	public void test2_DeleteDiagnostic() {
		logger.info("DEBUG: Junit TESTING: testDeleteDIAG ...");
		
		Collection<Diagnostic> diagnostics = service.getDiagnostics();
		for (Diagnostic d: diagnostics)
			service.removeDiagnostic(d);
		Collection<Diagnostic> diagnosticAfter = service.getDiagnostics();
		assertTrue("Fail to read patients!", diagnosticAfter.size() == 0);
	}	
}