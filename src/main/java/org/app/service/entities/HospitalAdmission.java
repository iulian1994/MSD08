package org.app.service.entities;


import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HospitalAdmission implements Serializable {
private static final long serialVersionUID = -9197364880790834815L;

@Id
private int addmissionID;
private long admissionDate= System.currentTimeMillis();
@Temporal(TemporalType.DATE)
private Date dischargeDate;
@ManyToOne
private Patient currentPatient;
@OneToMany(mappedBy="hospitaladmission", cascade = ALL, fetch = EAGER, orphanRemoval = false)
private List<MedicalService> MedicalService = new ArrayList<>();

public int getAddmissionID() {
	return addmissionID;
}

public void setAddmissionID(int addmissionID) {
	this.addmissionID = addmissionID;
}

public long getAdmissionDate() {
	return admissionDate;
}

public void setAdmissionDate(long admissionDate) {
	this.admissionDate = admissionDate;
}

public Date getDischargeDate() {
	return dischargeDate;
}

public void setDischargeDate(Date dischargeDate) {
	this.dischargeDate = dischargeDate;
}

public List<MedicalService> getMedicalService() {
	return MedicalService;
}

public void setMedicalService(List<MedicalService> medicalService) {
	MedicalService = medicalService;
}



public Patient getCurrentPatient() {
	return currentPatient;
}

public void setCurrentPatient(Patient currentPatient) {
	this.currentPatient = currentPatient;
	}


}