package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.FeatureDataService;
import org.app.service.ejb.FeatureDataServiceEJB;
import org.app.service.ejb.ProjectFactory;
import org.app.service.ejb.ProjectSprintDataService;
import org.app.service.ejb.ProjectSprintDataServiceEJB;
import org.app.service.ejb.SprintDataService;
import org.app.service.ejb.SprintDataServiceEJB;
import org.app.service.ejb.ValidatorInterceptor;
import org.app.service.entities.Project;
import org.app.service.entities.Release;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/*
 * JUnit test for EJB: ProjectSprintDataServiceEJB TestProjectSprintDataServiceEJBArq
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProjectSprintDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestProjectSprintDataServiceEJBArq.class.getName());	
	// Arquilian infrastructure
	@Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "msd-test.war")
                .addPackage(EntityRepository.class.getPackage())
                .addPackage(Project.class.getPackage())
                .addClass(SprintDataService.class).addClass(SprintDataServiceEJB.class)
                .addClass(FeatureDataService.class).addClass(FeatureDataServiceEJB.class)
                .addClass(ProjectFactory.class)
                .addClass(ProjectSprintDataService.class).addClass(ProjectSprintDataServiceEJB.class)
                .addClass(ValidatorInterceptor.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	@EJB
	private static ProjectSprintDataService service;
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}

	@Test
	public void test4_GetProjects() {
		logger.info("DEBUG: Junit TESTING: testGetProject 7002 ...");
		Project project = service.getById(7002);
		assertNotNull("Fail to Get Project 7002!", project);
	}
	
	/* CREATE Test 2: create aggregate*/
	@Test
	public void test3_CreateNewProject(){
		Project project = service.createNewProject(7002);
//		Project project = service.createNewProject(null);
		assertNotNull("Fail to create new project in repository!", project);
		// update project
		project.setName(project.getName() + " - changed by test client");		
		List<Release> releases = project.getReleases();
		// update project components
		for(Release r: releases)
			r.setIndicative(r.getIndicative() + " - changed by test client");
		project = service.add(project);
		assertNotNull("Fail to save new project in repository!", project);
		logger.info("DEBUG createNewProject: project changed: " + project);
		// check read
		project = service.getById(7002);  // !!!
		assertNotNull("Fail to find changed project in repository!", project);
		logger.info("DEBUG createNewProject: queried project" + project);
	}		

	@Test
	public void test2_DeleteProject() {
		logger.info("DEBUG: Junit TESTING: testDeleteProject 7002...");
		
		Project project = service.getById(7002);  // !!!
		if (project != null)
			service.remove(project);
		
		project = service.getById(7002);  // !!!
		assertNull("Fail to delete Project 7002!", project);
		
	}	
}




