package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.ProjectDataService;
import org.app.service.ejb.ProjectDataServiceEJB;
import org.app.service.entities.Project;
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
public class TestProjectDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestProjectDataServiceEJBArq.class.getName());
	
	@EJB
	private static ProjectDataService service;
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-test.war")
	                .addPackage(Project.class.getPackage())
	                .addClass(ProjectDataService.class)
	                .addClass(ProjectDataServiceEJB.class)
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
	public void test4_GetProjects() {
		logger.info("DEBUG: Junit TESTING: testGetProjects ...");
		Collection<Project> Projects = service.toCollection();
		assertTrue("Fail to read Projects!", Projects.size() > 0);
	}

	@Test
	public void test3_AddProject() {
		logger.info("DEBUG: Junit TESTING: testAddProject ...");
		
		Integer projectsToAdd = 3;
		for (int i=1; i <= projectsToAdd; i++){
			service.add(new Project(i, "Project_" + (100 + i)));
		}
		Collection<Project> projects = service.toCollection();
		assertTrue("Fail to add Projects!", projects.size() == projectsToAdd);
	}

	@Test
	public void test2_DeleteProject() {
		logger.info("DEBUG: Junit TESTING: testDeleteProject ...");
		
		Collection<Project> projects = service.toCollection();
		for (Project p: projects)
			service.remove(p);
		Collection<Project> ProjectsAfterDelete = service.toCollection();
		assertTrue("Fail to read Projects!", ProjectsAfterDelete.size() == 0);
	}	
}
