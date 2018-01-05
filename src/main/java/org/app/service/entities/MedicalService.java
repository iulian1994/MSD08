package org.app.service.entities;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class MedicalService implements Serializable{
	@Id @GeneratedValue
	private Integer serviceId;
	private String codeName;
	
	@Temporal(TemporalType.DATE)
	private Date appliedDate;
	
	@ManyToOne
	private HospitalAdmission hospitaladmission;

	@OneToMany(mappedBy="medsrv",cascade = ALL, fetch=FetchType.EAGER)
	private List<MedicalTest> medicaltest = new ArrayList<>();   //aici lucrezi
	
	//@ManyToMany(mappedBy = "medicalservices")
	//private List<Employee> employees = new ArrayList<>();
	// REPARELE MAINE IN DMZ MAMI LOR
	//@OneToOne
	//@MapsId
    //private Diagnostic diagnostic;
	
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public HospitalAdmission getHospitaladmission() {
		return hospitaladmission;
	}

	public void setHospitaladmission(HospitalAdmission hospitaladmission) {
		this.hospitaladmission = hospitaladmission;
	}

	public List<MedicalTest> getMedicaltest() {
		return medicaltest;
	}

	public void setMedicaltest(List<MedicalTest> medicaltest) {
		this.medicaltest = medicaltest;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public MedicalService() {
		super();
	}

	
	public MedicalService(Integer serviceId, String codeName, HospitalAdmission hospitaladmission,
			List<MedicalTest> medicaltest) {
		super();
		this.serviceId = serviceId;
		this.codeName = codeName;
		this.hospitaladmission = hospitaladmission;
		this.medicaltest = medicaltest;
	}
	
//cu asta lucrez in agregat!!!
		public MedicalService(Integer serviceId, String codeName, Date appliedDate, HospitalAdmission hospitaladmission) {
	super();
	this.serviceId = serviceId;
	this.codeName = codeName;
	this.appliedDate = appliedDate;
	this.hospitaladmission = hospitaladmission;
}
	//PTR TEST LA MEDICALSERVICE
	public MedicalService(Integer serviceId, String codeName) {
			super();
			this.serviceId = serviceId;
			this.codeName = codeName;
		}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result + ((hospitaladmission == null) ? 0 : hospitaladmission.hashCode());
		result = prime * result + ((medicaltest == null) ? 0 : medicaltest.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
		MedicalService other = (MedicalService) obj;
		if (codeName == null) {
			if (other.codeName != null)
				return false;
		} else if (!codeName.equals(other.codeName))
			return false;
		if (hospitaladmission == null) {
			if (other.hospitaladmission != null)
				return false;
		} else if (!hospitaladmission.equals(other.hospitaladmission))
			return false;
		if (medicaltest == null) {
			if (other.medicaltest != null)
				return false;
		} else if (!medicaltest.equals(other.medicaltest))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}
	
	
	
}
