package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Patient implements Serializable {
private static final long serialVersionUID = -8305250772026071276L;

@Id
@GeneratedValue
private Integer patientID;
private String name;

@OneToMany(mappedBy="currentPatient", cascade = ALL, fetch = EAGER, orphanRemoval = false)
private List<HospitalAdmission> HospitalAdmission = new ArrayList<>();


public int getPatientID() {
	return patientID;
}


public void setPatientID(int patientID) {
	this.patientID = patientID;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public List<HospitalAdmission> getHospitalAdmission() {
	return HospitalAdmission;
}


public void setHospitalAdmission(List<HospitalAdmission> hospitalAdmission) {
	HospitalAdmission = hospitalAdmission;
}


public Patient(int patientID, String name) {
	super();
	this.patientID = patientID;
	this.name = name;
	}


public Patient() {
	super();
	}
}
