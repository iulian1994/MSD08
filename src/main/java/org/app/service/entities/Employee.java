package org.app.service.entities;
import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = -634971356189992075L;
@Id
@GeneratedValue
private Integer eID;
private String name;
private String surname;
private String position; // eq indicative
@ManyToMany(mappedBy = "employees")
private List<MedicalService> medicalservice = new ArrayList<>();
@OneToMany(mappedBy="responsible")
protected Collection<Task> tasks;

public Collection<Task> getTasks() {
	return tasks;
}

public void setTasks(Collection<Task> tasks) {
	this.tasks = tasks;
}

public Integer geteID() {
	return eID;
}

public void seteID(Integer eID) {
	this.eID = eID;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public String getPosition() {
	return position;
}

public void setPosition(String position) {
	this.position = position;
}

public List<MedicalService> getMedicalservice() {
	return medicalservice;
}

public void setMedicalservice(List<MedicalService> medicalservice) {
	this.medicalservice = medicalservice;
	}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((eID == null) ? 0 : eID.hashCode());
	result = prime * result + ((medicalservice == null) ? 0 : medicalservice.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	result = prime * result + ((surname == null) ? 0 : surname.hashCode());
	result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
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
	Employee other = (Employee) obj;
	if (eID == null) {
		if (other.eID != null)
			return false;
	} else if (!eID.equals(other.eID))
		return false;
	if (medicalservice == null) {
		if (other.medicalservice != null)
			return false;
	} else if (!medicalservice.equals(other.medicalservice))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (position == null) {
		if (other.position != null)
			return false;
	} else if (!position.equals(other.position))
		return false;
	if (surname == null) {
		if (other.surname != null)
			return false;
	} else if (!surname.equals(other.surname))
		return false;
	if (tasks == null) {
		if (other.tasks != null)
			return false;
	} else if (!tasks.equals(other.tasks))
		return false;
	return true;
}

public Employee(Integer eID, String name) {
	super();
	this.eID = eID;
	this.name = name;
}

public Employee() {
	super();
	}
}
