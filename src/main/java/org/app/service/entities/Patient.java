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
private int patientID;
private int CNP;
private int socialSecurityID;
private String name;
private String surname;
private long birthdate;
private long address;
private String gender;

@OneToMany(mappedBy="currentPatient", cascade = ALL, fetch = EAGER, orphanRemoval = false)
private List<HospitalAdmission> HospitalAdmission = new ArrayList<>();

/**
 * @return the hospitalAdmission
 */
public List<HospitalAdmission> getHospitalAdmission() {
	return HospitalAdmission;
}

/**
 * @param hospitalAdmission the hospitalAdmission to set
 */
public void setHospitalAdmission(List<HospitalAdmission> hospitalAdmission) {
	HospitalAdmission = hospitalAdmission;
}

/**
 * @return the patientID
 */
public int getPatientID() {
	return patientID;
}

/**
 * @param patientID the patientID to set
 */
public void setPatientID(int patientID) {
	this.patientID = patientID;
}

/**
 * @return the cNP
 */
public int getCNP() {
	return CNP;
}

/**
 * @param cNP the cNP to set
 */
public void setCNP(int cNP) {
	CNP = cNP;
}

/**
 * @return the socialSecurityID
 */
public int getSocialSecurityID() {
	return socialSecurityID;
}

/**
 * @param socialSecurityID the socialSecurityID to set
 */
public void setSocialSecurityID(int socialSecurityID) {
	this.socialSecurityID = socialSecurityID;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @return the surname
 */
public String getSurname() {
	return surname;
}

/**
 * @param surname the surname to set
 */
public void setSurname(String surname) {
	this.surname = surname;
}

/**
 * @return the birthdate
 */
public long getBirthdate() {
	return birthdate;
}

/**
 * @param birthdate the birthdate to set
 */
public void setBirthdate(long birthdate) {
	this.birthdate = birthdate;
}

/**
 * @return the address
 */
public long getAddress() {
	return address;
}

/**
 * @param address the address to set
 */
public void setAddress(long address) {
	this.address = address;
}

/**
 * @return the gender
 */
public String getGender() {
	return gender;
}

/**
 * @param gender the gender to set
 */
public void setGender(String gender) {
	this.gender = gender;
}

@Override
public String toString() {
	return "Patient [patientID=" + patientID + ", CNP=" + CNP + ", socialSecurityID=" + socialSecurityID + ", name="
			+ name + ", surname=" + surname + ", birthdate=" + birthdate + ", address=" + address + ", gender=" + gender
			+ "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + CNP;
	result = prime * result + (int) (birthdate ^ (birthdate >>> 32));
	result = prime * result + ((gender == null) ? 0 : gender.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + patientID;
	result = prime * result + ((surname == null) ? 0 : surname.hashCode());
	return result;
}
/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Patient other = (Patient) obj;
	if (CNP != other.CNP)
		return false;
	if (birthdate != other.birthdate)
		return false;
	if (gender == null) {
		if (other.gender != null)
			return false;
	} else if (!gender.equals(other.gender))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (patientID != other.patientID)
		return false;
	if (surname == null) {
		if (other.surname != null)
			return false;
	} else if (!surname.equals(other.surname))
		return false;
	return true;
}



}
