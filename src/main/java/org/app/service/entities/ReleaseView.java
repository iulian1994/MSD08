package org.app.service.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ReleaseView {
	private static String BASE_URL = "http://localhost:8080/ScrumREST/projects/project/";
	
	private Integer releaseId;
	private String codeName;
	private String indicative;
	private String description;	
	private Date publishDate;
	private ProjectView project;
	
	public Integer getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getIndicative() {
		return indicative;
	}

	public void setIndicative(String indicative) {
		this.indicative = indicative;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public ProjectView getProject() {
		return project;
	}

	public void setProject(ProjectView project) {
		this.project = project;
	}

	public ReleaseView() {
	}

	public ReleaseView(Release release) {
		this.releaseId = release.getReleaseId();
		this.codeName = release.getCodeName();
		this.indicative = release.getIndicative();
		this.description = release.getDescription();
		this.publishDate = release.getPublishDate();
		this.project = new ProjectView(release.getProject());
	}

	public ReleaseView(Integer releaseId, String codeName, String indicative,
			String description, Date publishDate, ProjectView project) {
		super();
		this.releaseId = releaseId;
		this.codeName = codeName;
		this.indicative = indicative;
		this.description = description;
		this.publishDate = publishDate;
		this.project = project;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((releaseId == null) ? 0 : releaseId.hashCode());
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
		ReleaseView other = (ReleaseView) obj;
		if (releaseId == null) {
			if (other.releaseId != null)
				return false;
		} else if (!releaseId.equals(other.releaseId))
			return false;
		return true;
	}
	
	/* REST Resource URL */	
//	@XmlElement(name = "link", namespace = AtomLink.ATOM_NAMESPACE)
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getReleaseId();
        return new AtomLink(restUrl, "get-project");
    }	
}
