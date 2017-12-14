package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Project;
import org.app.service.entities.Release;

@Remote
public interface ProjectSprintDataService extends EntityRepository<Project>{
	Project createNewProject(Integer id);
	Release getReleaseById(Integer releaseid);
	String getMessage();
	
	// createNewProjectWithReleases();
	// getProjectByIdWithReleases();
	// getReleaseById();
	
	// createSprintsForProject();
	// getSprintById();
	
}