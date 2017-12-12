package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MedicalService implements Serializable {
private static final long serialVersionUID = -694004190345706168L;

@Id
@GeneratedValue
private int medicalServiceId;
private String Description;
@Temporal(TemporalType.DATE)
private Date deliveryDate;



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((Description == null) ? 0 : Description.hashCode());
	result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
	result = prime * result + ((hospitaladmission == null) ? 0 : hospitaladmission.hashCode());
	result = prime * result + medicalServiceId;
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
	if (Description == null) {
		if (other.Description != null)
			return false;
	} else if (!Description.equals(other.Description))
		return false;
	if (deliveryDate == null) {
		if (other.deliveryDate != null)
			return false;
	} else if (!deliveryDate.equals(other.deliveryDate))
		return false;
	if (hospitaladmission == null) {
		if (other.hospitaladmission != null)
			return false;
	} else if (!hospitaladmission.equals(other.hospitaladmission))
		return false;
	if (medicalServiceId != other.medicalServiceId)
		return false;
	return true;
}



public MedicalService(String description, Date deliveryDate,
		HospitalAdmission hospitaladmission) {
	super();
	Description = description;
	this.deliveryDate = deliveryDate;
	this.hospitaladmission = hospitaladmission;
}


@ManyToOne
private HospitalAdmission hospitaladmission;

}
