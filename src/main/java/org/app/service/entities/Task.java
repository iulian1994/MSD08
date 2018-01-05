package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.InheritanceType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="task") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Task implements Serializable{

@Id
@GeneratedValue
private Integer taskID;
private String taskDescription;
private String taskType;
@Temporal(TemporalType.DATE)
private Date dueDate;
@ManyToOne
private Employee responsible;


//ptr test, dar normal trebuie aia de sus!!
public Task(Integer taskID, String taskDescription, Employee responsible) {
	super();
	this.taskID = taskID;
	this.taskDescription = taskDescription;
	this.responsible = responsible;
}
@XmlElement
public Integer getTaskID() {
	return taskID;
}

public void setTaskID(Integer taskID) {
	this.taskID = taskID;
}
@XmlElement
public String getTaskDescription() {
	return taskDescription;
}
public void setTaskDescription(String taskDescription) {
	this.taskDescription = taskDescription;
}
@XmlElement
public String getTaskType() {
	return taskType;
}
public void setTaskType(String taskType) {
	this.taskType = taskType;
}
@XmlElement
public Date getDueDate() {
	return dueDate;
}
public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
}
@XmlElement
public Employee getResponsible() {
	return responsible;
}
@XmlElementWrapper(name = "employees") @XmlElement(name = "employee")
public void setResponsible(Employee responsible) {
	this.responsible = responsible;
	}
public Task() {
	super();
	}
}
