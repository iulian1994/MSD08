package org.app.service.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="activities") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class MedicalActivity implements Comparable<MedicalActivity>, Serializable {
	@Id @NotNull
	protected Integer activityID;
	private String name;
	private String description;
	@ManyToOne
	private MedicalService rel_service;
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(MedicalActivity other) {
		if (this.equals(other))
			return 0;
		return this.getName().compareTo(other.getName());
	}
	@XmlElement
	public Integer getActivityID() {
		return activityID;
	}
	public void setActivityID(Integer activityID) {
		this.activityID = activityID;
	}
	@XmlTransient
	public MedicalService getRel_service() {
		return rel_service;
	}
	public void setRel_service(MedicalService rel_service) {
		this.rel_service = rel_service;
	}
	public MedicalActivity(Integer activityID, String name, String description, MedicalService rel_service) {
		super();
		this.activityID = activityID;
		this.name = name;
		this.description = description;
		this.rel_service = rel_service;
	}
	public MedicalActivity() {
		super();
	}
	public MedicalActivity(Integer activityID, String name) {
		super();
		this.activityID = activityID;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityID == null) ? 0 : activityID.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rel_service == null) ? 0 : rel_service.hashCode());
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
		MedicalActivity other = (MedicalActivity) obj;
		if (activityID == null) {
			if (other.activityID != null)
				return false;
		} else if (!activityID.equals(other.activityID))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rel_service == null) {
			if (other.rel_service != null)
				return false;
		} else if (!rel_service.equals(other.rel_service))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MedicalTest [testID=" + activityID + ", name=" + name + ", description=" + description + "]";
	}
	public static String BASE_URL = MedicalService.BASE_URL;
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL 
				+ ((this.getRel_service() != null) ? this.getRel_service().getServiceId() : "")
				+ "/activities/" 
				+ this.getActivityID();
        return new AtomLink(restUrl, "get-activities");
    }	
	public void setLink(AtomLink link){}
}
