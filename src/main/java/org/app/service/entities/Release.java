package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="release")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Release implements Serializable{
	/* internal member fields*/
	@Id @GeneratedValue
	private Integer releaseId;
	private String codeName; // NEW born
	private String indicative; // vers 1.1
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date publishDate; // dataEstimarePublicare
	
	@ManyToOne
	private Project project;

	@OneToMany(cascade = ALL, fetch=FetchType.EAGER)
	private List<Feature> features = new ArrayList<>();
	
	/* properties for xmls and json dtos*/
	@XmlElement
	public Integer getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}
	
	@XmlElement
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	@XmlElement
	public String getIndicative() {
		return indicative;
	}
	public void setIndicative(String indicative) {
		this.indicative = indicative;
	}
	
	@XmlElement
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	@XmlElement
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@XmlElementWrapper(name = "features")
    @XmlElement(name = "feature")
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	/* constructors*/
	public Release() {
		super();
	}
	
	public Release(Integer releaseId, String codeName, String indicative,
			String description, Date publishDate, Project project) {
		super();
		this.releaseId = releaseId;
		this.codeName = codeName;
		this.indicative = indicative;
		this.description = description;
		this.publishDate = publishDate;
		this.project = project;
	}

	public Release(Integer releaseId, String indicative, Date publishDate,
			Project project) {
		super();
		this.releaseId = releaseId;
		this.indicative = indicative;
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
		Release other = (Release) obj;
		if (releaseId == null) {
			if (other.releaseId != null)
				return false;
		} else if (!releaseId.equals(other.releaseId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Release [releaseId=" + releaseId + ", codeName=" + codeName
				+ ", indicative=" + indicative + "]";
	}

	/* Rest Resource URL*/
//	public static String BASE_URL = "http://localhost:8080/ScrumREST/projects/";
	
	public static String BASE_URL = Project.BASE_URL;
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL 
				+ this.getProject().getProjectNo() 
				+ "/releases/" 
				+ this.getReleaseId();
        return new AtomLink(restUrl, "get-release");
    }	
	public void setLink(AtomLink link){}

	/* DTO Logic*/
	public Release toDTO(){
		return new Release(releaseId, indicative, publishDate, project.toDTO());
	}
	
	public static List<Release> toDTOList(List<Release> releases){
		List<Release> releaseDTOList = new ArrayList<>();
		for(Release r: releases){
			releaseDTOList.add(r.toDTO());
		}
		return releaseDTOList;
	}	
}