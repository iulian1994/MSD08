package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.app.patterns.EntityRepository;
import org.app.service.ejb.EmployeeDataService;
import org.app.service.ejb.EmployeeDataServiceEJB;
import org.app.service.ejb.EmployeeTaskDataService;
import org.app.service.ejb.EmployeeTaskDataServiceEJB;
import org.app.service.ejb.ValidatorInterceptor;
import org.app.service.entities.Employee;
import org.app.service.entities.Task;
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
public class TestEmployeeTaskDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestEmployeeTaskDataServiceEJBArq.class.getName());
	
	@Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "msd-test.war")
                .addPackage(EntityRepository.class.getPackage()).addPackage(Employee.class.getPackage())
                .addClass(EmployeeDataService.class).addClass(EmployeeDataServiceEJB.class)
                .addClass(EmployeeTaskDataService.class).addClass(EmployeeTaskDataServiceEJB.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	@EJB // Test EJB Data Service Reference is injected
	private static EmployeeTaskDataService service;	
	// JUnit test methods
	@Test
	public void test4_GetEmployee() {
		logger.info("DEBUG: Junit TESTING: testGetEmployee 7002 ...");
		Employee employee = service.getById(7002);
		assertNotNull("Fail to Get Employee 7002!", employee);
	}
	/* CREATE Test 2: create aggregate*/
	@Test
	public void test3_CreateNewEmployee(){
		Employee employee = service.createNewEmployee(7002);
		assertNotNull("Fail to create new employee in repository!", employee);
		// update project
		employee.setName(employee.getName() + " - changed by test client");		
		Collection<Task> tasks = employee.getTasks();
		for(Task r: tasks)
			r.setTaskDescription(r.getTaskDescription() + " - changed by test client");
		employee = service.add(employee);
		assertNotNull("Fail to save new employee in repository!", employee);
		logger.info("DEBUG createNewEmployee: project changed: " + employee);
		// check read
		employee = service.getById(7002);  // !!!
		assertNotNull("Fail to find changed employee in repository!", employee);
		logger.info("DEBUG createNewEmployee: queried project" + employee);
	}		

	@Test
	public void test2_DeleteEmployee() {
		logger.info("DEBUG: Junit TESTING: testDeleteEmployee 7002...");
		Employee employee = service.getById(7002);  // !!!
		if (employee != null)
			service.remove(employee);
		employee = service.getById(7002);  // !!!
		assertNull("Fail to delete Employee 7002!", employee);
	}	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}	
}




