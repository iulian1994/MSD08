package org.app.service.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProjectView {
	private static String BASE_URL = "http://localhost:8080/ScrumREST/projects/project/";
	
	private Integer projectNo;
	private String name;
	private Integer releaseCount = 0;
	
	public Integer getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(Integer projectNo) {
		this.projectNo = projectNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getReleaseCount() {
		return releaseCount;
	}
	public void setReleaseCount(Integer releaseCount) {
		this.releaseCount = releaseCount;
	}
	public ProjectView(Integer projectNo, String name) {
		super();
		this.projectNo = projectNo;
		this.name = name;
	}
	public ProjectView() {
		super();
	}
	
	public ProjectView(Project project) {
		this.setProjectNo(project.getProjectNo());
		this.setName(project.getName());
		if (project.getReleases() != null)
			this.setReleaseCount(project.getReleases().size());
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((projectNo == null) ? 0 : projectNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectView other = (ProjectView) obj;
		if (projectNo == null) {
			if (other.projectNo != null)
				return false;
		} else if (!projectNo.equals(other.projectNo))
			return false;
		return true;
	}	
	
	/* Rest Resource URL*/
//	@XmlElement(name = "link", namespace = AtomLink.ATOM_NAMESPACE)
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getProjectNo();
        return new AtomLink(restUrl, "get-project");
    }
	
	public static ProjectView[] getProjectViewList(Collection<Project> projects){
		List<ProjectView> projectViewList = new ArrayList<>();
		for(Project p: projects){
			projectViewList.add(new ProjectView(p));
		}
		return projectViewList.toArray(new ProjectView[0]);
	}	
}

// http://www.w3.org/2005/Atom
