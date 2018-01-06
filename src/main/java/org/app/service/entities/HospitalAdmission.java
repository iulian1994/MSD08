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
import javax.persistence.ManyToOne;
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
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="hospitaladmission") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class HospitalAdmission implements Serializable {
	@Id 
	private Integer addmissionNo;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@OneToMany(mappedBy="hospitaladmission", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<MedicalService> medicalservice = new ArrayList<>();
	@ManyToOne
	private Patient patient;
	
	public HospitalAdmission() {
		super();
	}
	public HospitalAdmission(Integer addmissionNo, Date startDate) {
		super();
		this.addmissionNo = addmissionNo;
		this.startDate = startDate;
	}
	
	//cu asta in RESTTTTTTTT
	public HospitalAdmission(Integer addmissionNo, Patient patient) {
		super();
		this.addmissionNo = addmissionNo;
		this.patient = patient;
	}
	
	//cu asta lucrez in test normal
	public HospitalAdmission(Integer addmissionNo) {
		super();
		this.addmissionNo = addmissionNo;
	}
	
	@XmlElement
	public Integer getAddmissionNo() {
		return addmissionNo;
	}
	public void setAddmissionNo(Integer addmissionNo) {
		this.addmissionNo = addmissionNo;
	}
	@XmlElement
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public List<MedicalService> getMedicalservice() {
		return medicalservice;
	}
	public void setMedicalservice(List<MedicalService> medicalservice) {
		this.medicalservice = medicalservice;
	}
	@XmlTransient
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public static String BASE_URL = Patient.BASE_URL;
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL 
				+ ((this.getPatient() != null) ? this.getPatient().getPatientid() : "")
				+ "/hospitaladmission/" 
				+ this.getAddmissionNo();
        return new AtomLink(restUrl, "get-admissions");
    }	
	public void setLink(AtomLink link){}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addmissionNo == null) ? 0 : addmissionNo.hashCode());
		result = prime * result + ((medicalservice == null) ? 0 : medicalservice.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		HospitalAdmission other = (HospitalAdmission) obj;
		if (addmissionNo == null) {
			if (other.addmissionNo != null)
				return false;
		} else if (!addmissionNo.equals(other.addmissionNo))
			return false;
		if (medicalservice == null) {
			if (other.medicalservice != null)
				return false;
		} else if (!medicalservice.equals(other.medicalservice))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
}
