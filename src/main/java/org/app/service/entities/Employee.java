package org.app.service.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Employee implements Serializable {

	@Id
	private Integer employeeID;
	private String name;
	private String surname;
	private String position;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Employee_MEDSRV")
	private List<MedicalService> medicalservices = new ArrayList<>();

	@OneToMany(mappedBy = "responsible", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<Task> tasks = new ArrayList<>();

	public List<MedicalService> getMedicalservices() {
		return medicalservices;
	}

	public void setMedicalservices(List<MedicalService> medicalservices) {
		this.medicalservices = medicalservices;
	}

	@XmlElementWrapper(name = "tasks")
	@XmlElement(name = "task")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@XmlElement
	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@XmlElement
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
		return result;
	}

	public Employee(Integer employeeID, String name) {
		super();
		this.employeeID = employeeID;
		this.name = name;
	}

	public Employee() {
		super();
	}

	public static String BASE_URL = "http://localhost:8080/MSD-S3/data/employees/";

	@XmlElement(name = "link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getEmployeeID();
		return new AtomLink(restUrl, "get-employee");
	}

	public void setLink(AtomLink link) {
	}
}
