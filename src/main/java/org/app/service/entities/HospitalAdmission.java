package org.app.service.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HospitalAdmission implements Serializable {
private static final long serialVersionUID = -9197364880790834815L;

@Id
@GeneratedValue
private int addmissionID;
private String name;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
	}
}