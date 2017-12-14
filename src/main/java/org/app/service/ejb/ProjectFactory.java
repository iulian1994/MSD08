package org.app.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.app.service.entities.Project;
import org.app.service.entities.Release;

@Singleton
public class ProjectFactory{
	@TransactionAttribute(TransactionAttributeType.SUPPORTS) // propagate transaction
	public static Project buildProiect(Integer projectID, String name, Integer releaseCount){
		
		Project project = new Project(projectID, name + "." + projectID , new Date());
		List<Release> releasesProject = new ArrayList<>();
		
		Date dataPublicare = new Date();
		Long interval =  30l /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		
		for (int i=0; i<=releaseCount-1; i++){
			releasesProject.add(new Release(null, "R: " + project.getProjectNo() + "." + i, 
					new Date(dataPublicare.getTime() + i * interval), project));
		}
		
		project.setReleases(releasesProject);
		
		return project;
	}

	@Override
	public String toString() {
		return "ProjectFactory-instance";
	}
}
