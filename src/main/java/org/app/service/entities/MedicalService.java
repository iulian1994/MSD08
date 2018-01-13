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

@XmlRootElement(name = "medicalservice")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class MedicalService implements Serializable {
	@Id
	//@GeneratedValue testam!!!!!!!!! ERROR
	private Integer serviceId;
	private String codeName;
	private String Description;
	private double price;

	@Temporal(TemporalType.DATE)
	private Date appliedDate;

	@ManyToOne /// trebuie lucrat aici!!
	private HospitalAdmission hospitaladmission;

	@OneToMany(mappedBy = "rel_service", cascade = ALL, fetch = EAGER)
	private List<MedicalActivity> medicalactivity = new ArrayList<>();

	// @ManyToMany(mappedBy = "medicalservices")
	// private List<Employee> employees = new ArrayList<>();

	// @OneToOne
	// @MapsId
	// private Diagnostic diagnostic;
	@XmlElement
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@XmlElement
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

//	@XmlElement
	public HospitalAdmission getHospitaladmission() {
		return hospitaladmission;
	}

	public void setHospitaladmission(HospitalAdmission hospitaladmission) {
		this.hospitaladmission = hospitaladmission;
	}

	@XmlElement
	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public MedicalService() {
		super();
	}

	@XmlElement
	public List<MedicalActivity> getMedicalactivity() {
		return medicalactivity;
	}

	public void setMedicalactivity(List<MedicalActivity> medicalactivity) {
		this.medicalactivity = medicalactivity;
	}
	@XmlElement
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	@XmlElement
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public MedicalService(Integer serviceId, String codeName, HospitalAdmission hospitaladmission,
			List<MedicalActivity> medicalactivity) {
		super();
		this.serviceId = serviceId;
		this.codeName = codeName;
		this.hospitaladmission = hospitaladmission;
		this.medicalactivity = medicalactivity;
	}

	// cu asta lucrez in agregat!!!
	public MedicalService(Integer serviceId, String codeName, Date appliedDate, HospitalAdmission hospitaladmission) {
		super();
		this.serviceId = serviceId;
		this.codeName = codeName;
		this.appliedDate = appliedDate;
		this.hospitaladmission = hospitaladmission;
	}

	// PTR TEST LA MEDICALSERVICE
	public MedicalService(Integer serviceId, String codeName) {
		super();
		this.serviceId = serviceId;
		this.codeName = codeName;
	}

	public MedicalService(Integer serviceId, String codeName, Date appliedDate, HospitalAdmission hospitaladmission,
			List<MedicalActivity> medicalactivity) {
		super();
		this.serviceId = serviceId;
		this.codeName = codeName;
		this.appliedDate = appliedDate;
		this.hospitaladmission = hospitaladmission;
		this.medicalactivity = medicalactivity;
	}

	/* Rest Resource URL */
	public static String BASE_URL = "http://localhost:8080/MSD-S3/data/medicalservices/";

	@XmlElement(name = "link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getServiceId();
		return new AtomLink(restUrl, "get-medicalservices");
	}

	public void setLink(AtomLink link) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedDate == null) ? 0 : appliedDate.hashCode());
		result = prime * result + ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result + ((hospitaladmission == null) ? 0 : hospitaladmission.hashCode());
		result = prime * result + ((medicalactivity == null) ? 0 : medicalactivity.hashCode());
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
		if (appliedDate == null) {
			if (other.appliedDate != null)
				return false;
		} else if (!appliedDate.equals(other.appliedDate))
			return false;
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
		if (medicalactivity == null) {
			if (other.medicalactivity != null)
				return false;
		} else if (!medicalactivity.equals(other.medicalactivity))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}
}