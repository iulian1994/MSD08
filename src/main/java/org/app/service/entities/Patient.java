package org.app.service.entities;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="patient") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Patient implements Serializable{
	@Id 
	private Integer patientid;
	private String name;
	@OneToMany(mappedBy="patient", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<HospitalAdmission> hospitaladmission = new ArrayList<HospitalAdmission>();
	
	public Patient() {
		super();
	}
	public Patient(Integer patientid, String name) {
		super();
		this.patientid = patientid;
		this.name = name;
	}
	@XmlElement
	public Integer getPatientid() {
		return patientid;
	}
	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElementWrapper(name = "admissions") @XmlElement(name = "hospitaladmission")
	public List<HospitalAdmission> getHospitaladmission() {
		return hospitaladmission;
	}
	public void setHospitaladmission(List<HospitalAdmission> hospitaladmission) {
		this.hospitaladmission = hospitaladmission;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patientid == null) ? 0 : patientid.hashCode());
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
		Patient other = (Patient) obj;
		if (patientid == null) {
			if (other.patientid != null)
				return false;
		} else if (!patientid.equals(other.patientid))
			return false;
		return true;
	}
	
	/* Rest Resource URL*/
	public static String BASE_URL = "http://localhost:8080/MSD-S3/data/patients/";
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getPatientid();
        return new AtomLink(restUrl, "get-patient");
    }	
	
	public void setLink(AtomLink link){}
	
}
