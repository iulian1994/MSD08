package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Project;
import org.app.service.entities.Release;

@Stateless @LocalBean
public class ProjectReleaseFeatureDataServiceEJB extends EntityRepositoryBase<Project>
		implements ProjectReleaseFeatureDataService, Serializable {
	private static Logger logger = Logger.getLogger(ProjectReleaseFeatureDataServiceEJB.class.getName());
	
	@EJB // injected DataService
	private FeatureDataService featureDataService; 
	// Local component-entity-repository
	private EntityRepository<Release> releaseRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		releaseRepository = new EntityRepositoryBase<Release>(this.em,Release.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.releaseRepository);
		logger.info("POSTCONSTRUCT-INIT featureDataService: " + this.featureDataService);
	}
	
	// Aggregate factory method
	public Project createNewProject(Integer id){
		// create project aggregate
		Project project = new Project(id, "NEW Project" + "." + id , new Date());
		List<Release> releasesProject = new ArrayList<>();
		Date dataPublicare = new Date();
		Long interval =  30l /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		Integer releaseCount = 3;
		for (int i=0; i<=releaseCount-1; i++){
			releasesProject.add(new Release(null, "R: " + project.getProjectNo() + "." + i, 
					new Date(dataPublicare.getTime() + i * interval), project));
		}
		project.setReleases(releasesProject);		
		// save project aggregate
		this.add(project);
		// return project aggregate to service client
		return project;
	}
	
	public Release getReleaseById(Integer releaseid) {
		return releaseRepository.getById(releaseid);
	}

	public String getMessage() {
		return "ProjectSprint DataService is working...";
	}
}



